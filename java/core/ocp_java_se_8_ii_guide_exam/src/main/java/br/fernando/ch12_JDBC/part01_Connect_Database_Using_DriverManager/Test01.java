package br.fernando.ch12_JDBC.part01_Connect_Database_Using_DriverManager;

import static br.fernando.ch12_JDBC.DataBaseConst.LOGIN;
import static br.fernando.ch12_JDBC.DataBaseConst.NAME;
import static br.fernando.ch12_JDBC.DataBaseConst.PASSWORD;
import static br.fernando.ch12_JDBC.DataBaseConst.PORT;
import static br.fernando.ch12_JDBC.DataBaseConst.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.myembedded.rdbms.MyEmbeddedRdbms;

// Certification Objectives
//
// • Describe the interfaces that Make Up the Core of the JDBC API Including the Driver, Connection,
// Statement, and ResultSet Interfaces and Their Relationship to Provider Implementations
//
// • Identify the Components Required to Connect to a Database Using the DriverManager Class
// Including the JDBC URL
//
// • Submit Queries and Read Results from the Database Including Creating Statements, Returning
// Result Sets, Iterating Through the Results, and Properly
// Closing Result Sets, Statements, and Connections
public class Test01 {

    // =========================================================================================================================================
    //
    // Core Interfaces of the JDBC API (OCP Objective 11.1)
    //
    // The java.sql.Connection interface defines the contract for an object that represents the
    // connection with a relational database system.
    //
    // The Statement interface provides an abstraction of the functionality needed to get a SQL
    // statement to execute on a database
    //
    // ResultSet interface is an abstraction functionality needed to process a result set (the table
    // of data) that is returned from the
    // SQL query when the query involves a SQL SELECT statement.
    //
    // Here is a partial list of the requirements for a JDBC driver
    //
    // * Fully implement the interfaces: java.sql.Driver , java.sql.DatabaseMetaData ,
    // java.sql.ResultSetMetaData.
    //
    // * Implement the java.sql.Connection interface. (Note that some methods are optional depending
    // on the SQL version the database supports—more on SQL
    // versions later in the chapter.)
    //
    // * Implement java.sql.Statement , and java.sql.PreparedStatement. Implement the
    // java.sql.CallableStatement interfaces if the database
    // supports stored procedures. Again, more on this interface later in the chapter.
    //
    // * Implement the java.sql.ResultSet interface.
    public static void test01() {

    }

    // =========================================================================================================================================
    //
    // Connect to a Database Using DriverManager (OCP Objective 11.2)
    //
    // The DriverManager class is a concrete, utility class in the JDBC API with static methods.
    //
    // How JDBC Drivers Register with the DriverManager?
    //
    // * The JVM loads the DriverManager class, a concrete class in the JDBC API.
    //
    // * The DriverManager class loads any instances of classes it finds in the META-INF/services/java.sql.Driver file of JAR/ZIP files on the classpath.
    //
    // * Driver classes call DriverManager.register(this) to self-register with the DriverManager.
    //
    // * When the DriverManager.getConnection(String url) method is invoked, DriverManager invokes the connect() method of each of these registered
    // Driver instances with the URL string.
    //
    // * The first Driver that successfully creates a connection with the URL returns an instance of a Connection object to the 
    // DriverManager.getConnection method invocation.

    public static void test02() throws SQLException {

        try (final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms();) {

            myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
            myEmbeddedRdbms.start(PORT);

            myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

            // The JDBC URL
            //
            // jdbc:hsqldb:hsql://localhost:2527/embeddedRdbmsTest
            // 
            // The first part, jdbc , simply identifies that this is a JDBC URL (versus HTTP or something else). 
            // The second part indicates that driver vendor is derby driver. The third  part indicates that the database is on the localhost 
            // of this machine (IP address 127.0.0.1), at port 2527 , and the final part indicates that we are interested in the 'embeddedRdbmsTest' database.
            //
            // jdbc:<subprotocol>:<subname>
            // 
            // In general, the subprotocol is the vendor name; for example:
            //
            // jdbc:derby
            // jdbc:mysql
            // jdbc:oracle
            //
            // Get an instance of a Connection object
            try (final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {

            }
        }

        // JDBC Driver Implementation Versions
        // If you are using a JDBC driver that is an earlier version, say, a JDBC 3.0 driver, then you must explicitly load the class 
        // provided by the database vendor that implements the java.sql.Driver interface.
        //
        // Class.forName("org.apache.derby.jdbc.ClientDriver"); // Class loads ClientDriver
        // Connection conn = DriverManager.getConnection(url);
        //
        // Note that using the Class.forName() method is compatible with both JDBC 3.0 and
        // JDBC 4.0 drivers. It is simply not needed when the driver supports 4.0.

    }

}
