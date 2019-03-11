/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.model;

import poo_tp2.controller.GameController;
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
    GameController controller;
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
     * The pigeon moves randomly somewhere else in the park TODO 2: gérer la
     * concurrence avec le changement de position de goTo TODO 4: utiliser
     * Position.generateRandomPosition()?
     */
    synchronized void beAfraid() {
        Cell oldCell = park.getCell(this.getPosition());
        int x = (int) (Math.random() * park.mapSize);
        int y = (int) (Math.random() * park.mapSize);
        Cell c = park.getCell(new Position(x, y));
        while (c.getPigeon() != null) {
            x = (int) (Math.random() * park.mapSize);
            y = (int) (Math.random() * park.mapSize);
            c = park.getCell(new Position(x, y));
        }

        Position pos = new Position(x, y);
        c = park.getCell(pos);
        synchronized (c) {
            //Pigeon pigeon = new Pigeon(pos, this.park);
            //c.setPigeon(pigeon);
            this.setPosition(pos);
            oldCell.setPigeon(null);
            c.setPigeon(this);
            park.controller.notifyPigeonMoved(this.number, pos);
        }

    }

    /**
     * Move to a cell and eat the food on if there is
     */
    void goTo(Position p) throws Exception {
        Cell c = park.getCell(p);
        synchronized (c) {
            if (park.canGo(this, p)) {
                park.pigeonIsMoving(this, p);
                this.position = p;
                park.controller.notifyPigeonMoved(this.number, this.position);
            }
            else{
                throw new Exception("There is already a pigeon here");
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
            System.out.println("Pigeon" + number + "The pigeon do nothing");
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
        System.out.println("Pigeon" + number + "\n" + park);
        while (true) {
            while (sleepCounter < maxTimeSleeping) {
                if (park.isScary) {
                    this.beAfraid();
                    System.out.println(park);
                }
                p = findNextPosition();
                System.out.println(park);
                System.out.println("Pigeon" + number + "nex pos : " + p);
                if (p != null) {
                    try {
                        goTo(p);
                    } catch (Exception ex) {
                        Logger.getLogger(Pigeon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    sleep(false);
                } else {
                    sleep(true);
                }
            }
            System.out.println("Pigeon" + number + "Je dors");
            synchronized (park) {
                try {
                    park.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Pigeon.class.getName()).log(Level.WARNING, null, ex);
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

    public void setPosition(Position p) {
        this.position = p;
    }

    void aChildCome() {
        thereIsAChild = true;
    }
}
