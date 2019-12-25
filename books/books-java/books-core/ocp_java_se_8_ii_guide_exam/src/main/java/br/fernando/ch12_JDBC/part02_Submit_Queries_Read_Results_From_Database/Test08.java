package br.fernando.ch12_JDBC.part02_Submit_Queries_Read_Results_From_Database;

import static br.fernando.ch12_JDBC.DataBaseConst.LOGIN;
import static br.fernando.ch12_JDBC.DataBaseConst.NAME;
import static br.fernando.ch12_JDBC.DataBaseConst.PASSWORD;
import static br.fernando.ch12_JDBC.DataBaseConst.PORT;
import static br.fernando.ch12_JDBC.DataBaseConst.URL;
import static java.lang.System.out;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.myembedded.rdbms.MyEmbeddedRdbms;

public class Test08 {

    // ==========================================================================================================================================
    // Properly Closing SQL Resources 1128
    //
    // What we have not done so far is properly close these resources. The reality is that it is probably less important for such small examples, but
    // for any code that uses a resource, like a socket, or a file, or a JDBC database connection, closing the open resources is a good practice.
    public static void test01_01() {

        try (final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms()) {

            myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
            myEmbeddedRdbms.start(PORT);
            myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

            Connection connection = null;

            try {

                connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

                final Statement stmt = connection.createStatement();

                final ResultSet rs = stmt.executeQuery("SELECT * FROM Customer");

                while (rs.next()) {
                    // process something
                }

                if (rs != null && stmt != null) {
                    rs.close(); // Attempt to close the ResultSet
                    stmt.close(); // Attempt to close the Statement
                }

            } catch (final SQLException ex) {
                out.println(ex);
            } finally {
                try {

                    if (connection != null) {
                        connection.close(); // close the connection
                    }
                } catch (SQLException e) {
                    // TODO: handle exception
                }
            }
        }
    }
    // Connection.close()
    // Releases the connection to the database: Closes any open ResultSet associated with this Statement.
    //
    // Statement.close()
    // Releases this Statement resource. Closes any open ResultSet associated with this Statement
    //
    // ResultSet.close()
    // Releases this ResultSet resource
    //
    // Statement.executeXXXX()
    // Any ResultSet assciated with a previous Statement execution is automatically closed.
    //
    // ==========================================================================================================================================
    // Properly Closing SQL Resources 1128
    //
    // This language change is called try -with-resources, and its longer name belies how much simpler it makes writing code with resources that should
    // be closed. The try -with-resources statement will automatically call the close() method on any resource declared in the parentheses at the end of the try block.
    // There is a caveat: A resource declared in the try -with-resource statement must implement the AutoCloseable interface.

    public static void test01_02() {

        try (final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms()) {

            myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
            myEmbeddedRdbms.start(PORT);
            myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

            try (final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);) {

                try (final Statement stmt = connection.createStatement()) {

                    try (final ResultSet rs = stmt.executeQuery("SELECT * FROM Customer")) {

                        while (rs.next()) {
                            // process something
                        }
                    }
                }

            } catch (final SQLException ex) {
                out.println(ex);
            }

            // The try -with-resources can also be used with multiple resources, so you could
            // include the Statement declaration in the try as well:
            try (final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD); //
                    final Statement stmt = connection.createStatement(); //
                    final ResultSet rs = stmt.executeQuery("SELECT * FROM Customer"); //
            ) {

                // Note that when more than one resource is declared in the try -with-resources
                // statement, the resources are closed in the reverse order of their declaration—so
                // stmt.close() will be called first, followed by conn.close()

                while (rs.next()) {
                    // process something
                }

            } catch (final SQLException ex) {
                // Anyexceptions thrown as a result of closing resources at the end of the try block are
                // suppressed, if there was also an exception thrown in the try block. These exceptions
                // can be retrieved from the exception thrown by calling the getSuppressed() method on
                // the exception thrown.

                out.println(ex);

                final Throwable[] suppressed = ex.getSuppressed(); // Get an array of suppressed exceptions

                for (final Throwable throwable : suppressed) {
                    out.println("Suppressed exception: " + throwable);
                }
            }
        }
    }
}
