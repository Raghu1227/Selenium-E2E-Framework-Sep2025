package com.orangehrm.utilities;

import com.orangehrm.base.BaseClass;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/orangehrm";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "password";
    private static final Logger logger=BaseClass.logger;

    public static Connection getDBConnection() {
        // Implement database connection logic here
        // Example: Use JDBC to connect to the database
        logger.info("Connecting to database: " + DB_URL);
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            logger.info("Database connected successfully.");
            return conn;
        } catch (SQLException e) {
            logger.error("Failed to connect to the database.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    //Get the employee details from the database amd store in a map and return the map
    public static Map<String,String> getEmployeeDetails(String employee_id) {
        // Implement logic to fetch employee data from the database
        logger.info("Fetching employee data from the database...");
        // Example: Execute SQL query to retrieve employee data
        String query = "SELECT * FROM hs_hr_employee WHERE employee_id = " + employee_id;

        Map<String, String> employeedetails = new HashMap<>();
        try {
            Connection conn = getDBConnection();
            Statement stmt = conn.createStatement();
            // Execute the query and process the result set
            ResultSet rs = stmt.executeQuery(query);
            logger.info("Executing query:."+query);
            if(rs.next()){
                String firstname = rs.getString("emp_firstname");
                String lastname = rs.getString("emp_lastname");
                String middlename = rs.getString("emp_middle_name");
                employeedetails.put("firstname", firstname);
                employeedetails.put("lastname", lastname);
                employeedetails.put("middlename", middlename!=null? middlename:"");
                System.out.println("Employee details fetched successfully: " + employeedetails);

            } else {
                logger.error("No employee found with ID: " + employee_id);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeedetails;
    }

}
