package br.com.alura.ecommerce;

import java.sql.SQLException;

public class OrdersDatabase implements AutoCloseable {

    private LocalDatabase database;

    public OrdersDatabase() {
        try {
            this.database = new LocalDatabase("orders_database");
            this.database
                    .createIfNotExists("create table Orders (uuid varchar(200) primary key)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean saveNew(Order order) throws SQLException {

        if (wasProcessed(order)) {
            return false;
        }

        database.update("insert into Orders (uuid) values (?) ", order.getOrderId());
        return true;
    }

    private boolean wasProcessed(Order order) throws SQLException {
        final var results = database.query("select uuid from Orders where uuid = ? limit 1", order.getOrderId());
        return results.next();
    }

    @Override
    public void close() throws Exception {
        database.close();
    }
}
