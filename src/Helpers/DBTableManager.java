/**
 * Objects of this class create, or recreate tables for the database.
 */

package Helpers;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBTableManager {
	private DBConnection conn;

	/**
	 * Constructor function. Creates a DBConnection object on instantiation.
	 */
	public DBTableManager() {
		conn = new DBConnection();
	}

	/**
	 * Closes DBConnection object's connection to database.
	 */
	public void closeTableManager() {
		conn.closeConnection();
	}

	/**
	 * Creates tables for the database. Uses strings representing SQL commands and prepared statements.
	 */
	public void createTables() {
		// Graded comic table.
		String gradedStr = ""
				+ "CREATE TABLE Graded ("
				+ " issue_title						VARCHAR(50) NOT NULL,"
				+ " issue_num						NUMBER NOT NULL,"
				+ " issue_year						NUMBER NOT NULL,"
				+ " issue_company					VARCHAR(50) NOT NULL,"
				+ " grade_id						VARCHAR(50) NOT NULL,"
				+ " grade_val						NUMBER NOT NULL,"
				+ " grade_page						VARCHAR(50) NOT NULL,"
				+ " grade_company					VARCHAR(50) NOT NULL,"
				+ " key_notes						VARCHAR(100),"
				+ " PRIMARY KEY (grade_id)"
				+ ")";
		// Ungraded comic table.
		String ungradedStr = ""
				+ "CREATE TABLE Ungraded ("
				+ " issue_title						VARCHAR(50) NOT NULL,"
				+ " issue_num						NUMBER NOT NULL,"
				+ " issue_year						NUMBER NOT NULL,"
				+ " issue_company					VARCHAR(50) NOT NULL,"
				+ " grade_val_estimate				NUMBER NOT NULL,"
				+ " grade_page_estimate				VARCHAR(50) NOT NULL,"
				+ " key_notes						VARCHAR(100),"
				+ " PRIMARY KEY (issue_title, issue_num, issue_year, grade_val_estimate, grade_page_estimate)"
				+ ")";

		try {
			PreparedStatement ps;
			ps = conn.getConnection().prepareStatement(gradedStr);
			ps.execute();
			ps = conn.getConnection().prepareStatement(ungradedStr);
			ps.execute();
			System.out.println("Tables created.\n");
		} catch (SQLException e) {
			System.out.println("Error: failed to create tables.");
			System.out.println("SQLState:" + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());
			System.out.println("Message: " + e.getMessage());
		}
	}

	/**
	 * Drops tables for the database. Uses strings representing SQL commands and prepared statements.
	 */
	public void dropTables() {
		String gradedStr = "DROP TABLE Graded CASCADE CONSTRAINTS";
		String ungradedStr = "DROP TABLE Ungraded CASCADE CONSTRAINTS";

		try {
			PreparedStatement ps;
			ps = conn.getConnection().prepareStatement(gradedStr);
			ps.execute();
			ps = conn.getConnection().prepareStatement(ungradedStr);
			ps.execute();
			System.out.println("Tables dropped.\n");
		} catch (SQLException e) {
			System.out.println("Error: failed to drop tables.");
			System.out.println("SQLState:" + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());
			System.out.println("Message: " + e.getMessage());
		}
	}
}
