package br.com.alura.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocalDatabase {

    private final Connection connection;

    public LocalDatabase(final String name) throws SQLException {
        final var url = "jdbc:sqlite:target/" + name + ".db";
        this.connection = DriverManager.getConnection(url);
    }

    public void createIfNotExists(String sql) throws SQLException {
        connection.createStatement().execute(sql);
    }

    public void update(String statement, String... params) throws SQLException {
        final var preparedStatement = getPreparedStatement(statement, params);
        preparedStatement.execute();
    }

    public ResultSet query(String sql, String... params) throws SQLException {
        final var preparedStatement = getPreparedStatement(sql, params);
        return preparedStatement.executeQuery();
    }

    private PreparedStatement getPreparedStatement(String statement, String... params) throws SQLException {
        final var preparedStatement = connection.prepareStatement(statement);

        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement;
    }

    public void close() throws SQLException {
        connection.close();
    }

}
