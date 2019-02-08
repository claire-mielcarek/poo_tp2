/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.model;

import java.util.ArrayList;

/**
 *
 * @author clair
 */
public class Park {
    ArrayList<ArrayList<Cell>> map;
    ArrayList<Food> foodAvailable;
    
    /**
     * Add food in the park
     * @param p position where to add
     * @return if the operation where really executed
     */
    boolean setFood(Position p){
        return true;
    }
    
    /**
     * Return the position of the nearest fresh food 
     * @param pigeonPosition
     * @return a Position or null if there is no food
     */
    Position findNearestFood(Position pigeonPosition){
        return null;
    }
    
    /**
     * @param p
     * @return the cell in p position
     */
    Cell getCell(Position p){
        return null;
    }
    
}
