import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import java.time.Period;

import javax.naming.spi.ResolveResult;

import java.io.*;

/**
 * This java file has all the SQL code for the Check In Info Table
 */
class CheckInSQL {
    private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/amanend";
    private static final String user = "amanend";
    private static final String password = "ahnv8011";

    static Connection connection = login.connection;
    static Statement statement = login.statement;
    static ResultSet result = login.result;

    /**
     * gets the row id for a specific check in info row
     * 
     * @param pid
     * @param startDate
     * @return
     */
    public static int getRowId(int pid, String startDate) {
        int identifier = 0;
        try {
            String sql1 = "Select id from check_in_info where patient_id=" + pid + " and start_date=" + "'" + startDate
                    + "'";
            Statement stmt1 = login.statement;
            ResultSet rs1 = stmt1.executeQuery(sql1);
            if (rs1.next() == true)
                identifier = rs1.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return identifier;
    }

    /**
     * Looks for all check in info for inputed patient id
     * 
     * @param pid
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static ResultSet viewAllCheckInForPatient(int pid)
            throws ClassNotFoundException, SQLException, ParseException {

        ResultSet returnSet = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM check_in_info WHERE patient_id = ?;");
            ps.setInt(1, pid);
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
     * Finds a single check in info for a patient id and start date
     * 
     * @param pid
     * @param startDate
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static ResultSet viewSingleCheckInForPatient(int pid, String startDate)
            throws ClassNotFoundException, SQLException, ParseException {

        ResultSet returnSet = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM check_in_info WHERE patient_id = ? AND start_date = ?;");
            ps.setInt(1, pid);
            ps.setString(2, startDate);
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
     * Deletes check in info for inputted patient ID
     * 
     * @param pid
     * @throws ParseException
     */
    static void deleteCheckIn(int pid, String startDate) throws ParseException {

        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM check_in_info WHERE patient_id = ? AND start_date = ?;");
            ps.setInt(1, pid);
            ps.setString(2, startDate);
            int id = ps.executeUpdate();

            System.out.println(id);

            if (id > 0) {
                System.out.println("Check In Info deleted");
            } else {
                System.out.println("No check in info found");
            }
        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
        }
    }

    /**
     * Deletes check in info by that record's id
     * 
     * @param pid
     * @throws ParseException
     */
    static void deleteCheckInById(int cid) throws ParseException {

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM check_in_info WHERE id = ?;");
            ps.setInt(1, cid);
            int id = ps.executeUpdate();

            System.out.println(id);

            if (id > 0) {
                System.out.println("Check In Info deleted");
            } else {
                System.out.println("No check in info found");
            }
        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
        }
    }

    /**
     * Normal check in to the hospital where the reception puts what ward and bed
     * the patient is assigned to
     * 
     * @param pid
     * @param wardId
     * @param bedId
     * @param startDate
     * @throws ParseException
     */
    static void normalCheckIn(int pid, int wardId, int bedId, String startDate) throws ParseException {

        int id = 0;

        try {

            if (wardId == 0) {
                emergencyCheckIn(pid, startDate);
            } else {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO check_in_info (patient_id, ward_id, bed_id, start_date) VALUES (?,?,?,?);");

                ps.setInt(1, pid);
                ps.setInt(2, wardId);
                ps.setInt(3, bedId);
                ps.setString(4, startDate);
                id = ps.executeUpdate();
                System.out.println(id);
                ps.close();

                if (id > 0) {
                    System.out.println("Check In Info added");

                } else {
                    System.out.println("Check In Info not added to database");

                }
            }

        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
        }
    }

    /**
     * Emergency check in where a patient gets treated right away and doesn't have
     * time to get an assigned bed or ward when they get to the hospital
     * 
     * @param pid
     * @param startDate
     * @throws ParseException
     */
    static void emergencyCheckIn(int pid, String startDate) throws ParseException {

        int id = 0;
        // java.sql.Date sqlDate = null;
        // if (!startDate.isEmpty() || startDate != null) {
        // sqlDate = getSQLDate(startDate);

        // }

        try {
            // connection = connect();
            statement = connection.createStatement();

            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO check_in_info (patient_id, start_date) VALUES (?,?);");

            ps.setInt(1, pid);
            ps.setString(2, startDate);
            id = ps.executeUpdate();
            System.out.println(id);
            ps.close();

            if (id > 0) {
                System.out.println("Check In Info added");

            } else {
                System.out.println("Check In Info not added to database");

            }
        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
            // return null;
        }
    }

    /**
     * This edits a check in info by looking for a specific patient id and start
     * date
     * 
     * @param pid
     * @param wardId
     * @param bedId
     * @param startDate
     * @param endDate
     * @throws ParseException
     */
    static void editCheckIn(int pid, int wardId, int bedId, String startDate, String endDate, int rowId)
            throws ParseException {

        PreparedStatement ps = null;
        int id = 0;
        try {
            // has ward and bed id
            String sql1 = "UPDATE check_in_info SET ward_id = ?, bed_id = ?, start_date = ?, end_date = ? WHERE id = ?;";
            // no ward and bed id
            String sql2 = "UPDATE check_in_info SET ward_id = NULL, bed_id = NULL, start_date = ?, end_date = ? WHERE id = ?;";
            // has ward, but no bed id
            String sql3 = "UPDATE check_in_info SET bed_id = NULL, ward_id = ?, start_date = ?, end_date = ? WHERE id = ?;";
            // has bed, no ward id
            String sql4 = "UPDATE check_in_info SET ward_id = NULL, bed_id = ?, start_date = ?, end_date = ? WHERE id = ?;";

            if (wardId == 0 && bedId == 0) {
                ps = connection.prepareStatement(sql2);
                ps.setString(1, startDate);
                ps.setString(2, endDate);
                ps.setInt(3, rowId);
                id = ps.executeUpdate();
                ps.close();
            } else if (wardId > 0 && bedId == 0) {
                ps = connection.prepareStatement(sql3);
                ps.setInt(1, wardId);
                ps.setString(2, startDate);
                ps.setString(3, endDate);
                ps.setInt(4, rowId);
                id = ps.executeUpdate();
                ps.close();
            } else if (wardId == 0 && bedId > 0) {
                ps = connection.prepareStatement(sql4);
                ps.setInt(1, bedId);
                ps.setString(2, startDate);
                ps.setString(3, endDate);
                ps.setInt(4, rowId);
                id = ps.executeUpdate();
                ps.close();
            } else if (wardId > 0 && bedId > 0) {
                ps = connection.prepareStatement(sql1);
                ps.setInt(1, wardId);
                ps.setInt(2, bedId);
                ps.setString(3, startDate);
                ps.setString(4, endDate);
                ps.setInt(5, rowId);
                id = ps.executeUpdate();
                ps.close();
            }

            if (id > 0) {
                System.out.println("Check In Info updated");

            } else {
                System.out.println("Check In Info not updated");

            }
        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
            // return null;
        }
    }

    // Close functions

    /**
     * Closes all connection, result and statement objects
     */
    static void close() {
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