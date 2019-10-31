import java.sql.*;
import java.util.Scanner;

public class Wards {
    static Scanner sc = new Scanner(System.in);
    int id;
    int capacity;
    float chargesperday;
    int nurseid;
	//this function lists down all the options we have for ward table
    public static void WardOptions() {
        Scanner sc = new Scanner(System.in);

        int selection = 0;

        do {
            System.out.println("1. Insert ward information");
            System.out.println("2. View all ward information");
            System.out.println("3. Update ward information");
            System.out.println("4. Delete ward information");
            System.out.println("5. Return to the home screen");
            System.out.println("Select suitable option");
            System.out.println("Choose what operation you want to perform");
            selection = sc.nextInt();
            switch (selection) {
            case 1:
                insertward();
                break;
            case 2:
                viewward();
                break;
            case 3:
                updateward();
                break;
            case 4:
                deleteward();
                break;
            case 5:
                System.out.println("Back to home menu");
                break;
            default:
                System.out.println("You have entered a wrong option");
            }
        } while (selection != 5);
        // sc.close();

    }
	// this function is used to insert a ward
    private static void insertward() {
        
        try {

            sc = new Scanner(System.in);
            System.out.println("Enter capacity: ");
            Integer capacity = sc.nextInt();
            System.out.println("Enter charges_per_day: ");
            Float charges_per_day = sc.nextFloat();
            System.out.println("nurse_id: ");
            Integer nurse_id = sc.nextInt();

            String sqlInsWards = "INSERT INTO wards (capacity,charges_per_day,nurse_id) VALUES( " + capacity + ", "
                    + charges_per_day + ",  " + nurse_id + " )";
            // ResultSet resIT = login.result;
            int id = login.statement.executeUpdate(sqlInsWards);

            if (id > 0)
                System.out.println("Ward Added!");
            else
                System.out.println("Ward not added!"); //if due to some reason ward is not added we get this message

            System.out.println("\n\nPress Enter To Continue");
            sc = new Scanner(System.in);
            sc.nextLine();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
	//this function is used to view ward information
    private static void viewward() {

        try {

            String sqlViewWards = "select * from wards";
            ResultSet resVT = login.result;
            resVT = login.statement.executeQuery(sqlViewWards);

            while (resVT.next()) {
                System.out.println("ID: " + resVT.getString("id") + ", capacity: " + resVT.getInt("capacity")
                        + ", charges_per_day: " + resVT.getFloat("charges_per_day") + ",nurse_id: "
                        + resVT.getInt("nurse_id") + "\n");
            }

            System.out.println("\n\nPress Enter To Continue");
            sc = new Scanner(System.in);
            sc.nextLine();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
	//this function is used to delete ward
    private static void deleteward() {
        try {

            sc = new Scanner(System.in);
            System.out.println("Enter Ward Id To Be Deleted: ");
            Integer WardID = sc.nextInt();

            String sqlDelWard = "Delete from wards where id=" + WardID;

            int id = login.statement.executeUpdate(sqlDelWard);

            if (id > 0)
                System.out.println("Ward Deleted!");
            else
                System.out.println("Ward not Deleted!");

            System.out.println("\n\nPress Enter To Continue");
            sc = new Scanner(System.in);
            sc.nextLine();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
	//this function is used to update attributes for ward
    private static void updateward() {
        try {

            sc = new Scanner(System.in);
            System.out.println("Enter Ward Id To Be Updated: ");
            Integer WardID = sc.nextInt();

            String sqlViewWard = "select * from wards where id=" + WardID;
            ResultSet resUT1 = login.result;
            resUT1 = login.statement.executeQuery(sqlViewWard);
            Integer idRes = 0;
            Integer upcapacity = 0;
            Integer upcharges_per_day = 0;
            Integer upnurse_id = 0;

            while (resUT1.next()) {
                upcapacity = resUT1.getInt("capacity");
                upcharges_per_day = resUT1.getInt("charges_per_day");
                idRes = resUT1.getInt("id");
                upnurse_id = resUT1.getInt("nurse_id");
            }
            Integer selection = 0;
            do {
                System.out.println("ID: " + idRes + ", Capacity: " + upcapacity + ", Charges per day: "
                        + upcharges_per_day + ",Nurse id: " + upnurse_id + ",  \n");

                System.out.println(
                        "\nEnter Choice Number:\n1) Edit Capacity\n2) Edit charges_per_day\n 3)Edit nurse_id \n4) Submit"); //this is the list of options we can choose from

                selection = sc.nextInt();
                sc = new Scanner(System.in);
                switch (selection) {
                case 1:
                    System.out.println("\nEnter New Capacity:"); //to enter the new capacity
                    upcapacity = sc.nextInt();
                    break;
                case 2:
                    System.out.println("\nEnter New charges_per_day:");
                    upcharges_per_day = sc.nextInt();
                    break;
                case 3:
                    System.out.println("\nEnter New nurse_id:");
                    upnurse_id = sc.nextInt();
                    break;

                case 4:
                    String sqlUpdWards = "UPDATE wards SET charges_per_day = " + upcharges_per_day + ", capacity = "
                            + upcapacity + " , nurse_id=" + upnurse_id + " WHERE id= " + WardID;

                    int id = login.statement.executeUpdate(sqlUpdWards);

                    if (id > 0)
                        System.out.println("Ward Updated!");
                    else
                        System.out.println("Ward not Updated!");
                    break;
                default:
                    System.out.println("You have entered a wrong option");
                }
            } while (selection != 4);

            System.out.println("\n\nPress Enter To Continue");
            sc = new Scanner(System.in);
            sc.nextLine();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}