import java.util.*;
import java.sql.*;
/**
 * This class contains the APIs for the medical_records table
 */
public class MedicalRecord {
	Scanner sc = new Scanner(System.in);
	static int id;
	static int patient_id;
	static String start_date;
	static String end_date;
	static int doctor_id;
	static String prescription;
	static String diagnosis;
	static int treatment;
	static boolean active=true;

	public static void medicalRecordOptions() {
		Scanner sc = new Scanner(System.in);
		int selection = 0;

		do {
			System.out.println("1. Create Medical Record Information");
			System.out.println("2. View Medical Record Information for a particular patient");
			System.out.println("3. View all Medical records");
			System.out.println("4. Update Medical Record Information");
			System.out.println("5. Delete Medical record Information");
			System.out.println("6. Return to home screen");
			System.out.println("Select suitable option");
			System.out.println("Choose what operation you want to perform");
			selection = sc.nextInt();
			switch (selection) {
			case 1:
				createMedicalRecord();
				break;
			case 2:
				viewMedicalRecord();
				break;
			case 3:
				viewAllMedicalRecords();
				break;
			case 4:
				updateMedicalRecord();
				break;
			case 5:
				deleteMedicalRecord();
				break;
			case 6:  
				 System.out.println("Back to home in menu");
				 break;
			default:
				System.out.println("You have entered a wrong option");
			}
		} while (selection != 6);
	}
	
	/**
	 * This method initializes the fields of the medical_records table to their default values
	 */
	public static void initialize()
	{
		doctor_id=0;
		treatment=0;
		prescription="";
		diagnosis="";
		active=true;
		end_date = null;
	}
	
	/**
	 * This method prints a medical_record given a patient_id and a start_date
	 * @param patient_id
	 * @param start_date
	 */
	private static void getRecord(int patient_id,String start_date)
	{
		try
		{
			String sql="";
			
			Statement stmt = login.connection.createStatement();
			if(start_date.equals(""))
				 sql = "Select * from medical_records where patient_id="+patient_id+"";
			else
				 sql = "Select * from medical_records where patient_id="+patient_id+" and start_date="+"'"+start_date+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if(start_date.equals(""))
			System.out.println("Printing all records for patient with id"+" "+patient_id);
			while(rs.next())
			{
			patient_id = rs.getInt("patient_id");
			start_date = rs.getString("start_date");
			end_date = rs.getString("end_date");
			doctor_id = rs.getInt("doctor_id");
			prescription = rs.getString("prescription");
			diagnosis = rs.getString("diagnosis");
			treatment = rs.getInt("treatment");
			active = rs.getBoolean("active");
			System.out.println("-------------------------");
			System.out.println("1.Patient id:"+patient_id);
			System.out.println("2.start date:"+start_date);
			System.out.println("3.end date:"+end_date);
			System.out.println("4.Doctor id:"+doctor_id);
			System.out.println("5.Prescription:"+prescription);
			System.out.println("6.Diagnosis:"+diagnosis);
			System.out.println("7.Treatment:"+treatment);
			System.out.println("8.Active:"+active);
			System.out.println("-------------------------");
			}
		}catch(SQLException e) {e.printStackTrace();};
	}
	
	/**
	 * This method displays all the medical_records in the medical_records table
	 */
	private static void viewAllMedicalRecords()
	{
		try
		{
			String sql="";
			
			Statement stmt = login.connection.createStatement();
				 sql = "Select * from medical_records";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("Printing all the medical records");
			while(rs.next())
			{
			System.out.println("---------------------------");
			patient_id = rs.getInt("patient_id");
			start_date = rs.getString("start_date");
			end_date = rs.getString("end_date");
			doctor_id = rs.getInt("doctor_id");
			prescription = rs.getString("prescription");
			diagnosis = rs.getString("diagnosis");
			treatment = rs.getInt("treatment");
			active = rs.getBoolean("active");
			System.out.println("1.Patient id: "+patient_id);
			System.out.println("2.start date: "+start_date);
			System.out.println("3.end date: "+end_date);
			System.out.println("4.Doctor id: "+doctor_id);
			System.out.println("5.Prescription: "+prescription);
			System.out.println("6.Dianosis: "+diagnosis);
			System.out.println("7.Treatment: "+treatment);
			System.out.println("8.Active: "+active);
			System.out.println("---------------------------");
			}
		}catch(SQLException e) {e.printStackTrace();};
	}
	
