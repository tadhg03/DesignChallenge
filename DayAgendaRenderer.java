/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallengemvc;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author gabri
 */
public class DayAgendaRenderer extends DefaultTableCellRenderer{
    private ArrayList<Event> events;
    
    private int TimeToRowNumber(String eTime){
        
        switch(eTime){
            
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
    
    public DayAgendaRenderer(ArrayList<Event> e){
        events = e;
    }
    
    @Override
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){

        Component comp = super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        
        for(int i = 0; i < events.size(); i++){
            
            if("Blue".equals(events.get(i).color)){
                if(row == TimeToRowNumber(events.get(i).startTime) && column == 1)
                comp.setBackground(Color.BLUE);
            }
            
            if("Green".equals(events.get(i).color)){
                if(row == TimeToRowNumber(events.get(i).startTime) && column == 1)
                    comp.setBackground(Color.GREEN);
            }
        }
        
        return comp;
    }
}
