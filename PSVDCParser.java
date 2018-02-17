/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

import java.util.*;
import java.io.*;
/**
 *
 * @author gabri
 */
public class PSVDCParser extends DCDataParser{
    
    public ArrayList<Event> events = new ArrayList<>();
    
    public PSVDCParser(CalendarProgram calendar){
        super.calendar = calendar;
    }
    
    @Override
    void readData(String name){
        System.out.println("Reading from PSV file...");
        String filename = name;
        File filePH = new File(filename);
        
         try{
            Scanner inputStream = new Scanner(filePH);
            
            while(inputStream.hasNext()){
                String data = inputStream.next();
                String [] values = data.split("|");
                events.add(new Event(values[0], values[1], values[2]));
            }
            inputStream.close();
            
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        
    }
    
    @Override
    void processData(){
        System.out.println("Looping through loaded CSV file...");
        for(int i = 0; i < events.size(); i++)
            super.calendar.events.add(events.get(i));
        }
        
}
