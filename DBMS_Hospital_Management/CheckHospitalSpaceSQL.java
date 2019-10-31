import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import java.time.Period;

import javax.naming.spi.ResolveResult;

import java.io.*;

/**
 * This java file holds all the SQL code to check if there is space in the
 * hospital
 */
class CheckHospitalSpaceSQL {
    private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/amanend";
    private static final String user = "amanend";
    private static final String password = "ahnv8011";

    static Connection connection = login.connection;
    static Statement statement = login.statement;
    static ResultSet result = login.result;

    /**
     * This method gets all the available beds in the hospital to see if there is
     * space to admit a patient
     * 
     */
    public static ResultSet checkHospitalSpace() throws ClassNotFoundException, SQLException, ParseException {
        // Connection connection = null;

        ResultSet returnSet = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM beds WHERE reserved = 0;");
            result = ps.executeQuery();
            returnSet = result;
            ps.close();
        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
            return null;
        }
        return returnSet;
    }

    // Close functions

    /**
     * Closes all connection, result and statement objects
     */
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Throwable whatever) {
            }
        }

        // if (statement != null) {
        // try {
        // statement.close();
        // } catch (Throwable whatever) {
        // }
        // }

        // if (result != null) {
        // try {
        // result.close();
        // } catch (Throwable whatever) {
        // }
        // }
    }

    /**
     * Closes connection object
     * 
     * @param connection
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Throwable whatever) {
            }
        }
    }

    /**
     * Closes statement objects
     * 
     * @param statement
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (Throwable whatever) {
            }
        }
    }

    /**
     * Closes result objects
     * 
     * @param result
     */
    public static void close(ResultSet result) {
        if (result != null) {
            try {
                result.close();
            } catch (Throwable whatever) {
            }
        }
    }

}