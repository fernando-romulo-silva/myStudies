package br.com.fernando.chapter12_javaTransaction.part04_transactionIsolation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.derby.jdbc.EmbeddedDataSource;

public class TranscationIsolationTest {
    static Log logger = LogFactory.getLog(TranscationIsolationTest.class);

    public static final String CREATE_USER_TABLE = "CREATE TABLE Users (FirstName varchar(255), LastName varchar(255), City varchar(255), PostalCode int, Country varchar(255))";

    public static final String INSERT_STUDY_DATA = "INSERT INTO Users (FirstName, LastName, City, PostalCode, Country) VALUES ('Study','Hsueh','hsinchu',300,'Taiwan')";
    public static final String INSERT_BLUEHEART_DATA = "INSERT INTO Users (FirstName, LastName, City, PostalCode, Country) VALUES ('Blueheart','Shih','hualien',4006,'Taiwan')";

    public static final String UPDATE_STUDY_CITY = "UPDATE Users SET City = 'Taipei' WHERE FirstName = 'Study'";

    public static final String SELECT_STUDY_DATA = "SELECT * FROM Users WHERE FirstName = 'Study'";
    public static final String SELECT_USERS_DATA = "SELECT * FROM Users";

    private static String tmpDir = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");

    private EmbeddedDataSource ds;

    public TranscationIsolationTest() throws SQLException {
	System.setProperty("derby.system.home", tmpDir);
	String dbname = "derby-" + (int) (new Random().nextDouble() * 10000);

	ds = new EmbeddedDataSource();
	ds.setDatabaseName(dbname);
	ds.setCreateDatabase("create");
	Connection conn = ds.getConnection();

	PreparedStatement stmt = conn.prepareStatement(CREATE_USER_TABLE);
	stmt.execute();
	DbUtils.closeQuietly(stmt);

	stmt = conn.prepareStatement(INSERT_STUDY_DATA);
	stmt.execute();
	DbUtils.closeQuietly(stmt);
	DbUtils.closeQuietly(conn);
    }

