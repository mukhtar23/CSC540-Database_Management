import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.*;
/**
 * This class contains the APIs for the tests table
 */
public class Test {
	Scanner sc = new Scanner(System.in);
	public static int id;
	public static String name;
	public static float price;

	public static void testOptions() {
		Scanner sc = new Scanner(System.in);
		int selection = 0;

		do {
			System.out.println("1. Insert test information");
			System.out.println("2. View a particluar test");
			System.out.println("3. View all tests");
			System.out.println("4. Update test information");
			System.out.println("5. Delete test information");
			System.out.println("6. Return to the home screen");
			System.out.println("Select suitable option");
			System.out.println("Choose what operation you want to perform");
			selection = sc.nextInt();
			switch (selection) {
			case 1:
				createTest();
				break;
			case 2:
				viewTest();
				break;
			case 3: viewAllTests();
					break;
			case 4: updateTest();
				    break;
			case 5: deleteTest();
				    break;
			case 6: System.out.println("Back to home menu");
				    break;
			default: 
				System.out.println("You have entered a wrong option");
			}
		} while (selection != 6);

	}
	
 /**
  * This method creates a record in the tests table
  */
	private static void createTest() {
		Scanner sc = new Scanner(System.in);
		int selection = 0;
		System.out.println("Enter test id or -1 to go back");
		selection = sc.nextInt();
		while(selection!=-1)
		{
			id = selection;
			if(isValidTest(id))
				System.out.println("A test with this id already exists");
			else
			{
				initialize();
				int option=0;
				while(option!=-1 && option!=3)
				{
					System.out.println("Enter the option to want to add");
					System.out.println("1. test name");
					System.out.println("2. test price");
					System.out.println("3. Add record");
					System.out.println("Enter -1 to go back");
					option = sc.nextInt();
					sc.nextLine();
					switch(option)
					{
					case 1: System.out.println("test name:");
							name = sc.nextLine();
							break;
					case 2: System.out.println("price:");
							price = sc.nextFloat();
							break;
					case 3: break;
					case -1: break;
					default :System.out.println("Please select a correct option");
					}
				}
					if(option!=-1)
					{
						try {
						Connection conn = login.connection;
						PreparedStatement ps = conn.prepareStatement("Insert into tests (id,name,price) values (?,?,?)");
						ps.setInt(1, id);
						ps.setString(2, name);
						ps.setFloat(3,price);
						int rows;
						rows = ps.executeUpdate();
						if(rows>0)
						{
							System.out.println("Successfully inserted record");
						}
						else
						{
							System.out.println("Record not inserted check the console output");
						}
						}
						catch(SQLException e) {e.printStackTrace();}
					}
					
				}
			System.out.println("Enter test id");
			System.out.println("Enter -1 to go back");
			selection = sc.nextInt();
			}
			
		}
	
	/**
	 * This method displays all the tests
	 */
	public static void viewAllTests()
	{
		try
		{
			Connection conn = login.connection;
			String sql = "select * from tests";
			//stmt = conn.createStatement();
			Statement stmt = login.connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
				while(rs.next())
				{id = rs.getInt("id");
				name = rs.getString("name");
				price = rs.getFloat("price");
				System.out.println("********************");
				
				System.out.println("***************************");
				System.out.println("Showing test with id "+id);
				System.out.println("1.ID: "+id);
				//System.out.println("1.Patient id: "+patient_id);
				System.out.println("2.Name:"+name);
				System.out.println("3.Price: "+price);
				//System.out.println("2.Billing address: "+billing_address);
				System.out.println("***********************");
				//System.out.println("***************************");
				}
		}
		catch(SQLException e) {e.printStackTrace();}
		
	}
	
	/**
	 * This methods return a boolean if the test_id entered is valid
	 * @param testId
	 * @return
	 */
	private static boolean isValidTest(int testId)
	{
		try
		{
			Connection conn = login.connection;
			String sql1 = "select * from tests where id="+testId;
			//stmt = conn.createStatement();
			Statement stmt = login.connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql1);
			if(rs.next())
			{System.out.println("Returning true");
			return true;
			}
			else 
				return false;
		}catch(SQLException e) {e.printStackTrace();};
			
