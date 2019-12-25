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

// Submit Queries and Read Results from the Database (OCP Objective 11.3)
public class Test01 {

    // =========================================================================================================================================
    //
    // All of Bob’s Customers
    //
    public static void test01() {

        final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms();

        myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
        myEmbeddedRdbms.start(PORT);

        myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

        try {
            // get Connection - We are creating a Connection object instance using the information we need to access Bob’s Books Database
            final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            // We are using the Connection to create a Statement object. The Statement object handles passing Strings
            // to the database as queries for the database to execute.
            final Statement stmt = connection.createStatement(); // create Statement

            final String query = "SELECT * FROM Customer";

            // We are executing the query string on the database and returning a ResultSet object.
            final ResultSet rs = stmt.executeQuery(query); // Execute Query

            // We are iterating through the result set rows—each call to next() moves us to the next row of results.
            while (rs.next()) { // Process REsults
                out.print(rs.getInt("CustomerID") + " "); // Print Columns
                out.print(rs.getString("FirstName") + " ");
                out.print(rs.getString("LastName") + " ");
                out.print(rs.getString("EMail") + " ");
                out.println(rs.getString("Phone"));
            }

            // All of the JDBC API method invocations throw SQLException.
            // A SQLException can be thrown when a method is used improperly or
            // if the database is no longer responding. For example, a
            // SQLException is thrown if the JDBC URL, username, or password is invalid.
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        myEmbeddedRdbms.stop();
    }

    // =========================================================================================================================================
    //
    // Statements
    //
    // The primary purpose of a Statement is to execute a SQL statement using a method and return some type of result.
    // There are several forms of Statement methods: those that return a result set, and those that return an integer status.
    public static void test02() {

        final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms();

        myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
        myEmbeddedRdbms.start(PORT);

        myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

        // Constructing and Using Statements
        //

        try {
            final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            // Some commands return an integer status. For example, SQL INSERT, UPDATE, and DELETE commands,
            // or any of the SQL Data Definition Language (DDL) statements like CREATE TABLE,
            // return either the number of rows affected by the query or 0.
            final Statement stmt = connection.createStatement();

            // public ResultSet executeQuery(String sql) throws SQLException
            // This method is used when we know that we want to return results—we are querying the database for one or more rows of data.
            final ResultSet rs01 = stmt.executeQuery("SELECT * FROM Customer");

            // public boolean execute(String sql) throws SQLException
            // This method is used when you are not sure what the result will be—perhaps the query will return a result
            // set and perhaps not. This method can be used to execute a query whose type may not be known until runtime—for
            // example, one constructed in code. The return value is true if the query resulted in a result set and false if
            // the query resulted in an update count or no results.

            final ResultSet rs02;
            int numRows;

            boolean status = stmt.execute("");

            // True if there is a ReesultSet
            if (status) {
                rs02 = stmt.getResultSet();
                // Process the result set...
            } else {
                numRows = stmt.getUpdateCount(); // Get the update count

                if (numRows == -1) { // If -1, there are no results
                    out.println("No results");
                } else { // else, print the number the of rows affected
                    out.println(numRows + " rows affected.");
                }
            }

            // public ResultSet getResultSet() throws SQLException
            // To get the result set, as shown earlier, call the getResultSet() method on the Statement object.
            // Then you can process the ResultSet object (which we will cover in the next section).
            // This method is basically foolproof—if, in fact, there are no results, the method will return a null.

            // public int getUpdateCount() throws SQLException
            // This method will return the number of rows affected.
            // A return value of –1 indicates that there are no results.

            // One reason that these methods all throw SQLException is that a connection to the database is likely to a database on a
            // network. As with all things on the network, availability is not guaranteed, so one possible reason for SQLException is
            // the lack of availability of the database itself
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // =========================================================================================================================================
    //
    // ResultSets
    // The ResultSet object represents the results of the query—all of the data in each row on a per-column basis.
    // Again, as a reminder, how data in a ResultSet are stored is entirely up to the JDBC driver vendor. It is possible that the
    // JDBC driver caches the entire set of results in memory all at once, or that it uses internal buffers and gets only a few rows at a time.
    public static void test03() {
        final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms();

        myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
        myEmbeddedRdbms.start(PORT);

        myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

        // Moving Forward in a ResultSet
        // Assume that in our BookSellerDB database we have several customers whose last name begins with the
        // letter “C.” We could create a query to return those rows “like” this
        //

        try {
            final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            final Statement stmt = connection.createStatement();
            // So, LastName LIKE 'C%' means “any LastName with a C, followed by any other character(s).”
            final String query = "SELECT CustomerID, FirstName, LastName, EMail FROM Customer WHERE LastName LIKE 'C%'";

            final ResultSet rs01 = stmt.executeQuery(query);

            // When the ResultSet object is first returned from the query, the cursor is not yet pointing to a row of results—the cursor is pointing above
            // the first row. In order to get the results of the table, you must always call the next() method on the ResultSet object to move the cursor forward to the first row of data.
            //
            // The next() method moves the cursor forward one row and returns true if the
            // cursor now points to a row of data in the ResultSet.
            while (rs01.next()) {
                out.print(rs01.getInt("CustomerID") + " "); // Read the data in the column named "CustomerID" into a int
                out.print(rs01.getString("FirstName") + " "); // Read the data in the column 
                out.print(rs01.getString("LastName") + " ");

                // ResultSet also provides an overloaded method that takes an integer index value for
                // each of the SQL types. This value is the integer position of the column in the result set,
                // numbered from 1 to the number of columns returned. So

                out.print(rs01.getString(4) + " ");
            }

            // public boolean getBoolean(String columnLabel)
            // This method reieves the value of the named column in the ResultSet as a Java boolean
            //
            // public double getDouble(String columnLabel)
            // This method retrieves the value of the column as a Java double. This method is recommended for returning the value
            // stored in the database as SQL DOUBLE and SQL FLOAT types.
            //
            // public int getInt(String columnLabel)
            // This method retrieves the value of the column as a Java int. Integers are often a good choice for primary keys. This method is
            // recommended for returning values stored in the database as SQL INTEGER types.
            //
            // public float getFloat(String columnLabel)
            // This method retrieves the value of the column as a Java float. It is recommended for SQL REAL types.
            //
            // public long getLong(String columnLabel)
            // This method retrieves the value of the column as a Java long. It is recommended for SQL BIGINT types.
            //
            // public java.sql.Date getDate(String columnLabel)  
            // This method retrieves the value of the column as a Java Date object. Note that java.sql.Date extends java.util.Date. One difference between 
            // the two is that the toString() method of java.sql.Date returns a date string in the form: “yyyy mm dd.” This method is
            // recommended for SQL DATE types.
            //
            // public java.lang.String getString(String columnLabel)
            // This method retrieves the value of the column as a Java String object. It is good for reading SQL columns with
            // CHAR, VARCHAR, and LONGVARCHAR types.
            //
            // public java.sql.Time getTime(String columnLabel)
            // This method retrieves the value of the column as a Java Time object. Like java.sql.Date, this class extends
            // java.util.Date, and its toString() method returns a time string in the form:
            // “hh:mm:ss.” TIME is the SQL type that this method is designed to read.           
            //
            // public java.sql.Timestamp getTimestamp(String columnLabel)
            // This method retrieves the value of the column as a Timestamp object. Its toString() method
            // formats the result in the form: yyyy-mm-dd hh:mm:ss.fffffffff, where ffffffffff is nanoseconds. This method is recommended for reading SQL TIMESTAMP types
            // 
            // public java.lang.Object getObject(String columnLabel)
            // This method retrieves the value of the column as a Java Object. It can be used as a general-purpose method for reading data in a column. 
            // This method works by reading the value returned as the appropriate Java wrapper class for the type and returning that as a Java Object object.

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        myEmbeddedRdbms.stop();
    }

    //
    // ==========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
