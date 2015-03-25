/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoD;

import java.util.regex.Pattern;
import javax.swing.JDialog;

/**
 *
 * @author Stefan
 */
public class MobileNoValidator extends AbstractValidator
{
    private static final String[] messages = 
    {
        "Mobile number has to be 11 digits long.",
        "Please input a valid mobile number."
    };
    private static final String regEx = "(\\+44(\\d){9})|(0(\\d){10})";

    /**
     *
     * @param parent 
     * @param c JComponent object, target of EmailValidator
     */
    public MobileNoValidator(JDialog parent, javax.swing.JComponent c)
    {
        super(parent, c, "");
    }
    
    @Override
    protected boolean validationCriteria(javax.swing.JComponent c)
    {
        String number = ((javax.swing.JTextField)c).getText();
        Pattern p = Pattern.compile(regEx);
        java.util.regex.Matcher m = p.matcher(number);
        if (number.isEmpty())
            return true;
        else
        {
            if ((number.charAt(0) == '+' && number.length() != 12) || 
                (number.charAt(0) == '0' && number.length() != 11))
            {
                setMessage(messages[0]);
                return false;
            }else if (!m.matches())
            {
                setMessage(messages[1]);
                return false;
            } else return true;
        }
    }
}