	/**
	 * This method returns the id of a medical record given patient_id and start_date
	 * @param patient_id
	 * @param start_date
	 * @return
	 */
	private static int getMedicalRecordId(int patient_id,String start_date)
	{
		int id=0;
		try
		{
			Connection conn = login.connection;
		    Statement stmt = conn.createStatement();
			String sql = "Select * from medical_records where patient_id="+patient_id+" and start_date="+"'"+start_date+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				 id = rs.getInt("id");
			}
		}catch(SQLException e) 
		{
			e.printStackTrace();
		} 
		return id;
	}
	
	/**
	 * This method checks if a medical_record exists in the medical_records table given a patient_id and a start_date
	 * @param patient_id
	 * @param start_date
	 * @return 
	 */
	private static boolean isValid(int patient_id,String start_date)
	{
		try
		{
			Connection conn = login.connection;
		    Statement stmt = conn.createStatement();
			String sql = "Select * from medical_records where patient_id="+patient_id+" and start_date="+"'"+start_date+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if(!rs.next())
			{
				return false;
			}
		}catch(SQLException e) 
		{
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * This method checks if a patient_id is valid
	 * @param patient_id
	 * @return
	 */
	private static boolean existsPatient(int patient_id)
	{
		try
		{
			Connection conn = login.connection;
		    Statement stmt = conn.createStatement();
			String sql = "Select * from patients where id="+patient_id;
			ResultSet rs = stmt.executeQuery(sql);
			if(!rs.next())
			{
				return false;
			}
		}catch(SQLException e)  
		{
			e.printStackTrace();
		}; 
		return true;
	}
	
	/**
	 * This method displays a record in the medical_records table
	 */
	private static void viewMedicalRecord() {
		int selection=0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter id of the patient");
		System.out.println("Enter -1 to go back");
		selection = sc.nextInt();
		while(selection!=-1)
		{
			if(selection==-1)
			{
				return;
			}
			else
			{
				patient_id = selection;
				System.out.println("Enter start date of the patient");
				start_date = sc.next();
				if(!isValid(patient_id,start_date))
					System.out.println("You have entered invalid patient id or start date");
				else
				{
					getRecord(patient_id,start_date);
				}
				System.out.println("Enter a patient id or enter -1 to go back:");
				selection = sc.nextInt();
			}
		}
	}
	
	/**
	 * This method creates a new medical_record
	 */
	private static void createMedicalRecord() {
		int selection=0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter id of the patient");
		System.out.println("Enter -1 to go back");
		selection = sc.nextInt();
		while(selection!=-1)
		{
			if(selection==-1)
			{
				return;
			}
			else
			{
					patient_id = selection;
					boolean ans3 = existsPatient(patient_id); 
					if(ans3==false)
					{
						System.out.println("This patient does not exist");
					}
					else
					{
						initialize();
						System.out.println("Enter start date of the patient");
						start_date = sc.next();
						sc.nextLine();
						if(isValid(patient_id,start_date))
							System.out.println("Already exists medical record with same patient id and start date");
						else
						{
							int option = 0;
							while(option!=7 && option!=-1)
							{
								System.out.println("1.Insert end date");
								System.out.println("2.Insert doctor id");
								System.out.println("3.Insert prescription");
								System.out.println("4.Insert diagnosis");
								System.out.println("5.Insert treatment");
								System.out.println("6.Insert active(true/false)");
								System.out.println("7.Insert Record");
								System.out.println("-1 to go back");
								option = sc.nextInt();
								sc.nextLine();
								switch(option)
								{                        
								case 1: System.out.println("End date:");
										end_date = sc.next();
										break;
								case 2: System.out.println("Doctor id:");
										doctor_id = sc.nextInt();
										boolean ans = existsDoctorId(doctor_id);
										if(ans==false)
										{
											System.out.println("Invalid doctor ID re-enter the record");
											option = -1;
										}
										break;
								case 3: System.out.println("Prescription:");
										prescription = sc.nextLine();
										break;
								case 4: 
										System.out.println("Diagnosis:");
										diagnosis = sc.nextLine();
										break;
								case 5: System.out.println("Treatment:");
										treatment = sc.nextInt();
										boolean ans2 = existsTreatmentId(treatment);
										if(ans2==false)
										{
											System.out.println("Invalid treatment Id re-enter the record");
											option = -1;
										}
										sc.nextLine();
										break;
								case 6: System.out.println("Active:");
										active = sc.nextBoolean();
										break;
								case 7: break;
								case -1: break;
								default: System.out.println("Enter a valid option");
								}
							}
							if(option!=-1)
							{
								if(treatment==0 && doctor_id==0)
								{
									try
									{
										PreparedStatement ps = login.connection.prepareStatement("Insert into medical_records (patient_id,start_date,end_date,prescription,diagnosis,active) values (?,?,?,?,?,?)");
										ps.setInt(1, patient_id);
									    ps.setString(2, start_date);
									    ps.setString(3,end_date);                           
									    ps.setString(4, prescription);
									    ps.setString(5, diagnosis);
									    ps.setBoolean(6, active);
										int rows = ps.executeUpdate();
										if(rows>0)
										{
											System.out.println("Successfully inserted in the medical record");
										}
										else
										{
											System.out.println("Medical Record not inserted");
										}
									}catch(SQLException e) {e.printStackTrace();};
								}
								else if(treatment==0 && doctor_id!=0)
								{
									try
									{
										PreparedStatement ps = login.connection.prepareStatement("Insert into medical_records (patient_id,start_date,end_date,doctor_id,prescription,diagnosis,active) values (?,?,?,?,?,?,?)");
										ps.setInt(1, patient_id);
									    ps.setString(2, start_date);
									    ps.setString(3,end_date);                           
									    ps.setInt(4, doctor_id);
									    ps.setString(5, prescription);
									    ps.setString(6, diagnosis);
									    ps.setBoolean(7, active);
										int rows = ps.executeUpdate();
										if(rows>0)
										{
											System.out.println("Successfully inserted in the medical record");
										}
										else
										{
											System.out.println("Medical Record not inserted");
										}
									}catch(SQLException e) {e.printStackTrace();};

								}
								else if(treatment!=0 && doctor_id==0)
								{
									try
									{
										PreparedStatement ps = login.connection.prepareStatement("Insert into medical_records (patient_id,start_date,end_date,prescription,diagnosis,treatment,active) values (?,?,?,?,?,?,?)");
										ps.setInt(1, patient_id);
									    ps.setString(2, start_date);
									    ps.setString(3,end_date);                           
									    ps.setString(4, prescription);
									    ps.setString(5, diagnosis);
									    ps.setInt(6, treatment);
									    ps.setBoolean(7, active);
										int rows = ps.executeUpdate();
										if(rows>0)
										{
											System.out.println("Successfully inserted in the medical record");
										}
										else
										{
											System.out.println("Medical Record not inserted");
										}
										ps = login.connection.prepareStatement("Update patients set processing_treatment_plan=? where id=?");
										ps.setInt(1, treatment);
										ps.setInt(2, patient_id);
										int result2 = ps.executeUpdate();
										if(result2>0)
										{
											System.out.println("Successfully updated the processing treatment plan in patient");
										}
										else
										{
											System.out.println("Could not update the processing treatment plan in patient please check the console output!");
										}
									}catch(SQLException e) {e.printStackTrace();};

								}
								else
								{
									try
									{
										PreparedStatement ps = login.connection.prepareStatement("Insert into medical_records (patient_id,start_date,end_date,doctor_id,prescription,diagnosis,treatment,active) values (?,?,?,?,?,?,?,?)");
										ps.setInt(1, patient_id);
									    ps.setString(2, start_date);
									    ps.setString(3,end_date);                           
									    ps.setInt(4, doctor_id);
									    ps.setString(5, prescription);
									    ps.setString(6, diagnosis);
									    ps.setInt(7, treatment);
									    ps.setBoolean(8, active);
										int rows = ps.executeUpdate();
										if(rows>0)
										{
											System.out.println("Successfully inserted in the medical record");
										}
										else
										{
											System.out.println("Medical Record not inserted");
										}
										ps = login.connection.prepareStatement("Update patients set processing_treatment_plan=? where id=?");
										ps.setInt(1, treatment);
										ps.setInt(2, patient_id);
										int result2 = ps.executeUpdate();
										if(result2>0)
										{
											System.out.println("Successfully updated the processing treatment plan in patient");
										}
										else
										{
											System.out.println("Could not update the processing treatment plan in patient");
										}
									}catch(SQLException e) {e.printStackTrace();};

								}
								getRecord(patient_id,start_date);
					}
							}
			}
					System.out.println("Enter a patient id or enter -1 to go back:");
					selection = sc.nextInt();
					}
			}
		}

		
	/**
	 * This record checks if the entered doctor_id is valid
	 * @param doctor_id
	 * @return
	 */
	public static boolean existsDoctorId(int doctor_id)
	{
		try {
		Connection conn = login.connection;
		Statement stmt = conn.createStatement();
		String sql = "Select id from staff where id="+doctor_id+" "+"and"+" "+"jobTitle='doctor'";
		ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
			return true;
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method checks if the entered treatment is valid
	 * @param treatment_id
	 * @return
	 */
	public static boolean existsTreatmentId(int treatment_id)
	{
		try {
		Connection conn = login.connection;
		Statement stmt = conn.createStatement();
		String sql = "Select id from treatments where id="+treatment_id;
		ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
			return true;
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method updates a record in the medical_records table	 
	 */
	private static void updateMedicalRecord() {
		int selection=0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter id of the patient");
		System.out.println("Enter -1 to go back");
		selection = sc.nextInt();
		
		while(selection!=-1)
		{
			if(selection==-1)
			{
				return;
			}
			else
			{
				patient_id = selection;
				System.out.println("Enter start date of the patient");
				start_date = sc.next();
				if(!isValid(patient_id,start_date))
					System.out.println("Please check the patient id and start date");
				else
				{
					getRecord(patient_id,start_date);
					int id = getMedicalRecordId(patient_id,start_date);
					boolean difftreatment=false;
					int option = 0;
					while(option!=8 && option!=-1)
					{
						
						System.out.println("1.Update start date");
						System.out.println("2.Update end date");
						System.out.println("3.Update doctor id");
						System.out.println("4.Update prescription");
						System.out.println("5.Update diagnosis");
						System.out.println("6.Update treatment");
						System.out.println("7.Update active");
						System.out.println("8.Update Record");
						System.out.println("-1 to go back");
						option = sc.nextInt();
						switch(option)
						{                        
						case 1: System.out.println("Start date:");
								start_date = sc.next();
								break;
						case 2: System.out.println("End date:");
								start_date = sc.next();
								sc.nextLine();
								break;
						case 3: System.out.println("Doctor id:");
								doctor_id = sc.nextInt();
								boolean ans = existsDoctorId(doctor_id);
								if(ans==false)
								{
									System.out.println("Invalid doctor ID re-enter the record");
									option = -1;
								}
								break;
						case 4: System.out.println("Prescription:");
								prescription = sc.next();
								sc.nextLine();
								break;
						case 5: 
								System.out.println("Diagnosis:");
								diagnosis = sc.next();
								sc.nextLine();
								break;
						case 6: System.out.println("Treatment:");
								treatment = sc.nextInt();
								boolean ans2 = existsTreatmentId(treatment);
								if(ans2==false)
								{
									System.out.println("Invalid treatment Id re-enter the record");
									option = -1;
								}
								else
								{
									difftreatment =true;
								}
								sc.nextLine();
								break;
						case 7: System.out.println("Active(true/false):");
						        active = sc.nextBoolean();
						        break;
						case 8: break;
						case -1: break;
						default: System.out.println("Enter a valid option");
						}
					}
					if(option!=-1)
					{
					try
					{	
						if(treatment==0 && doctor_id!=0)
						{
							PreparedStatement ps = login.connection.prepareStatement("Update medical_records set start_date=?,end_date=?,doctor_id=?,prescription=?,diagnosis=?,active=? where id=?");
							ps.setString(1, start_date);
						    ps.setString(2,end_date);                           
						    ps.setInt(3, doctor_id);
						    ps.setString(4, prescription);
						    ps.setString(5, diagnosis);
						    ps.setBoolean(6, active);
						    ps.setInt(7, id);
							int rows = ps.executeUpdate();
							if(rows>0)
							{
								System.out.println("Successfully updated the medical record");

							}
							else
							{
								System.out.println("Medical Record not updated");
							}

						}
						if(treatment!=0 && doctor_id==0)
						{
								PreparedStatement ps = login.connection.prepareStatement("Update medical_records set start_date=?,end_date=?,prescription=?,diagnosis=?,treatment=?,active=? where id=?");
								ps.setString(1, start_date);
							    ps.setString(2,end_date);                           
							    ps.setString(3, prescription);
							    ps.setString(4, diagnosis);
							    ps.setInt(5, treatment);
							    ps.setBoolean(6, active);
							    ps.setInt(7, id);
							    int rows = ps.executeUpdate();
								if(rows>0)
								{
									System.out.println("Successfully updated the medical record");
								}
								else
								{
									System.out.println("Medical Record not updated");
								}
							if(difftreatment)
							{
								PreparedStatement ps1 = login.connection.prepareStatement("Update patients set processing_treatment_plan=? where id=?");
								ps1.setInt(1, treatment);
								ps1.setInt(2,patient_id);
								int result = ps1.executeUpdate();
								if(result>0)
								{
									System.out.println("Successfully updated processing treatment plan in patints");
								}
								else
								{
									System.out.println("Could not update processing treatment plan in patients");
								}
							}
						}
						
						if(treatment==0 && doctor_id==0)
						{
							PreparedStatement ps = login.connection.prepareStatement("Update medical_records set start_date=?,end_date=?,prescription=?,diagnosis=?,active=? where id=?");
							ps.setString(1, start_date);
						    ps.setString(2,end_date);                           
						    ps.setString(3, prescription);
						    ps.setString(4, diagnosis);
						    ps.setBoolean(5, active);
						    ps.setInt(6, id);
							int rows = ps.executeUpdate();
							if(rows>0)
							{
								System.out.println("Successfully updated the medical record");
							}
							else
							{
								System.out.println("Medical Record not updated");
							}
						}
						
						if(treatment!=0 && doctor_id!=0)
						{
								PreparedStatement ps = login.connection.prepareStatement("Update medical_records set start_date=?,end_date=?,doctor_id=?,prescription=?,diagnosis=?,treatment=?,active=? where id=?");
								ps.setString(1, start_date);
							    ps.setString(2,end_date);                           
							    ps.setInt(3, doctor_id);
							    ps.setString(4, prescription);
							    ps.setString(5, diagnosis);
							    ps.setInt(6, treatment);
							    ps.setBoolean(7, active);
							    ps.setInt(8, id);
								int rows = ps.executeUpdate();
								if(rows>0)
								{
									System.out.println("Successfully updated the medical record");

								}
								else
								{
									System.out.println("Medical Record not updated");
								}
								if(difftreatment)
								{
									PreparedStatement ps1 = login.connection.prepareStatement("Update patients set processing_treatment_plan=? where id=?");
									ps1.setInt(1, treatment);
									ps1.setInt(2,patient_id);
									int result = ps1.executeUpdate();
									if(result>0)
									{
										System.out.println("Successfully updated processing treatment plan in patints");
									}
									else
									{
										System.out.println("Could not update processing treatment plan in patients");
									}
								}
													
						
						}
						System.out.println("here");
						getRecord(patient_id,start_date);
					}catch(SQLException e) {e.printStackTrace();};
					
				}}
				System.out.println("Enter a patient id or enter -1 to go back:");
				selection = sc.nextInt();
				
					
		}
			}
		}

	/**
	 * This method deletes a record in the medical_records table
	 */
	private static void deleteMedicalRecord() {
		int selection=0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter id of the patient");
		System.out.println("Enter -1 to go back");
		selection = sc.nextInt();
		while(selection!=-1)
		{
			if(selection==-1)
			{
				return;
			}
			else
			{
				patient_id = selection;
				System.out.println("Enter start date of the patient");
				start_date = sc.next();
				if(!isValid(patient_id,start_date))
					System.out.println("Please check the patient id and start date");
				else
				{
					try {
					Connection conn = login.connection;
					PreparedStatement ps = conn.prepareStatement("Delete from medical_records where patient_id=? and start_date=?");
					 ps.setInt(1, patient_id);
					 ps.setString(2, start_date);
					 int rows = ps.executeUpdate();
					 if(rows>0)
					 {
						 System.out.println("This billing account was successfully deleted");
					 }
					 else
					 {
						 System.out.println("Deletion failed, please check the console output");
					 }
					 }catch(SQLException e) {e.printStackTrace();};
				}			
				
		     }
				System.out.println("Enter a patient id or enter -1 to go back:");
				selection = sc.nextInt();
	}
}
}	

