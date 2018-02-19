/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;
import facebook.FBView;
import java.util.*;
import java.awt.Color;

/**
 *
 * @author Paolo & Tadhg
 */
public class FBViewAdap extends Observer{
    
    private FBView fbv;
    private ArrayList<Event> events;
    
    public FBViewAdap(FBView fbv, CalendarProgram cp){
    
        super(cp);
        events = new ArrayList<>();
        this.fbv = fbv;
        
    }
    
    @Override
    public void update(Event e){
        
        //if the events ArrayList doesn't contain anything, we store e to the ArrayList.
        if(!events.contains(e))
               events.add(e);
        
        switch((e.color.toLowerCase()).replaceAll("\\s", "")){
            
            //makes the text to the desired color.
            case "red":
                      fbv.showNewEvent(e.name, e.month, e.day, Integer.parseInt(cp.cmbYear.getSelectedItem().toString()), Color.RED);
                       break;
            case "blue":
                      fbv.showNewEvent(e.name, e.month, e.day, Integer.parseInt(cp.cmbYear.getSelectedItem().toString()), Color.BLUE);
                       break;
            case "green":
                      fbv.showNewEvent(e.name, e.month, e.day, Integer.parseInt(cp.cmbYear.getSelectedItem().toString()), Color.GREEN);
                       break;
            default:
                    fbv.showNewEvent(e.name, e.month, e.day, Integer.parseInt(cp.cmbYear.getSelectedItem().toString()), Color.BLACK);
                    break;                       
        }
    }
    
}
