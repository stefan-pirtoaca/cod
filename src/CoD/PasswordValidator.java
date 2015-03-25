/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoD;

import java.util.regex.Pattern;

/**
 *
 * @author Stefan
 */
public class PasswordValidator extends AbstractValidator
{
    private static final String[] messages =
    {
        "This field cannot be empty.",
        "Password must contain at least one letter and one digit.",
        "Password must be between 6-64 characters long.",
        "Passwords do not match."
    };
    private final String passwordRegEx = "^.*(?=.{6,10})(?=.*[a-zA-Z])(?=.*\\d)"
            + "[a-zA-Z0-9!@#$%]+$";
    private final Pattern passwordPattern;
    private final javax.swing.JPasswordField passField;
    
    /**
     *
     * @param parent
     * @param passField JPasswordField
     * @param repeatPass JPasswordField, for use only for the repeated password
     * field, use same object as passField otherwise
     */
    public PasswordValidator(javax.swing.JDialog parent,
                               javax.swing.JPasswordField passField,
                               javax.swing.JPasswordField repeatPass)
    {
        super(parent, repeatPass, "");
        this.passField = passField;
        passwordPattern = Pattern.compile(passwordRegEx);
    }
    
    /**
     *
     * @param parent 
     * @param passField JPasswordField
     * @param repeatPass JPasswordField, for use only for the repeated password
     * field, use same object as passField otherwise
     */
    public PasswordValidator(Object parent,
                               javax.swing.JPasswordField passField,
                               javax.swing.JPasswordField repeatPass)
    {
        super(parent, repeatPass, "");
        this.passField = passField;
        passwordPattern = Pattern.compile(passwordRegEx);
    }
    
    
    @Override
    protected boolean validationCriteria(javax.swing.JComponent c)
    {
        char[] pass = ((javax.swing.JPasswordField)c).getPassword();
        String name = ((javax.swing.JPasswordField)c).getName();
        
        if (name.equals("repeatPass"))
        {
            if (!java.util.Arrays.equals(pass, passField.getPassword()))
            {
                setMessage(messages[3]);
                return false;
            } else return true;
        }
		
        if (pass.length == 0)
        {
            setMessage(messages[0]);
            return false;
        }
        
        if (pass.length < 6 || pass.length > 64)
        {
            setMessage(messages[2]);
            return false;
        }
        
        java.util.regex.Matcher passwordMatcher = passwordPattern.matcher(new String(pass));
        if (!passwordMatcher.find())
        {
            setMessage(messages[1]);
            return false;
        } else return true;
    }
}
