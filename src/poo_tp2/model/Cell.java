/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.model;

/**
 *
 * @author clair
 */
public class Cell {
    boolean isLocked;
    Object content; // A pigeon or a food
    //TODO: un objet pigeon et un objet food?
    Pigeon pigeon;
    Food food;
    
    Cell(){
        isLocked = false;
        pigeon = null;
        food = null;
        content = null;
    }
    
    boolean lock(){
        if (isLocked)
            return false;
        else
        {
            isLocked = true;
            return true;
        }
    }
    
    boolean unlock(){
        isLocked = false;
        return true;
    }
    
    void removeFood(){
        this.food = null;
    }
    
    void pigeonIsComing(Pigeon p){
        
    }
    
    void setFood(){
        Food newFood = new Food();
        content = new Object() {
            @Override
            public String toString(){
                return "food";
            }
            
        };
        this.food = newFood;
    }
    
}
