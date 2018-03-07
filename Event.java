/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge2;

/**
 *
 * @author gabri
 */
public class Event {
    
    public final int day, month, year;
    public final String name, color, startTime, endTime;
    
    public Event(String date, String name, String color, String startTime, String endTime){
        
        String[] dates = date.split("/");

        month = Integer.parseInt(dates[0]);
        day = Integer.parseInt(dates[1]);
        year = Integer.parseInt(dates[2]);
        
        this.name = name; 
        this.color = color;
        
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public String toString(){
        String string = "day: " +day+ " month: " +month+ " year: " +year+ " name: " +name+ " color: " +color+ "start time: " +startTime+ "end time:" +endTime;
        return string;
    }
    
    
}
