package br.fernando.ch12_JDBC.part02_Submit_Queries_Read_Results_From_Database;

import static br.fernando.ch12_JDBC.DataBaseConst.LOGIN;
import static br.fernando.ch12_JDBC.DataBaseConst.NAME;
import static br.fernando.ch12_JDBC.DataBaseConst.PASSWORD;
import static br.fernando.ch12_JDBC.DataBaseConst.PORT;
import static br.fernando.ch12_JDBC.DataBaseConst.URL;
import static java.lang.System.out;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.myembedded.rdbms.MyEmbeddedRdbms;

public class Test02 {

    // ==========================================================================================================================================
    //
    // Getting Information about a ResultSet
    // What happens when you want to allow your users to dynamically construct the query?
    // You may not always know in advance how many columns are returned and the type and name of the columns returned.
    //
    // Using ResultSetMetaData, you can get important information about the results returned from the query,
    // including the number of columns, the table name, the column name, and the column class name—the Java class
    // that is used to represent this column when the column is returned as an Object
    public static void test01() {
        final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms();

        myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
        myEmbeddedRdbms.start(PORT);

        myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

        try {
            final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            final Statement stmt = connection.createStatement();

            final String query = "SELECT AuthorID FROM Author";

            // ------------
            final ResultSet rs01 = stmt.executeQuery(query);

            final ResultSetMetaData resmd = rs01.getMetaData();

            rs01.next();

            int colCount = resmd.getColumnCount(); // How many columns in this ResultSet?
            // It returns the integer count of the number of columns returned by the query.

            out.println("Coulumn Count: " + colCount);

            for (int i = 0; i < colCount; i++) {
                // The method returns the String name of the table that this column belongs to. This method is useful
                // when the query is a join of two or more tables and we need to know which table a column came from
                out.print("Table Name: " + resmd.getTableName(i));

                // This method returns the String name of this column.
                out.print("Column Name: " + resmd.getColumnName(i));

                // This method returns an integer of the size of the column. This information is useful for determining
                // the maximum number of characters a column can hold (if it is a VARCHAR type) and the spacing that is
                // required between columns for a report.
                out.print("Column Size: " + resmd.getColumnDisplaySize(i));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        myEmbeddedRdbms.stop();
    }

    // ==========================================================================================================================================
    //
    // Moving Around in ResultSets
    //
    // The default characteristics of a Statement are cursors that only move forward and result sets that do not support changes. The ResultSet interface actually
    //
    // Let’s look at how to modify a result set in more detail. There are three ResultSet cursor types:
    //
    // * TYPE_FORWARD_ONLY : The default value for a ResultSet—the cursor moves forward only through a set of results.
    //
    // * TYPE_SCROLL_INSENSITIVE : A cursor position can be moved in the result forward or backward, or positioned to a particular cursor location.
    // Any changes made to the underlying data—the database itself—are not reflected in the result set. In other words, the result set does not
    // have to “keep state” with the database. This type is generally supported by databases.
    //
    // * TYPE_SCROLL_INSENSITIVE : A cursor can be moved in the results forward or backward, or positioned to a particular cursor location. Any changes made to
    // the underlying data are reflected in the open result set. As you can imagine, this is difficult to implement and is, therefore, not implemented in a database
    // or JDBC driver very often.
    //
    // JDBC provides two options for data concurrency with a result set:
    //
    // * CONCUR_READ_ONLY : This is the default value for result set concurrency. Any open result set is read-only and cannot be modified or changed.
    //
    // * CONCUR_UPDATABLE : A result set can be mod ified through the ResultSet methods while the result set is open.
    //
    // Because a database and JDBC driver are not required to support cursor movement and concurrent updates, the JDBC provides methods to query the database and driver
    // using the DatabaseMetaData object to determine if your driver supports these capabilities
    //
    //
    public static void test02() {

        try (final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms()) {

            myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
            myEmbeddedRdbms.start(PORT);

            myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

            try (final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {

                DatabaseMetaData dbmd = connection.getMetaData();

                // ------------------------------------------------------------------------

                if (dbmd.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)) {

                    out.print("Supports TYPE_FORWARD_ONLY");

                    if (dbmd.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
                        out.println(" and supports CONCUR_UPDATABLE");
                    }
                }

                // ------------------------------------------------------------------------

                if (dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)) {

                    out.print("Supports TYPE_SCROLL_INSENSITIVE");

                    if (dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                        out.println(" and supports CONCUR_UPDATETABLE");
                    }
                }

                // ------------------------------------------------------------------------

                if (dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)) {

                    out.print("Supports TYPE_SCROLL_SENSITIVE");

                    if (dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                        out.println(" and supports CONCUR_UPDATABLE");
                    }
                }

                // You can determine what cursor type and concurrency the Statement was created with, but once created, you can’t change the cursor
                // type or concurrency of an existing Statement object.
                //
                final Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                //
                // As you will see in the section on exceptions, the driver can determine that the database doesn’t support one or both of
                // the settings you chose and it will throw a warning and (silently) revert to its default settings
                //
                // Also, note that just because you set a cursor type or concurrency setting, that doesn’t mean you will get those settings.
                // As you will see in the section on exceptions, the driver can determine that the database doesn’t support one or both of
                // the settings you chose and it will throw a warning and (silently) revert to its default settings, if they are not supported.
                // You will see how to detect these JDBC warnings in the section on exceptions and warnings.
                //
                // Besides being able to use a ResultSet object to update results, which we’ll look at next, being able to manipulate the cursor
                // provides a side benefit—we can use the cursor to determine the number of rows returned in a query.

                final ResultSet resultSet = statement.executeQuery("SELECT * FROM Author"); // Get a ResultSet

                if (resultSet.last()) { // Move the very last row
                    int rowCount = resultSet.getRow(); // Get row number (the count)

                    resultSet.beforeFirst();// move to before the 1st row
                }
                //
                // public boolean absolute(int row) throws SQLException : 
                // This method positions the cursor to an absolute row number. The contrasting method is relative. 
                // Passing 0 as the row argument positions the cursor to before the first row. 
                // Passing a negative value, like -1, positions the cursor to the position after the last row minus one—in other words, the last row. 
                //
                // public int getRow() throws SQLException : 
                // This method returns the current row position as a positive integer (1 for the first row, 2 for the second, and so on) or 0 if
                // there is no current row—the cursor is either before the first row or after the last row.
                // 
                // public boolean relative(int rows) throws SQLException :
                // The relative() will position the cursor either before or after the current position of the number of rows passed in to the
                // method. So if the cursor is on row 15 of a 30-row ResultSet, calling relative(2) will position the cursor to row 17, and 
                // then calling relative(-5) positions the cursor to row 12
                //
                // public boolean previous() throws SQLException
                // The previous() method works exactly the same as the next() method, only it backs up through the ResultSet. 
                // Using this method with the afterLast() method described next, you can move through a ResultSet in reverse order (from last row to first).
                // 
                // public void afterLast() throws SQLException
                // This method positions the cursor after the last row. Using this method and then the previous() method, you can iterate
                // through a ResultSet in reverse.
                //
                // public void beforeFirst() throws SQLException
                // This method will return the cursor to the position it held when the ResultSet was first created and returned by a Statement object.
                //
                // public boolean first() throws SQLException
                // The first() method positions the cursor on the first row. It is the equivalent of calling absolute(1). 
                // This method returns true if the cursor was moved to a valid row and false if the ResultSet has no rows.
                //
                // public boolean last() throws SQLException
                // The last() method positions the cursor on the last row. This method is the equivalent of calling absolute(-1). 
                // This method returns true if the cursor was moved to a valid row and false if the ResultSet has no rows.
                //
                // 1107
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // ====================================================================================================================================
    //
    public static void main(String[] args) {
        test02();
    }
}
