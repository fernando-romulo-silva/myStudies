package org.nandao.cap09.p02Java7DatabaseEnhancements;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

// There are numerous small enhancements to the database support provided by Java 7. This
// recipe addresses these enhancements and provides examples where practical. Due to the
// immaturity of many JDBC 4.1 drives, not all of the code examples will be completely functional.
public class Test {

    static String getTable() {
        final StringBuilder sb = new StringBuilder();

        sb.append(" CREATE TABLE COLLEAGUES (         ");
        sb.append("  ID INTEGER not null primary key, ");
        sb.append("  FIRSTNAME VARCHAR(30),           ");
        sb.append("  LASTNAME VARCHAR(30),            ");
        sb.append("  TITLE VARCHAR(10),               ");
        sb.append("  DEPARTMENT VARCHAR(20),          ");
        sb.append("  EMAIL VARCHAR(60)                ");
        sb.append(" )                                ");

        return sb.toString();
    }

    static String getInserts() {
        final StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO COLLEAGUES VALUES (1, 'Mike','Johnson','Manager','Engineering','mike.johnson@foo.com');");
        sb.append("INSERT INTO COLLEAGUES VALUES (2, 'James', 'Still', 'Engineer', 'Engineering', 'james.still@foo.com');");
        sb.append("INSERT INTO COLLEAGUES VALUES (3, 'Jerilyn', 'Stall', 'Manager', 'Marketing', 'jerilyn.stall@foo.com');");
        sb.append("INSERT INTO COLLEAGUES VALUES (4, 'Jonathan', 'Smith', 'Manager', 'Marketing', 'jonathan.smith@foo.com');");

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String databaseUrl = "jdbc:derby:memory:contact;create=true";

        // from the createConnection method
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

        try (final Connection connection = DriverManager.getConnection(databaseUrl); final Statement statement = connection.createStatement();) {

            statement.executeUpdate(getTable());

            String[] inserts = getInserts().split(";");

            for (String insert : inserts) {
                statement.executeUpdate(insert);
            }

            DatabaseMetaData metaData = connection.getMetaData();
            String schemma = connection.getSchema();

            System.out.println("Schema: " + schemma);
            System.out.println("Auto Generated Keys: " + metaData.generatedKeyAlwaysReturned());

            final ResultSet resultSet = metaData.getPseudoColumns("", "schemaName", "tableName", "");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("TABLE_SCHEM ") + " - " + resultSet.getString("COLUMN_NAME "));
            }

            // not work, because derby database don't have stored procedure
            Driver driver = DriverManager.getDriver("jdbc:derby://localhost:1527");
            System.out.println("Parent Logger" + driver.getParentLogger());

            // not work, because derby database don't have stored procedure
            String query = "{CALL GETDATE(?,?)}";

            CallableStatement callableStatement = (CallableStatement) connection.prepareCall(query);
            callableStatement.setInt(1, 1000);
            callableStatement.registerOutParameter(1, Types.DATE);
            callableStatement.executeQuery();
            final Date date = callableStatement.getObject(2, Date.class);

            System.out.println("Date:" + date);
        }
    }

}
