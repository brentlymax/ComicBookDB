/**
 * Create connections to our database and insert or remove data from it.
 */

import Helpers.*;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class ComicBookDB {
	static Scanner sc;

	/**
	 * Main function for program. Allows user to manage tables or parse JSON data.
	 * @param args
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ParseException, SQLException, IOException {
		String tables = "tables";
		String parser = "parser";
		String exit = "exit";
		System.out.println("Welcome to the Comic Book Database manager.");
		boolean notDone = true;
		sc = new Scanner(System.in);

		// Loop until exit is chosen.
		while(notDone) {
			System.out.println("Enter one of the following:");
			System.out.println(tables + " - to manage tables");
			System.out.println(parser + " - to parse JSON files");
			System.out.println(exit + " - to exit table manager\n");
			String input = sc.nextLine();

			if (input.matches(tables)) {
				manageTables();
			}
			else if (input.matches(parser)) {
				parseJson();
			}
			else if (input.matches(exit)) {
				notDone = false;
			}
			else {
				System.out.println("ERROR: invalid input.");
			}
		}
		System.out.println("Exiting Comic Book Database manager...");
		System.out.println("Goodbye.");
		sc.close();
	}

	/**
	 * Allows user to create and delete database tables.
	 * Mainly for testing DBTableManager class.
	 */
	public static void manageTables() {
		String create = "create";
		String drop = "drop";
		String reset = "reset";
		String exit = "exit";
		System.out.println("Starting the database table manager...");
		DBTableManager manager = new DBTableManager();
		boolean notDoneManaging = true;
		sc = new Scanner(System.in);

		// Loop until exit is chosen.
		while(notDoneManaging) {
			System.out.println("Enter one of the following:");
			System.out.println(create + " - to create tables");
			System.out.println(drop + " - to drop existing tables");
			System.out.println(reset + " - to reset existing tables");
			System.out.println(exit + " - to exit table manager\n");
			String input = sc.nextLine();

			if (input.matches(create)) {
				manager.createTables();
			}
			else if (input.matches(drop)) {
				manager.dropTables();
			}
			else if (input.matches(reset)) {
				manager.dropTables();
				manager.createTables();
			}
			else if (input.matches(exit)) {
				notDoneManaging = false;
			}
			else {
				System.out.println("ERROR: invalid input.");
			}
		}
		System.out.println("Exiting table manager...");
		manager.closeTableManager();
	}

	/**
	 * Allows user to parse contents of graded.json and/or ungraded.json files.
	 * Mainly for testing DBJsonParser class.
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void parseJson() throws ParseException, SQLException, IOException {
		String graded = "graded";
		String ungraded = "ungraded";
		String exit = "exit";
		System.out.println("Starting the database JSON data parser...");

		DBJsonParser parser = new DBJsonParser();
		boolean notDoneParsing = true;
		sc = new Scanner(System.in);

		// Loop until exit is chosen.
		while(notDoneParsing) {
			System.out.println("Enter one of the following:");
			System.out.println(graded + " - to parse graded comics");
			System.out.println(ungraded + " - to parse ungraded comics");
			System.out.println(exit + " - to exit table manager\n");
			String input = sc.nextLine();

			if (input.matches(graded)) {
				parser.parseGraded();
			}
			else if (input.matches(ungraded)) {
				parser.parseUngraded();
			}
			else if (input.matches(exit)) {
				notDoneParsing = false;
			}
			else {
				System.out.println("ERROR: invalid input.");
			}
		}
		System.out.println("Exiting JSON parser...");
		parser.closeJsonParser();
	}
}
