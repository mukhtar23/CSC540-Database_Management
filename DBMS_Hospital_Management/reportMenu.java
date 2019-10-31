import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class reportMenu {

    static int selection = 0;                       //stores the choice number we have selected
    static Scanner scan2=new Scanner(System.in);

    //this function generates the medical report for a patient for (a particular month and year/all of the record)
    public static void genMedReport(){
        try {
            //variables for holding all the columns
            String patientID="";
            Date stDate=null;
            Date endDate=null;
            String pres="";
            String docID="";
            String diagnosis="";
            String treatment="";

            //seeking user input for patient ID and the month and year for which he wants to see medical records of
            System.out.println("Enter Patient ID: ");
            String patID = scan2.nextLine();
            System.out.println("Enter month and year (mm-yyyy): ");
            String monYear = scan2.nextLine();


            String sqlGenMR="";

            //if the user doesn't input any particular month and year, we query for all the medical records of that patient
            if (monYear.isEmpty()){
                sqlGenMR= "SELECT * FROM medical_records WHERE patient_id='"+ patID+"'";
            }
            else {
                //otherwise we query for the medical records which were somewhat active in that month
                String month = monYear.split("-")[0];
                String year = monYear.split("-")[1];
                String dateMR = year + "-" + month + "-31";
                String dateMR2 = year + "-" + month + "-1";
                sqlGenMR = "SELECT * FROM medical_records WHERE start_date <= '" + dateMR + "' and (end_date IS NULL or end_date >= '" + dateMR2
                        + "') AND patient_id= '" + patID + "'";
            }
            ResultSet resRM = login.result;
            resRM = login.statement.executeQuery(sqlGenMR);
            if (!resRM.isBeforeFirst() ) {
                System.out.println("No data");      //if found no results for the query
            }
            else {
                //otherwise we print the details for all the output medical records
                while (resRM.next()) {
                    patientID = resRM.getString("patient_id");
                    stDate = resRM.getDate("start_date");
                    endDate = resRM.getDate("end_date");
                    pres = resRM.getString("prescription");
                    docID = resRM.getString("doctor_id");
                    diagnosis = resRM.getString("diagnosis");
                    treatment = resRM.getString("treatment");

                    System.out.println("Medical Record Details\n\n Patient ID: " + patientID + ", Start Date: " + stDate + ", End Date: " + endDate + ", Prescription: " + pres
                            + ", Doctor ID: " + docID + ", Diagnosis: " + diagnosis + ", Treatment :" + treatment);
                }
            }

            System.out.println("\n\nPress Enter To Continue");      //after displaying, the user needs to press enter to continue seeing the set of menu actions
            scan2.nextLine();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //this function generates all the patients who were admitted per month
    public static void genPatPerMonth(){
        try{
                //System.out.println("1. Particular month and year");
                //System.out.println("2. Every month and year");

            //System.out.println("Please make a selection:");
            //scan2=new Scanner(System.in);
            Integer selPPM=1;

            String sqlPPM="";
            //if we want to see the data for a particular month and year, we choose 1
            if (selPPM==1){
                System.out.println("Enter month and year (mm-yyyy): ");
                scan2=new Scanner(System.in);
                String monYear = scan2.nextLine();
                String month = monYear.split("-")[0];
                String year = monYear.split("-")[1];
                String dateMR = year + "-" + month + "-31";
                String dateMR2 = year + "-" + month + "-1";

                //sqlPPM="SELECT COUNT(*) FROM medical_records WHERE MONTH(start_date) = '"+ month + "' AND YEAR(start_date) = '" +year+"'";
                sqlPPM="SELECT count(id) FROM medical_records WHERE start_date <= '" + dateMR + "' and (end_date IS NULL or end_date >= '" + dateMR2
                        + "')";
                ResultSet resPPM = login.result;
                resPPM = login.statement.executeQuery(sqlPPM);
                while (resPPM.next()){
                    System.out.println("Number of patients for the given month and year: "+resPPM.getString("count(id)"));
                }
            }
            else if (selPPM==2){        //otherwise it displays data for all the months and years
                sqlPPM="SELECT COUNT(*),month(start_date),year(start_date) FROM medical_records GROUP BY month(start_date),year(start_date) ORDER BY start_date";
                ResultSet resPPM = login.result;
                resPPM = login.statement.executeQuery(sqlPPM);

                while (resPPM.next()){
                    System.out.println("Number of patients: "+resPPM.getString("COUNT(*)")+", Month: "+resPPM.getString("month(start_date)").toString()+
                            ", Year: "+resPPM.getString("year(start_date)".toString())+"\n");
                }
            }
            else
                System.out.println("Incorrect selection");

            System.out.println("\n\nPress Enter To Continue");
            scan2=new Scanner(System.in);
            scan2.nextLine();


        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //this function generated data for all the patients who are/were under a particular doctor
    public static void genPatUnderDoc(){

        try {

            System.out.println("Enter Doctor ID:");
            scan2 = new Scanner(System.in);
            Integer docID = scan2.nextInt();

            //query is constructed using inner join of medical records and patients data, since medical records stores the responsible doctor records
            String sqlPUD = "SELECT p.id,ssn,name,dateOfBirth,gender,phone,address,status,ward_id,start_date,prescription,diagnosis,treatment,active" +
                    " FROM patients p INNER JOIN medical_records mr on p.id=mr.patient_id WHERE mr.doctor_id= '" + docID + "' AND mr.active=1";

            ResultSet resPUD = login.result;
            resPUD = login.statement.executeQuery(sqlPUD);
            if (!resPUD.isBeforeFirst() ) {
                System.out.println("No data");
            }
            else {
                while (resPUD.next()) {
                    System.out.println("Patient Name: " + resPUD.getString("name") +", ID: " + resPUD.getInt("p.id")+ ", Date of Birth: " + resPUD.getString("dateOfBirth")
                            + ", Gender: " + resPUD.getString("gender") + ", Phone Number: " + resPUD.getString("phone") + ", Address: " + resPUD.getString("address") +
                            ", Status: " + resPUD.getString("status") + ", Ward ID: " + resPUD.getString("ward_id") + ", Start Date: " + resPUD.getDate("start_date").toString() +
                            ", Prescription: " + resPUD.getString("prescription") + ", Diagnosis: " + resPUD.getString("diagnosis") + ", Treatment: " + resPUD.getString("treatment")
                            + "\n");
                }
            }
            System.out.println("\n\nPress Enter To Continue");
            scan2=new Scanner(System.in);
            scan2.nextLine();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //this function prints out all the staff data grouped by their jobtitle, prof title and their department
    public static void genHosStaff(){
        try {

            String sqlHS = "SELECT jobTitle,professionalTitle,department,count(id) FROM staff group by jobTitle,professionalTitle,department";
            ResultSet resHS = login.result;
            resHS = login.statement.executeQuery(sqlHS);
            while (resHS.next()){
                System.out.println("Job Title: "+resHS.getString("jobTitle")+
                        ", Department: "+resHS.getString("department")+", Professional Title: "+resHS.getString("professionalTitle")+", Count: "+resHS.getString("count(id)")
                +"\n");
            }
            System.out.println("\n\nPress Enter To Continue");
            scan2=new Scanner(System.in);
            scan2.nextLine();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //this function prints out all the wards information and the percentage filled status of them
    public static void genCurrWardUsage(){
        try {

            String sqlAllWard="select * from wards";
            ResultSet resAW = login.result;
            resAW = login.statement.executeQuery(sqlAllWard);

            while (resAW.next()){
                String sqlWU = "SELECT ward_id,count(*) as total_beds,((select count(*) FROM beds WHERE ward_id='"+resAW.getInt("id")+ "' AND " +
                        "patient_id IS NOT NUll)/count(*))*100 as 'usage %' from beds WHERE ward_id='"+resAW.getInt("id")+"'";

                ResultSet resWU = login.result;
                resWU = login.statement.executeQuery(sqlWU);

                while (resWU.next()) {
                    System.out.println("Ward ID: " + resWU.getString("ward_id") + ", Total Beds: " + resWU.getString("total_beds")
                            + ", Percentage Usage: " + resWU.getString("usage %"));
                }

            }

            System.out.println("\n\nPress Enter To Continue");
            scan2=new Scanner(System.in);
            scan2.nextLine();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this function displays all the beds and their status which is stored in the reserved attribute
    public static void genCurrBedUsage(){
        try {

            String sqlBU = "Select * from beds";
            ResultSet resBU = login.result;
            resBU = login.statement.executeQuery(sqlBU);
            while (resBU.next()){
                String reserved="";
                reserved=resBU.getInt("reserved")==1?"Yes":"No";
                System.out.println("ID: "+resBU.getString("id")+", Ward ID: "+resBU.getString("ward_id")
                        +", Patient ID: "+resBU.getString("patient_id")+", Reserved: "+reserved
                        +"\n");
            }
            System.out.println("\n\nPress Enter To Continue");
            scan2=new Scanner(System.in);
            scan2.nextLine();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //this function prints out all the tests a patient has taken for their particular visit to the hospital
    public static void genAllTestPatVisit(){
        try {

            //asking for patient id
            System.out.println("Enter Patient ID:");
            scan2 = new Scanner(System.in);
            String patID = scan2.nextLine();

            //asking for the start date of the visit of the patient for which we want all the tests
            System.out.println("Enter Start Date (yyyy-mm-dd):");
            scan2 = new Scanner(System.in);
            String stDate = scan2.nextLine();

            String sqlTPV = "select t.test_id,t.result,a.id from test_for_patients t inner join medical_records a on t.medical_record_id=a.id where" +
                    " a.patient_id='"+patID+"' and a.start_date ='"+ stDate+"'";


            ResultSet resTPV = login.result;
            resTPV = login.statement.executeQuery(sqlTPV);
            if (!resTPV.isBeforeFirst() ) {
                System.out.println("No data");
            }
            else {
                while (resTPV.next()) {
                    System.out.println("Test ID: " + resTPV.getString("test_id") + ", Result: " + resTPV.getString("Result") + "\n");
                }
            }
            System.out.println("\n\nPress Enter To Continue");
            scan2=new Scanner(System.in);
            scan2.nextLine();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this is the list of all the options a user will see when they select reports from the main menu
    public static void reportOptions(){
        do {

            System.out.println("Reports");
            System.out.println("1. Generate Medical Report");
            System.out.println("2. Generate Patients Per Month");
            System.out.println("3. Generate Patients Under Doctor");
            System.out.println("4. Generate Hospital Staff");
            System.out.println("5. Generate Current Ward Usage");
            System.out.println("6. Generate Current Bed Usage");
            System.out.println("7. Generate All Tests For A Patient's Visit");
            System.out.println("8. Back");

            System.out.println("Make a selection: ");

            Scanner scan1=new Scanner(System.in);
            selection = scan1.nextInt();

            switch (selection) {
                case 1:
                    genMedReport();
                    break;
                case 2:
                    genPatPerMonth();
                    break;
                case 3:
                    genPatUnderDoc();
                    break;
                case 4:
                    genHosStaff();
                    break;
                case 5:
                    genCurrWardUsage();
                    break;
                case 6:
                    genCurrBedUsage();
                    break;
                case 7:
                    genAllTestPatVisit();
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
