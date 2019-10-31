import java.util.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.sql.*;

/**
 * This java file holds all the menu options for the Patients Table
 */
public class PatientMenu {

    // Scanner object to read command line input
    static Scanner scan = new Scanner(System.in);
    static int selection = 0;

    // Patient attribute variables
    static String name = "";
    static String dob = "";
    static String gender = "";
    static String phone = "";
    static String address = "";
    static String ssn = "";
    static String status = "";
    static int wardId = 0;
    static int treatmentPlan = 0;
    static int completingTreatment;
    static int patientId = 0;
    static int age = 0;
    static String in_ward = "no";

    /**
     * Prints patient info for the user
     * 
     * @param result
     * @throws SQLException
     */
    public static void printPatientInfo(ResultSet result) throws SQLException {
        if (!result.next()) {
            System.out.println("No Patient with this id exists");
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
    }

    /**
     * This method lets a user find patients by ID
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static void view() throws ClassNotFoundException, SQLException, ParseException {
        // Scanner scan = new Scanner(System.in);
        // int selection;
        do {
            System.out.println("Enter 0 to go back to Patient Menu");
            System.out.println("Enter patient ID to view patient");
            System.out.println();
            System.out.println("Patient ID: ");

            selection = scan.nextInt();
            scan.nextLine();

            if (selection > 0) {
                ResultSet result = PatientSQL.viewPatientById(selection);
                printPatientInfo(result);
                result.close();
                System.out.println();
            } else if (selection < 0) {
                System.out.println("The selection is invalid");
            } else if (selection == 0) {
                System.out.println("Back to Patient Menu");
                break;
            }

        } while (selection != 0);
    }

    /**
     * This method shows all patients in the db
     * 
     * @throws ParseException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void viewAll() throws ClassNotFoundException, SQLException, ParseException {
        do {

            ResultSet result = PatientSQL.viewAllPatients();
            printPatientInfo(result);
            result.close();

            System.out.println("1. Back to Patient Menu");

            selection = scan.nextInt();
            scan.nextLine();

            switch (selection) {
            case 1:
                System.out.println("Back to Patient Menu");
                break;
            default:
                System.out.println("Selection not valid");
                break;

            }

        } while (selection != 1);
    }

    /**
     * This method deletes a patient by id
     */
    public static void delete() {
        do {
            System.out.println("Enter 0 to go back to Patient Menu");
            System.out.println("Enter patient ID to delete patient");
            System.out.println();
            System.out.println("Patient ID: ");

            selection = scan.nextInt();
            scan.nextLine();

            if (selection > 0) {
                // sql drop statement
                PatientSQL.deletePatientById(selection);
                System.out.println();
            } else if (selection < 0) {
                System.out.println("The selection is invalid");
            } else if (selection == 0) {
                System.out.println("Back to Patient Menu");
                break;
            }

        } while (selection != 0);
    }

    /**
     * This method edits a patient's info
     */
    public static void edit() throws ClassNotFoundException, SQLException, ParseException {

        int selection2 = 0;

        do {
            System.out.println("Enter 0 to go back to Patient Menu");
            System.out.println("Enter Patient ID to edit:");

            selection = scan.nextInt();
            scan.nextLine();
            System.out.println();

            if (selection > 0) {
                ResultSet result = PatientSQL.viewPatientById(selection);
                if (!result.next()) {
                    System.out.println("No Patient with this id exists");
                    System.out.println();
                } else {
                    do {
                        name = result.getString("name");
                        dob = result.getString("dateOfBirth");
                        gender = result.getString("gender");
                        phone = result.getString("phone");
                        address = result.getString("address");
                        ssn = result.getString("ssn");
                        status = result.getString("status");
                        wardId = result.getInt("ward_id");
                        patientId = result.getInt("id");
                        age = result.getInt("age");
                        completingTreatment = result.getInt("completing_treatment");
                        in_ward = result.getString("in_ward");
                        treatmentPlan = result.getInt("processing_treatment_plan");
                    } while (result.next());

                    do {
                        System.out.println("1. Name: " + name);
                        System.out.println("2. Date of Birth: " + dob);
                        System.out.println("3. Gender: " + gender);
                        System.out.println("4. Phone: " + phone);
                        System.out.println("5. Address: " + address);
                        System.out.println("6. SSN: " + ssn);
                        System.out.println("7. Status: " + status);
                        System.out.println("8. Ward ID: " + wardId);
                        System.out.println("9. Processing Treatment Plan: " + treatmentPlan);
                        System.out.println("10. Completing Treatment? (1-Yes, 0-No): " + completingTreatment);
                        System.out.println("11. Patient ID: " + patientId);
                        System.out.println("12. Patient Age: " + age);
                        System.out.println("13. In Ward: " + in_ward);
                        System.out.println("14. Submit edited user");
                        System.out.println("15. Back to Edit Another Patient");
                        System.out.println();
                        System.out.println("Current Input will show next to attribute");
                        System.out.println();
                        System.out.println("Enter # to edit info: ");

                        selection2 = scan.nextInt();
                        scan.nextLine();
                        System.out.println();

                        switch (selection2) {
                        case 1:
                            System.out.println("Enter name:");
                            name = scan.nextLine();
                            break;
                        case 2:
                            System.out.println("Enter Date of Birth (YYYY-MM-DD):");
                            dob = scan.nextLine();
                            break;
                        case 3:
                            System.out.println("Enter Gender (M/F):");
                            gender = scan.nextLine();
                            break;
                        case 4:
                            System.out.println("Enter Phone Number (10 digits):");
                            phone = scan.nextLine();
                            break;
                        case 5:
                            System.out.println("Enter Address:");
                            address = scan.nextLine();
                            break;
                        case 6:
                            System.out.println("Enter SSN (9 digits)");
                            ssn = scan.nextLine();
                            break;
                        case 7:
                            System.out.println("Enter patient's status in hospital");
                            status = scan.nextLine();
                            break;
                        case 8:
                            System.out.println("Enter Ward ID that patient is in:");
                            wardId = scan.nextInt();
                            scan.nextLine();
                            break;
                        case 9:
                            System.out.println("Enter Treatment Plan ID:");
                            treatmentPlan = scan.nextInt();
                            scan.nextLine();
                            break;
                        case 10:
                            System.out.println("Is Patient Completing Treatment (1-Yes, 0-No):");
                            wardId = scan.nextInt();
                            scan.nextLine();
                            break;
                        case 11:
                            System.out.println("Enter Patiend ID:");
                            patientId = scan.nextInt();
                            scan.nextLine();
                            break;
                        case 12:
                            System.out.println("Enter Patient's Age:");
                            age = scan.nextInt();
                            scan.nextLine();
                            break;
                        case 13:
                            System.out.println("Is Patient in a ward (Yes/No):");
                            in_ward = scan.nextLine();
                            break;
                        case 14:
                            // send sql param for statement
                            // return entry of new patient
                            PatientSQL.editPatient(selection, name, dob, gender, phone, address, ssn, status, wardId,
                                    treatmentPlan, completingTreatment, patientId, age, in_ward);
                            System.out.println();
                            ResultSet result2 = PatientSQL.viewPatientById(patientId);
                            printPatientInfo(result2);
                            result2.close();
                            System.out.println();
                            name = "";
                            dob = "";
                            gender = "";
                            phone = "";
                            address = "";
                            ssn = "";
                            status = "";
                            wardId = 0;
                            completingTreatment = 0;
                            treatmentPlan = 0;
                            patientId = 0;
                            age = 0;
                            in_ward = "no";
                            System.out.println();
                            break;
                        case 15:
                            System.out.println("Back to Edit Patient menu");

                            name = "";
                            dob = "";
                            gender = "";
                            phone = "";
                            address = "";
                            ssn = "";
                            status = "";
                            wardId = 0;
                            completingTreatment = 0;
                            treatmentPlan = 0;
                            patientId = 0;
                            age = 0;
                            in_ward = "no";
                            break;
                        default:
                            System.out.println("The selection is invalid");
                            break;
                        }
                        System.out.println();
                    } while (selection2 != 14 && selection2 != 15);
                }
                result.close();
            }

        } while (selection != 0);
    }

    /**
     * This method takes input for adding a patient
     * 
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void add() throws ParseException, ClassNotFoundException, SQLException {

        do {

            System.out.println("1. Name: " + name);
            System.out.println("2. Date of Birth: " + dob);
            System.out.println("3. Gender: " + gender);
            System.out.println("4. Phone: " + phone);
            System.out.println("5. Address: " + address);
            System.out.println("6. SSN: " + ssn);
            System.out.println("7. Status: " + status);
            System.out.println("8. Ward ID: " + wardId);
            System.out.println("9. Processing Treatment Plan: " + treatmentPlan);
            System.out.println("10. Completing Treatment? (1-Yes, 0-No): " + completingTreatment);
            System.out.println("11. Patient ID: " + patientId);
            System.out.println("12. Patient Age: " + age);
            System.out.println("13. In Ward: " + in_ward);
            System.out.println("14. Submit new user");
            System.out.println("15. Go back to previous screen");
            System.out.println();
            System.out.println("Current Input will show next to attribute");
            System.out.println();
            System.out.println("Enter # to add info:");

            selection = scan.nextInt();
            scan.nextLine();

            switch (selection) {
            case 1:
                System.out.println("Enter name:");
                name = scan.nextLine();
                break;
            case 2:
                System.out.println("Enter Date of Birth (YYYY-MM-DD):");
                dob = scan.nextLine();
                break;
            case 3:
                System.out.println("Enter Gender (M/F):");
                gender = scan.nextLine();
                break;
            case 4:
                System.out.println("Enter Phone Number (10 digits):");
                phone = scan.nextLine();
                break;
            case 5:
                System.out.println("Enter Address:");
                address = scan.nextLine();
                break;
            case 6:
                System.out.println("Enter SSN (9 digits)");
                ssn = scan.nextLine();
                break;
            case 7:
                System.out.println("Enter patient's status in hospital");
                status = scan.nextLine();
                break;
            case 8:
                System.out.println("Enter Ward ID that patient is in:");
                wardId = scan.nextInt();
                scan.nextLine();
                break;
            case 9:
                System.out.println("Enter Treatment Plan ID:");
                treatmentPlan = scan.nextInt();
                scan.nextLine();
                break;
            case 10:
                System.out.println("Is patient completing treatment (1-Yes, 0-No):");
                completingTreatment = scan.nextInt();
                scan.nextLine();
                break;
            case 11:
                System.out.println("Enter Patiend ID:");
                patientId = scan.nextInt();
                scan.nextLine();
                break;
            case 12:
                System.out.println("Enter Patient's Age:");
                age = scan.nextInt();
                scan.nextLine();
                break;
            case 13:
                System.out.println("Is Patient in Ward (Yes/No):");
                in_ward = scan.nextLine();
                break;
            case 14:
                // send sql param for statement
                // return entry of new patient
                PatientSQL.addPatient(name, dob, gender, phone, address, ssn, status, wardId, treatmentPlan,
                        completingTreatment, patientId, age, in_ward);
                System.out.println();
                ResultSet result = PatientSQL.viewPatientById(patientId);
                printPatientInfo(result);
                result.close();
                System.out.println();

                name = "";
                dob = "";
                gender = "";
                phone = "";
                address = "";
                ssn = "";
                status = "";
                wardId = 0;
                treatmentPlan = 0;
                completingTreatment = 0;
                patientId = 0;
                age = 0;
                in_ward = "no";
                break;
            case 15:
                System.out.println("Back to Patient menu");
                System.out.println();
                name = "";
                dob = "";
                gender = "";
                phone = "";
                address = "";
                ssn = "";
                status = "";
                wardId = 0;
                treatmentPlan = 0;
                completingTreatment = 0;
                patientId = 0;
                age = 0;
                in_ward = "no";
                break;
            default:
                System.out.println("The selection is invalid");
                break;
            }
        } while (selection != 15);
    }

    /**
     * This methods releases a patient from the hospital
     * 
     * Updates their patient record, bed info, medical record, check in info, and
     * billing account record
     * 
     * @throws ClassNotFoundException
     */
    public static void releasePatient() throws ParseException, ClassNotFoundException {

        int patientId = 0;
        String startDate = "";
        String endDate = "";
        do {

            System.out.println("1. Patient ID: " + patientId);
            System.out.println("2. Start Date of Visit: " + startDate);
            System.out.println("3. End Date of Visit: " + endDate);
            System.out.println("4. Submit");
            System.out.println("5. Go back to Patient Menu");
            System.out.println();
            System.out.println("Current Input will show next to attribute");
            System.out.println();
            System.out.println("Enter # to edit info:");

            selection = scan.nextInt();
            scan.nextLine();

            switch (selection) {
            case 1:
                System.out.println("Enter Patient ID:");
                patientId = scan.nextInt();
                break;
            case 2:
                System.out.println("Enter Start Date of Visit (YYYY-MM-DD):");
                startDate = scan.nextLine();
                break;
            case 3:
                System.out.println("Enter End Date of Visit (YYYY-MM-DD):");
                endDate = scan.nextLine();
                break;
            case 4:
                // send sql param for statement
                // return confirmation
                PatientSQL.releasePatient(patientId, startDate, endDate);
                patientId = 0;
                startDate = "";
                endDate = "";
                System.out.println();
                break;
            case 5:
                System.out.println("Back to Patient menu");
                break;
            default:
                System.out.println("The selection is invalid");
                break;
            }

        } while (selection != 5);
    }

    /**
     * This methods prints all the tests that a patient has taken based on their id
     * 
     * @throws SQLException
     */
    public static void testsPatientHasTaken() throws SQLException {
        do {
            System.out.println("Enter 0 to go back");
            System.out.println("Enter patient ID to view what tests they have taken");
            System.out.println();
            System.out.println("Patient ID: ");

            selection = scan.nextInt();
            scan.nextLine();

            if (selection > 0) {
                // sql select statement
                ResultSet result = PatientSQL.testsPatientHasTaken(selection);
                if (!result.next()) {
                    System.out.println("No Patient with this id exists");
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
                System.out.println();
            } else if (selection < 0) {
                System.out.println("The selection is invalid");
            } else if (selection == 0) {
                System.out.println("Back to Patient Menu");
                break;
            }

        } while (selection != 0);
    }

    /**
     * This prints all the patient menu options for that a user can do
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static void patientOptions() throws ClassNotFoundException, SQLException, ParseException {

        do {

            System.out.println("Patient Menu");
            System.out.println();
            System.out.println("1. View Patient By ID");
            System.out.println("2. View All Patients");
            System.out.println("3. Add");
            System.out.println("4. Edit");
            System.out.println("5. Delete");
            System.out.println("6. Tests Patients Have Taken");
            System.out.println("7. Release Patients");
            System.out.println("8. Back to Home Menu");

            System.out.println("Make a selection: ");

            selection = scan.nextInt();

            switch (selection) {
            case 1:
                view();
                break;
            case 2:
                viewAll();
                break;
            case 3:
                add();
                break;
            case 4:
                edit();
                break;
            case 5:
                delete();
                break;
            case 6:
                testsPatientHasTaken();
                break;
            case 7:
                releasePatient();
                break;
            case 8:
                System.out.println("Back to Home menu");
                break;
            default:
                System.out.println("The selection is invalid");
                break;
            }

        } while (selection != 8);
    }
}