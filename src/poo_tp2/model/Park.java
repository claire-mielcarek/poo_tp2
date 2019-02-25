/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import poo_tp2.controller.Controller;

/**
 *
 * @author clair
 */
public class Park {

    ArrayList<ArrayList<Cell>> map;
    final ArrayList<Food> foodAvailable;
    int mapSize;
    Controller controller;

    /**
     * At the beginning, map created without food
     *
     * @param mapSize
     * @param nbPigeons
     */
    public Park(int mapSize) {
        this.foodAvailable = new ArrayList<>();
        ArrayList<Cell> row;
        this.map = new ArrayList<>();
        this.mapSize = mapSize;
        for (int i = 0; i < mapSize; i++) {
            row = new ArrayList<>();
            for (int j = 0; j < mapSize; j++) {
                row.add(new Cell(new Position(i, j)));
            }
            map.add(row);
        }
    }

    /**
     * Add food in the park
     *
     * @param p position where to add
     * @return if the operation is successful
     */
    public boolean addFood(Position p) {
        Cell c = getCell(p);
        boolean ret = false;
        synchronized (c) {
            if (c.food == null) {
                synchronized (foodAvailable) {
                    Food f = new Food(p, this.controller);
                    (new Thread(f)).start();
                    map.get(p.getX()).get(p.getY()).setFood(f);
                    foodAvailable.add(f);
                    ret = true;
                }
            }
        }
        return ret;
    }

    /**
     * Return the position of the nearest fresh food
     *
     * @param pigeonPosition
     * @return a Position or null if there is no food
     */
    Position findNearestFood(Position pigeonPosition) {
        Position ret = null;
        synchronized (foodAvailable) {
            System.out.println(foodAvailable);
            Position p;
            int distanceMin = 2 * mapSize * mapSize; //Init to the max distance between two points of the map  
            for (Food f : this.foodAvailable) {
                if (f.isFresh()) {
                    p = f.getPosition();
                    //System.out.println("Position étudiée :" + p);
                    int distance = p.distanceTo(pigeonPosition);
                    //System.out.println(distance);
                    if (distance < distanceMin) {
                        distanceMin = distance;
                        ret = p;
                    }
                }
            }
        }
        System.out.println("NearestFood : " + ret);
        return ret;
    }

    /**
     * @param p
     * @return the cell in p position
     */
    Cell getCell(Position p) {
        if (this.mapSize > p.getX()) {
            if (this.mapSize > p.getY()) {
                return this.map.get(p.getX()).get(p.getY());
            }
        }
        return null;
    }

    @Override
    synchronized public String toString() {
        String ret = "";
        Cell c;
        for (int i = 0; i < this.mapSize; i++) {
            for (int j = 0; j < this.mapSize; j++) {
                c = getCell(this.map.get(i).get(j).getPosition());
                ret += c.toString();
            }
            ret += "\n";
        }
        return ret;
    }

    /**
     * Add a pigeon at a random place in the map
     *
     * @return the pigeon added
     */
    public Pigeon addPigeon() {
        int x = (int) (Math.random() * mapSize);
        int y = (int) (Math.random() * mapSize);
        Cell c = this.map.get(x).get(y);
        while (c.getPigeon() != null) {
            x = (int) (Math.random() * mapSize);
            y = (int) (Math.random() * mapSize);
        }

        Position pos = new Position(x, y);
        Pigeon pigeon = new Pigeon(pos, this);
        map.get(x).get(y).setPigeon(pigeon);
        return pigeon;
    }

    void pigeonIsComing(Pigeon aThis, Position p) {

        if (!p.equals(aThis.getPosition())) {

            Cell oldCell = this.getCell(aThis.getPosition());
            Cell nextCell = this.getCell(p);
            oldCell.pigeonIsLeaving();

            //On enlève la nourriture de la liste            
            Food f = nextCell.getFood();
            if (f != null) {
                synchronized (f) {
                    if (f.isFresh) {
                        removeFood(f);
                    }
                }
            }

            //On notifie la cellule que le pigeon est là
            nextCell.pigeonIsComing(aThis);
        } else {
            Cell currentCell = this.getCell(p);
            Food f = currentCell.getFood();
            if (f != null) {
                synchronized (f) {
                    removeFood(f);
                }
            }
        }
    }

    boolean canGo(Pigeon pigeon, Position p) {
        boolean ret = true;
        Cell c = getCell(p);
        synchronized (c) {
            if (c.getPigeon() != null && c.getPigeon() != pigeon) {
                ret = false;
            }
        }
        return ret;
    }

    public ArrayList<Food> getFoodAvailable() {
        synchronized (foodAvailable) {
            return this.foodAvailable;
        }
    }

    private void removeFood(Food f) {
        synchronized (foodAvailable) {
            this.foodAvailable.remove(f);
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
