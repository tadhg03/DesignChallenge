/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Paolo & Tadhg
 */

//Template Pattern
abstract public class DCDataParser{ //took out extend here because it would error when extending and instantiating the same class
    
    protected ArrayList<Event> events = new ArrayList<>();
    CalendarProgram calendar;
    protected String name;
    
    abstract void readData(String name);
    abstract void processData(CalendarProgram c); //adding this so that we can pass it onto the arraylist of the actual calendar
    
    public void writeData(String name, ArrayList<Event> events){
        System.out.println("Output generated, writing to CSV...");
            /*
                Idk how to CSV/PSV yet but the logic is:
                - try catch statement first
                - the loop will help write the event to the .csv or .psv file so we need to identify what kind of file we should save it in.
                - we need to determine if the event is a holiday, if yes, save it to holiday.csv, if not, placec it to the default.
                - once done, flush and close.
                */   
            
//        try {
//            FileWriter w;
//            
//            for(int i = 0; i < events.size(); i++){
//                if()
//            }
//        } catch (Exception e) {
//        }
            
    }
}
