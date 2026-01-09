package br.com.alura.ecommerce;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;

public class NewOrderServlet extends HttpServlet {

    private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();

    @Override
    public void destroy() {
        try {
            orderDispatcher.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            final var orderId = req.getParameter("uuid"); // UUID.randomUUID().toString();
            final var email = req.getParameter("email");
            final var amount = new BigDecimal(req.getParameter("amount"));
            final var order = new Order(orderId, amount, email);
            final var correlationId = new CorrelationId(NewOrderServlet.class.getSimpleName());

            try (final var database = new OrdersDatabase();) {
                if (database.saveNew(order)) {

                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, correlationId, order);

                    System.out.println("New order sent sucessfully!");
                    resp.getWriter().print("new order sent");
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    System.out.println("Old order received");
                    resp.getWriter().print("Old order received");
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
            }

        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
