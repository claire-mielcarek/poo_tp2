/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.controller;

import java.util.ArrayList;
import poo_tp2.model.Park;
import poo_tp2.model.Pigeon;
import poo_tp2.model.Position;

/**
 *
 * @author clair
 */
public class Controller {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int nbPigeons = Integer.parseInt(args[0]);
        int mapSize = Integer.parseInt(args[1]);
        Park myPark = new Park(mapSize);

        ArrayList<Pigeon> pigeons = new ArrayList<>();

        Position p = new Position(0, 2);
        //System.out.println(myPark);

        myPark.addFood(p);

        myPark.addFood(new Position(4, 4));
        //System.out.println(myPark);

        for (int i = 0; i < nbPigeons; i++) {
            addPigeon(myPark, pigeons);
        }
    }

    private static void addPigeon(Park myPark, ArrayList<Pigeon> pigeons) {
        Pigeon p = myPark.addPigeon();
        pigeons.add(p);
        (new Thread(p)).start();
    }

}
