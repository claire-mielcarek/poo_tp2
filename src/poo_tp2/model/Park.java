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
     * @return if the operation is really executed
     */
    boolean setFood(Position p){
        this.map.get(p.getX()).get(p.getY()).setFood();
        return true;
    }
    
    /**
     * Return the position of the nearest fresh food 
     * @param pigeonPosition
     * @return a Position or null if there is no food
     */
    Position findNearestFood(Position pigeonPosition){
        //TODO 1: findNearestFood, regarder cases autour du pigeon en spirale
        //moves: left = (-1,0), up = (0,1), right = (1,0), down = (0,-1)
        int [] move_x = {-1,0,1,0};
        int [] move_y = {0,1,0,-1};
        int stepToDo=1;
        Position currentPosition = new Position(pigeonPosition);
        int x = pigeonPosition.getX();
        int y = pigeonPosition.getY();
        int dir = 0;
        int max = this.map.size() * this.map.get(1).size();
        int k = 0;
        while (k < max) {
            
            for (int i = 0; i < 2; i++)
            {
                for (int j = 0; j < stepToDo; j++)
                {
                    currentPosition.setX(currentPosition.getX()+move_x[dir]);
                    currentPosition.setY(currentPosition.getY()+move_y[dir]);
                    k++;
                    System.out.println("P: (" + currentPosition.getX() + ", " + currentPosition.getY() + ")");
                    if (getCell(currentPosition)!=null && getCell(currentPosition).food != null)
                    {
                        System.out.println("Food here: (" + currentPosition.getX() + ", " + currentPosition.getY() + ")");
                        return currentPosition;
                    }
                    if (getCell(currentPosition)==null)
                        k--;
                }
                dir = (dir + 1) % 4;

            }
            stepToDo++;
        }
        return null;
    }
    

    
    /**
     * @param p
     * @return the cell in p position
     */
    Cell getCell(Position p){
        if (this.map.size()> p.getX())
        {
            if(this.map.get(p.getX()).size()> p.getY())
                return this.map.get(p.getX()).get(p.getY());
        }
        return null;
    }
    
        public static void main(String[] args) {
        // TODO 1.1: test findNearestFood
        
        Park myPark = new Park();
        ArrayList<ArrayList<Cell>> myMap = new ArrayList<>();
        ArrayList<Food> foodAvailable = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) //génération map
        {
            myMap.add(new ArrayList<>());
            for (int j = 0; j < 5; j++)
            {
                myMap.get(i).add(new Cell());
            }
        }

        myMap.get(2).get(4).setFood();
        myMap.get(0).get(4).setFood();
        myMap.get(4).get(0).setFood();
        myPark.map = myMap;
        myPark.foodAvailable = foodAvailable;
        
        Position p = new Position(2, 2);
        myPark.findNearestFood(p);
        
    }
    
}
