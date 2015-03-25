/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoD;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Stefan
 */
public class User
{
    private final DBConnect conn;
    private String          name;
    private final String    email;
    private final String    pass;
    private String          storedEmail;
    private String          storedPass;
    private int             userID;
    private String          type;

    /**
     *
     * @param email
     * @param password
     * @param conn
     */
    public User(String email, String password, DBConnect conn)
    {
        this.email = email;
        pass = password;
        this.conn = conn;
        getDetails();
    }

    /**
     * Logs the user in.
     * 
     * @return true if password is correct
     */
    public boolean login()
    {
        boolean success = false;
        try {
            byte[] bytePass = pass.getBytes("UTF-8");
            if (email.equals(storedEmail)
                && checkPassword(bytePass))
            {  
                conn.login(this);
                success = true;
            }
        } catch(UnsupportedEncodingException ex) {}
        return success;
    }

    /**
     * Logs the user out.
     */
    public void logout() { conn.logout(this); }
    
    /**
     *
     * @return user's ID
     */
    public int getUserID() { return userID; }
    
    /**
     *
     * @return user's type
     */
    public String getType() { return type; }
    
    /**
     *
     * @return
     */
    public String getName() { return name; }
    
    /**
     *
     * @param email new email to be set
     * @param password attempted password
     * @return true if password is correct, false otherwise 
     */
    public boolean setEmail(String email, byte[] password)
    {
        if (checkPassword(password))
        {
            storedEmail = email;
            conn.write("UPDATE Users "
                    + "SET email = '" + email
                    + "' WHERE userID = " + userID);
            return true;
        } else return false;
    }
    
    /**
     *
     * @param currentPass
     * @param newPass
     * @return true if password is correct, false otherwise
     */
    public boolean setPassword(byte[] currentPass, byte[] newPass)
    {
        if (checkPassword(currentPass))
        {
            storedPass = encryptPassword(newPass);
            conn.write("UPDATE Users "
                    + "SET password= '" + storedPass
                    + "' WHERE userID = " + userID);
            return true;
        } else return false;
    }
    
    /**
     *
     * @param password
     * @return encrypted password, as a String
     */
    public static String encryptPassword(byte[] password) 
    {
        try {
            MessageDigest md = null;
            md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(password);
            byte[] raw = md.digest();
            return (new BASE64Encoder()).encode(raw);
        } catch (NoSuchAlgorithmException ex) { return null; }
    }
    
    private boolean checkPassword(byte[] attemptedPassword)
    {
        String hash = encryptPassword(attemptedPassword);
        return storedPass.equals(hash);
    }
    
    private void getDetails()
    {
        try
        {
            ResultSet result = conn.read("SELECT userID, type, name, email, password "
                    + "FROM Users WHERE email= '" + email + "'");
            result.first();
            userID = result.getInt("userID");
            type = result.getString("type");
            name = result.getString("name");
            storedEmail = result.getString("email");
            storedPass = result.getString("password");
        } catch (SQLException ex) {}
    }
}
