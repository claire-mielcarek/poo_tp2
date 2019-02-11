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
    private int x;
    private int y;
    
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
    
    
    public void setX(int x) { this.x = x;}
    
    public void setY(int y) { this.y = y;}
    
    
    /**
     * Given a goal, return the first position where to go to attain this goal
     * @param target Position of the goal
     * @return the next position to attain
     */
    Position getStep (Position target){
        return null;
        //TODO 2: getstep
    }

    /**
     * Distance euclidienne au carré
     * @param pigeonPosition
     * @return 
     */
    int distanceTo(Position pigeonPosition) {
        int otherX = pigeonPosition.getX();
        int otherY = pigeonPosition.getY();
        int diffX = this.x - otherX;
        int diffY = this.y - otherY;
        return diffX*diffX + diffY*diffY;
    }

    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }
    
    
}
