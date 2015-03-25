/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoD;

import javax.swing.JDialog;

/**
 *
 * @author Stefan
 */
public class NameValidator extends AbstractValidator
{
    private static final String[] messages =
    {
        "This field cannot be empty.",
        "The input must be between 2-32 characters long."
    };

    /**
     *
     * @param parent
     * @param c JComponent object, target of EmailValidator
     */
    public NameValidator(JDialog parent, javax.swing.JComponent c)
    {
        super(parent, c, "");
    }

    @Override
    protected boolean validationCriteria(javax.swing.JComponent c)
    {
        String name = ((javax.swing.JTextField)c).getText();
        if (name.length() == 0)
        {
            setMessage(messages[0]);
            return false;
        }
        if (name.length() < 2 || name.length() > 32)
        {
            setMessage(messages[1]);
            return false;
        } else return true;
            
    }
    
}
