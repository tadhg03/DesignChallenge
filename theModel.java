/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallengemvc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

//MODEL DOESNT DO ANYTHING EXCEPT READ DATA AND HAVE GETTERS AND SETTERS FOR VIEW

/**
 *
 * @author Tadhg
 */
public class theModel {
    
    private theView view;
    
    public theModel(theView view){
        this.view = view;
        readData();
    }
    
    public void readData(){
        System.out.println("READING OK");
        
        ArrayList<Event> events = new ArrayList<>();
        String filename = "DLSU Unicalendar.psv";
        
        File file = new File(filename);
        
        List<String> temp = new ArrayList<String>();
        String line = "";
        String delimiter = " [|] ";
        String[] seperated;
        
        try{
            
            BufferedReader br = new BufferedReader(new FileReader(file));
            
            while((line = br.readLine()) != null){
                seperated = line.split(delimiter);
                temp = Arrays.asList(seperated);
                events.add(new Event(temp.get(1), temp.get(0), temp.get(2), temp.get(3), temp.get(4)));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        view.setEvents(events); //everytime we read, we update the state of the view
        
        
        //not sure if we are allowed to have this in here but if ever we can move it to controller
        GregorianCalendar cal = new GregorianCalendar();
        view.refreshCalendar(cal.get(GregorianCalendar.MONTH), cal.get(GregorianCalendar.YEAR));
    }
    
}
