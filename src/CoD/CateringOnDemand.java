/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoD;
import CoD.gui.NoLoginMenu;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;


/**
 *
 * @author Stefan
 */
public class CateringOnDemand
{

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args)
    {
        DBConnect conn = new DBConnect();
        new NoLoginMenu(conn).main(new String[0]);
        MessageDigest md = null;
        try
        {
            md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update("password1".getBytes("UTF-8"));
            byte[] raw = md.digest();
            System.out.println(new String((new BASE64Encoder()).encode(raw)));
        } catch(NoSuchAlgorithmException | UnsupportedEncodingException ex) {}
    }
    
}
