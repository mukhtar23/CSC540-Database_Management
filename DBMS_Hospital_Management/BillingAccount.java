import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * This class contains the APIs for the billing_account table
 * 
 */
public class BillingAccount {
	static int id;
	static int patient_id;
	static String ssn;
	static String billing_address;
	static String payment_method;
	static String card_number;
	static String check_number;
	static String insurance_company_name;
	static float ward_charge;
	static float test_charge;
	static float treatment_charge;
	static float fee_charge;
	static float total_charge;
	static float accomodation_fee;
	static float registration_fee;
	static boolean medication_prescribed;
	static String start_date;
	static String end_date;
	static boolean settled;
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	Scanner sc = new Scanner(System.in);

	/**
	 * This method initializes the fields of the billing_accounts with the default
	 * values
	 */
	public static void initialize() {
		id = 0;
		billing_address = "";
		payment_method = "";
		card_number = "";
		check_number = "";
		insurance_company_name = "";
		ward_charge = 0;
		test_charge = 0;
		treatment_charge = 0;
		fee_charge = 0;
		accomodation_fee = 0;
		registration_fee = 0;
		medication_prescribed = false;
		total_charge = 0;
		end_date = null;
		settled = false;

	}

	/**
	 * This method displays all records in the billing_accounts table
	 */
	public static void getAllRecords() {
		String sql1 = "Select * from billing_accounts";
		try {
			stmt = login.connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql1);
			if (!rs.next())
				System.out.println("No records in the table");
			else {
				do {
					patient_id = rs.getInt("patient_id");
					ssn = rs.getString("ssn");
					billing_address = rs.getString("billing_address");
					payment_method = rs.getString("payment_method");
					card_number = rs.getString("card_number");
					check_number = rs.getString("check_number");
					insurance_company_name = rs.getString("insurance_company_name");
					ward_charge = rs.getFloat("ward_charge");
					test_charge = rs.getFloat("test_charge");
					treatment_charge = rs.getFloat("treatment_charge");
					fee_charge = rs.getFloat("fee_charge");
					total_charge = rs.getFloat("total_charge");
					start_date = rs.getString("start_date");
					end_date = rs.getString("end_date");
					settled = rs.getBoolean("settled");
					System.out.println("***************************");
					System.out.println("Showing billing account for patient with id " + patient_id);
					System.out.println("1.Patient ID: " + patient_id);
					System.out.println("2.SSN: " + ssn);
					System.out.println("3.Billing adress: " + billing_address);
					System.out.println("4.Payment method: " + payment_method);
					System.out.println("5.Card no: " + card_number);
					System.out.println("6.Check no: " + check_number);
					System.out.println("7.Insurance company name: " + insurance_company_name);
					System.out.println("8.Accomodation fee: " + accomodation_fee);
					System.out.println("9.Reistration fee: " + registration_fee);
					System.out.println("10.Medication_prescribed(true/false): " + medication_prescribed);
					System.out.println("13.Start date: " + start_date);
					System.out.println("14.End date: " + end_date);
					System.out.println("15.Settled(true/false): " + settled);
					System.out.println("***************************");
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		;
	}

	/**
	 * This method displays a record of the billing_accounts table given a
	 * patient_id and start_date
	 * 
	 * @param patient_id
	 * @param start_date
	 */
	public static void viewRecord(int patient_id, String start_date) {
		try {
			String sql2 = "select * from billing_accounts where patient_id=" + patient_id + " " + "and start_date='"
					+ start_date + "'";
			stmt = login.connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql2);
			if (!rs.next())
				System.out.println("You have entered a wrong value for ID or start date");
			else {
				do {
					patient_id = rs.getInt("patient_id");
					ssn = rs.getString("ssn");
					billing_address = rs.getString("billing_address");
					payment_method = rs.getString("payment_method");
					card_number = rs.getString("card_number");
					check_number = rs.getString("check_number");
					insurance_company_name = rs.getString("insurance_company_name");
					accomodation_fee = rs.getFloat("accomodation_fee");
					registration_fee = rs.getFloat("registration_fee");
					medication_prescribed = rs.getBoolean("medication_prescribed");
					start_date = rs.getString("start_date");
					end_date = rs.getString("end_date");
					settled = rs.getBoolean("settled");
					System.out.println("***************************");
					System.out.println("Showing billing account for patient with id " + patient_id);
					System.out.println("1.Patient ID: " + patient_id);
					System.out.println("2.SSN:" + ssn);
					System.out.println("3.Billing adress: " + billing_address);
					System.out.println("4.Payment method: " + payment_method);
					System.out.println("5.Card no: " + card_number);
					System.out.println("6.Check no: " + check_number);
					System.out.println("7.Insurance company name: " + insurance_company_name);
					System.out.println("8.Accomodation_fee: " + accomodation_fee);
					System.out.println("9.Registration_fee: " + registration_fee);
					System.out.println("10.Medication_prescribed(true/false): " + medication_prescribed);
					System.out.println("13.Start date: " + start_date);
					System.out.println("14.End date: " + end_date);
					System.out.println("15.Settled(true/false): " + settled);
					System.out.println("***************************");
				} while (rs.next());
			}

		} catch (SQLException e) {
		}
		;

	}

	/**
	 * This method displays the operations that can be performed on the
	 * billing_accounts table
	 */
	public static void billingAccountOptions() {
		Scanner sc = new Scanner(System.in);

		int selection = 0;

		do {
			System.out.println("1. View Billing Account Information for a specific patient id");
			System.out.println("2. View all Billing Accounts");
			System.out.println("3. Create Billing Account");
			System.out.println("4. Update billing Account");
			System.out.println("5. Delete billing Account");
			System.out.println("6. Settle Bills for Billing Account");
			System.out.println("7. Return to home screen");
			System.out.println("Select suitable option");
			System.out.println("Choose what operation you want to perform");
			selection = sc.nextInt();
			switch (selection) {
			case 1:
				viewBillingAccount();
				break;
			case 2:
				getAllRecords();
				break;
			case 3:
				createBillingAccount();
				break;
			case 4:
				updateBillingAccount();
				break;
			case 5:
				deleteBillingAccount();
				break;
			case 6:
				settleBillsForPatient();
			case 7:
				System.out.println("Back to home screen");
				break;
			case 8:
				updateAccomodationFee(1, "2018-04-06");
			default:
				System.out.println("You have entered a wrong option");
			}
		} while (selection != 7);

	}

	/**
	 * This method checks if a patient_id entered is valid
	 * 
	 * @param patient_id
	 * @return
	 */
	public static boolean isValidPatientId(int patient_id) {
		try {
			Statement stmt = login.connection.createStatement();
			String sql = "Select * from patients where id=" + patient_id;
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * This method checks if a billing_accout is valid given a patient_id and
	 * start_date
	 * 
	 * @param patient_id
	 * @param start_date
	 * @return
	 */
	public static boolean isValid(int patient_id, String start_date) {
		try {
			Statement stmt = login.connection.createStatement();
			String sql = "Select * from billing_accounts where patient_id=" + patient_id + " " + "and" + " " + ""
					+ "start_date=" + "'" + start_date + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method displays a record of the billing_accounts table
	 */
	private static void viewBillingAccount() {
		int selection = -1;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter id of the patient");
		System.out.println("Enter -1 to go back");
		selection = sc.nextInt();
		while (selection != -1) {
			patient_id = selection;
			System.out.println("Enter the start date of the patient: ");
			start_date = sc.next();
			if (isValid(patient_id, start_date))
				viewRecord(patient_id, start_date);
			else {
				System.out.println("Invalid patient id or start date");
			}
			System.out.println("Enter patient id of patient or -1 to go back");
			selection = sc.nextInt();
		}
	}

	/**
	 * This method returns a boolean value if a bed is assigned to the given
	 * patient_id
	 * 
	 * @param patient_id
	 * @param start_date
	 * @return
	 */
	private static boolean assignedBed(int patient_id, String start_date) {
		int bed_id = 0;
		try {
			Statement stmt = login.statement;
			String sql = "Select bed_id from check_in_info where patient_id=" + patient_id + " and start_date=" + "'"
					+ start_date + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
				bed_id = rs.getInt("bed_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (bed_id > 0)
			return true;
		else
			return false;

	}

	/**
	 * This method creates a record in the billing_accounts table
	 */
	private static void createBillingAccount() {
		int option = 13;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter patient_id or -1 to o back");
		int selection = sc.nextInt();
		while (selection != -1) {
			patient_id = selection;
			if (!isValidPatientId(patient_id)) {
				System.out.println("No such patient exists");
			} else {
				int yes_billing = 0;
				System.out.println("Enter start date");
				start_date = sc.next();
				if (isValid(patient_id, start_date)) {
					System.out.println("A billing account for this patient with this start date already exists");
					yes_billing = 1;
				} else if (assignedBed(patient_id, start_date) == true) {
					System.out.println("Bed is already assigned to the patient");
					yes_billing = 2;
				} else if (assignedBed(patient_id, start_date) == false) {
					System.out.println("Checking hospital space");
					Statement stmt = login.statement;
					try {
						ResultSet rs = stmt.executeQuery("SELECT * FROM beds WHERE reserved = 0;");
						if (rs.next() == false) {
							System.out.println("No space in the hospital...Cannot create billing account");
							yes_billing = 3;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

				if (yes_billing == 2 || yes_billing == 0) {
					if (yes_billing == 0) {
						System.out.println("There is space in the hospital, proceeding with creation of billing account");

					} else {
						System.out.println("proceeding with creation of billing account");

					}
					System.out.println();
					initialize();
					int selection2 = 0;
					while (selection2 != -1 && selection2 != 12) {
						System.out.println(
								"Enter the field number you want to insert OR enter 12 to submit the user OR enter -1 to go back");
						System.out.println("1.SSN");
						System.out.println("2.Billing Address");
						System.out.println("3.Payment method");
						System.out.println("4.Card no.");
						System.out.println("5.Check no");
						System.out.println("6.Insurance company name");
						System.out.println("7.Accomodation fee");
						System.out.println("8.Registration fee");
						System.out.println("9.Medication prescribed(true/false)");
						System.out.println("10.End date");
						System.out.println("11.Settled(true/false)");
						System.out.println("12.To insert the record");
						System.out.println("Selection:");
						selection2 = sc.nextInt();
						sc.nextLine();
						switch (selection2) {
						case 1:
							System.out.println("Enter the SSN");
							ssn = sc.next();
							break;
						case 2:
							System.out.println("Enter the billing address");
							billing_address = sc.nextLine();
							break;
						case 3:
							System.out.println("Enter the payment method");
							payment_method = sc.nextLine();
							break;
						case 4:
							System.out.println("Enter the card no.");
							card_number = sc.next();
							break;
						case 5:
							System.out.println("Enter the check no.");
							check_number = sc.next();
							break;
						case 6:
							System.out.println("Enter the insurance company name");
							insurance_company_name = sc.nextLine();
							break;
						case 7:
							System.out.println("Enter the accomodation fee");
							accomodation_fee = sc.nextFloat();
							break;
						case 8:
							System.out.println("Enter the registration fee");
							registration_fee = sc.nextFloat();
							break;
						case 9:
							System.out.println("Enter the medication prescribed");
							medication_prescribed = sc.nextBoolean();
							break;
						case 10:
							System.out.println("Enter the end date");
							end_date = sc.next();
							break;
						case 11:
							System.out.println("Enter boolean value for settled");
							settled = sc.nextBoolean();
							break;
						case 12:
							break;
						case -1:
							break;
						default:
							System.out.println("You have entered an invaid selection");
							break;
						}
					}
					if (selection2 != -1) {

						try {
							conn = login.connection;
							PreparedStatement ps = login.connection.prepareStatement(
									"Insert into billing_accounts (patient_id,ssn,billing_address,payment_method,card_number,check_number,insurance_company_name,accomodation_fee,registration_fee,medication_prescribed,start_date,end_date,settled) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps.setInt(1, patient_id);
							ps.setString(2, ssn);
							ps.setString(3, billing_address);
							ps.setString(4, payment_method);
							ps.setString(5, card_number);
							ps.setString(6, check_number);
							ps.setString(7, insurance_company_name);
							ps.setFloat(8, accomodation_fee);
							ps.setFloat(9, registration_fee);
							ps.setBoolean(10, medication_prescribed);
							ps.setString(11, start_date);
							ps.setString(12, end_date);
							ps.setBoolean(13, settled);
							int rows = ps.executeUpdate();
							if (rows == 1) {
								System.out.println("Your record has been successfully inserted");
							} else {
								System.out.println("Record has not ben inserted. Please check the console output");
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
						;
						viewRecord(patient_id, start_date);
					}
				}
			}
			// catch (SQLException e) {
			// e.printStackTrace();
			// }
			System.out.println("Enter patient id and start date or -1 to go back");
			selection = sc.nextInt();
		}

	}

	// }

	// }

	/**
	 * This method settles bills for a record in the billing_account table given a
	 * patient_id and a start_date
	 * 
	 * @param patientId
	 * @param start_date
	 */
	public static void settleBills(int patientId, String start_date) {
		if (!isValid(patientId, start_date)) {
			System.out.println("Patient ID or Start date entered is wrong");
		} else {
			try {
				Connection conn = login.connection;
				PreparedStatement ps = conn
						.prepareStatement("Update billing_accounts set settled=? where patient_id=? and start_date=?");
				ps.setBoolean(1, true);
				ps.setInt(2, patientId);
				ps.setString(3, start_date);
				int rows = ps.executeUpdate();
				if (rows > 0) {
					System.out.println("Successfully updated the billing account");
				} else {
					System.out.println("Updation failed, please check the console log");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method returns a boolean value if a record in the billing_account table
	 * is settled
	 * 
	 * @param patientId
	 * @param start_date
	 * @return
	 */
	public static boolean isSettled(int patientId, String start_date) {
		boolean b = false;
		if (!isValid(patientId, start_date)) {
			System.out.println("Please check you patient ID or start date");
		} else {
			try {
				Connection conn = login.connection;
				PreparedStatement ps = conn
						.prepareStatement("Select * from billing_accounts where patient_id=? and start_date=?");
				ps.setInt(1, patientId);
				ps.setString(2, start_date);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					do {

						b = rs.getBoolean("settled");
						System.out.println(b);
					} while (rs.next());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return b;

	}

	/**
	 * This method updates a record in the billing_account table
	 */
	private static void updateBillingAccount() {
		Scanner sc = new Scanner(System.in);
		int selection = 0;
		int selection2 = 0;

		System.out.println("Enter patient id: ");
		System.out.println("Enter -1 to go back to the Billing Account menu: ");
		selection = sc.nextInt();
		while (selection != -1) {
			patient_id = selection;
			System.out.println("Enter start date");
			start_date = sc.next();
			if (!isValid(patient_id, start_date)) {
				System.out.println("No billing record with this patient id and start date:");
			} else {
				int identifier = 0;
				try {
					String sql1 = "Select id from billing_accounts where patient_id=" + patient_id + " and start_date=" + "'"
							+ start_date + "'";
					Statement stmt1 = login.statement;
					ResultSet rs1 = stmt1.executeQuery(sql1);
					if (rs1.next() == true)
						identifier = rs1.getInt("id");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				;

				viewRecord(patient_id, start_date);
				selection2 = 0;
				while (selection2 != 13 && selection2 != -1) {
					System.out.println(
							"Enter the field number you want to update OR enter 12 to submit the user OR enter -1 to go back");
					System.out.println("1.SSN");
					System.out.println("2.Billing Address");
					System.out.println("3.Payment method");
					System.out.println("4.Card no.");
					System.out.println("5.Check no");
					System.out.println("6.Insurance company name");
					System.out.println("7.Accomodation fee");
					System.out.println("8.Registration fee");
					System.out.println("9.Medication prescribed(true/false)");
					System.out.println("10.Start date");
					System.out.println("11.End date");
					System.out.println("12.Settled");
					System.out.println("13.To insert the record");
					System.out.println("Selection:");
					selection2 = sc.nextInt();
					sc.nextLine();
					switch (selection2) {
					case 1:
						System.out.println("Enter the SSN");
						ssn = sc.next();
						break;
					case 2:
						System.out.println("Enter the billing address");
						billing_address = sc.nextLine();
						break;
					case 3:
						System.out.println("Enter the payment method");
						payment_method = sc.nextLine();
						break;
					case 4:
						System.out.println("Enter the card no.");
						card_number = sc.next();
						break;
					case 5:
						System.out.println("Enter the check no.");
						check_number = sc.next();
						break;
					case 6:
						System.out.println("Enter the insurance company name");
						insurance_company_name = sc.nextLine();
						break;
					case 7:
						System.out.println("Enter the accomodation fee");
						accomodation_fee = sc.nextFloat();
						break;
					case 8:
						System.out.println("Enter the registration fee");
						registration_fee = sc.nextFloat();
						break;
					case 9:
						System.out.println("Enter the medication prescribed");
						medication_prescribed = sc.nextBoolean();
						break;
					case 10:
						System.out.println("Enter start date");
						start_date = sc.next();
						break;
					case 11:
						System.out.println("Enter the end date");
						end_date = sc.next();
						break;
					case 12:
						System.out.println("Enter boolean value for settled");
						settled = sc.nextBoolean();
						break;
					case 13:
						break;
					case -1:
						break;
					default:
						System.out.println("You have entered an invaid selection");
						break;
					}
				}
				if (selection2 != -1) {
					try {
						conn = login.connection;
						PreparedStatement ps = login.connection.prepareStatement(
								"Update billing_accounts set ssn=?,billing_address=?,payment_method=?,card_number=?,check_number=?,insurance_company_name=?,accomodation_fee=?,registration_fee=?,medication_prescribed=?,start_date=?,end_date=?,settled=? where id=?");
						ps.setString(1, ssn);
						ps.setString(2, billing_address);
						ps.setString(3, payment_method);
						ps.setString(4, card_number);
						ps.setString(5, check_number);
						ps.setString(6, insurance_company_name);
						ps.setFloat(7, accomodation_fee);
						ps.setFloat(8, registration_fee);
						ps.setBoolean(9, medication_prescribed);
						ps.setString(10, start_date);
						ps.setString(11, end_date);
						ps.setBoolean(12, settled);
						ps.setInt(13, identifier);
						int rows = ps.executeUpdate();
						if (rows > 0) {
							System.out.println("Your record has been successfully inserted");
						} else {
							System.out.println("Record has not been updated, check console output");
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					;
					viewRecord(patient_id, start_date);
				}
			}
			System.out.println("Enter patient id and start date or -1 to go back");
			selection = sc.nextInt();
		}
	}

	/**
	 * This method is for settling bills of a record in the billing_accounts table
	 */
	public static void settleBillsForPatient() {
		Scanner sc = new Scanner(System.in);
		int selection = 0;
		System.out.println("Enter the patient id or enter -1 to go back");
		System.out.println("Patient id:");
		selection = sc.nextInt();
		while (selection != -1) {
			patient_id = selection;
			System.out.println("Enter the start date");
			start_date = sc.next();
			settleBills(patient_id, start_date);
			if (isSettled(patient_id, start_date))
				System.out.println("Returned true");
			else
				System.out.println("returned false");
			System.out.println("Enter patient id or -1 to go back");
			selection = sc.nextInt();
		}
	}

	/**
	 * This method updates accommodation fee of a medical record given a patient_id
	 * and a start_date
	 * 
	 * @param patient_id
	 * @param start_date
	 */
	public static void updateAccomodationFee(int patient_id, String start_date) {
		float ward_charge = 0;
		if (!isValid(patient_id, start_date))
			System.out.println("No record exists with this id and start date");
		else {
			try {
				Connection conn = login.connection;
				Statement stmt = conn.createStatement();
				String sql = "Select charges_per_day from wards w inner join (Select ward_id from check_in_info where patient_id="
						+ patient_id + " and start_date='" + start_date + "'" + ")" + "as c on w.id=c.ward_id";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {

					ward_charge = rs.getFloat("charges_per_day");
					stmt = conn.createStatement();
					sql = "Select start_date,end_date from billing_accounts where patient_id=" + patient_id + " and start_date="
							+ "'" + start_date + "'";
					rs = stmt.executeQuery(sql);
					if (rs.next()) {
						String st = rs.getString("start_date");
						String end = rs.getString("end_date");
						if (end != null) {
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
							java.util.Date sdate = (java.util.Date) formatter.parse(st);
							java.util.Date edate = (java.util.Date) formatter.parse(end);
							long diff = edate.getTime() - sdate.getTime();
							long days = (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) + 1;
							if (days > 0) {
								float total_ward_charge = ward_charge * days;
								System.out.println("Total ward charge is: " + total_ward_charge);
								PreparedStatement ps = conn.prepareStatement(
										"Update billing_accounts set accomodation_fee=? where patient_id=? and start_date=?");
								ps.setFloat(1, total_ward_charge);
								ps.setInt(2, patient_id);
								ps.setString(3, start_date);
								int rows = ps.executeUpdate();
								if (rows > 0) {
									System.out.println("Updated accomodation fee");
								} else {
									System.out.println("Accomodation Fee updation failed please check your inputs and try again");
								}

							} else {
								System.out.println("Zero days in the hospital. Patient wont be charged for the ward fees");
							}
						} else {
							System.out.println("End date is null please update the end date first");
						}
					}
				} else {
					System.out.println("Query returned 0 results");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * This method deletes a record in the billing_accounts table
	 */
	private static void deleteBillingAccount() {
		Scanner sc = new Scanner(System.in);
		int selection = 0;
		System.out.println("Enter the patient id or enter -1 to go back");
		System.out.println("Patient id:");
		selection = sc.nextInt();
		while (selection != -1) {
			patient_id = selection;
			System.out.println("Enter the start date");
			start_date = sc.next();
			if (!isValid(patient_id, start_date)) {
				System.out.println("Billing Account with this patient id and strat date does  not exist");
			} else {
				try {
					Connection conn = login.connection;
					PreparedStatement ps = conn
							.prepareStatement("Delete from billing_accounts where patient_id=? and start_date=?");
					ps.setInt(1, patient_id);
					ps.setString(2, start_date);
					int rows = ps.executeUpdate();
					if (rows > 0) {
						System.out.println("This billing account was successfully deleted");
					} else {
						System.out.println("Deletion failed, please check the console output");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Enter patient id or -1 to go back");
			selection = sc.nextInt();
		}

	}

}