    public void executeDirtyReadsQuery(int transactionLevel) throws SQLException, InterruptedException {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	String address1 = null;
	String address2 = null;
	String address3 = null;

	try {
	    conn = ds.getConnection();
	    conn.setAutoCommit(false);
	    conn.setTransactionIsolation(transactionLevel);

	    stmt = conn.createStatement();
	    rs = stmt.executeQuery(SELECT_STUDY_DATA);
	    rs.next();
	    address1 = rs.getString("City");
	    DbUtils.closeQuietly(rs);

	    // update study address and rollback after 2000ms
	    Runnable runnable = new Runnable() {
		@Override
		public void run() {
		    try {
			makeDirtyReads(2000);
		    } catch (SQLException | InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    };
	    Thread dirtyReadsThread = new Thread(runnable);
	    dirtyReadsThread.start();
	    Thread.sleep(1000);

	    rs = stmt.executeQuery(SELECT_STUDY_DATA);
	    rs.next();
	    address2 = rs.getString("City");
	    DbUtils.closeQuietly(rs);

	    Thread.sleep(2000);

	    rs = stmt.executeQuery(SELECT_STUDY_DATA);
	    rs.next();
	    address3 = rs.getString("City");
	    DbUtils.closeQuietly(rs);

	    if (address1.equals(address3) && address1.equals(address2)) {
		logger.info("Dirty Reads not happened.");
	    } else {
		logger.info("Dirty Reads happened.");
	    }
	    conn.commit();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    conn.setAutoCommit(true);
	    DbUtils.closeQuietly(conn, stmt, rs);
	}
    }

    public void executeNonRepeatableReadsQuery(int transactionLevel) throws SQLException, InterruptedException {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	String address1 = null;
	String address2 = null;
	String address3 = null;

	try {
	    conn = ds.getConnection();
	    conn.setAutoCommit(false);
	    conn.setTransactionIsolation(transactionLevel);

	    stmt = conn.createStatement();
	    rs = stmt.executeQuery(SELECT_STUDY_DATA);
	    rs.next();
	    address1 = rs.getString("City");
	    DbUtils.closeQuietly(rs);

	    // update study address and commit
	    Runnable runnable = new Runnable() {
		@Override
		public void run() {
		    try {
			makeNonRepeatableReads();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    };
	    Thread nonRepeatableThread = new Thread(runnable);
	    nonRepeatableThread.start();
	    Thread.sleep(2000);

	    rs = stmt.executeQuery(SELECT_STUDY_DATA);
	    rs.next();
	    address2 = rs.getString("City");
	    DbUtils.closeQuietly(rs);

	    rs = stmt.executeQuery(SELECT_STUDY_DATA);
	    rs.next();
	    address3 = rs.getString("City");
	    DbUtils.closeQuietly(rs);

	    if (address1.equals(address3) && address1.equals(address2)) {
		logger.info("Non-Repeatable Reads not happened.");
	    } else {
		logger.info("Non-Repeatable Reads happened.");
	    }

	    conn.commit();
	    nonRepeatableThread.join();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    conn.setAutoCommit(true);
	    DbUtils.closeQuietly(conn, stmt, rs);
	}
    }

    public void executePhantomReadsQuery(int transactionLevel) throws SQLException, InterruptedException {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	int rowCount1 = 0;
	int rowCount2 = 0;

	try {
	    conn = ds.getConnection();
	    conn.setAutoCommit(false);
	    conn.setTransactionIsolation(transactionLevel);

	    stmt = conn.createStatement();
	    rs = stmt.executeQuery(SELECT_USERS_DATA);
	    while (rs.next()) {
		rowCount1++;
	    }
	    DbUtils.closeQuietly(rs);

	    // update study address and commit
	    Runnable runnable = new Runnable() {
		@Override
		public void run() {
		    try {
			makePhantomReads();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    };
	    Thread nonRepeatableThread = new Thread(runnable);
	    nonRepeatableThread.start();
	    Thread.sleep(2000);

	    rs = stmt.executeQuery(SELECT_USERS_DATA);
	    while (rs.next()) {
		rowCount2++;
	    }
	    DbUtils.closeQuietly(rs);

	    if (rowCount1 == rowCount2) {
		logger.info("Phantom Reads not happened.");
	    } else {
		logger.info("Phantom Reads happened.");
	    }
	    conn.commit();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    conn.setAutoCommit(true);
	    DbUtils.closeQuietly(conn, stmt, rs);
	}
    }

    public void makeDirtyReads(long rollbackAfterMilliseconds) throws SQLException, InterruptedException {
	Connection conn = null;
	PreparedStatement stmt = null;

	try {
	    conn = ds.getConnection();
	    conn.setAutoCommit(false);
	    conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

	    stmt = conn.prepareStatement(UPDATE_STUDY_CITY);
	    stmt.execute();
	    Thread.sleep(rollbackAfterMilliseconds);
	    conn.rollback();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    conn.setAutoCommit(true);
	    DbUtils.closeQuietly(stmt);
	    DbUtils.closeQuietly(conn);
	}
    }

    public void makePhantomReads() throws SQLException {
	Connection conn = null;
	PreparedStatement stmt = null;

	try {
	    conn = ds.getConnection();
	    conn.setAutoCommit(false);
	    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

	    stmt = conn.prepareStatement(INSERT_BLUEHEART_DATA);
	    stmt.execute();
	    conn.commit();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    conn.setAutoCommit(true);
	    DbUtils.closeQuietly(stmt);
	    DbUtils.closeQuietly(conn);
	}
    }

    public void makeNonRepeatableReads() throws SQLException {
	Connection conn = null;
	PreparedStatement stmt = null;

	try {
	    conn = ds.getConnection();
	    conn.setAutoCommit(false);
	    conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

	    stmt = conn.prepareStatement(UPDATE_STUDY_CITY);
	    stmt.execute();
	    conn.commit();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    conn.setAutoCommit(true);
	    DbUtils.closeQuietly(stmt);
	    DbUtils.closeQuietly(conn);
	}
    }
}
