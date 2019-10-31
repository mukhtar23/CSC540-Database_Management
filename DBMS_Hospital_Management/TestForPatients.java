import java.util.Scanner;
import java.sql.*;

public class TestForPatients {
    static Scanner sc = new Scanner(System.in);
    int medicalrecordid;
    int testid;
    String result;

    //this function prints out all the options available for test for patients table
    public static void Tests_for_patientsOptions() {
         sc = new Scanner(System.in);

        int selection = 0;

        do {
            System.out.println("1. Insert Tests_for_patients information");
            System.out.println("2. View all Tests_for_patients information");
            System.out.println("3. Update Tests_for_patients information");
            System.out.println("4. Delete Tests_for_patients information");
            System.out.println("5. Return to the home screen");
            System.out.println("Select suitable option");
            System.out.println("Choose what operation you want to perform");
            selection = sc.nextInt();
            switch (selection) {
                case 1:
                    insertTests_for_patients();
                    break;
                case 2:
                    viewTests_for_patients();
                    break;
                case 3:
                    updateTests_for_patients();
                    break;
                case 4:
                    deleteTests_for_patients();
                    break;
                case 5:
                    System.out.println("Back to home menu");
                    break;
                default:
                    System.out.println("You have entered a wrong option");
            }
        } while (selection != 5);

    }

    //this function is used for inserting a new record
    private static void insertTests_for_patients() {
        try {

            sc = new Scanner(System.in);
            System.out.println("Enter Medical Record Id For Which You Want To Insert A Test: ");        //seeks input for the medical record to which this test needs to be linked to
            Integer mrID = sc.nextInt();

            Integer iTID=0;
            String iRes="";
            Integer selection=0;
            do {

                System.out.println("\nEnter Choice Number:\n1)Test ID\n2)Result\n3) Submit");

                selection = sc.nextInt();
                sc = new Scanner(System.in);
                switch (selection) {
                    case 1:
                        System.out.println("\nEnter ID:");
                        iTID=sc.nextInt();
                        break;
                    case 2:
                        System.out.println("\nEnter Result:");
                        iRes=sc.nextLine();
                        break;
                    case 3:
                        if (iTID==0){
                            System.out.println("Please Enter Test ID");     //if test id isn't entered and hit submit, then prompted with this message
                        }
                        else {
                            String sqliTFP="Insert into test_for_patients (medical_record_id,test_id,result) values ( "+mrID+", "+iTID+", '"+iRes+"' )";    //sql statement to insert new record

                            int id = login.statement.executeUpdate(sqliTFP);

                            if (id>0)
                                System.out.println("Test For Patient Added!");
                            else
                                System.out.println("Test For Patient not added!");      //if due to some error, the record doesn't get inserted
                        }
                        break;
                    default:
                        System.out.println("You have entered a wrong option");
                }
            } while (selection != 3 || iTID==0);

            System.out.println("\n\nPress Enter To Continue");
            sc=new Scanner(System.in);
            sc.nextLine();

        }
        catch (SQLException ex){
            System.out.println("ERROR: Incorrect Test/Medical Record ID \n");
        }

    }

