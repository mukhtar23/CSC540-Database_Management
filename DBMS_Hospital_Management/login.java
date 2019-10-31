import java.sql.*;
import java.util.Scanner;

public class login {

    private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/amanend";
    private static final String user = "amanend";
    private static final String password = "ahnv8011";

    public static Connection connection = null;
    public static Statement statement = null;
    public static ResultSet result = null;

    public static void main(String[] args) {
        try {

            Class.forName("org.mariadb.jdbc.Driver");

            try {
                System.out.println("Connecting to database...");
                connection = DriverManager.getConnection(jdbcURL, user, password);
                statement = connection.createStatement();
                // Runtime.getRuntime().exec("clear");
                System.out.println("\t\tWelcome to wolfhospital Management System\n\n");
                boolean firstCheck = false;
                do {
                    if (firstCheck)
                        System.out.println("Incorrect Credentials Entered!!! \nPlease Try Again.\n\n");
                    System.out.println("Please enter your username: ");
                    Scanner in = new Scanner(System.in);
                    String loginUser = in.nextLine();
                    System.out.println("Please enter your password: ");
                    String loginPwd = in.nextLine();

                    String sqlCred = "select username,password,jobtitle from credentials where username=  '" + loginUser
                            + "' and password='" + loginPwd + "'";

                    // System.out.println(sqlCred);
                    result = statement.executeQuery(sqlCred);
                    firstCheck = true;
                } while (!result.next());
                /*
                 * { System.out.println("Incorrect Credentials Entered!!! "); }
                 */
                System.out.println("Login successful!!! \n");

                UsersInterface.callUI();

            } finally {
                close(result);
                close(statement);
                close(connection);
            }
        }

        catch (Throwable oops) {
            oops.printStackTrace();
        }
    }

    static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Throwable whatever) {
            }
        }
    }

    static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (Throwable whatever) {
            }
        }
    }

    static void close(ResultSet result) {
        if (result != null) {
            try {
                result.close();
            } catch (Throwable whatever) {
            }
        }
    }

}
