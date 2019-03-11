/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.model;

import poo_tp2.Position;
import java.util.ArrayList;
import poo_tp2.controller.GameController;

/**
 * Implements the park
 * @author Claire and Tiffany
 */
public class Park {

    ArrayList<ArrayList<Cell>> map;
    final ArrayList<Food> foodAvailable;
    int mapSize;
    GameController controller;
    public boolean isScary =  false;

    /**
     * At the beginning, map created without food and without pigeons
     *
     * @param mapSize
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

    public void setController(GameController controller) {
        this.controller = controller;
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
                    Food f = new Food(p);
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
     * Return the position of the freshest fresh food
     *
     * @param pigeonPosition
     * @return a Position or null if there is no food
     */
    Position findFreshestFood(Position pigeonPosition) {
        Position ret = null;
        synchronized (foodAvailable) {
            System.out.println(foodAvailable);
            Food freshest = Food.getFreshestFood(foodAvailable);
            if (freshest != null) {
                ret = freshest.getPosition();
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
    public Pigeon addPigeon(int pigeonNumber) {
        int x = (int) (Math.random() * mapSize);
        int y = (int) (Math.random() * mapSize);
        Cell c = this.map.get(x).get(y);
        while (c.getPigeon() != null) {
            x = (int) (Math.random() * mapSize);
            y = (int) (Math.random() * mapSize);
        }

        Position pos = new Position(x, y);
        Pigeon pigeon = new Pigeon(pigeonNumber, pos, this);
        map.get(x).get(y).setPigeon(pigeon);
        return pigeon;
    }

    /**
     * Pigeon move to p position
     * @param pigeon the pigeon which move
     * @param p where it goes
     */
    void pigeonIsMoving(Pigeon pigeon, Position p) {

        //if pigeon is not in position p
        if (!p.equals(pigeon.getPosition())) {

            Cell oldCell = this.getCell(pigeon.getPosition());
            Cell nextCell = this.getCell(p);
            oldCell.pigeonIsLeaving();

            //remove eaten food from the list           
            Food f = nextCell.getFood();
            if (f != null) {
                synchronized (f) {
                    if (f.isFresh()) {
                        removeFood(f);
                    }
                }
            }

            //notify the Cell that a pigeon arrives
            nextCell.pigeonIsComing(pigeon);
        } 
        else { //if pigeon already in p position, it just have to eat and not to move
            Cell currentCell = this.getCell(p);
            Food f = currentCell.getFood();
            if (f != null) {
                synchronized (f) {
                    removeFood(f);
                }
            }
        }
    }

    /**
     * Tests if a pigeon can go to a position (can't if there is already another
     * pigeon there)
     * @param pigeon
     * @param position
     * @return 
     */
    boolean canGo(Pigeon pigeon, Position position) {
        boolean ret = true;
        Cell c = getCell(position);
        synchronized (c) {
            if (c.getPigeon() != null && c.getPigeon() != pigeon) {
                ret = false;
            }
        }
        return ret;
    }
}
