package br.com.alura.ecommerce;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.alura.ecommerce.consumer.KafkaService;
import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;

public class BatchSendMessageService {

    public static void main(String[] args) throws SQLException, IOException, InterruptedException, ExecutionException {

        final var batchSendMessageService = new BatchSendMessageService();
        try (final var service = new KafkaService<>(
                "ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS",
                batchSendMessageService::parse,
                BatchSendMessageService.class.getSimpleName(), Map.of())) {
            service.run();
        }
    }

    private final Connection connection;

    private final KafkaDispatcher<User> userDispatcher = new KafkaDispatcher<>();

    BatchSendMessageService() throws SQLException {
        final var url = "jdbc:sqlite:target/users_database.db";
        this.connection = DriverManager.getConnection(url);

        try {
            connection.createStatement().execute("""
                    create table Users (uuid varchar(200) primary key, email vachar(200))
                    """);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void parse(final ConsumerRecord<String, Message<String>> record)
            throws InterruptedException, ExecutionException, SQLException {
        System.out.println("---------------------");
        System.out.println("Processing new batch");
        final var message = record.value();

        System.out.println("Topic: " + message.getPayload());

        for (final var user : getAllUsers()) {
            System.out.println("User: " + user);
            final var correlationId = message.getId().continueWith(BatchSendMessageService.class.getSimpleName());
            userDispatcher.sendAsync(message.getPayload(), user.getUuid(), correlationId, user);
            System.out.println("Envie para: " + user);
        }
    }

    private List<User> getAllUsers() throws SQLException {

        final var results = connection.prepareStatement("""
                select uuid
                  from Users
                """).executeQuery();

        final var users = new ArrayList<User>();

        while (results.next()) {
            users.add(new User(results.getString(1)));
        }
        return users;
    }
}
