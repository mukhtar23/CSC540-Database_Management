import java.util.*;

public class MedicalRecord_old{
	Scanner sc = new Scanner(System.in);
	int medical_record_id;
	int patient_id;
	String start_date;
	String end_date;
	int doctor_id;
	String prescription;
	String diagnosis;
	String treatment;
	boolean active;

	public static void medicalRecordOptions() {
		Scanner sc = new Scanner(System.in);
		/*
		 * System.out.println("1. View CheckIn Information");
		 * System.out.println("2. Emergency CheckIn");
		 * System.out.println("3. Normal CheckIn");
		 * System.out.println("4. Update CheckIn Information");
		 * System.out.println("5. Delete checkIn information");
		 * System.out.println("6. Return to home screen");
		 * System.out.println("Select suitable option");
		 */
		int selection = 0;

		do {
			System.out.println("1. Create Medical Record Information");
			System.out.println("2. View Medical Record Information");
			System.out.println("3. Update Medical Record Information");
			System.out.println("4. Delete Medical record Information");
			System.out.println("5. Return to home screen");
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
				updateMedicalRecord();
				break;
			case 4:
				deleteMedicalRecord();
				break;
			case 5:
				System.out.println("Back to home in menu");
				break;
			default:
				System.out.println("You have entered a wrong option");
			}
		} while (selection != 5);
		// sc.close();

	}

	private static void viewMedicalRecord() {
		System.out.println("In view check in");

	}

	private static void createMedicalRecord() {

		System.out.println("In emergency check in");
	}

	private static void updateMedicalRecord() {
		System.out.println("In normal check in");
	}

	private static void deleteMedicalRecord() {
		System.out.println("In update check in");
	}

}
