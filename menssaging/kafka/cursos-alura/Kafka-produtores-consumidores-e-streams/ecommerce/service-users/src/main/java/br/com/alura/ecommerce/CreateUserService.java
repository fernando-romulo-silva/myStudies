package br.com.alura.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.alura.ecommerce.consumer.ConsumerService;
import br.com.alura.ecommerce.consumer.ServiceRunner;

public class CreateUserService implements ConsumerService<Order> {

    public static void main(String[] args) {
        new ServiceRunner<>(CreateUserService::new).starts(1);
    }

    private final Connection connection;

    @Override
    public String getTopic() {
        return "ECOMMERCE_NEW_ORDER";
    }

    @Override
    public String getConsumerGroup() {
        return CreateUserService.class.getSimpleName();
    }

    private CreateUserService() throws SQLException {
        final var url = "jdbc:sqlite:target/users_database.db";
        this.connection = DriverManager.getConnection(url);

        try {
            connection.createStatement().execute("""
                    create table Users (uuid varchar(200) primary key, email vachar(200))
                    """);
        } catch (SQLException ex) {
        }
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
        final var insert = connection.prepareStatement("insert into Users (uuid, email) values (?, ?)");

        insert.setString(1, UUID.randomUUID().toString());
        insert.setString(2, email);

        insert.execute();
    }

    private boolean isNewUser(String email) throws SQLException {
        System.out.println("Inserting new user " + email);
        final var exists = connection.prepareStatement("""
                select uuid
                  from Users
                 where email = ?
                 limit 1
                """);

        exists.setString(1, email);
        final var results = exists.executeQuery();

        return !results.next();
    }
}
