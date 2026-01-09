package br.com.alura.ecommerce;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.alura.ecommerce.consumer.ConsumerService;
import br.com.alura.ecommerce.consumer.ServiceRunner;

public class CreateUserService implements ConsumerService<Order> {

    private LocalDatabase localDatabase;

    public static void main(String[] args) {
        new ServiceRunner<>(CreateUserService::new).starts(1);
    }

    CreateUserService() {
        try {
            this.localDatabase = new LocalDatabase("users_database");
            this.localDatabase
                    .createIfNotExists("create table Users (uuid varchar(200) primary key, email vachar(200))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getTopic() {
        return "ECOMMERCE_NEW_ORDER";
    }

    @Override
    public String getConsumerGroup() {
        return CreateUserService.class.getSimpleName();
    }

    public void parse(ConsumerRecord<String, Message<Order>> record)
            throws InterruptedException, ExecutionException, SQLException {
        System.out.println("---------------------");
        System.out.println("Processing new order, checking for new user");
        final var message = record.value();

        final var id = message.getId();
        System.out.println(id);
        final var order = message.getPayload();

        if (isNewUser(order.getEmail())) {
            insertNewUser(order.getEmail());
        }
    }

    private void insertNewUser(String email) throws SQLException {
        localDatabase.update("insert into Users (uuid, email) values (?, ?)", UUID.randomUUID().toString(), email);
    }

    private boolean isNewUser(String email) throws SQLException {
        System.out.println("Inserting new user " + email);
        final var results = localDatabase.query("""
                select uuid
                  from Users
                 where email = ?
                 limit 1
                """, email);
        return !results.next();
    }

}
