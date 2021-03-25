/**
 * Objects of this class parse Json data into the database.
 */

package Helpers;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DBJsonParser {
	private DBConnection conn;
	private String workingDir;
	private BufferedReader fileBuffer;
	private String line = null;
	JSONObject jsonRow;

	/**
	 * Constructor function. Creates a DBConnection object on instantiation.
	 */
	public DBJsonParser() {
		conn = new DBConnection();
		workingDir = System.getProperty("user.dir");
	}

	/**
	 * Closes DBConnection object's connection to database.
	 */
	public void closeJsonParser() {
		conn.closeConnection();
	}

	/**
	 * Parses contents of graded.json into Graded table in database.
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public void parseGraded() throws SQLException, IOException, ParseException {
		String filePath = workingDir + "\\json\\graded.json";
		System.out.println("Parsing: " + filePath);

		try {
			fileBuffer = new BufferedReader(new FileReader(filePath));
			PreparedStatement ps = null;

			// Read JSON file line by line and insert each row into the graded table.
			while ((line = fileBuffer.readLine()) != null) {
				jsonRow = (JSONObject) new JSONParser().parse(line);

				String issue_title =		(String) jsonRow.get("issue_title");
				long issue_num =			(long) jsonRow.get("issue_num");
				long issue_year =			(long) jsonRow.get("issue_year");
				String issue_company =		(String) jsonRow.get("issue_company");
				String grade_id =			(String) jsonRow.get("grade_id");
				double grade_val =			(double) jsonRow.get("grade_val");
				String grade_page =			(String) jsonRow.get("grade_page");
				String grade_company =		(String) jsonRow.get("grade_company");
				String key_notes =			(String) jsonRow.get("key_notes");

				if (ps == null) {
					String insertStr = "INSERT INTO GRADED VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
					ps = conn.getConnection().prepareStatement(insertStr);
				}

				ps.setString(1, issue_title);
				ps.setLong(2, issue_num);
				ps.setLong(3, issue_year);
				ps.setString(4, issue_company);
				ps.setString(5, grade_id);
				ps.setDouble(6, grade_val);
				ps.setString(7, grade_page);
				ps.setString(8, grade_company);
				ps.setString(9, key_notes);
				ps.addBatch();
				jsonRow = null;
			}

			if (ps != null){
				try {
					ps.executeBatch();
				}
				catch (SQLException e) {
					System.out.println("Error: failed to execute batch.");
					System.out.println("SQLState:" + e.getSQLState());
					System.out.println("Vendor Error: " + e.getErrorCode());
					System.out.println("Message: " + e.getMessage());
				}
			}
			closePreparedStatement(ps);
			fileBuffer.close();

		}
		catch (FileNotFoundException e) {
			System.out.println("Error: file not found.");
			System.out.println("Message: " + e.getMessage() + "\n");
		}
	}

	/**
	 * Parses contents of ungraded.json into Ungraded table in database.
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public void parseUngraded() throws SQLException, IOException, ParseException {
		String filePath = workingDir + "\\json\\ungraded.json";
		System.out.println("Parsing: " + filePath);

		try {
			fileBuffer = new BufferedReader(new FileReader(filePath));
			PreparedStatement ps = null;

			// Read JSON file line by line and insert each row into the ungraded table.
			while ((line = fileBuffer.readLine()) != null) {
				jsonRow = (JSONObject) new JSONParser().parse(line);

				String issue_title =			(String) jsonRow.get("issue_title");
				long issue_num =				(long) jsonRow.get("issue_num");
				long issue_year =				(long) jsonRow.get("issue_year");
				String issue_company =			(String) jsonRow.get("issue_company");
				double grade_val_estimate =		(double) jsonRow.get("grade_val_estimate");
				String grade_page_estimate =	(String) jsonRow.get("grade_page_estimate");
				String key_notes =				(String) jsonRow.get("key_notes");

				if (ps == null) {
					String insertStr = "INSERT INTO UNGRADED VALUES(?, ?, ?, ?, ?, ?, ?)";
					ps = conn.getConnection().prepareStatement(insertStr);
				}

				ps.setString(1, issue_title);
				ps.setLong(2, issue_num);
				ps.setLong(3, issue_year);
				ps.setString(4, issue_company);
				ps.setDouble(5, grade_val_estimate);
				ps.setString(6, grade_page_estimate);
				ps.setString(7, key_notes);
				ps.addBatch();
				jsonRow = null;
			}

			if (ps != null){
				try {
					ps.executeBatch();
				}
				catch (SQLException e) {
					System.out.println("Error: failed to execute batch.");
					System.out.println("SQLState:" + e.getSQLState());
					System.out.println("Vendor Error: " + e.getErrorCode());
					System.out.println("Message: " + e.getMessage());
				}
			}
			closePreparedStatement(ps);
			fileBuffer.close();

		}
		catch (FileNotFoundException e) {
			System.out.println("Error: file not found.");
			System.out.println("Message: " + e.getMessage() + "\n");
		}
	}

	/**
	 * Close PreparedStatements safely.
	 * @param ps
	 */
	private void closePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			}
			catch (SQLException e) {
				System.out.println("Error: failed to close PreparedStatement.");
				System.out.println("SQLState:" + e.getSQLState());
				System.out.println("Vendor Error: " + e.getErrorCode());
				System.out.println("Message: " + e.getMessage());
			}
		}
	}
}
