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
            + "'customer',"
            + "?,"
            + "?,"
            + "?,"
            + "?)";
    private static final    String customerIntegritySQL = 
            "INSERT INTO Customers (customerID) VALUES(?)";
   
    private                 PreparedStatement registerCustomerPS;
    private                 PreparedStatement customerIntegrityPS;

    public DBConnect()
    {
        try
        {
            conn = DriverManager.getConnection(host, uName, pass);
            registerCustomerPS  = conn.prepareStatement(registerCustomerSQL);
            customerIntegrityPS = conn.prepareStatement(customerIntegritySQL);
        }
        catch (SQLException ex)
        { 
            System.err.printf(
                "DBConnect error in constructor. " + 
                ex.getMessage());
        }
    }
    
    public ResultSet read(String query) throws SQLException
    {
        Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        return stmt.executeQuery(query);
    }
    
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
    
    public boolean login(User user)
    {
        return write("UPDATE Users "
            + "SET isLoggedIn = true "
            + "WHERE userID = " + user.getUserID());
    }
    
    public boolean logout(User user)
    {
        return write("UPDATE Users "
            + "SET isLoggedIn = false "
            + "WHERE userID = " + user.getUserID());
    }
    
    public int register(String n, String sn, String e, String pw, String p)
    {
        int userID = -1;
        try {
            registerCustomerPS.setString(1, n);
            registerCustomerPS.setString(2, sn);
            registerCustomerPS.setString(3, e);
            registerCustomerPS.setString(4, pw);
            registerCustomerPS.executeUpdate();
            
            ResultSet rs = read("SELECT userID FROM Users "
                    + "WHERE email = '" + e + "'");
            rs.first();
            userID = rs.getInt(1);
            customerIntegrityPS.setInt(1, userID);
            customerIntegrityPS.executeUpdate();
            
            if (!p.isEmpty())
            {
                String phone = p.equals("") ? "NULL" : p;
                write("UPDATE Customers "
                        + "SET mobile_number= '" + phone
                        + "' WHERE customerID = " + userID);
            }
        } catch (SQLException ex)
        {
            System.err.println("Register SQL error. " + ex.getMessage());
        }
        return userID;
    }
}
