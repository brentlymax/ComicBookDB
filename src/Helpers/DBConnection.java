/**
 * Objects of this class create connections to the database upon instantiation.
 */

package Helpers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private Connection conn;

	/**
	 * Constructor function. Connects to the database on instantiation.
	 */
	public DBConnection() {
		conn = null;
		try {
			conn = openConnection();
			if (conn == null) {
				System.out.println("Error: failed to connect to database.");
			}
		}
		catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calls sql's close() function to safely close a connection to the database.
	 */
	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		}
		catch (SQLException e) {
			System.out.println("Error: failed to close database connection.");
			System.out.println("SQLState:" + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());
			System.out.println("Message: " + e.getMessage());
		}
	}

	/**
	 * Calls sql's getConnection() function to open a connection to the database.
	 * @return
	 * @throws SQLException
	 */
	private Connection openConnection() throws SQLException {
		String host = "localhost";
		String port = "1521";
		String dbName = "CBDB";
		String userName = "system";
		String password = "oracle";
		String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
		return DriverManager.getConnection(dbURL, userName, password);
	}

	/**
	 * Accessor for Connection variable.
	 * @return
	 */
	public Connection getConnection() {
		return conn;
	}
}
