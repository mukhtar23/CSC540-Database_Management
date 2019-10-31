import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import java.time.Period;

import javax.naming.spi.ResolveResult;

import java.io.*;

/**
 * This java file holds all the SQL code for the Staff table
 */
class StaffSQL {
    private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/amanend";
    private static final String user = "amanend";
    private static final String password = "ahnv8011";

    static Connection connection = login.connection;
    static Statement statement = login.statement;
    static ResultSet result = login.result;

    /**
     * Looks for Staff in DB by inputed staff id
     * 
     * @param sid
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static ResultSet viewStaffById(int sid) throws ClassNotFoundException, SQLException, ParseException {

        ResultSet returnSet = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM staff WHERE id = ?;");
            ps.setInt(1, sid);
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

    /**
     * Looks for all staff members by inputed job title
     * 
     * @param jobTitle
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static ResultSet viewStaffByJobTitle(String jobTitle)
            throws ClassNotFoundException, SQLException, ParseException {

        jobTitle.toLowerCase();
        ResultSet returnSet = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM staff WHERE jobTitle = ?;");
            ps.setString(1, jobTitle);
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

    /**
     * Gets all staff members in the DB
     * 
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static ResultSet viewAllStaff() throws ClassNotFoundException, SQLException, ParseException {

        ResultSet returnSet = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM staff;");
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

    /**
     * Deletes a staff member by inputed staff id
     * 
     * @param sid
     */
    static void deleteStaffById(int sid) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM staff WHERE id = ?;");
            ps.setInt(1, sid);
            int id = ps.executeUpdate();

            System.out.println(id);

            if (id > 0) {
                System.out.println("Staff deleted");
            } else {
                System.out.println("No Staff Member with that id");
            }
        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
        }
    }

    /**
     * Adds a staff member to DB with inputed parameters from the user
     * 
     * @param name
     * @param dob
     * @param gender
     * @param phone
     * @param address
     * @param jobTitle
     * @param department
     * @param professionalTitle
     * @throws ParseException
     */
    static void addStaff(String name, int age, String gender, String phone, String address, String jobTitle,
            String department, String professionalTitle, int staffId) throws ParseException {
        int id = 0;

        try {

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO staff (name, age, gender, jobTitle, phone, address, department, professionalTitle, id) VALUES (?,?,?,?,?,?,?,?,?);");

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, jobTitle);
            ps.setString(5, phone);
            ps.setString(6, address);
            ps.setString(7, department);
            ps.setString(8, professionalTitle);
            ps.setInt(9, staffId);
            id = ps.executeUpdate();
            System.out.println(id);
            ps.close();

            if (id > 0) {
                System.out.println("Staff Member added");

            } else {
                System.out.println("Staff Member  not added to database");

            }
        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
        }
    }

    /**
     * Edits a staff member with inputed parameters from the user
     * 
     * @param sid
     * @param name
     * @param dob
     * @param gender
     * @param phone
     * @param address
     * @param jobTitle
     * @param department
     * @param professionalTitle
     * @throws ParseException
     */
    static void editStaff(int sid, String name, int age, String gender, String phone, String address, String jobTitle,
            String department, String professionalTitle, int staffId) throws ParseException {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE staff SET name = ?, gender = ?, age = ?, phone = ?, address = ?, jobTitle = ?, department = ?, professionalTitle = ?, id = ? WHERE id= ?;");
            ps.setString(1, name);
            ps.setString(2, gender);
            ps.setInt(3, age);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, jobTitle);
            ps.setString(7, department);
            ps.setString(8, professionalTitle);
            ps.setInt(9, staffId);
            ps.setInt(10, sid);
            int id = ps.executeUpdate();
            ps.close();

            if (id > 0) {
                System.out.println("Staff Member updated");

            } else {
                System.out.println("Staff Member not updated");

            }
        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
            // return null;
        }
    }

    // Close functions

    /**
     * Closes connection object
     * 
     * @param connection
     */
    static void close(Connection connection) {
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
    static void close(Statement statement) {
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
    static void close(ResultSet result) {
        if (result != null) {
            try {
                result.close();
            } catch (Throwable whatever) {
            }
        }
    }
}