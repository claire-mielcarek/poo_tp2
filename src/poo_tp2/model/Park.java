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
    int mapSize;

    public Park(int mapSize, int nbPigeons) {
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

        //TODO faire l'ajout des pigeons (pour l'instant ajout de 3 tjs au meme endroit
        Food f = map.get(2).get(4).setFood();
        foodAvailable.add(f);
        f = map.get(0).get(4).setFood();
        foodAvailable.add(f);
        f = map.get(4).get(0).setFood();
        foodAvailable.add(f);
    }

    /**
     * Add food in the park
     *
     * @param p position where to add
     * @return if the operation is really executed
     */
    boolean setFood(Position p) {
        this.map.get(p.getX()).get(p.getY()).setFood();
        return true;
    }

    /**
     * Return the position of the nearest fresh food
     *
     * @param pigeonPosition
     * @return a Position or null if there is no food
     */
    Position findNearestFood(Position pigeonPosition) {
        Position ret = null;
        Position p;
        int distanceMin = mapSize * mapSize; //Init to the max distance between two points of the map  
        for (Food f : this.foodAvailable) {
            p = f.getPosition();
            int distance = p.distanceTo(pigeonPosition);
            if (distance < distanceMin) {
                distanceMin = distance;
                ret = p;
            }
        }
        return ret;
    }

    /**
     * @param p
     * @return the cell in p position
     */
    Cell getCell(Position p) {
        if (this.map.size() > p.getX()) {
            if (this.map.get(p.getX()).size() > p.getY()) {
                return this.map.get(p.getX()).get(p.getY());
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < this.mapSize; i++) {
            for (int j = 0; j < this.mapSize; j++) {
                Object content = this.map.get(i).get(j).getContent();
                if (content instanceof Food) {
                    ret+="F ";
                }
                else if (content instanceof Pigeon){
                    ret+="P ";
                }
                else{
                    ret+="* ";
                }
            }
            ret+="\n";
        }
        return ret;
    }

    public static void main(String[] args) {

        Park myPark = new Park(5, 3);

        Position p = new Position(0, 2);
        System.out.println(myPark);

        System.out.println(myPark.findNearestFood(p));

    }

}
