import java.util.Scanner;

public class Test_old {
	Scanner sc = new Scanner(System.in);
	int id;
	String name;
	float price;

	public static void testOptions() {
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
			System.out.println("1. Insert test information");
			System.out.println("2. View all tests");
			System.out.println("3. Update test information");
			System.out.println("4. Delete test information");
			System.out.println("5. Return to the home screen");
			System.out.println("Select suitable option");
			System.out.println("Choose what operation you want to perform");
			selection = sc.nextInt();
			switch (selection) {
			case 1:
				insertTest();
				break;
			case 2:
				viewTest();
				break;
			case 3:
				updateTest();
				break;
			case 4:
				deleteTest();
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

	private static void insertTest() {
		System.out.println("In insert test");

	}

	private static void viewTest() {

		System.out.println("In view test");
	}

	private static void updateTest() {
		System.out.println("In update test");
	}

	private static void deleteTest() {
		System.out.println("In delete test");
	}

}
