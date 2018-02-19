/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

import facebook.FBView;
import sms.SMSView;

/**
 *
 * @author Arturo III
 */
public class DesignChallenge1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CalendarProgram cp = new CalendarProgram();
        //added for obs
        cp.attatchObserver(new SMSViewAdap(new SMSView(), cp));
        cp.attatchObserver(new FBViewAdap(new FBView(), cp));
    }
}
