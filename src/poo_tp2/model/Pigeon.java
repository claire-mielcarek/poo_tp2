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
public class Pigeon {

    Position myPosition;
    Park park;

    public Pigeon(Position position, Park park) {
        this.myPosition = position;
        this.park = park;
    }

    /**
     * Search the nearest food and return the next position to go to attain it
     *
     * @return next position to go (null if there is no food in the park)
     */
    Position findNextPosition() {
        Position nextPosition = null;
        Position target = park.findNearestFood(myPosition);
        if (target != null) {
            nextPosition = myPosition.getStep(target);
        }
        return nextPosition;
    }

    /**
     * The pigeon moves randomly somewhere else in the park TODO gérer la
     * concurrence avec le changement de position de goTo
     */
    void beAfraid() {

    }

    /**
     * Move to a cell and eat the food on if there is
     *
     * @param cell
     */
    void goTo(Position p) {
        Cell cell = park.getCell(p);
        while(!cell.lock()){         
        }
        cell.pigeonIsComing(this);
        myPosition = p;
        cell.unlock();
    }

    /**
     * Function called when there is no food to eat
     */
    void sleep() {
        System.out.println("The pigeon do nothing");
    }

    /**
     * Main function : implements the behavior of the pigeon
     */
    void act() {
        Position p;
        while (true) {
            p = findNextPosition();
            if (p != null) {
                goTo(p);
            } else {
                sleep();
            }
        }

    }

}
