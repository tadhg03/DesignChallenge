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
public class CSVDCParser extends DCDataParser{
    
    //public ArrayList<Event> events = new ArrayList<>(); we technically dont need this i think
    
    public CSVDCParser(CalendarProgram calendar){
        super.calendar = calendar;
    }
    
    @Override
    void readData(String name){
        System.out.println("Reading from CSV file...");
        String filename = name;
        File filePH = new File(filename + ".csv");
        
        List<String> temp = new ArrayList<String>();
        String line = "";
        String delimiter = ",";
        String[] seperated;
        
         try{
            BufferedReader br = new BufferedReader(new FileReader(filePH));
            
            while((line = br.readLine()) != null){
                seperated = line.split(delimiter);
                temp = Arrays.asList(seperated);
                super.events.add(new Event(temp.get(0), temp.get(1), temp.get(2), true));
            }
            
        } catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("csv done reading");
    }
    
    @Override
    void processData(CalendarProgram calendar){
        System.out.println("Looping through loaded CSV file...");
        for(int i = 0; i < super.events.size(); i++){
            calendar.events.add(super.events.get(i));
        }
    }
        
}
