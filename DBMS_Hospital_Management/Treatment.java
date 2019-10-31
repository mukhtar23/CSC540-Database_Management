import java.sql.*;
import java.util.Scanner;

public class Treatment {
	static Scanner sc = new Scanner(System.in);
	int id;
	String name;
	float price;


	//this method prints out all the option present under treatment
	public static void treatmentOptions() {
		sc = new Scanner(System.in);
		
		int selection = 0;

		do {
			System.out.println("1. Insert treatment information");
			System.out.println("2. View all treatments");
			System.out.println("3. Update treatment information");
			System.out.println("4. Delete treatment information");
			System.out.println("5. Return to the home screen");
			System.out.println("Select suitable option");
			System.out.println("Choose what operation you want to perform");
			selection = sc.nextInt();
			switch (selection) {
				case 1:
					insertTreatment();
					break;
				case 2:
					viewTreatment();
					break;
				case 3:
					updateTreatment();
					break;
				case 4:
					deleteTreatment();
					break;
				case 5:
					System.out.println("Back to home menu");
					break;
				default:
					System.out.println("You have entered a wrong option");
			}
		} while (selection != 5);

	}


	//this function will insert a new treatment record
	private static void insertTreatment() {
		try {

			sc = new Scanner(System.in);
			System.out.println("Enter Treatment Id: ");
			Integer treatMentID = sc.nextInt();

			String sqlTreatCheck = "select * from treatments where id="+treatMentID;		//checks if the corresponding treatment id already exists
			ResultSet resTC = login.result;
			resTC=login.statement.executeQuery(sqlTreatCheck);
			if (resTC.next()){
				System.out.println("Treatment Id already exists");		//if it already exists, then we print this message
			}
			else {
				sc = new Scanner(System.in);
				System.out.println("Enter Treatment Name: ");		//otherwise we ask for the name of the treatment
				String treatmentName = sc.nextLine();
				System.out.println("Enter Price: ");				//price of the treatment
				Integer price = sc.nextInt();

				//sql statement to insert the new treatment
				String sqlInsTreat = "INSERT INTO treatments (name, price,id) VALUES('" + treatmentName + "'," + price +", "+ treatMentID+ ")";
				//ResultSet resIT = login.result;
				int id = login.statement.executeUpdate(sqlInsTreat);

				if (id > 0)
					System.out.println("Treatment Added!");
				else
					System.out.println("Treatment not added!");
			}

			System.out.println("\n\nPress Enter To Continue");
			sc=new Scanner(System.in);
			sc.nextLine();

		}
		catch (SQLException ex){
			ex.printStackTrace();
		}

	}


	//this function lists down all the treatment records
	private static void viewTreatment() {

		try {

			String sqlViewTreat = "select * from treatments";
			ResultSet resVT = login.result;
			resVT = login.statement.executeQuery(sqlViewTreat);

			while (resVT.next()){
				System.out.println("ID: "+resVT.getString("id")+", Treatment Name: "+resVT.getString("name")
						+", Price: "+resVT.getInt("price") +"\n");
			}

			System.out.println("\n\nPress Enter To Continue");
			sc=new Scanner(System.in);
			sc.nextLine();

		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}

	//this function is used to update a treatment record
	private static void updateTreatment() {
		try {

			sc = new Scanner(System.in);
			System.out.println("Enter Treatment Id To Be Updated: ");
			Integer treatmentID = sc.nextInt();

			String sqlViewTreat = "select * from treatments where id="+treatmentID;
			ResultSet resUT1 = login.result;
			resUT1 = login.statement.executeQuery(sqlViewTreat);
			if (!resUT1.isBeforeFirst() ) {
				System.out.println("No data");
			}
			else {
				String upName = "";
				Integer upPrice = 0;
				Integer idRes = 0;

				while (resUT1.next()) {
					upName = resUT1.getString("name");
					upPrice = resUT1.getInt("price");
					idRes = resUT1.getInt("id");
				}
				Integer selection = 0;
				do {
					System.out.println("ID: " + idRes + ", Treatment Name: " + upName + ", Price: " + upPrice + "\n");

					//we have the option to change the name or the price of the treatment
					System.out.println("\nEnter Choice Number:\n1) Edit Name\n2) Edit Price \n3) Submit");

					selection = sc.nextInt();
					sc = new Scanner(System.in);
					switch (selection) {
						case 1:
							System.out.println("\nEnter New Name:");
							upName = sc.nextLine();
							break;
						case 2:
							System.out.println("\nEnter New Price:");
							upPrice = sc.nextInt();
							break;
						case 3:
							String sqlUpdTreat = "UPDATE treatments SET name = '" + upName + "', price = " + upPrice + " WHERE id= " + treatmentID;	//sql statement to update the treatment

							int id = login.statement.executeUpdate(sqlUpdTreat);

							if (id > 0)
								System.out.println("Treatment Updated!");
							else
								System.out.println("Treatment not Updated!");
							break;
						default:
							System.out.println("You have entered a wrong option");
					}
				} while (selection != 3);
			}
			System.out.println("\n\nPress Enter To Continue");
			sc=new Scanner(System.in);
			sc.nextLine();

		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}

	//this function is used to delete a treatment record
	private static void deleteTreatment() {
		try {

			sc = new Scanner(System.in);
			System.out.println("Enter Treatment Id To Be Deleted: ");
			Integer treatmentID = sc.nextInt();

			String sqlDelTreat = "Delete from treatments where id="+treatmentID;

			int id = login.statement.executeUpdate(sqlDelTreat);

			if (id>0)
				System.out.println("Treatment Deleted!");
			else
				System.out.println("Treatment does not exist!");		//if the treatment does not exists then we print this message

			System.out.println("\n\nPress Enter To Continue");
			sc=new Scanner(System.in);
			sc.nextLine();

		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
	}

}
