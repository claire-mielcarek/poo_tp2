/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import poo_tp2.controller.Controller;

/**
 *
 * @author clair
 */
public class Pigeon implements Runnable {

    Position position;
    Park park;
    int maxTimeSleeping = 5;
    int sleepCounter = 0;
    Controller controller;

    public Pigeon(Position position, Park park) {
        this.position = position;
        this.park = park;
        this.controller = park.controller;
    }

    /**
     * Search the nearest food and return the next position to go to attain it
     *
     * @return next position to go (null if there is no food in the park)
     */
    Position findNextPosition() {
        Position nextPosition = null;
        Position target = park.findNearestFood(position);
        if (target != null) {
            nextPosition = position.getStep(target);
        }
        return nextPosition;
    }

    /**
     * The pigeon moves randomly somewhere else in the park TODO gérer la
     * concurrence avec le changement de position de goTo
     */
    synchronized void beAfraid() {
        int x = (int) (Math.random() * park.mapSize);
        int y = (int) (Math.random() * park.mapSize);
        Cell c = park.getCell(new Position(x, y));
        while (c.getPigeon() != null) {
            x = (int) (Math.random() * park.mapSize);
            y = (int) (Math.random() * park.mapSize);
        }

        Position pos = new Position(x, y);
        c = park.getCell(pos);
        synchronized (c) {
            Pigeon pigeon = new Pigeon(pos, this.park);
            c.setPigeon(pigeon);
        }
        System.out.println("I've got afraid");

    }

    /**
     * Move to a cell and eat the food on if there is
     *
     * @param cell
     */
    void goTo(Position p) {
        Cell c = park.getCell(p);
        synchronized (c) {
            if (park.canGo(this, p)) {
                //System.out.println("Pigeon goes to " + p);
                park.pigeonIsComing(this, p);
                this.position = p;
                park.controller.notifyPigeonMoved();
            }
        }
    }

    /**
     * Function called when there is no food to eat
     */
    void sleep(boolean incrementCounter) {
        if (incrementCounter) {
            sleepCounter++;
            System.out.println("The pigeon do nothing");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Pigeon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Main function : implements the behavior of the pigeon
     */
    void act() {
        Position p;
        System.out.println(park);
        while (true) {
            while (sleepCounter < maxTimeSleeping) {
                p = findNextPosition();
                System.out.println(park);
                System.out.println("nex pos : " + p);
                if (p != null) {
                    goTo(p);
                    sleep(false);
                } else {
                    sleep(true);
                }
            }
            System.out.println("Je dors");
            synchronized (park) {
                try {
                    park.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Pigeon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            sleepCounter = 0; 
        }

    }

    @Override
    public void run() {
        act();
    }

    public Position getPosition() {
        return position;
    }

}
