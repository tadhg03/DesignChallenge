//my g

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
    protected CalendarProgram calendar;
    protected String name;
    
    abstract void readData(String name);
    public void processData(CalendarProgram c){
        System.out.println("Looping through loaded CSV file...");
        for(int i = 0; i < events.size(); i++){
            calendar.events.add(events.get(i));
        }
    }
    
    abstract void writeData(ArrayList<Event> events);
}
