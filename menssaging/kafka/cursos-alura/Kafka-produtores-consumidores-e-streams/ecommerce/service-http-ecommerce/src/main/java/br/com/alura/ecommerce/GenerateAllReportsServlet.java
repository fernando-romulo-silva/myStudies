package br.com.alura.ecommerce;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;

public class GenerateAllReportsServlet extends HttpServlet {

    private final KafkaDispatcher<String> batchDispatcher = new KafkaDispatcher<>();

    @Override
    public void destroy() {
        try {
            batchDispatcher.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            final var correlationId = new CorrelationId(GenerateAllReportsServlet.class.getSimpleName());

            batchDispatcher.send("ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS", "ECOMMERCE_USER_GENERATE_READING_REPORT",
                    correlationId, "ECOMMERCE_USER_GENERATE_READING_REPORT");

            System.out.println("Sent generate reports to all users");
            resp.getWriter().print("Request generate");
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
