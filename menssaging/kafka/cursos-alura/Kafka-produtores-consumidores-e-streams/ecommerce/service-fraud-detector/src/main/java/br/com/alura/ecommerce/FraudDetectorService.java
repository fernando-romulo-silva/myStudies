package br.com.alura.ecommerce;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.alura.ecommerce.consumer.ConsumerService;
import br.com.alura.ecommerce.consumer.ServiceRunner;
import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;

public class FraudDetectorService implements ConsumerService<Order> {

    public static void main(String[] args) {
        new ServiceRunner<>(FraudDetectorService::new).starts(1);
    }

    private LocalDatabase localDatabase;
    private final KafkaDispatcher<Order> ordeDispatcher = new KafkaDispatcher<>();

    FraudDetectorService() {
        try {
            this.localDatabase = new LocalDatabase("frauds_database");
            this.localDatabase
                    .createIfNotExists("create table Orders (uuid varchar(200) primary key, is_fraud boolean)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void parse(final ConsumerRecord<String, Message<Order>> record) throws Exception {
        System.out.println("---------------------");
        System.out.println("Processing new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final var message = record.value();
        final var order = message.getPayload();
        final var amount = order.getAmount();
        final var correlationId = message.getId().continueWith(FraudDetectorService.class.getSimpleName());

        if (wasProcessed(order)) {
            System.out.println("Order " + order.getOrderId() + " was already processed");
            return;
        }

        if (isFraud(amount)) {
            // pretending that the fraud happens when the amount is >= 4500
            System.out.println("Order is a fraud: " + order);
            ordeDispatcher.send("ECOMMERCE_ORDER_REJECTED", order.getEmail(), correlationId, order);

            localDatabase.update("insert into Orders (uuid, is_fraud) values (?, true) ", order.getOrderId());
        } else {
            System.out.println("Approved: " + order);
            ordeDispatcher.send("ECOMMERCE_ORDER_APPROVED", order.getEmail(), correlationId, order);

            localDatabase.update("insert into Orders (uuid, is_fraud) values (?, false) ", order.getOrderId());
        }
    }

    private boolean wasProcessed(Order order) throws SQLException {
        final var results = localDatabase.query("select uuid from Orders where uuid = ? limit 1", order.getOrderId());
        return results.next();
    }

    private boolean isFraud(final BigDecimal amount) {
        return amount.compareTo(new BigDecimal("4500")) >= 0;
    }

    @Override
    public String getTopic() {
        return "ECOMMERCE_NEW_ORDER";
    }

    @Override
    public String getConsumerGroup() {
        return FraudDetectorService.class.getSimpleName();
    }
}
