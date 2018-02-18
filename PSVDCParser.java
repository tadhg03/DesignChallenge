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
    
    //public ArrayList<Event> events = new ArrayList<>(); we technically dont need this i think
    
    public PSVDCParser(CalendarProgram calendar){
        super.calendar = calendar;
    }
    
    @Override
    void readData(String name){
        System.out.println("Reading from PSV file...");
        String filename = name + ".psv";
        File filePH = new File(filename);
        
        List<String> temp = new ArrayList<String>();
        String line = "";
        String delimiter = " [|] ";
        String[] seperated;
        
         try{
            BufferedReader br = new BufferedReader(new FileReader(filePH));
            
            while((line = br.readLine()) != null){
                seperated = line.split(delimiter);
                temp = Arrays.asList(seperated);
                super.events.add(new Event(temp.get(1), temp.get(0), temp.get(2), false));
            }
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    @Override
    void processData(CalendarProgram calendar){
        System.out.println("Looping through loaded PSV file...");
        for(int i = 0; i < super.events.size(); i++)
            calendar.events.add(super.events.get(i));
    }
        
}
