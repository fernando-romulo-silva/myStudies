package org.nandao.cap09.p01UsingTheRowSetFactoryClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

// Row sets can now be created using the new javax.sql.rowset package's
// RowSetFactoryInterface interface and the RowSetProvider class. This permits the
// creation of any type of row set supported by JDBC. We will use the Derby database to illustrate
// the process of creating row sets. The COLLEAGUES table will be used. A description of how to
// create this table is found at http://netbeans.org/kb/docs/ide/java-db.html. The
// SQL code to create the table is as follows:

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
        }
        
        final RowSetFactory rowSetFactory = RowSetProvider.newFactory("com.sun.rowset.RowSetFactoryImpl", null);

        try (final JdbcRowSet rowSet = rowSetFactory.createJdbcRowSet();) {
            
            rowSet.setUrl("jdbc:derby:contact");

            // rowSet.setUsername(username);
            // rowSet.setPassword(password);

            rowSet.setCommand("SELECT * FROM COLLEAGUES");

            rowSet.execute();
            
            while (rowSet.next()) {
                System.out.println(rowSet.getInt("ID") + " - " + rowSet.getString("FIRSTNAME"));
            }
        }

    }
}
