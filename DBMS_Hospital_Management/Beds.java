import java.sql.*;
import java.util.Scanner;
public class Beds{
    static Scanner sc = new Scanner(System.in);
    int id;
    int wardid;
    int patientid;
    boolean reserved;

    //this function lists down all the options we have for beds table
    public static void bedOptions()
    {
        Scanner sc = new Scanner(System.in);
        int selection = 0;

        do {
            System.out.println("1. Create Bed information");
            System.out.println("2. View Bed information");
            System.out.println("3. Update Bed information");
            System.out.println("4. Delete Bed information");
            //System.out.println("5. Reserve Bed");
            System.out.println("5. Assign/Reserve Bed");
            System.out.println("6. Release Bed");
            System.out.println("7. Return to the home screen");
            System.out.println("Select suitable option");
            System.out.println("Choose what operation you want to perform");
            selection = sc.nextInt();
            switch(selection) {
                case 1: createBeds();
                    break;
                case 2: viewBeds();
                    break;
                case 3: updateBeds();
                    break;
                case 4: deleteBeds();
                    break;
                /*case 5: reserveBed();
                    break;*/
                case 5: assignBed();
                    break;
                case 6: releaseBed();
                    break;
                case 7: System.out.println("Back to home menu");
                    break;
                default : System.out.println("You have entered a wrong option");
            }
        }while(selection!=7);
        //sc.close();

    }

    //this function checks if the total number of beds assigned to a ward is equal to its capacity
    public static boolean checkWardCapacity(Integer ward_id){

        try {
            //check capacity of ward
            ResultSet resCapWard = login.result;
            String capWard = "select capacity from wards where id=" + ward_id;
            resCapWard = login.statement.executeQuery(capWard);
            Integer capacityWard = 0;
            while (resCapWard.next()) {
                capacityWard = resCapWard.getInt("capacity");
            }

            //check number of beds in that ward
            ResultSet resBedChk = login.result;
            String sqlBedChk = "select count(id) from beds where ward_id=" + ward_id;       //query for counting all the beds in a ward
            resBedChk = login.statement.executeQuery(sqlBedChk);
            Integer bedInWard = 0;
            while (resBedChk.next()) {
                bedInWard = resBedChk.getInt("count(id)");
            }

            if (bedInWard == capacityWard) {        //if the beds in a ward is equal to capacity of ward then return false
                return false;
            }
        }
        catch (SQLException ex){

        }
        return true;
    }

