/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Child who affray randomly pigeons
 *
 * @author Claire Mielcarek
 */
public class Child implements Runnable {

    ArrayList<Pigeon> list;

    public Child(ArrayList<Pigeon> list) {
        this.list = list;
    }

    /*public void newPositionOfPigeons(int mapSize){
        int nbPigeons = list.size();
        for (int i = 0; i < nbPigeons; i++){
            Pigeon currentPigeon = list.get(i);
            Position p = currentPigeon.getPosition();
            p.generateRandomPosition(mapSize);
            for (int j = 0; j < i; j++){
                if (p.equals(list.get(j)))
                {
                    j = 0;
                    p.generateRandomPosition(mapSize);
                }
            }
            list.get(i).setPosition(p);
        }
    }*/
    
    @Override
    public void run() {
        while (true) {
            int time = (int) (500 + Math.random() * 10000);
            try {
                Thread.sleep(time);
            } catch (InterruptedException ex) {
                Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("i like Afraid pigeons");
            for (Pigeon p : list) {
                p.aChildCome();
            }
            for (Pigeon p : list) {
                p.beAfraid();
            }
            for (Pigeon p : list){
                synchronized(p){
                    p.notifyAll();
                }
            }

        }

    }
}
