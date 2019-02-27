/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import poo_tp2.controller.GameController;
import poo_tp2.model.Park;
import poo_tp2.model.Pigeon;
import poo_tp2.Position;
import poo_tp2.controller.StartController;
import poo_tp2.view.View;

/**
 *
 * @author clair
 */

public class Main{
    
    public static void main(String[] args) {
        StartController sc = new StartController();
    }
}
    
    /*public static void main(String[] args) {
        int nbPigeons = Integer.parseInt(args[0]);
        int mapSize = Integer.parseInt(args[1]);
        Park myPark = new Park(mapSize);

        GameController c = new GameController(myPark, nbPigeons, mapSize);
        ArrayList<Pigeon> pigeons = c.getPigeons();
        ArrayList<Position> pigeonPositions = c.getPigeonPositions();

        myPark.setController(c);

        
        
        for (int i = 0; i < nbPigeons; i++) {
            //addPigeon(myPark, pigeons);
            Pigeon pg = myPark.addPigeon(i);
            c.getPigeons().add(pg);
            pigeons.add(pg);
            c.setPigeons(pigeons);
            
            
            pigeonPositions.add(pg.getPosition());
            c.setPigeonPositions(pigeonPositions);
            
        }
        //création de la vue
        c.setView(new View(mapSize, mapSize, c.getPigeonPositions(), c));
        //c.v.gv.controller = c;
        for (int i = 0; i < nbPigeons; i++) {
            Thread threadPigeon = new Thread(c.getPigeons().get(i));
            threadPigeon.start();
        }
        
        Thread thread = new Thread(){
            @Override
            public void run(){
                while(true){
                    int time = (int) (5000 + Math.random() * 10000);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    myPark.isScary = true;
                    System.out.println("The park gets scary.");
                    synchronized (myPark) {
                        System.out.println("The pigeons are awake.");
                        myPark.notifyAll();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    myPark.isScary = false;
                }
            }
        };
        thread.start();
    }
        
        /*int nbPigeons = Integer.parseInt(args[0]);
        int mapSize = Integer.parseInt(args[1]);
        Park myPark = new Park(mapSize);
        GameController c = new GameController(myPark, nbPigeons, mapSize);
        ArrayList<Pigeon> pigeons = c.getPigeons();
        
        myPark.setController(c);

        for (int i = 0; i < nbPigeons; i++) {
            //addPigeon(myPark, pigeons);
            Pigeon pg = myPark.addPigeon(5);
            pigeons.add(pg);
            c.setPigeons(pigeons);
        }
        //création de la vue
        c.setView(new View(mapSize, mapSize, new ArrayList<Position>(), c));

        for (int i = 0; i < nbPigeons; i++) {
            Thread threadPigeon = new Thread(c.getPigeons().get(i));
            threadPigeon.start();
        }
        Thread thread = new Thread(){
            @Override
            public void run(){
                while(true){
                    int time = (int) (5000 + Math.random() * 10000);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    myPark.isScary = true;
                    System.out.println("The park gets scary.");
                    synchronized (myPark) {
                        System.out.println("The pigeons are awake.");
                        myPark.notifyAll();
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    myPark.isScary = false;
                }
            }
        };
        thread.start();
                //Child child = new Child(c.pigeons);
        //(new Thread(child)).start();
    }*/

