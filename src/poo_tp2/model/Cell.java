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
public class Cell {

    Pigeon pigeon;
    Food food;
    Position position;

    Cell(Position p) {
        pigeon = null;
        food = null;
        this.position = p;
    }

    synchronized void removeFood() {
        this.food = null;
        notifyAll();
    }

    synchronized void pigeonIsComing(Pigeon p) {
        if (this.food != null) {
            removeFood();
        }
        this.pigeon = p;
        notifyAll();
    }

    public Position getPosition() {
        return position;
    }

    synchronized void setFood(Food f) {;
        this.food = f;
        notifyAll();
    }

    synchronized public void setPigeon(Pigeon pigeon) {
        this.pigeon = pigeon;
        notifyAll();
    }

    synchronized public Food getFood() {
        return food;
    }

    synchronized public Pigeon getPigeon() {
        return pigeon;
    }

    @Override
    public synchronized String toString() {
        String ret = "";

        if (food != null) {
            ret += food.toString();
            if (pigeon != null) {
                ret += "P ";
            } else {
                ret += "  ";
            }
        } else {
            if (pigeon != null) {
                ret += "P  ";
            } else {
                ret += "*  ";
            }
        }
        return ret;
    }

    synchronized void pigeonIsLeaving() {
        pigeon = null;
        notifyAll();
    }

}
