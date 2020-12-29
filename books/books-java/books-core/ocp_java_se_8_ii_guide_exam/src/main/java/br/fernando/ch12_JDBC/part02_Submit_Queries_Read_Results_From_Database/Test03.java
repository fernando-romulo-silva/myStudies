package br.fernando.ch12_JDBC.part02_Submit_Queries_Read_Results_From_Database;

import static br.fernando.ch12_JDBC.DataBaseConst.LOGIN;
import static br.fernando.ch12_JDBC.DataBaseConst.NAME;
import static br.fernando.ch12_JDBC.DataBaseConst.PASSWORD;
import static br.fernando.ch12_JDBC.DataBaseConst.PORT;
import static br.fernando.ch12_JDBC.DataBaseConst.URL;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.myembedded.rdbms.MyEmbeddedRdbms;

public class Test03 {

    // ==========================================================================================================================================
    //
    // A couple of notes on the exceptions thrown by all of these methods:
    //
    // * A SQLException will be thrown by these methods if the type of the ResultSet is TYPE_FORWARD_ONLY, if the ResultSet is closed
    // (we will look at how a result set is closed in an upcoming section), or if a database error occurs.
    //
    // * A SQLFeatureNotSupportedException will be thrown by these methods if the JDBC driver does not support the method. This exception is a
    // subclass of SQLException.
    //
    // * Most of these methods have no effect if the ResultSet has no rows—for example, a ResultSet returned by a query that returned no rows.
    //
    //
    // The following methods return a boolean to allow you to “test” the current cursor position without moving the cursor.
    //
    // # isBeforeFirst() True if the cursor is positioned before the first row
    // # isAfterLast() True if the cursor is positioned after the last row
    // # isFirst() True if the cursor is on the first row
    // # isLast() True if the cursor is on the last row
    //
    public static int getRowCount(ResultSet rs) throws SQLException {
        int rowCount = -1;
        int currRow = 0;

        if (rs != null) { // make sure the ResultSet is not null
            currRow = rs.getRow(); // save the current row position, zero indicates that there is no current row position - could be beforeFirst or afterLast

            if (rs.isAfterLast()) { // AfterLast, so set the currRow negative
                currRow = -1;
            }

            if (rs.last()) { // move to the last row and get the position if this method returns false, three are no results

                rowCount = rs.getRow(); // get the row count, return the cursor to the position it was before the method was called.

                if (currRow == -1) { // if the currRow is negative, the cursor position was after the last row, so return the cursor to the last row
                    rs.afterLast();

                } else if (currRow == 0) { // else if the cursor is zero, move the cursor to before the first row.

                    rs.beforeFirst();

                } else { // else return the cursor to its last position
                    rs.absolute(currRow);
                }
            }
        }

        return rowCount;
    }

