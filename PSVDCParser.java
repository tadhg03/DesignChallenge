/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge2;

import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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
//        String filename = name + ".psv";
//        File filePH = new File(filename);
//        
//        List<String> temp = new ArrayList<String>();
//        String line = "";
//        String delimiter = " [|] ";
//        String[] seperated;
//        
//         try{
//            BufferedReader br = new BufferedReader(new FileReader(filePH));
//            
//            while((line = br.readLine()) != null){
//                seperated = line.split(delimiter);
//                temp = Arrays.asList(seperated);
//                super.events.add(new Event(temp.get(1), temp.get(0), temp.get(2), temp.get(3), temp.get(4)));
//            }
//            
//        } catch(Exception e){
//            e.printStackTrace();
//        }
        
    }

    @Override
    void writeData(ArrayList<Event> events) {
        FileWriter w;
        
        try {
            w = new FileWriter("DLSU Unicalendar.psv");
            
            for(int i = 0; i < events.size(); i++){
                    w.write(events.get(i).name);
                    w.write(" | ");
                    w.write(events.get(i).month+ "/" +events.get(i).day+ "/" +events.get(i).year);
                    w.write(" | ");
                    w.write(events.get(i).color);
                    w.append("\n");
                    System.out.println("wrote psv");
            }
            w.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
