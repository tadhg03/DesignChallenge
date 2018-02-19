//my g

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

abstract class Observer {
    
    //used to pass through CalendarProgram
    protected CalendarProgram cp;
    abstract void update(Event events);
    
    public Observer(CalendarProgram cp){
    
        this.cp = cp;
           
    }
    
}
