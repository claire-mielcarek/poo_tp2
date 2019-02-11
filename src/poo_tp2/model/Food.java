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
public class Food {
    boolean isFresh;

    /**
     * Lance un thread pour gérer le pourrissement
     */
    public Food() {
        isFresh = true;
    }
    
    /**
     * ¨Déclenche le pourrissement de la nourriture
     */
    void rot(){
        isFresh = false;
    }
}
