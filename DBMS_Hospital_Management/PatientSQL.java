import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import java.time.Period;

import javax.naming.spi.ResolveResult;

import java.io.*;

/**
 * This java file holds all the SQL code for the Patients table
 */
class PatientSQL {
    private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/amanend";
    private static final String user = "amanend";
    private static final String password = "ahnv8011";

    static Connection connection = login.connection;
    static Statement statement = login.statement;
    static ResultSet result = login.result;

    /**
     * This method looks for a patient by an inputed patient id
     * 
     */
    public static ResultSet viewPatientById(int pid) throws ClassNotFoundException, SQLException, ParseException {

        ResultSet returnSet = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM patients WHERE id = ?;");
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
     * This method looks for a patient by an inputed patient id
     * 
     */
    public static ResultSet viewAllPatients() throws ClassNotFoundException, SQLException, ParseException {

        ResultSet returnSet = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM patients;");
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
     * This deletes a patient by an inputed patient id
     * 
     * @param pid
     */
    static void deletePatientById(int pid) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM patients WHERE id = ?;");
            ps.setInt(1, pid);
            int id = ps.executeUpdate();

            System.out.println(id);

            if (id > 0) {
                System.out.println("Patient deleted");
            } else {
                System.out.println("Patient not deleted");
            }
        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
        }
    }

    /**
     * Method that adds a Patient to the db by inputed parameters by the user
     * 
     * @param name
     * @param dob
     * @param gender
     * @param phone
     * @param address
     * @param ssn
     * @param status
     * @param wardId
     * @throws ParseException
     */
    static void addPatient(String name, String dob, String gender, String phone, String address, String ssn,
            String status, int wardId, int treatmentPlan, int completingTreatment, int patientId, int age,
            String in_ward) throws ParseException {

        int id = 0;

        try {
            String sql1 = "INSERT INTO patients (ssn, name, dateOfBirth, gender, phone, address, status, ward_id, age, processing_treatment_plan, completing_treatment, id, in_ward) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
            String sql2 = "INSERT INTO patients (ssn, name, dateOfBirth, gender, phone, address, status, age, processing_treatment_plan, completing_treatment, id, in_ward) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";

            if (wardId == 0) {
                PreparedStatement ps = connection.prepareStatement(sql2);
                ps.setString(1, ssn);
                ps.setString(2, name);
                ps.setString(3, dob);
                ps.setString(4, gender);
                ps.setString(5, phone);
                ps.setString(6, address);
                ps.setString(7, status);
                ps.setInt(8, age);
                ps.setInt(9, treatmentPlan);
                ps.setInt(10, completingTreatment);
                ps.setInt(11, patientId);
                ps.setString(12, in_ward);
                id = ps.executeUpdate();
                System.out.println(id);
                ps.close();

            } else {
                PreparedStatement ps = connection.prepareStatement(sql1);
                ps.setString(1, ssn);
                ps.setString(2, name);
                ps.setString(3, dob);
                ps.setString(4, gender);
                ps.setString(5, phone);
                ps.setString(6, address);
                ps.setString(7, status);
                ps.setInt(8, wardId);
                ps.setInt(9, age);
                ps.setInt(10, treatmentPlan);
                ps.setInt(11, completingTreatment);
                ps.setInt(12, patientId);
                ps.setString(13, in_ward);
                id = ps.executeUpdate();
                System.out.println(id);
                ps.close();

            }

            if (id > 0) {
                System.out.println("Patient added");

            } else {
                System.out.println("Patient not added to database");

            }
        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
        }
    }

    /**
     * Method that edits a patient with inputed parameters by the user
     * 
     * @param pid
     * @param name
     * @param dob
     * @param gender
     * @param phone
     * @param address
     * @param ssn
     * @param status
     * @param wardId
     * @throws ParseException
     */
    static void editPatient(int pid, String name, String dob, String gender, String phone, String address, String ssn,
            String status, int wardId, int treatmentPlan, int completingTreatment, int patientId, int age,
            String in_ward) throws ParseException {

        String sql1 = "UPDATE patients SET name = ?, dateOfBirth = ?, gender = ?, age = ?, phone = ?, address = ?, "
                + "ssn = ?, status= ?, ward_id = ?, processing_treatment_plan = ?, completing_treatment = ?, id = ?, in_ward = ? WHERE id= ?;";
        String sql2 = "UPDATE patients SET name = ?, dateOfBirth = ?, gender = ?, age = ?, phone = ?, address = ?, "
                + "ssn = ?, status= ?, ward_id = NULL, processing_treatment_plan = ?, completing_treatment = ?, id = ?, in_ward = ? WHERE id= ?;";

        int id = 0;
        try {
            // connection = connect();
            if (wardId != 0) {
                PreparedStatement ps = connection.prepareStatement(sql1);
                ps.setString(1, name);
                ps.setString(2, dob);
                ps.setString(3, gender);
                ps.setInt(4, age);
                ps.setString(5, phone);
                ps.setString(6, address);
                ps.setString(7, ssn);
                ps.setString(8, status);
                ps.setInt(9, wardId);
                ps.setInt(10, treatmentPlan);
                ps.setInt(11, completingTreatment);
                ps.setInt(12, patientId);
                ps.setString(13, in_ward);
                ps.setInt(14, pid);
                id = ps.executeUpdate();
                ps.close();
            } else {
                PreparedStatement ps = connection.prepareStatement(sql2);
                ps.setString(1, name);
                ps.setString(2, dob);
                ps.setString(3, gender);
                ps.setInt(4, age);
                ps.setString(5, phone);
                ps.setString(6, address);
                ps.setString(7, ssn);
                ps.setString(8, status);
                ps.setInt(9, treatmentPlan);
                ps.setInt(10, completingTreatment);
                ps.setInt(11, patientId);
                ps.setString(12, in_ward);
                ps.setInt(13, pid);
                id = ps.executeUpdate();
                ps.close();
            }

            if (id > 0) {
                System.out.println("Patient updated");

            } else {
                System.out.println("Patient not updated");

            }
        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
        }
    }

