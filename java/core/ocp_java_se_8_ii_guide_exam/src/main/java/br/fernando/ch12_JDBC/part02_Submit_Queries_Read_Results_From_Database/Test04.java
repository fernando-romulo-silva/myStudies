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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.myembedded.rdbms.MyEmbeddedRdbms;

public class Test04 {

    // ==========================================================================================================================================
    // Inserting New Rows Using a ResultSet
    //
    // First, (1) you must have a valid ResultSet open, so typically, you have performed some query. ResultSet provides a special row,
    // called the insert row, that you are actually modifying (updating) before performing the insert.
    // Think of the insert row as a buffer where you can modify an empty row of your ResultSet with values.
    //
    // Inserting a row is a three-step process: First (1) move to the special insert row, then (2) update the values of the columns
    // for the new row, andfinally (3) perform the actual insert (write to the underlying database).
    public static void test01_01() {

        try (final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms()) {

            myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
            myEmbeddedRdbms.start(PORT);

            myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

            try (final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {

                final Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                final String query = "SELECT AuthorId, FirstName, LastName FROM  Author";

                final ResultSet rs = stmt.executeQuery(query);
                rs.next(); // first row

                // (1)
                rs.moveToInsertRow();

                // (2)
                rs.updateInt("AuthorID", 1055);
                rs.updateString("FirstName", "Tom");
                rs.updateString("LastName", "McGinn");

                // (3)
                rs.insertRow(); //

                rs.moveToCurrentRow(); // return to the first row

            } catch (final SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // * public void moveToInsertRow() throws SQLException
    // This method moves the cursor to insert a row buffer. Wherever the cursor was when this method was called is
    // remembered. After calling this method, the appropriate updater methods are called to update the values of the columns.
    //
    // * public void insertRow() throws SQLException
    // This method writes the insert rowbuffer to the database. Note that the cursor must be on the insert row when this method
    // is called. Also, note that each column must be set to a value before the row is inserted in the database or a SQLException will be thrown.
    //
    // * public void moveToCurrentRow() throws SQLException
    // This method returns the result set cursor to the row the cursor was on before the moveToInsertRow() method was called.
    //
    // ==========================================================================================================================================
    //
    // Getting Information about a Database Using DatabaseMetaData
    //
    // This section covers DatabaseMetaData , an interface that provides a significant amount of information about the database itself.
    // This topic is fairly advanced stuff and is not on the exam, but it is provided here to give you an idea about how you can use metadata to build a
    // model of a database without having to know anything about the database in advance.
    public static void test01_02() {
        try (final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms()) {

            myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
            myEmbeddedRdbms.start(PORT);

            myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");

            try (final Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {

                final DatabaseMetaData dbmd = connection.getMetaData();

                final Logger logger = Logger.getLogger("Test05");

                if (!dbmd.supportsANSI92EntryLevelSQL()) {
                    logger.info("Jdbc Driver does not meet minimum requirements for SQL-92 support");
                }

                logger.info("Driver Name: " + dbmd.getDriverName());
                logger.info("Driver version: " + dbmd.getDriverVersion());

                dbmd.getCatalogs();

                dbmd.getSchemas();

                // get a result Set of all the stored procedures in any catalog (null) in any schema with wildcard name %
                final ResultSet rsProcesures = dbmd.getProcedures(null, null, "%");

                while (rsProcesures.next()) {
                    out.println("Procedure Name: " + rsProcesures.getString("PROCEDURE_NAME"));
                }

                final ResultSet rsColumn = dbmd.getColumns(null, null, "%BOOK%", "%");

                while (rsColumn.next()) {
                    out.print("Table Name: " + rsColumn.getString("TABLE_NAME") + " ");
                    out.print("Column Name: " + rsColumn.getString("COLUMN_NAME") + " ");
                    out.print("Type Name: " + rsColumn.getString("TYPE_NAME") + " ");
                    out.println("Column Size: " + rsColumn.getString("COLUMN_SIZE"));
                }

            }

        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
    }
    // * public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException
    // This method is one of the best all-purpose data retrieval methods for details about the tables and columns in your database.
    // Before we look at a code sample, it might be helpful to define catalogs and schemas. In a database, a schema is an object that enforces the
    // integrity of the tables in the database. The schema name is generally the name of the person who created the database.
    // In our examples, the BookGuy database holds the collection of tables and is the name of the schema. Databases may have multiple schemas stored in a catalog.
    //
    // * public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException
    // Stored procedures are functions that are sometimes built into a database and often defined by a database developer or
    // database admin. These functions can range from data cleanup to complex queries. This method returns a result set that
    // contains descriptive information about the stored procedures for a catalog and schema.
    //
    // * public String getDriverName() throws SQLException
    // This method returns the JDBC driver version number as a string. This information and the driver name would be good to log in at startup of an application.
    //
    // * public boolean supportsANSI92EntryLevelSQL() throws SQLException
    // This method returns true if the database and JDBC driver support ANSI SQL-92 entry-level
    // grammar. Support for this level (at a minimum) is a requirement for JDBC drivers (and, therefore, the database).
    // 

    // ==========================================================================================================================================
    public static void main(String[] args) {
        test01_02();
    }
}
