/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.model;

import poo_tp2.Position;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implement the food for the pigeons
 * @author Claire and Tiffany
 */
public class Food implements Runnable {

    private static final int FRESHNESS_MAX = 10;
    private static final int FRESHNESS_MIN = 0;
    private int freshness;
    private final Position p;

    /**
     * Main constructor initializing the food and its freshness
     * @param p 
     */
    public Food(Position p) {
        this.p = p;
        freshness = FRESHNESS_MAX;
    }

    public boolean isFresh() {
        return freshness > FRESHNESS_MIN;
    }

    /**
     * Starts the rotting of the food
     */
    @Override
    public void run() {
        while (freshness > FRESHNESS_MIN) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Food.class.getName()).log(Level.SEVERE, null, ex);
            }
            rot();
        }
    }

    /**
     * The food is rotting so its freshness decreases
     */
    synchronized private void rot() {
        freshness--;
    }

    public Position getPosition() {
        return p;
    }

    private int getFreshness() {
        return freshness;
    }

    @Override
    public synchronized String toString() {
        String ret;
        if (isFresh()) {
            ret = "F" + freshness;
        } else {
            ret = "R "; //rot
        }
        return ret;
    }

    /**
     * Return the freshest food in a list of Food
     *
     * @param list
     * @return the freshest food found or null if list is empty
     */
    public static Food getFreshestFood(ArrayList<Food> list) {
        Food best = null;
        int bestFreshness = Food.FRESHNESS_MIN;
        int tmpFreshness;
        try {
            for (Food f : list) {
                tmpFreshness = f.getFreshness();
                if (tmpFreshness > bestFreshness) {
                    best = f;
                    bestFreshness = tmpFreshness;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("getFreshestFood got a null argument");
        }
        return best;
    }

}
