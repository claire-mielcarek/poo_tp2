/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author clair
 */
public class Food implements Runnable {

    boolean isFresh;
    private final Position p;
    private final Object lock = new Object();

    /**
     * TODO : Lance un thread pour gérer le pourrissement
     */
    public Food(Position p) {
        isFresh = true;
        this.p = p;

    }

    /**
     * ¨Déclenche le pourrissement de la nourriture TODO : doit faire
     * disparaitre la nourriture au bout d'un moment
     */
    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(Food.class.getName()).log(Level.SEVERE, null, ex);
        }
        //rot();
    }

    synchronized private void rot() {
        isFresh = false;
        notifyAll();
    }

    synchronized public boolean isFresh() {
        return isFresh;
    }

    public Position getPosition() {
        return p;
    }

    @Override
    public synchronized String toString() {
        String ret;
        if (isFresh) {
            ret = "F";
        } else {
            ret = "R"; //rot
        }
        return ret;
    }

}