		return false;	
	}
	
	/**
	 * This method displays a record in the tests table given a test_id
	 * @param testId
	 */
	private static void viewTestWithId(int testId)
	{
		try
		{
			Connection conn = login.connection;
			String sql1 = "select * from tests where id="+testId;
			Statement stmt = login.connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql1);
			
				while(rs.next())
				{id = rs.getInt("id");
				name = rs.getString("name");
				price = rs.getFloat("price");
				System.out.println("********************");
				
				System.out.println("***************************");
				System.out.println("Showing test with id "+id);
				System.out.println("1.ID: "+id);
				//System.out.println("1.Patient id: "+patient_id);
				System.out.println("2.Name:"+name);
				System.out.println("3.Price: "+price);
				//System.out.println("2.Billing address: "+billing_address);
				System.out.println("***********************");
				//System.out.println("***************************");
				}
		}
		catch(SQLException e) {e.printStackTrace();}
		
	}
		
	
	/**
	 * This method initializes the fields of the tests table with the default values
	 */
	public static void initialize()
	{
		name="";
		price=0;
	}
	
	/**
	 * This method is used to view a particular record in the tests table
	 */
	private static void viewTest() {

		System.out.println("In view test");
		int selection = 0;
		System.out.println("");
		int selection2 = -1;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter id of the test");
		System.out.println("Enter -1 to go back");
		selection = sc.nextInt();
		while(selection!=-1)
		{
			id = selection;
			if(!isValidTest(id))
			{
				System.out.println("No such test id exists");
			}
			else
			{
				viewTestWithId(id);
			}
			System.out.println("Enter test id or -1 to go back");
			selection = sc.nextInt();	
		}
	}

	/**
	 * This method updates a record in the tests table
	 */
	private static void updateTest() {
		Scanner sc = new Scanner(System.in);
		int selection = 0;
		System.out.println("Enter test id or -1 to go back");
		selection = sc.nextInt();
		while(selection!=-1)
		{
			id = selection;
			if(!isValidTest(id))
				System.out.println("Wrong test id inserted try again");
			else
			{
				viewTestWithId(id);
				int option=0;
				while(option!=-1 && option!=3)
				{
					System.out.println("Enter the option to want to update");
					System.out.println("1. test name");
					System.out.println("2. test price");
					System.out.println("3. Update record");
					System.out.println("Enter -1 to go back");
					option = sc.nextInt();
					sc.nextLine();
					switch(option)
					{
					case 1: System.out.println("test name:");
							name = sc.nextLine();
							break;
					case 2: System.out.println("price:");
							price = sc.nextFloat();
							break;
					case 3: break;
					case -1: break;
					default :System.out.println("Please select a correct option");
					}
				}
					if(option!=-1)
					{
						try {
						Connection conn = login.connection;
						PreparedStatement ps = conn.prepareStatement("Update tests set name=?,price=? where id=?");
						ps.setString(1,name);
						ps.setFloat(2, price);
						ps.setInt(3,id);
						int rows;
						rows = ps.executeUpdate();
						if(rows>0)
						{
							System.out.println("Successfully updates record");
						}
						else
						{
							System.out.println("Record not updated check the console output");
						}
						}
						catch(SQLException e) {e.printStackTrace();}
					}
					
				}
			System.out.println("Enter test id");
			System.out.println("Enter -1 to go back");
			selection = sc.nextInt();
			}
		}
	
	/**
	 * This method deletes a record inn the tests table
	 */
	private static void deleteTest() {
		Scanner sc = new Scanner(System.in);
		int selection = 0;
		System.out.println("Enter test id or -1 to go back");
		selection = sc.nextInt();
		while(selection!=-1)
		{
			id = selection;
		    if(!isValidTest(id))
		    	System.out.println("ID is not valid");
		    else
		    {
		    	Connection conn = login.connection;
		    	try
		    	{
		    		PreparedStatement ps = conn.prepareStatement("Delete from tests where id=?");
		    		ps.setInt(1, id);
		    		int rows=0;
		    		rows = ps.executeUpdate();
		    		if(rows>0)
		    		{
		    			System.out.println("Record deleted successfully");
		    		}
		    		else
		    		{
		    			System.out.println("Record not deleted");
		    		}
		    	
		    	}catch(SQLException e) {e.printStackTrace();}
		    }
		    System.out.println("Enter test id or -1 to go back");
		    selection = sc.nextInt();
		}	
	}

}