    //this function prints our all the test for patients records
    private static void viewTests_for_patients() {


        try {
            sc = new Scanner(System.in);
            System.out.println("Enter Medical Record Id: ");
            Integer mrID = sc.nextInt();
            String sqlViewTFP = "select * from test_for_patients where medical_record_id="+mrID;
            ResultSet resTFP = login.result;
            resTFP = login.statement.executeQuery(sqlViewTFP);

            if (!resTFP.isBeforeFirst() ) {
                System.out.println("No data");      //if no data in db for all the test taken corresponding to this medical record
            }

            while (resTFP.next()){
                System.out.println("Test ID: "+resTFP.getInt("test_id")+", Result: "+resTFP.getString("result")
                        +"\n");
            }

            System.out.println("\n\nPress Enter To Continue");
            sc=new Scanner(System.in);
            sc.nextLine();

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    //this function is used to update a test for patients record
    private static void updateTests_for_patients() {
        try {
            sc = new Scanner(System.in);
            System.out.println("Enter Medical Record Id: ");
            Integer mrID = sc.nextInt();
            String sqlViewTFP = "select * from test_for_patients where medical_record_id="+mrID;
            ResultSet resTFP = login.result;
            resTFP = login.statement.executeQuery(sqlViewTFP);

            if (!resTFP.isBeforeFirst() ) {
                System.out.println("No data");      //if no such data for thie medical record
            }
            else {
                while (resTFP.next()) {
                    System.out.println("Test ID: " + resTFP.getInt("test_id") + ", Result: " + resTFP.getString("result")
                            + "\n");
                }

                System.out.println("Select Test ID To Update A Record: ");      //input the test id which needs to be updated
                sc = new Scanner(System.in);
                Integer testID = sc.nextInt();

                sqlViewTFP = "select * from test_for_patients where medical_record_id=" + mrID + " and test_id=" + testID;
                resTFP = login.statement.executeQuery(sqlViewTFP);
                if (!resTFP.isBeforeFirst()) {
                    System.out.println("No data");
                } else {
                    Integer upTID = 0;
                    String upResult = "";
                    Integer idRes = 0;

                    //display the test id, result for this
                    while (resTFP.next()) {
                        upTID = resTFP.getInt("test_id");
                        upResult = resTFP.getString("result");
                        idRes = resTFP.getInt("test_id");
                    }


                    Integer selection = 0;
                    do {
                        System.out.println("Test ID: " + upTID + ", Result: " + upResult + "\n");

                        System.out.println("\nEnter Choice Number:\n1) Edit Result\n2) Submit");    //we get the option to update the result of this test

                        selection = sc.nextInt();
                        sc = new Scanner(System.in);
                        switch (selection) {
                            case 1:
                                System.out.println("\nEnter New Result:");
                                upResult = sc.nextLine();
                                break;
                            case 2:
                                String sqlUpdTFP = "UPDATE test_for_patients SET result = '" + upResult + "' WHERE medical_record_id= " + mrID + " and test_id=" + testID;  //sql statement to update this record

                                int id = login.statement.executeUpdate(sqlUpdTFP);

                                if (id > 0)
                                    System.out.println("Test For Patient Updated!");
                                else
                                    System.out.println("Test For Patient not Updated!");
                                break;
                            default:
                                System.out.println("You have entered a wrong option");
                        }
                    } while (selection != 2);
                }
            }
            System.out.println("\n\nPress Enter To Continue");
            sc=new Scanner(System.in);
            sc.nextLine();

        }
        catch (SQLException ex){
            //ex.printStackTrace();
            System.out.println("Incorrect Test/Medical Record ID");
        }
    }

    //function to delete a test for patient record
    private static void deleteTests_for_patients() {

        try {

            sc = new Scanner(System.in);
            System.out.println("Enter Medical Record Id: ");        //asks for the medical record
            Integer mrID = sc.nextInt();
            String sqlViewDTFP = "select * from test_for_patients where medical_record_id="+mrID;       //show all the test corresponding to this medical record
            ResultSet resDTFP = login.result;
            resDTFP = login.statement.executeQuery(sqlViewDTFP);

            if (!resDTFP.isBeforeFirst() ) {
                System.out.println("No data");      //if no output for the above query
            }
            else {
                while (resDTFP.next()) {
                    System.out.println("Test ID: " + resDTFP.getInt("test_id") + ", Result: " + resDTFP.getString("result")
                            + "\n");
                }

                System.out.println("Select Test ID To Delete: ");       //asks for which test to delete
                sc = new Scanner(System.in);
                Integer testID = sc.nextInt();

                sqlViewDTFP = "Delete from test_for_patients where medical_record_id=" + mrID + " and test_id=" + testID;   //sql statement to delete this record
                int id = login.statement.executeUpdate(sqlViewDTFP);

                if (id > 0)
                    System.out.println("Test For Patients Deleted!");
                else
                    System.out.println("Test For Patients not Deleted!");

            }
            System.out.println("\n\nPress Enter To Continue");
            sc=new Scanner(System.in);
            sc.nextLine();

        }
        catch (SQLException ex){
            System.out.println("Incorrect Test ID");
        }

    }

}
