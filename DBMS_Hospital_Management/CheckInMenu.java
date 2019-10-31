import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class CheckInMenu {
	Scanner sc = new Scanner(System.in);
	static int id;
	static int patient_id;
	static int ward_id;
	static int bed_id;
	static String start_date = "";
	static String end_date = "";
	static int selection = 0;

	public static void checkInOptions() throws ClassNotFoundException, SQLException, ParseException {
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("1. View All CheckIn Info For a Patient");
			System.out.println("2. View Single CheckIn Info For a Patient");
			System.out.println("3. Emergency CheckIn");
			System.out.println("4. Normal CheckIn");
			System.out.println("5. Update CheckIn Information");
			System.out.println("6. Delete CheckIn with Patient ID & Start Date");
			System.out.println("7. Delete CheckIn with checkInID");
			System.out.println("8. Return to home screen");
			System.out.println("Select suitable option");
			System.out.println("Choose what operation you want to perform");
			selection = sc.nextInt();
			switch (selection) {
			case 1:
				viewCheckIn();
				break;
			case 2:
				viewSingleCheckIn();
				break;
			case 3:
				emergencyCheckIn();
				break;
			case 4:
				normalCheckIn();
				break;
			case 5:
				updateCheckIn();
				break;
			case 6:
				deleteCheckIn();
				break;
			case 7:
				deleteCheckInById();
				break;
			case 8:
				System.out.println("Back to home in menu");
				break;
			default:
				System.out.println("You have entered a wrong option");
			}
			System.out.println();
		} while (selection != 8);
		// sc.close();

	}

	public static boolean patientExists(int pid) throws ClassNotFoundException, SQLException, ParseException {
		boolean exists;
		ResultSet r = PatientSQL.viewPatientById(pid);
		if (!r.next()) {
			System.out.println("No Patient with this id exists");
			System.out.println();
			exists = false;
		} else {
			exists = true;
		}
		r.close();
		return exists;
	}

	/**
	 * View all the check in info for a patient id
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	private static void viewCheckIn() throws ClassNotFoundException, SQLException, ParseException {
		Scanner sc = new Scanner(System.in);
		System.out.println("In view check in");
		System.out.println();
		do {
			System.out.println("Enter 0 to go back to Check In Menu");
			System.out.println("Enter patient ID to view their check in info");
			System.out.println();
			System.out.println("Patient ID: ");
			selection = sc.nextInt();
			sc.nextLine();

			if (selection < 0) {

				System.out.println("You have entered an invalid selection");

			} else if (selection > 0) {
				// Check if patient exists
				if (patientExists(selection)) {
					ResultSet result = CheckInSQL.viewAllCheckInForPatient(selection);
					if (!result.next()) {
						System.out.println("No Check in info for this patient id exists");
					} else {
						ResultSetMetaData rsmd = result.getMetaData();
						int columnsNumber = rsmd.getColumnCount();
						do {
							// statement(s)
							for (int i = 1; i <= columnsNumber; i++) {
								if (i > 1)
									System.out.print(",  ");
								String columnValue = result.getString(i);
								System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
							}
							System.out.println("");
						} while (result.next());
					}
					result.close();
				}

				ward_id = 0;
				bed_id = 0;
				start_date = "";
				end_date = "";
				System.out.println();
			}
			System.out.println();
		} while (selection != 0);

	}

	/**
	 * Views a single check in record by using a patient ID and start date
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	private static void viewSingleCheckIn() throws ClassNotFoundException, SQLException, ParseException {
		Scanner sc = new Scanner(System.in);
		System.out.println("In view check in");
		do {
			System.out.println("Enter 0 to go back to Check In Menu");
			System.out.println("Enter patient ID to view patient");
			System.out.println();
			System.out.println("Patient ID: ");
			selection = sc.nextInt();
			sc.nextLine();

			if (selection < 0) {

				System.out.println("You have entered an invalid selection");

			} else if (selection > 0) {
				// Check if patient exists
				if (patientExists(selection)) {
					System.out.println("Enter Start Date (YYYY-MM-DD): ");
					start_date = sc.nextLine();

					ResultSet result = CheckInSQL.viewSingleCheckInForPatient(selection, start_date);
					if (!result.next()) {
						System.out.println("No Check in info for this patient and start date exists");
					} else {
						ResultSetMetaData rsmd = result.getMetaData();
						int columnsNumber = rsmd.getColumnCount();
						do {
							// statement(s)
							for (int i = 1; i <= columnsNumber; i++) {
								if (i > 1)
									System.out.print(",  ");
								String columnValue = result.getString(i);
								System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
							}
							System.out.println("");
						} while (result.next());
					}
					result.close();
				}
				start_date = "";
				System.out.println();
			}

		} while (selection != 0);

	}

	private static void emergencyCheckIn() throws ParseException, ClassNotFoundException, SQLException {

		System.out.println();
		System.out.println("In emergency check in");
		System.out.println();
		Scanner sc = new Scanner(System.in);
		int selection;
		do {
			System.out.println("Enter 0 to go back to Check In Menu");
			System.out.println("Enter the patient ID you want to do an emergency check In for:");
			System.out.println("Patient ID: ");
			selection = sc.nextInt();
			sc.nextLine();

			if (selection < 0) {
				System.out.println("Invalid selection");
			} else if (selection == 0) {
				break;
			} else {
				// Check if patient exists
				if (patientExists(selection)) {
					System.out.println("Enter the start date (YYYY-MM-DD):");
					start_date = sc.nextLine();
					// check start date is valid
					// Insert into check in table
					CheckInSQL.emergencyCheckIn(selection, start_date);
				}

				start_date = "";
			}
			System.out.println();
		} while (selection != 0);

	}

	private static void normalCheckIn() throws ClassNotFoundException, SQLException, ParseException {
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("In normal check in");
		System.out.println();
		do {

			System.out.println("Enter 0 to go back: ");
			System.out.println("Enter the patient ID you want to do a check In for:");
			System.out.println("Patient ID: ");
			selection = sc.nextInt();
			sc.nextLine();

			if (selection < 0)
				System.out.println("Invalid selection");
			else if (selection == 0)
				break;
			else {
				if (patientExists(selection)) {
					System.out.println("Enter the start date (YYYY-MM-DD):");
					start_date = sc.nextLine();
					System.out.println("Enter the ward ID:");
					ward_id = sc.nextInt();
					if (ward_id != 0) {
						System.out.println("Enter the bed is:");
						bed_id = sc.nextInt();
					}
					CheckInSQL.normalCheckIn(selection, ward_id, bed_id, start_date);
				}

				start_date = "";
				end_date = "";
				ward_id = 0;
				bed_id = 0;
			}
			System.out.println();
		} while (selection != 0);
	}

	private static void updateCheckIn() throws ClassNotFoundException, SQLException, ParseException {
		System.out.println("In update check in");
		int selection2 = 0;

		do {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter 0 to go back to Check In Menu");
			System.out.println("Enter Patient ID:");
			selection = sc.nextInt();
			sc.nextLine();

			if (selection > 0) {

				if (patientExists(selection)) {
					System.out.println("Enter Start Date (YYYY-MM-DD):");
					String startDate = sc.nextLine();

					ResultSet result = CheckInSQL.viewSingleCheckInForPatient(selection, startDate);
					if (!result.next()) {
						System.out.println("No check in info with this patient id and start date exists");
						System.out.println();
					} else {
						do {
							patient_id = result.getInt("patient_id");
							ward_id = result.getInt("ward_id");
							bed_id = result.getInt("bed_id");
							start_date = result.getString("start_date");
							end_date = result.getString("end_date");
						} while (result.next());

						int rowId = CheckInSQL.getRowId(patient_id, start_date);
						do {
							// line 107 - 112 placeholder for displaying the current data
							System.out.println("1. Patient ID: " + patient_id);
							System.out.println("2. Ward ID " + ward_id);
							System.out.println("3. Bed ID: " + bed_id);
							System.out.println("4. Start date: " + start_date);
							System.out.println("5. End date: " + end_date);
							System.out.println("6. Submit the edited info");
							System.out.println("7. Back to Edit another Check In");
							System.out.println();
							System.out.println("Current Input will show next to attribute");
							System.out.println();
							System.out.println("Enter # to edit info: ");

							selection2 = sc.nextInt();
							sc.nextLine();
							System.out.println();

							switch (selection2) {
							case 2:
								System.out.println("Enter Ward ID that the patient is in:");
								ward_id = sc.nextInt();
								sc.nextLine();
								break;
							case 3:
								System.out.println("Enter Bed ID patient is in:");
								bed_id = sc.nextInt();
								sc.nextLine();
								break;
							case 4:
								System.out.println("Start date (YYYY-MM-DD):");
								start_date = sc.nextLine();
								break;
							case 5:
								System.out.println("End date (YYYY-MM-DD):");
								end_date = sc.nextLine();
								break;
							case 6:
								// sql update statement
								CheckInSQL.editCheckIn(patient_id, ward_id, bed_id, start_date, end_date, rowId);
								patient_id = 0;
								start_date = "";
								ward_id = 0;
								bed_id = 0;
								end_date = "";
								System.out.println();
								break;
							case 7:
								System.out.println("Back to Check In menu");
								patient_id = 0;
								start_date = "";
								ward_id = 0;
								bed_id = 0;
								System.out.println();
								break;
							default:
								System.out.println("The selection is invalid");
								break;
							}
							if (selection2 == 6) {
								break;
							}
						} while (selection2 != 7);
					}

				}

			} else if (selection < 0) {
				System.out.println("Selection is invalid");
			}
		} while (selection != 0);

	}

	/**
	 * Deletes a check in info by using patient id and start date
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	private static void deleteCheckIn() throws ClassNotFoundException, SQLException, ParseException {
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Enter 0 to go back to Check In Menu");
			System.out.println("Enter patient ID to delete check In info for a patient");
			System.out.println();
			System.out.println("Patient ID: ");

			selection = sc.nextInt();
			sc.nextLine();

			if (selection > 0) {
				if (patientExists(selection)) {
					System.out.println("Enter the start date for this Check In (YYYY-MM-DD):");
					start_date = sc.nextLine();
					CheckInSQL.deleteCheckIn(selection, start_date);
				}

				// sql statement to delete the check in account
				// successfully deleted patient
				start_date = "";

			} else if (selection < 0) {
				System.out.println("You have entered a wrong selection");

			} else if (selection == 0) {
				System.out.println("Back to Check In Menu");
			}
			System.out.println();
		} while (selection != 0);
	}

	/**
	 * Deletes a check in info row by using the id for that row
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	private static void deleteCheckInById() throws ClassNotFoundException, SQLException, ParseException {
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Enter 0 to go back to Check In Menu");
			System.out.println("Enter Check In Id to delete record");
			System.out.println();
			System.out.println("Check In ID: ");

			selection = sc.nextInt();
			sc.nextLine();

			if (selection > 0) {
				CheckInSQL.deleteCheckInById(selection);

			} else if (selection < 0) {
				System.out.println("You have entered a wrong selection");

			} else if (selection == 0) {
				System.out.println("Back to Check In Menu");
			}
			System.out.println();
		} while (selection != 0);
	}
}
