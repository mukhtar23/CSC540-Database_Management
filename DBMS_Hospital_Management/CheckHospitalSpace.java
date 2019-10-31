import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

class CheckHospitalSpace {
    static Scanner scan = new Scanner(System.in);
    static int selection = 0;

    /**
     * Calls sql code to get available beds in hospital
     */
    public static void checkSpace() throws ClassNotFoundException, SQLException, ParseException {
        System.out.println("All Available Beds in Hospital:");
        System.out.println();
        ResultSet result = CheckHospitalSpaceSQL.checkHospitalSpace();
        if (!result.next()) {
            System.out.println("No Space in Hospital");
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
        System.out.println();
    }

    /**
     * This methods shows the available beds in hospital and option to go back to
     * home screen
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static void checkHospital() throws ClassNotFoundException, SQLException, ParseException {
        do {
            // sql statemnt that shows empty beds and wards in the hospital
            checkSpace();
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
}