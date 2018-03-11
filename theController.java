/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import designchallengemvc.Event;
import Model.theModel;
import View.theView;
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
    
    public int TimeToRowNumber(String eTime) {

        switch (eTime) {

            case "0:30":
                return 1;

            case "1:00":
                return 2;

            case "1:30":
                return 3;

            case "2:00":
                return 4;

            case "2:30":
                return 5;

            case "3:00":
                return 6;

            case "3:30":
                return 7;

            case "4:00":
                return 8;

            case "4:30":
                return 9;

            case "5:00":
                return 10;

            case "5:30":
                return 11;

            case "6:00":
                return 12;

            case "6:30":
                return 13;

            case "7:00":
                return 14;

            case "7:30":
                return 15;

            case "8:00":
                return 16;

            case "8:30":
                return 17;

            case "9:00":
                return 18;

            case "9:30":
                return 19;

            case "10:00":
                return 20;

            case "10:30":
                return 21;

            case "11:00":
                return 22;

            case "11:30":
                return 23;

            case "12:00":
                return 24;

            case "12:30":
                return 25;

            case "13:00":
                return 26;

            case "13:30":
                return 27;

            case "14:00":
                return 28;

            case "14:30":
                return 29;

            case "15:00":
                return 30;

            case "15:30":
                return 31;

            case "16:00":
                return 32;

            case "16:30":
                return 33;

            case "17:00":
                return 34;

            case "17:30":
                return 35;

            case "18:00":
                return 36;

            case "18:30":
                return 37;

            case "19:00":
                return 38;

            case "19:30":
                return 39;

            case "20:00":
                return 40;

            case "20:30":
                return 41;

            case "21:00":
                return 42;

            case "21:30":
                return 43;

            case "22:00":
                return 44;

            case "22:30":
                return 45;

            case "23:00":
                return 46;

            case "23:30":
                return 47;

            //if time is 0:00    
            default:
                return 0;
        }
    }
    
    public int MonthToInt(String month) {

        switch (month.toLowerCase()) {
            case "january":
                return 1;

            case "february":
                return 2;

            case "march":
                return 3;

            case "april":
                return 4;

            case "may":
                return 5;

            case "june":
                return 6;

            case "july":
                return 7;

            case "august":
                return 8;

            case "september":
                return 9;

            case "october":
                return 10;

            case "november":
                return 11;

            case "december":
                return 12;

            default:
                return 0;

        }

    }
    
}