    // ==========================================================================================================================================
    //
    // Updating ResultSets
    // In a traditional SQL application, you might perform the following SQL queries to raise the price of all of the hardcover books in inventory
    // that are currently 10.95 to 11.95 in price:
    public static void test02_01() {
        try (final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms()) {

            myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
            myEmbeddedRdbms.start(PORT);

            myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

            try (final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {

                final Statement stmt = connection.createStatement();

                final String dml = "UPDATE Book SET UnitPrice = 11.95 WHERE UnitPrice = 10.95 AND Format = 'Hardcover'";

                int rowsUpdated = stmt.executeUpdate(dml);

                System.out.println("Rows updated: " + rowsUpdated);

                // But what if you wanted to do the updates on a book-by-book basis?
                // What if you only want to increase the price of your bestsellers, rather than every single book?

            }

        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void test02_02() {
        try (final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms()) {

            myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
            myEmbeddedRdbms.start(PORT);

            myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

            try (final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {

                // Scrollable and updatable
                final Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                final String dml = "UPDATE Book SET UnitPrice = 11.95 WHERE Format = 'Hardcover'";

                int rowsUpdated = stmt.executeUpdate(dml);

                final String query = "SELECT UnitPrice FROM Book WHERE Format = 'Hardcover'";

                final ResultSet rs = stmt.executeQuery(query); // populate the ResulSet

                while (rs.next()) {
                    // Check each row: if unitPrice = 10.95
                    if (rs.getFloat("UnitPrice") == 10.95f) {
                        // set it to 11.95
                        rs.updateFloat("UnitPrice", 11.95f); // Use the appropriate updateXXX() method
                        // and update the row in the database
                        rs.updateRow();
                    }
                }
            }

            // Notice that after modifying the value of UnitPrice using the updateFloat()
            // method, we called the method updateRow(). This method writes the current row to the
            // database. This two-step approach ensures that all of the changes are made to the row
            // before the row is written to the database.
            // And you can change your mind with a cancelRowUpdates() method call.

        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Let’s look at the common methods used for altering database contents through the ResultSet in detail.

    // * public void updateRow() throws SQLException
    // This method updates the database with the contents of the current row of the ResultSet
    // There are a couple of caveats for this method:
    // First, the ResultSet must be from a SQL SELECT statement on asingle table—a SQL statement
    // that includes a JOIN or a SQL statement with two tables cannot be updated.
    // Second, the updateRow() method should be called before moving to the next row.
    //
    // * public boolean rowUpdated() throws SQLException
    // This method returns true if the current row was updated. Note that not all databases can detect updates.
    // The JDBC provides a method in DatabaseMetaData to determine if updates are detectable, DatabaseMetaData.updatesAreDetected(int type)
    //
    // * public void cancelRowUpdates() throws SQLException
    // This method allows you to “back out” changes made to the row. This method is important, because the
    // updateXXXX methods should not be called twice on the same column. In other words, if you set the value of UnitPrice to 11.95 in
    // the previous example and then decided to switch the price back to 10.95, calling the updateFloat() method again can lead to
    // unpredictable results. So the better approach is to call cancelRowUpdates() before changing the value of a column a second time.

    // Delete a Row with ResultSet

    public static void test02_03() {

        try (final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms()) {

            myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
            myEmbeddedRdbms.start(PORT);

            myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

            final int type = ResultSet.TYPE_SCROLL_INSENSITIVE;

            try (final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {

                // get meta data about the driver and DB
                final DatabaseMetaData dmbd = connection.getMetaData();

                final Statement stmt = connection.createStatement(type, ResultSet.CONCUR_UPDATABLE);

                final String query = "SELECT * FROM Book WHERE ISBN = '068983434'";

                final ResultSet rs = stmt.executeQuery(query);

                rs.last(); // 
                rs.deleteRow(); // delete the last row

                rs.beforeFirst();

                // Returns false if deleted rows are removed from the ResultSet
                if (!dmbd.deletesAreDetected(type)) {

                    while (rs.next()) {

                        // deleted rows are falgged, but not removed, so skip them
                        if (rs.rowDeleted()) {
                            continue;
                        } else {
                            // process the row
                        }
                    }
                }

            } catch (final SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    //
    // * public void deleteRow() throws SQLException
    // This method will remove the current row from the ResultSet and from the underlying database. The row in the
    // database is removed (similar to the result of a DELETE statement).
    // The DatabaseMetaData interface can be used to determine if the ResultSet can detect deletions
    // Deleting the current row does not move the cursor—it remains on the current row — so if you deleted row 1, the cursor is still
    // positioned at row 1. However, if the deleted row was the last row, then the cursor is positioned after the last row.
    // Note that there is no undo for deleteRow() , at least, not by default
    //
    // * public boolean rowDeleted() throws SQLException
    // As described earlier, when a ResultSet can detect deletes, the rowDeleted() method is used to indicate a row has been
    // deleted but remains as a part of the ResultSet object So if you are working with a ResultSet that is being passed around between
    // methods and shared across classes, you might use rowDeleted() to detect if the current row contains valid data.

    // -----------------------------------------------------------------------------------------
    //
    // Updating Columns Using Objects
    // you can also use String s to update almost every column in a ResultSet . All of the most common SQL types—integer,float, double,
    // long, and date—are wrapped by their representative Java object: Integer , Float , Double , Long , and java.sql.Date.
    // Each of these objects has a method valueOf() that takes a String. The updateObject() method takes two arguments: the first, a column name
    // ( String ) or column index; and the second, an Object .
    //
    public static void test02_04() {

        try (final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms()) {

            myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
            myEmbeddedRdbms.start(PORT);

            myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

            try (final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {

                final Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                final String query = "SELECT * FROM Book WHERE ISBN='142311339X'";

                final ResultSet rs = stmt.executeQuery(query);

                rs.next();

                // Update PubDate using a String date
                rs.updateObject("PubDate", "2005-04-23");

                // update this row
                rs.updateRow();

            } catch (final SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // ==========================================================================================================================================
    public static void main(String[] args) {
        test02_03();
    }
}
