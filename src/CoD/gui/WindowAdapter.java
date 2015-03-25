/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoD.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Stefan
 */
public class WindowAdapter implements WindowListener
{
    private final CoD.User user;
    
    public WindowAdapter(CoD.User u)
    {
        user = u;
    }

    @Override
    public void windowOpened(WindowEvent we) {}

    @Override
    public void windowClosing(WindowEvent we)
    {
        java.awt.Window source = we.getWindow();
        int logoutOpt = JOptionPane.showConfirmDialog(
                source,
                "Are you sure you want to log out?",
                "Log out confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (logoutOpt == 1)
        {
            user.logout();
            
            
            
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent we) {}

    @Override
    public void windowIconified(WindowEvent we) {}

    @Override
    public void windowDeiconified(WindowEvent we) {}

    @Override
    public void windowActivated(WindowEvent we) {}

    @Override
    public void windowDeactivated(WindowEvent we) {}
    
}