    /**
     * Releases Patient from the hospital
     * 
     * Updates Patients, Billing Accounts, Medical Records, Check In Info, Beds
     * 
     * @throws ClassNotFoundException
     */
    static void releasePatient(int pid, String startDate, String endDate)
            throws ParseException, ClassNotFoundException {

        int id = 0;
        PreparedStatement ps1 = null;
        try {
            connection.setAutoCommit(false); // set auto commit to false

            // Sets end date for billing account for this patient
            ps1 = connection.prepareStatement(
                    "UPDATE billing_accounts SET end_date = ?, settled = 1 WHERE patient_id= ? AND start_date = ?;");
            ps1.setString(1, endDate);
            ps1.setInt(2, pid);
            ps1.setString(3, startDate);
            id = ps1.executeUpdate();

            if (id > 0) {
                System.out.println("Billing Account Updated");
            } else {
                System.out.println("Billing Account Not Updated");

            }

            // Updates accommodation fee for this patient and start date
            BillingAccount.updateAccomodationFee(pid, startDate);

            // sets end date for medical records for this patient
            ps1 = connection.prepareStatement(
                    "UPDATE medical_records SET end_date = ?, active = 0  WHERE patient_id= ? AND start_date = ?;");
            ps1.setString(1, endDate);
            ps1.setInt(2, pid);
            ps1.setString(3, startDate);
            id = ps1.executeUpdate();

            if (id > 0) {
                System.out.println("Medical Record Updated");
            } else {
                System.out.println("Medical Record Not Updated");

            }
            // sets end date for check in info for this patient
            ps1 = connection
                    .prepareStatement("UPDATE check_in_info SET end_date = ? WHERE patient_id= ? AND start_date = ?;");
            ps1.setString(1, endDate);
            ps1.setInt(2, pid);
            ps1.setString(3, startDate);
            id = ps1.executeUpdate();

            if (id > 0) {
                System.out.println("Check In Info Updated");
            } else {
                System.out.println("Check In Info Not Updated");

            }

            // Removes patient from the bed they are currently assigned to
            ps1 = connection.prepareStatement("UPDATE beds SET reserved = 0, patient_id = NULL WHERE patient_id= ?;");
            ps1.setInt(1, pid);
            id = ps1.executeUpdate();

            if (id > 0) {
                System.out.println("Patient Removed From Bed");
            } else {
                System.out.println("Patient Not removed from bed");

            }
            // Sets ward id to null and in ward to no in the patient table for this patient
            ps1 = connection.prepareStatement(
                    "UPDATE patients SET ward_id = NULL, processing_treatment_plan = 0, completing_treatment = 0, in_ward = 'no' WHERE id= ?;");
            ps1.setInt(1, pid);
            id = ps1.executeUpdate();

            if (id > 0) {
                System.out.println("Patient Updated");
            } else {
                System.out.println("Patient Not Updated");

            }

            ps1.close();
            connection.commit();
            /* if both the statements execute successfully then commit the transaction */
        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
            if (connection != null) {
                try {
                    System.out.println("Transaction is being rolled back");
                    connection.rollback(); // if any exception occurs rollback the transaction
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } finally {
            try {
                connection.setAutoCommit(true); // set auto commit to true
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets all the tests that a patient has taken from the DB
     * 
     * @param pid
     * @return
     */
    static ResultSet testsPatientHasTaken(int pid) {
        ResultSet returnSet = null;

        try {

            PreparedStatement ps = connection.prepareStatement(
                    "select a.patient_id, t.test_id,t.medical_record_id,t.result from test_for_patients t inner join medical_records a on t.medical_record_id=a.id where a.patient_id=?;");
            ps.setInt(1, pid);
            result = ps.executeQuery();
            returnSet = result;
        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
            return null;
        }
        return returnSet;
    }

    // Close functions

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
