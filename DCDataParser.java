/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

/**
 *
 * @author Paolo & Tadhg
 */

//Template Pattern
abstract public class DCDataParser extends CalendarProgram{
    
    public void parseDataAndGenerateOutput(){
    
        readData();
        processData();
        writeData();
        
    }
    
    abstract void readData();
    abstract void processData();
    
    public void writeData(){
        System.out.println("Output generated, writing to CSV...");
        
    }
    
}
