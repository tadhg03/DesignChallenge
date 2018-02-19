/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTextPane;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.*;

/**
 *
 * @author Arturo III
 */
public class TableRenderer extends JTextPane implements TableCellRenderer {
    
    private ArrayList<Event> events;
    
    public TableRenderer(ArrayList<Event> e){
    
        events = e;
    
    }
    
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
            StyledDocument sdoc = this.getStyledDocument();
            Style style = this.addStyle("temp", null);
        
            // removed super.getTableCellRendererComponent(table, value, selected, focused, row, column); since it is unnecessary
            if (column == 0 || column == 6)
                    setBackground(new Color(220,220,255));
            else
                    setBackground(Color.WHITE);
            setBorder(null);
            
            // removed setForeground(Color.black); so that we can properly set colors to events added.
            
            try {
                
                sdoc.remove(0, sdoc.getLength());
                
            } catch(BadLocationException e){
                
                Logger.getLogger(TableRenderer.class.getName()).log(Level.SEVERE, null, e);
                
            }
            
            if(value != null){
            
                String sval = String.valueOf(value);
                String subs = sval.substring(sval.indexOf(" ") + 1, sval.length());
                
                try {
                
                    if(sval.indexOf(" ") == -1)
                        sdoc.insertString(sdoc.getLength(), sval, style);
                    else
                        sdoc.insertString(sdoc.getLength(), sval.substring(0, sval.indexOf(" ")), style);
                    
                    for(int i = 0; i < events.size(); i++){
                    
                        if(subs.contains(events.get(i).name)){
                        
                            switch((events.get(i).color.toLowerCase().replaceAll("\\s", ""))){
                            
                                case "red":
                                           StyleConstants.setForeground(style, Color.RED);
                                           //setForeground(Color.RED);
                                           break;
                                case "blue":
                                           StyleConstants.setForeground(style, Color.BLUE);
                                           //setForeground(Color.BLUE);
                                           break;
                                case "green":
                                           StyleConstants.setForeground(style, Color.GREEN);
                                           //setForeground(Color.GREEN);
                                           break;
                                default:
                                        StyleConstants.setForeground(style, Color.BLACK);
                                        break;
                            
                            }
                            sdoc.insertString(sdoc.getLength(), " " + events.get(i).name, style);
                        }
                    
                    }
                
                } catch(BadLocationException e){
                
                Logger.getLogger(TableRenderer.class.getName()).log(Level.SEVERE, null, e);
                
                }
            
            }
            
            //if user clicks on a panel
            if(selected){
                setBackground(Color.LIGHT_GRAY);
            }
            
            return this;  
    }
}
