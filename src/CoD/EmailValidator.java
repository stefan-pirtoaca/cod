/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoD;

import javax.swing.JDialog;
import java.util.regex.Pattern;

/**
 *
 * @author Stefan
 */
public class EmailValidator extends AbstractValidator
{
    private static final String[] messages =
    {
        "This field cannot be empty.",
        "Please input a valid email address."
    };
    private final String emailRegEx = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"; 
    
    /**
     *
     * @param parent 
     * @param c JComponent object, target of EmailValidator
     */
    public EmailValidator(JDialog parent, javax.swing.JComponent c)
    {
        super(parent, c, "");
    }
    
    /**
     *
     * @param parent 
     * @param c JComponent object, target of EmailValidator
     */
    public EmailValidator(Object parent, javax.swing.JComponent c)
    {
        super(parent, c, "");
    }
    
    @Override
    protected boolean validationCriteria(javax.swing.JComponent c)
    {
        String  email = ((javax.swing.JTextField)c).getText();
		
        if (email.equals(""))
        {
            setMessage(messages[0]);
            return false;
        }
        
        Pattern emailPattern = Pattern.compile(emailRegEx,
                Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher emailMatcher = emailPattern.matcher(email);
        if(!emailMatcher.find())
        {
            setMessage(messages[1]);
            return false;
        } else return true;
    }
}
