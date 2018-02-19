/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;
import sms.SMSView;
import sms.SMS;
import java.util.*;
import java.awt.Color;
/**
 *
 * @author Paolo & Tadhg
 */
public class SMSViewAdap extends Observer{
    
    private SMSView smsv;
    private ArrayList<Event> events;
    private Calendar calendar;
    
    public SMSViewAdap(SMSView vsms, CalendarProgram cp){
    
        super(cp);
        events = new ArrayList<>();
        smsv = vsms;
    
    }
    
    
    @Override
    void update(Event e){
    
        //if the current ArrayList doesn't have Event e, it adds.
        if(!events.contains(e))
            events.add(e);
        
        calendar = Calendar.getInstance();
        calendar.set(e.year, e.month - 1, e.day);
        
        switch((e.color.toLowerCase()).replaceAll("\\s", "")){
        
            case "red":
                      smsv.sendSMS(new SMS(e.name, calendar, Color.RED));
                       break;
            case "blue":
                      smsv.sendSMS(new SMS(e.name, calendar, Color.BLUE));
                       break;
            case "green":
                      smsv.sendSMS(new SMS(e.name, calendar, Color.GREEN));
                       break;
            default:
                    smsv.sendSMS(new SMS(e.name, calendar, Color.BLACK));
                    break;
        
        }
        
    }
    
}
