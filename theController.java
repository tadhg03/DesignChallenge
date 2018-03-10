/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallengemvc;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Tadhg
 */
public class theController {
    
    private theModel model;
    private theView view;
    
    public theController(theView view, theModel model){
        this.view = view;
        this.model = model;
        view.attach(this);
    }
    
    public void writeData(ArrayList<Event> events, boolean delete){
        FileWriter w;
        
        try {
            if(delete)
                w = new FileWriter("DLSU Unicalendar.psv", false);
            else
                w = new FileWriter("DLSU Unicalendar.psv");
            
            for(int i = 0; i < events.size(); i++){
                    w.write(events.get(i).name);
                    w.write(" | ");
                    w.write(events.get(i).month+ "/" +events.get(i).day+ "/" +events.get(i).year);
                    w.write(" | ");
                    w.write(events.get(i).color);
                    w.write(" | ");
                    w.write(events.get(i).startTime);
                    w.write(" | ");
                    w.write(events.get(i).endTime);
                    w.append("\n");
                    System.out.println("wrote psv");
            }
            w.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteData(ArrayList<Event> events, String name){
        int month = 0, year = 0;
        for(int i = 0; i < events.size(); i++){
            if(events.get(i).name.equals(name)){
                month = events.get(i).month - 1;
                year = events.get(i).year;
                events.remove(i);
            }
        }
        writeData(events, true);
        view.refreshCalendar(month, year);
    }
    
    
}
