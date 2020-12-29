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
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.logging.Logger;

import org.myembedded.rdbms.MyEmbeddedRdbms;

public class Test05 {

    // ==========================================================================================================================================
    // When Things Go Wrong—Exceptions and Warnings
    //
    // Let’s look at the type of data you get from a SQLException through its methods.
    //
    public static void test01_01() {

        try (final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms()) {

            myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
            myEmbeddedRdbms.start(PORT);
            myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

            final Logger logger = Logger.getLogger(Test05.class.getName());

            try (final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {

            } catch (final SQLException ex) {

                SQLException ex2 = ex;

                while (ex2 != null) {
                    logger.severe("------------------- SQLException -----------------");
                    logger.severe("SQL State: " + ex2.getSQLState());
                    logger.severe("Vendor error code: " + ex2.getErrorCode());
                    logger.severe("Message: " + ex2.getMessage());

                    ex2.getNextException();
                }
            }
        }
    }

    // * public String getMessage()
    // This method is actually inherited from java.lang.Exception , which SQLException extends from. This method returns the
    // detailed reason why the exception was thrown. Often, the message contents SQLState and error code provide specific information about what went wrong.
    //
    // * public String getSQLState()
    // The String returned by getSQLState provides a specific code and related message. SQLState messages are defined by the X/Open and
    // SQL:2003 standards; however, it is up to the implementation to use these values.
    // You can determine which standard your JDBC driver uses (or if it does not) through the DatabaseMetaData.getSQLStateType() method.
    //
    // public int getErrorCode()
    // Error codes are not defined by a standard and are thus implementation specific.
    // They can be used to pass an actual error code or severity level, depending on the implementation.
    //
    // public SQLException getNextException()
    // One of the interesting aspects of SQLException is that the exception thrown could be the result of more than one issue.
    // Fortunately, JDBC simply tacks each exception onto the next in a process called chaining.
    // Typically, the most severe exception is thrown last, so it is the first exception in the chain.
    //
    // ==========================================================================================================================================
    // Warnings
    // Although SQLWarning is a subclass of SQLException , warnings are silently chained to the JDBC object that reported them.
    // The reason is that a warning is not an exception and Warnings can be reported on Connection , Statement , and ResultSet objects.
    public static void test01_02() {

        try (final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms()) {

            myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
            myEmbeddedRdbms.start(PORT);
            myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

            // Connection objects will add warnings (if necessary) until the Connection is closed
            // or until the clearWarnings() method is called on the Connection instance. The
            // clearWarnings() method sets the list of warnings to null until another warning is
            // reported for this Connection object.

            try (final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {

                // Statement s and ResultSet s also generate SQLWarnings, and these objects have
                // their own clearWarnings() methods. Statement warnings are cleared automatically
                // when a statement is reexecuted, and ResultSet warnings are cleared each time a new
                // row is read from the result set.

                final Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                final String query = "SELECT * FROM Book WHERE Format = 'Hardcover'";

                final ResultSet rs = stmt.executeQuery(query);

                SQLWarning warn = connection.getWarnings(); // get any SQLWarnings

                while (warn != null) {
                    out.println("SQL State: " + warn.getSQLState());
                    out.println("SQL Message: " + warn.getMessage());

                    warn = warn.getNextWarning(); // get the next warning
                }

            } catch (final SQLException ex) {

            }
        }
    }
    // The following sections summarize the methods associated with SQLWarnings
    //
    // * SQLWarning getWarnings() throws SQLException
    // This method gets the first SQLWarning object or returns null if there are no warnings for this Connection ,
    // Statement, or ResultSet object. A SQLException is thrown if the method is called on a closed object.
    //
    // * void clearWarnings() throws SQLException
    // This method clears and resets the current set of warnings for this Connection , Statement, or ResultSet object.
    // A SQLException is thrown if the method is called on a closed object.
    //
}