    //this function will create new bed record
    private static void createBeds()
    {
        try {

            sc = new Scanner(System.in);
            System.out.println("Enter ward_id: ");
            Integer ward_id = sc.nextInt();


            //we call the check ward capacity method which tells us if the current ward is full or not
            if (!checkWardCapacity(ward_id)){
                System.out.println("Ward already full. Try different ward.");
                System.out.println("\n\nPress Enter To Continue");
                sc = new Scanner(System.in);
                sc.nextLine();
            }
            else {
                //if not full, then we insert the bed into this ward with initial reserved value set to 0
                String sqlInsBeds = "INSERT INTO beds (ward_id,reserved) VALUES(" + ward_id + ",0 )";
                int id = login.statement.executeUpdate(sqlInsBeds);

                if (id > 0)
                    System.out.println("Beds Added!");
                else
                    System.out.println("Beds not added!");      //if due to some reason, beds isn't added, we get this message

                System.out.println("\n\nPress Enter To Continue");
                sc = new Scanner(System.in);
                sc.nextLine();
            }

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    //this method will display all the beds in the hospital and their related attributes
    private static void viewBeds() {

        try {

            String sqlViewBeds = "select * from beds";
            ResultSet resVT = login.result;
            resVT = login.statement.executeQuery(sqlViewBeds);

            while (resVT.next()){
                System.out.println("ID: "+resVT.getInt("id")+", ward_id: "+resVT.getInt("ward_id")
                        +", patient_id: "+resVT.getInt("patient_id") +",reserved: "+resVT.getInt("reserved")+",patient_ssn:"
                        +resVT.getString("patient_ssn")+"\n");
            }

            System.out.println("\n\nPress Enter To Continue");
            sc=new Scanner(System.in);
            sc.nextLine();

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    //this function is used to update attributes for a bed
    private static void updateBeds()
    {
        try {

            sc = new Scanner(System.in);
            System.out.println("Enter Bed Id To Be Updated: ");
            Integer BedID = sc.nextInt();

            String sqlViewBeds = "select * from beds where id=" + BedID;
            ResultSet resUT1 = login.result;
            resUT1 = login.statement.executeQuery(sqlViewBeds);
            Integer idRes = 0;
            Integer upward_id = 0;

            while (resUT1.next()) {
                upward_id = resUT1.getInt("ward_id");
                idRes = resUT1.getInt("id");
            }
            Integer selection = 0;
            do {
                System.out.println("ID: " + idRes + ", ward_id: " + upward_id + ", patient_id: ");

                System.out.println("\nEnter Choice Number:\n1) Edit ward_id\n2) Submit \n3) Go Back");  //this is the list of options we can choose

                selection = sc.nextInt();
                sc = new Scanner(System.in);
                switch (selection) {
                    case 1:
                        System.out.println("\nEnter New ward_id:");     //for updating the ward id of the bed
                        sc = new Scanner(System.in);
                        upward_id = sc.nextInt();
                        break;

                    case 2:
                        if (!checkWardCapacity(upward_id)) {
                            System.out.println("Ward already full. Try different ward.");       //if the ward is full, we display this message
                            System.out.println("\n\nPress Enter To Continue");
                            sc = new Scanner(System.in);
                            sc.nextLine();
                        } else {
                            String sqlUpBeds = "UPDATE beds SET ward_id = " + upward_id + " WHERE id= " + BedID;    //otherwise we update the ward id

                            int id = login.statement.executeUpdate(sqlUpBeds);

                            if (id > 0)
                                System.out.println("Bed Updated!");
                            else
                                System.out.println("Bed not Updated!");
                        }
                        break;
                    case 3:
                        break;
                            default:
                                System.out.println("You have entered a wrong option");
                        }
            } while (!(selection == 2 || selection==3));

            System.out.println("\n\nPress Enter To Continue");
            sc = new Scanner(System.in);
            sc.nextLine();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //this function will delete beds
    private static void deleteBeds()
    {
        try {

            sc = new Scanner(System.in);
            System.out.println("Enter Beds Id To Be Deleted: ");
            Integer BedID = sc.nextInt();

            String sqlCheckBed = "select * from beds where reserved=1 and id="+BedID;       //checks if the beds is reserved or assigned to someone
            ResultSet resCB = login.result;
            resCB = login.statement.executeQuery(sqlCheckBed);
            if (resCB.next()){
                System.out.println("This bed is reserved/assigned. Please release this bed first"); //if bed is reserved or assigned then it won't let us delete the bed
                System.out.println("\n\nPress Enter To Continue");
                sc = new Scanner(System.in);
                sc.nextLine();
            }
            else {
                String sqlDelBed = "Delete from beds where id=" + BedID;        //otherwise we delete it

                int id = login.statement.executeUpdate(sqlDelBed);

                if (id > 0) {
                    System.out.println("Bed Deleted!");
                } else
                    System.out.println("Bed not Deleted!");

                System.out.println("\n\nPress Enter To Continue");
                sc = new Scanner(System.in);
                sc.nextLine();
            }

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    //checks if the ward usage is 100 percent or not
    public static String wardReqCheck(Integer request){
        String wardNumStr = "";
        try {

            String sqlCheckWards = "select * from wards where capacity =" + request;
            ResultSet resCW = login.result;
            resCW = login.statement.executeQuery(sqlCheckWards);
            if (!resCW.isBeforeFirst()) {

            }
            else {
                while (resCW.next()){
                    Integer wardID=resCW.getInt("id");
                    String sqlWU = "SELECT ward_id,count(*) as total_beds,((select count(*) FROM beds WHERE ward_id='"+wardID+ "' AND " +
                            "patient_id IS NOT NUll)/count(*))*100 as 'usage' from beds WHERE ward_id='"+wardID+"'";
                    ResultSet resWU = login.result;
                    resWU = login.statement.executeQuery(sqlWU);
                    while (resWU.next()){
                        if (resWU.getString("usage")!="100"){
                            wardNumStr+=wardID+" ,";
                        }
                    }
                }
            }
        }
        catch (SQLException ex){

        }
        return wardNumStr;
    }


    //this function is used to assign a bed
    private static void assignBed(){
        try {

            sc = new Scanner(System.in);
            System.out.println("Enter Patient Id: ");
            Integer patID = sc.nextInt();
            String sqlcheckPatWard="select * from patients where ward_id IS NOT NUll and id="+patID;    //checks if a bed is already assigned to the patient
            ResultSet resCP = login.result;
            resCP = login.statement.executeQuery(sqlcheckPatWard);
            if (resCP.next()) {
                System.out.println("Bed already assigned to this patient");     //if yes then we print this message
                System.out.println("\n\nPress Enter To Continue");
                sc = new Scanner(System.in);
                sc.nextLine();
            }
            else {
                System.out.println("Do you have a ward type request?(y/n)\n");      //otherwise we ask if the patient has a specific type of ward request (1-bed,2-bed etc.)
                sc = new Scanner(System.in);
                String reqYN=sc.nextLine();
                if (reqYN.equals("y")) {                                        //if yes, we ask for the type
                    System.out.println("Enter Number of Beds In A Ward: ");
                    Integer reqWard = sc.nextInt();
                    String wardNums = "";
                    wardNums = wardReqCheck(reqWard);
                    if (wardNums == "") {                                       //if we don't find any such ward we print this
                        System.out.println("No Such Ward Available. Try a different request.");
                        System.out.println("\n\nPress Enter To Continue");
                        sc = new Scanner(System.in);
                        sc.nextLine();
                    }
                    else {
                        System.out.println("Available Wards: "+wardNums);                   //otherwise we show all the available wards
                        System.out.println("\n Enter the ward number you want to assign patient to: ");
                        Integer wardNumPat = sc.nextInt();
                        String sqlBedAssign = "select * from beds where patient_id IS NULL and ward_id="+wardNumPat;
                        ResultSet resBA = login.result;
                        resBA = login.statement.executeQuery(sqlBedAssign);
                        boolean done=false;
                        while (resBA.next() && !done){
                            done=true;
                            Integer bedID = resBA.getInt("id");
                            //we update the beds table
                            String sqlUpdateBed="update beds set reserved=1, patient_id="+patID+" where id="+bedID;
                            int idUB = login.statement.executeUpdate(sqlUpdateBed);
                            //we then update the beds table with patient's ssn
                            sqlUpdateBed="Update beds inner join patients on patients.id=beds.patient_id set beds.patient_ssn=patients.ssn where beds.id=" + bedID;
                             idUB = login.statement.executeUpdate(sqlUpdateBed);
                            System.out.println("Patient assigned to bed number "+bedID);
                            //we thn update patients table with ward id
                            String sqlUpPatWard = "UPDATE patients inner join beds on patients.id=beds.patient_id set patients.ward_id =beds.ward_id,in_ward='"+ "yes" +"' where beds.id=" + bedID;
                            int idPat = login.statement.executeUpdate(sqlUpPatWard);

                            //and finally we update check in info with beds and ward id
                            String sqlUpChckIn = "Update check_in_info c inner join patients p on c.patient_id=p.id set c.ward_id=p.ward_id,c.bed_id= "+bedID+ " where start_date<= CURDATE() and (end_date IS NULL or end_date>=CURDATE())";
                            int idCI = login.statement.executeUpdate(sqlUpChckIn);

                            System.out.println("\n\nPress Enter To Continue");
                            sc = new Scanner(System.in);
                            sc.nextLine();
                        }
                    }
                }
                else {
                    System.out.println("Enter Bed Id: ");           //if no such ward type request, we ask for the bed to be assigned to
                    Integer bedID = sc.nextInt();
                    String sqlCheckBed = "select * from beds where reserved=1 and id=" + bedID + " and patient_id IS NOT NUll";     //check is this bed already assigned to someone
                    ResultSet resCB = login.result;
                    resCB = login.statement.executeQuery(sqlCheckBed);
                    if (resCB.next()) {
                        System.out.println("Bed already assigned to another patient");
                        System.out.println("\n\nPress Enter To Continue");
                        sc = new Scanner(System.in);
                        sc.nextLine();
                    } else {
                        String sqlUpdateBed = "UPDATE beds SET reserved = 1,patient_id =" + patID + " WHERE id =" + bedID;          //if not assigned, we update the beds and set reserved to 1
                        ResultSet resUB = login.result;
                        int id = login.statement.executeUpdate(sqlUpdateBed);
                        if (id > 0) {
                            System.out.println("Bed Assigned!");
                            //update wards id in patients
                            String sqlUpPatWard = "UPDATE patients inner join beds on patients.id=beds.patient_id set patients.ward_id =beds.ward_id ,in_ward='"+ "yes" +"' where beds.id=" + bedID;
                            String sqlUpPatSSN = "Update beds inner join patients on patients.id=beds.patient_id set beds.patient_ssn=patients.ssn where beds.id=" + bedID;
                            int idPat = login.statement.executeUpdate(sqlUpPatWard);
                            int idBed = login.statement.executeUpdate(sqlUpPatSSN);

                            //update check in info
                            String sqlUpChckIn = "Update check_in_info c inner join patients p on c.patient_id=p.id set c.ward_id=p.ward_id,c.bed_id= "+bedID+ " where start_date<= CURDATE() and (end_date IS NULL or end_date>=CURDATE())";
                            int idCI = login.statement.executeUpdate(sqlUpChckIn);

                        } else
                            System.out.println("Bed not assigned!");

                        System.out.println("\n\nPress Enter To Continue");
                        sc = new Scanner(System.in);
                        sc.nextLine();
                    }
                }
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

  //this function is used to release a patient from bed and make it available
    private static void releaseBed(){
    	Connection conn = login.connection;
        try{               
            System.out.println("Enter Bed Id: ");
            Integer bedID = sc.nextInt();
            String sqlCheckBed = "select * from beds where reserved=1 and id=" + bedID ;  //checks if this bed is already vacant
            ResultSet resCB = login.result;
            resCB = login.statement.executeQuery(sqlCheckBed);
            if (!resCB.isBeforeFirst()) {
                System.out.println("Bed already vacant");
                System.out.println("\n\nPress Enter To Continue");
                sc = new Scanner(System.in);
                sc.nextLine();
            } else {
            	
            	conn.setAutoCommit(false);   //set auto commit to false
                String patID="";
                while (resCB.next()) {
                    patID = resCB.getString("patient_id");
                }
                String sqlUpdateBed = "UPDATE beds SET reserved = 0,patient_id =NULL,patient_ssn=NULL WHERE id =" + bedID; //here we update beds and set reserved to 0 and remove the patient
                int id = login.statement.executeUpdate(sqlUpdateBed);
                if (id > 0) {
                    System.out.println("Bed Released!");
                    //update wards id in patients
                    if (patID!="") {
                        String sqlUpPatWard = "UPDATE patients SET ward_id=NULL,in_ward='"+ "no" +"' where id=" + patID;
                        int idPat = login.statement.executeUpdate(sqlUpPatWard);
                    }
                } else
                    System.out.println("Bed not released!");

                System.out.println("\n\nPress Enter To Continue");
                sc = new Scanner(System.in);
                sc.nextLine();
                conn.commit();       // if both the updates are successful then commit both the statements together
                
                
            }
            
        }
        catch (SQLException ex){
            ex.printStackTrace();
            if(conn!=null)
            {
            	try {
            		System.out.println("Transaction is being rolled back");
            		conn.rollback();                  //if any exception occurs rollback the transaction
            	}catch(SQLException se)
            	{
            	se.printStackTrace();
            	}
            }
        }
        finally {
        	try {
				conn.setAutoCommit(true);       // set auto commit to true 
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        }

}
