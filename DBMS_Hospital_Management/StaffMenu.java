import java.util.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * This java file has all the menu options for the Staff table
 */
public class StaffMenu {
    static Scanner scan = new Scanner(System.in);
    static int selection = 0;

    static String name = "";
    static int age = 0;
    static String gender = "";
    static String phone = "";
    static String address = "";
    static String jobTitle = "";
    static String department = "";
    static String professionalTitle = "";
    static int staffId = 0;

    /**
     * Prints staff info for user
     * 
     * @param result
     * @throws SQLException
     */
    public static void printStaffInfo(ResultSet result) throws SQLException {
        if (!result.next()) {
            System.out.println("No Staff Member with this id exists");
        } else {
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            do {
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
     * Views a staff member in the DB by their id
     */
    public static void viewById() throws ClassNotFoundException, SQLException, ParseException {
        do {

            System.out.println("Enter staff ID to view patient");
            System.out.println("Enter 0 to go back");

            System.out.println("Staff ID: ");

            selection = scan.nextInt();
            scan.nextLine();

            if (selection > 0) {
                // sql select statement
                ResultSet result = StaffSQL.viewStaffById(selection);
                printStaffInfo(result);
                System.out.println();
                result.close();

                System.out.println();
            } else if (selection < 0) {
                System.out.println("The selection is invalid");
            } else if (selection == 0) {
                System.out.println("Back to Staff screen");
                break;
            }

        } while (selection != 0);
    }

    /**
     * Views all staff members by their job title
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static void viewByJobTitle() throws ClassNotFoundException, SQLException, ParseException {

        String job = "";
        do {

            System.out.println("1. Job Title: " + job);
            System.out.println("2. Submit");
            System.out.println("3. Back to Staff");
            System.out.println("Enter # for option: ");

            selection = scan.nextInt();
            scan.nextLine();

            switch (selection) {
            case 1:
                System.out.println("Enter Job Title:");
                job = scan.nextLine();
                break;
            case 2:
                // sql stuff
                // return entries
                ResultSet result = StaffSQL.viewStaffByJobTitle(job);
                printStaffInfo(result);
                System.out.println();
                result.close();

                job = "";
                jobTitle = "";
                break;
            case 3:
                System.out.println("Back to Staff Menu");
                job = "";
                jobTitle = "";
                break;
            default:
                System.out.println("Selection not valid");
                break;

            }

        } while (selection != 3);
    }

    /**
     * Views all staff members in the DB
     */
    public static void viewAllStaff() throws ClassNotFoundException, SQLException, ParseException {
        do {

            ResultSet result = StaffSQL.viewAllStaff();
            printStaffInfo(result);
            System.out.println();
            result.close();

            System.out.println("1. Back to Staff Menu");

            selection = scan.nextInt();
            scan.nextLine();

            switch (selection) {
            case 1:
                System.out.println("Back to Staff Menu");
                break;
            default:
                System.out.println("Selection not valid");
                break;

            }

        } while (selection != 1);

    }

    /**
     * Deletes a staff member in the DB
     */
    public static void delete() {
        do {

            System.out.println("Enter staff ID to delete staff member");
            System.out.println("Enter 0 to go back");

            System.out.println("Staff ID: ");

            selection = scan.nextInt();
            scan.nextLine();

            if (selection > 0) {
                // sql drop statement
                StaffSQL.deleteStaffById(selection);
            } else if (selection < 0) {
                System.out.println("The selection is invalid");
            } else if (selection == 0) {
                System.out.println("Back to Staff screen");
                break;
            }
            System.out.println();
        } while (selection != 0);
    }

    /**
     * Edits a staff member in the DB
     * 
     * @throws ParseException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void edit() throws ClassNotFoundException, SQLException, ParseException {

        int selection2 = 0;

        do {

            System.out.println("Enter 0 to go back to Staff menu");
            System.out.println("Enter Staff ID to edit:");

            selection = scan.nextInt();
            scan.nextLine();
            System.out.println();

            if (selection > 0) {
                ResultSet result = StaffSQL.viewStaffById(selection);
                if (!result.next()) {
                    System.out.println("No Staff with this id exists");
                    System.out.println();
                } else {
                    do {

                        staffId = result.getInt("id");
                        name = result.getString("name");
                        age = result.getInt("age");
                        gender = result.getString("gender");
                        phone = result.getString("phone");
                        address = result.getString("address");
                        jobTitle = result.getString("jobTitle");
                        department = result.getString("department");
                        professionalTitle = result.getString("professionalTitle");
                    } while (result.next());
                    do {
                        System.out.println("1. Name: " + name);
                        System.out.println("2. Age: " + age);
                        System.out.println("3. Gender: " + gender);
                        System.out.println("4. Phone: " + phone);
                        System.out.println("5. Address: " + address);
                        System.out.println("6. Job Title: " + jobTitle);
                        System.out.println("7. Department: " + department);
                        System.out.println("8. Professional Title: " + professionalTitle);
                        System.out.println("9. Staff ID: " + staffId);
                        System.out.println("10. Submit edited staff");
                        System.out.println("11. Go back to edit anothe staff member");

                        System.out.println("Enter # to edit info:");
                        System.out.println("Current Input will show next to attribute");

                        selection2 = scan.nextInt();
                        scan.nextLine();

                        switch (selection2) {
                        case 1:
                            System.out.println("Enter name:");
                            name = scan.nextLine();
                            break;
                        case 2:
                            System.out.println("Enter Age:");
                            age = scan.nextInt();
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
                            System.out.println("Enter Job Title:");
                            jobTitle = scan.nextLine();
                            break;
                        case 7:
                            System.out.println("Enter Staff's Department:");
                            department = scan.nextLine();
                            break;
                        case 8:
                            System.out.println("Enter Staff's Professional Title:");
                            professionalTitle = scan.nextLine();
                            break;
                        case 9:
                            System.out.println("Enter Staff's ID:");
                            staffId = scan.nextInt();
                            scan.nextLine();
                            break;
                        case 10:
                            // send sql param for statement
                            // return entry of new patient
                            StaffSQL.editStaff(selection, name, age, gender, phone, address, jobTitle, department,
                                    professionalTitle, staffId);
                            ResultSet result2 = StaffSQL.viewStaffById(staffId);
                            printStaffInfo(result2);
                            System.out.println();
                            result2.close();
                            name = "";
                            age = 0;
                            gender = "";
                            phone = "";
                            address = "";
                            jobTitle = "";
                            department = "";
                            professionalTitle = "";
                            staffId = 0;
                            break;
                        case 11:
                            System.out.println("Back to Edit another Staff Member");
                            name = "";
                            age = 0;
                            gender = "";
                            phone = "";
                            address = "";
                            jobTitle = "";
                            department = "";
                            professionalTitle = "";
                            staffId = 0;
                            break;
                        default:
                            System.out.println("The selection is invalid");
                            break;
                        }
                        System.out.println();
                    } while (selection2 != 10 && selection2 != 11);
                }
                result.close();
            }

        } while (selection != 0);
    }

    /**
     * Adds a staff member to the DB
     * 
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void add() throws ParseException, SQLException, ClassNotFoundException {

        do {

            System.out.println("1. Name: " + name);
            System.out.println("2. Age: " + age);
            System.out.println("3. Gender: " + gender);
            System.out.println("4. Phone: " + phone);
            System.out.println("5. Address: " + address);
            System.out.println("6. Job Title: " + jobTitle);
            System.out.println("7. Department: " + department);
            System.out.println("8. Professional Title: " + professionalTitle);
            System.out.println("9. Staff ID: " + staffId);
            System.out.println("10. Submit new staff member");
            System.out.println("11. Go back to previous screen");

            System.out.println("Enter # to add info:");
            System.out.println("Current Input will show next to attribute");

            selection = scan.nextInt();
            scan.nextLine();

            switch (selection) {
            case 1:
                System.out.println("Enter name:");
                name = scan.nextLine();
                break;
            case 2:
                System.out.println("Enter Age:");
                age = scan.nextInt();
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
                System.out.println("Enter Staff's Job Title:");
                jobTitle = scan.nextLine();
                break;
            case 7:
                System.out.println("Enter Staff's Department:");
                department = scan.nextLine();
                break;
            case 8:
                System.out.println("Enter Staff's Professional Title:");
                professionalTitle = scan.nextLine();
                break;
            case 9:
                System.out.println("Enter Staff's ID: ");
                staffId = scan.nextInt();
                scan.nextLine();
                break;
            case 10:
                // send sql param for statement
                // return entry of new patient
                StaffSQL.addStaff(name, age, gender, phone, address, jobTitle, department, professionalTitle, staffId);
                ResultSet result = StaffSQL.viewStaffById(staffId);
                printStaffInfo(result);
                System.out.println();
                result.close();
                name = "";
                age = 0;
                gender = "";
                phone = "";
                address = "";
                jobTitle = "";
                department = "";
                professionalTitle = "";
                staffId = 0;
                break;
            case 11:
                System.out.println("Back to Staff menu");
                break;
            default:
                System.out.println("The selection is invalid");
                break;
            }
            System.out.println();

        } while (selection != 11);
    }

    /**
     * All the options you can do for the Staff table
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static void staffOptions() throws ClassNotFoundException, SQLException, ParseException {

        do {

            System.out.println("Staff");
            System.out.println();
            System.out.println("1. View Staff By ID");
            System.out.println("2. View Staff By Job Title");
            System.out.println("3. View All Staff");
            System.out.println("4. Add Staff Member");
            System.out.println("5. Edit Staff Member");
            System.out.println("6. Delete Staff Member");
            System.out.println("7. Back to Home Menu");
            System.out.println("Make a selection: ");

            selection = scan.nextInt();

            switch (selection) {
            case 1:
                viewById();
                break;
            case 2:
                viewByJobTitle();
                break;
            case 3:
                viewAllStaff();
                break;
            case 4:
                add();
                break;
            case 5:
                edit();
                break;
            case 6:
                delete();
                break;
            case 7:
                System.out.println("Back to Home menu");
                break;
            default:
                System.out.println("The selection is invalid");
                break;
            }

        } while (selection != 7);
    }
}