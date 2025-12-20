package br.com.alura.ecommerce;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.alura.ecommerce.consumer.ConsumerService;
import br.com.alura.ecommerce.consumer.ServiceRunner;

public class ReadingReportService implements ConsumerService<User> {

    private static final Path SOURCE = new File("/service-reading-report/src/main/resources/report.txt").toPath();

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        new ServiceRunner<>(ReadingReportService::new).starts(5);
    }

    @Override
    public void parse(final ConsumerRecord<String, Message<User>> record) {
        System.out.println("---------------------");
        System.out.println("Processing report for " + record.value());

        final var user = record.value().getPayload();
        final var target = new File(user.getReportPath());

        try {
            IO.copyTo(SOURCE, target);
            IO.append(target, "Create for " + user.getUuid());
        } catch (IOException e) {
        }

        System.out.println("File created: " + target.getAbsolutePath());
    }

    @Override
    public String getTopic() {
        return "ECOMMERCE_USER_GENERATE_READING_REPORT";
    }

    @Override
    public String getConsumerGroup() {
        return ReadingReportService.class.getSimpleName();
    }
}
