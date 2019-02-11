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

    boolean isLocked;
    Object content; // A pigeon or a food
    //TODO: un objet pigeon et un objet food?
    Pigeon pigeon;
    Food food;
    Position p;

    Cell(Position p) {
        isLocked = false;
        pigeon = null;
        food = null;
        content = null;
        this.p = p;
    }

    boolean lock() {
        if (isLocked) {
            return false;
        } else {
            isLocked = true;
            return true;
        }
    }

    boolean unlock() {
        isLocked = false;
        return true;
    }

    void removeFood() {
        this.food = null;
    }

    void pigeonIsComing(Pigeon p) {

    }

    public Position getPosition() {
        return p;
    }

    public Object getContent() {
        return content;
    }

    Food setFood() {
        Food newFood = new Food(this.p);
        this.content = newFood;
        this.food = newFood;
        return newFood;
    }

}
