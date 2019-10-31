import java.util.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * This java file is sort of the main navigation hub for the user. It calls the
 * home menu and all the menus for the db tables
 */
public class UsersInterface {

    static Scanner scan = new Scanner(System.in);
    static int selection = 0;

    /**
     * Checks if there is room to admit a hospital by showing available beds and
     * wards
     */
    public static void checkHospitalSpace() {

        do {
            // sql statemnt that shows empty beds and wards in the hospital

            System.out.println("1. Go Back");

            selection = scan.nextInt();
            scan.nextLine();

            switch (selection) {
            case 1:
                System.out.println("Back to Home menu");
                break;
            default:
                System.out.println("Selection is invalid");
                break;
            }

        } while (selection != 1);

    }

    /**
     * Prints the Home Menu options that a user can do
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static void callUI() throws ClassNotFoundException, SQLException, ParseException {

        do {

            HomeMenu.homeMenuOptions();
            // tableOptions();

            System.out.println("Make a selection: ");

            selection = scan.nextInt();
            scan.nextLine();
            System.out.println();

            switch (selection) {

            case 1:
                PatientMenu.patientOptions();
                break;
            case 2:
                StaffMenu.staffOptions();
                break;
            case 3:
                // checkHospitalSpace();
                CheckHospitalSpace.checkHospital();
                break;
            case 4:
                CheckInMenu.checkInOptions();
                break;
            case 5:
                BillingAccount.billingAccountOptions();
                break;
            case 6:
                MedicalRecord.medicalRecordOptions();
                break;
            case 7:
                Test.testOptions();
                break;
            case 8:
                Treatment.treatmentOptions();
                break;
            case 9:
                Wards.WardOptions();
                break;
            case 10:
                Beds.bedOptions();
                break;
            case 11:
                TestForPatients.Tests_for_patientsOptions();
                break;
            case 12:
                reportMenu.reportOptions();
                break;
            case 13:
                System.out.println("System Exited");
                break;
            default:
                System.out.println("The selection is invalid");
                break;
            }

        } while (selection != 13);

    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {

        System.out.println("Welcome to the Hospital");

        // Scanner scan = new Scanner(System.in);

        // int selection = 0;

        do {

            HomeMenu.homeMenuOptions();
            // tableOptions();

            System.out.println("Make a selection: ");

            selection = scan.nextInt();
            System.out.println();
            switch (selection) {

            case 1:
                PatientMenu.patientOptions();
                break;
            case 2:
                StaffMenu.staffOptions();
                break;
            case 3:
                // checkHospitalSpace();
                CheckHospitalSpace.checkHospital();
                break;
            case 4:
                CheckInMenu.checkInOptions();
                break;
            case 5:
                BillingAccount.billingAccountOptions();
                break;
            case 6:
                MedicalRecord.medicalRecordOptions();
                break;
            case 7:
                Test.testOptions();
                break;
            case 8:
                Treatment.treatmentOptions();
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                System.out.println("System Exited");
                break;
            default:
                System.out.println("The selection is invalid");
                break;
            }

        } while (selection != 13);

    }
}
