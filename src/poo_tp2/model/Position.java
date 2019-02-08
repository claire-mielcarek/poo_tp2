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
public class Position {
    private final int x;
    private final int y;
    
    //Constructor 1
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    //Constructor 2
    public Position (Position p){
        this.x = p.getX();
        this.y = p.getY();
    }
    
    /**
     * @return the abscissa of the position
     */
    public int getX(){ return x; };
    
    /**
     * @return the ordinate of the position
     */
    public int getY(){ return y; }
    
    /**
     * Given a goal, return the first position where to go to attain this goal
     * @param target Position of the goal
     * @return the next position to attain
     */
    Position getStep (Position target){
        return null;
    }
    
}
