/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Stefan
 */
public class DBConnect
{ 
    private                 Connection conn;
    private static final    String host = "jdbc:derby://localhost:1527/CoD";
    private static final    String uName = "root";
    private static final    String pass = "root";
    private static final    String registerCustomerSQL =
            "INSERT INTO Users (type, name, surname, email, password)"
            + "VALUES ("
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?)";
    private static final    String customerIntegritySQL = 
            "INSERT INTO Customers (customerID) VALUES(?)";
    private static final    String employeesIntegritySQL = 
            "INSERT INTO Employees (employeeID) VALUES(?)";
   
    private                 PreparedStatement registerPS;
    private                 PreparedStatement customerIntegrityPS;
    private                 PreparedStatement employeeIntegrityPS;

    public DBConnect()
    {
        try
        {
            conn = DriverManager.getConnection(host, uName, pass);
            registerPS  = conn.prepareStatement(registerCustomerSQL);
            customerIntegrityPS = conn.prepareStatement(customerIntegritySQL);
            employeeIntegrityPS = conn.prepareStatement(employeesIntegritySQL);
        }
        catch (SQLException ex)
        { 
            System.err.printf(
                "DBConnect error in constructor. " + 
                ex.getMessage());
        }
    }
    
    /**
     * Read from the database.
     * Used for SELECT.
     * 
     * @param query SQL query to be sent to the DB (String)
     * @return ResultSet containing resulting rows
     * @throws SQLException
     */
    public ResultSet read(String query) throws SQLException
    {
        Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        return stmt.executeQuery(query);
    }
    
    /**
     * Write a query to the database.
     * Used for UPDATE, INSERT, DELETE.
     * 
     * @param query SQL query to be sent to the DB (String)
     * @return true if execution was successful, false otherwise
     */
    public boolean write(String query)
    {
        boolean ok = true;
        try (Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE))
        {
            stmt.executeUpdate(query);
        } catch (SQLException ex) { ok = !ok; }
        return ok;
    }
    
    /**
     * Logs a user in.
     * 
     * @param user
     * @return true if login successful, false otherwise
     */
    public boolean login(User user)
    {
        return write("UPDATE Users "
            + "SET isLoggedIn = true "
            + "WHERE userID = " + user.getUserID());
    }
    
    /**
     * Logs a user out.
     * 
     * @param user
     * @return true if logout successful, false otherwise
     */
    public boolean logout(User user)
    {
        return write("UPDATE Users "
            + "SET isLoggedIn = false "
            + "WHERE userID = " + user.getUserID());
    }
    
    /**
     * Registers a user provided the details and returns a userID, or -1,
     * if registration failed.
     * 
     * @param t user type
     * @param n user name
     * @param sn user surname
     * @param e user email
     * @param pw user password (encrypted!)
     * @param p user phone (optional, but must be empty string, not null)
     * @return userID (int) or -1
     */
    public int register(String t, String n, String sn, String e, String pw, String p)
    {
        int userID = -1;
        try {
            registerPS.setString(1, t);
            registerPS.setString(2, n);
            registerPS.setString(3, sn);
            registerPS.setString(4, e);
            registerPS.setString(5, pw);
            registerPS.executeUpdate();
            
            ResultSet rs = read("SELECT userID FROM Users "
                    + "WHERE email = '" + e + "'");
            rs.first();
            userID = rs.getInt(1);
            customerIntegrityPS.setInt(1, userID);
            customerIntegrityPS.executeUpdate();
            
            if (t.equals("Manager") || t.equals("Staff"))
            {
                employeeIntegrityPS.setInt(1, userID);
                employeeIntegrityPS.executeUpdate();
            } else 
            {
                customerIntegrityPS.setInt(1, userID);
                customerIntegrityPS.executeUpdate();
                if (!p.isEmpty())
                {
                    String phone = p.equals("") ? "NULL" : p;
                    write("UPDATE Customers "
                        + "SET mobile_number= '" + phone
                        + "' WHERE customerID = " + userID);
                }
            }
        } catch (SQLException ex)
        {
            System.err.println("Register SQL error. " + ex.getMessage());
        }
        return userID;
    }
}
