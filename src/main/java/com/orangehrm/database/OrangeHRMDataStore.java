package com.orangehrm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class OrangeHRMDataStore {
	private static Connection connection;
	private static PreparedStatement statement = null;
	Employee employee;
	
	 // Initialize H2 (in-memory for tests)
    public static void init() throws Exception {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
            try (
            		Statement stmt = connection.createStatement()
            	) {
            	// Create a credentials table
                stmt.execute("CREATE TABLE credentials (username VARCHAR(50), password VARCHAR(50))");
                //Insert into credentials table
                stmt.execute("INSERT INTO credentials VALUES('Admin', 'admin123')");
                stmt.execute("CREATE TABLE IF NOT EXISTS employees (employee_id INT AUTO_INCREMENT PRIMARY KEY, first_name VARCHAR(50), middle_name VARCHAR(50), last_name VARCHAR(50))");
            }
        }
    }
    
    public static Employee insertEmployee() throws SQLException {
        Employee emp = new Employee();
        //emp.setEmployeeId(getEmployeeId());
        emp.setFirstName(getRandomStringLetters());
        emp.setMiddleName("Alwane");
        emp.setLastName("Khaka");

        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO employees (first_name, middle_name, last_name) VALUES (?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, emp.getFirstName());
            stmt.setString(2, emp.getMiddleName());
            stmt.setString(3, emp.getLastName());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    emp.setEmployeeId(generatedKeys.getInt(1));
                }
            }
        }
        return emp;
    }
    
//    public Employee getEmployeeFromDB() throws SQLException {
//    	
//    	employee = new Employee();
//    	Statement statement = connection.createStatement();
//    	ResultSet rs = statement.executeQuery("SELECT * FROM employees LIMIT 1");
//    	if(rs.next()) {
//    	    employee.setFirstName(rs.getString("first_name") != null ? rs.getString("first_name") : "");
//    	    employee.setMiddleName(rs.getString("middle_name") != null ? rs.getString("middle_name") : "");
//    	    employee.setLastName(rs.getString("last_name") != null ? rs.getString("last_name") : "");
//    	    employee.setEmployeeId(rs.getInt("employee_id"));
//    	}
//    	connection.close();
//    	return employee;
//    }
    
//    public int getGeneratedEmployeeId() throws SQLException {
//    	
//    	ResultSet generatedKeys = statement.getGeneratedKeys();
//    	int newEmployeeId = 0;
//    	if(generatedKeys.next()) {
//    		newEmployeeId = generatedKeys.getInt(1);
//    	}
//    	return newEmployeeId;
//    }
    
//    public static int getEmployeeId() {
//		Random rand = new Random();
//		return rand.nextInt(300); 
//	}
    
    public static String getRandomStringLetters() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for(int i = 0; i<7; i++) {
			 int index = random.nextInt(characters.length());
	            sb.append(characters.charAt(index));
		}
		return sb.toString();
	}
    
    // Fetch username
    public static String getUsername() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT username FROM credentials LIMIT 1");
            if (rs.next()) return rs.getString("username");
        }
        return null;
    }

    // Fetch password
    public static String getPassword() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT password FROM credentials LIMIT 1");
            if (rs.next()) return rs.getString("password");
        }
        return null;
    }

}
