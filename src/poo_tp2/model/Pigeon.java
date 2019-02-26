/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.model;
import poo_tp2.controller.Controller;
import poo_tp2.Position;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    int number;
    boolean thereIsAChild = false;

    public Pigeon(int number, Position position, Park park) {
        this.position = position;
        this.park = park;
        this.controller = park.controller;
        this.number = number;
    }

    /**
     * Search the nearest food and return the next position to go to attain it
     *
     * @return next position to go (null if there is no food in the park)
     */
    Position findNextPosition() {
        Position nextPosition = null;
        Position target = park.findFreshestFood(position);
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
//            Pigeon pigeon = new Pigeon(pos, this.park);
//            c.setPigeon(pigeon);
        }
        System.out.println("I've got afraid");

    }

    /**
     * Move to a cell and eat the food on if there is
     */
    void goTo(Position p) {
        Cell c = park.getCell(p);
        synchronized (c) {
            if (park.canGo(this, p)) {
                //System.out.println("Pigeon goes to " + p);
                park.pigeonIsMoving(this, p);
                this.position = p;
                park.controller.notifyPigeonMoved(this.number, this.position);
            }
        }
    }

    /**
     * Sleep function so that the movements of the pigeon can be visible
     * Increment an inactivity counter whether the pigeon did something or not
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
                if (thereIsAChild) {
                    synchronized (this) {
                        try {
                            System.out.println("I have to wait to be afraid");
                            this.wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Pigeon.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    thereIsAChild = false;
                }
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

    void aChildCome() {
        thereIsAChild = true;
    }
}
