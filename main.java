/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallengemvc;

import View.theView;
import Model.theModel;
import Controller.theController;

/**
 *
 * @author Tadhg
 */
public class main {
    
    public static void main(String[] args){
    
        theView view = new theView(); //no params because what can you put inside it

        theModel model = new theModel(view); //has view so that it can get its components and setState/getState

        theController controller = new theController(view, model);
    
    }
    
    public main(){
        
    }
    
}
