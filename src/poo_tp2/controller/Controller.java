/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import poo_tp2.model.Park;
import poo_tp2.model.Pigeon;
import poo_tp2.model.Position;
import poo_tp2.view.View;

/**
 *
 * @author clair
 */
public class Controller implements MouseListener {

    View v;
    Park myPark;
    ArrayList<Pigeon> pigeons;

    public Controller(Park park, int nbPigeons, int mapSize) {
        this.myPark = park;
        this.pigeons = new ArrayList<>();

        myPark.setController(this);

        for (int i = 0; i < nbPigeons; i++) {
            //addPigeon(myPark, pigeons);
            Pigeon pg = myPark.addPigeon();
            pigeons.add(pg);
        }
        //création de la vue
        v = new View(mapSize, mapSize, pigeons, myPark.getFoodAvailable(), this);
        //c.v.gv.controller = c;
        for (int i = 0; i < nbPigeons; i++) {
            Thread threadPigeon = new Thread(pigeons.get(i));
            threadPigeon.start();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int nbPigeons = Integer.parseInt(args[0]);
        int mapSize = Integer.parseInt(args[1]);
        Park myPark = new Park(mapSize);

        Controller c = new Controller(myPark, nbPigeons, mapSize);

    }

    /**
     * Implémente action quand l'utilisateur clique sur une cellule du parc
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        synchronized (myPark.getFoodAvailable()) {
            if (e.getSource() instanceof JPanel) {
                JPanel cell = (JPanel) e.getSource();
                JLabel labelX = (JLabel) cell.getComponent(1);
                JLabel labelY = (JLabel) cell.getComponent(0);
                int x = Integer.parseInt(labelX.getText());
                int y = Integer.parseInt(labelY.getText());
                Position p = new Position(x, y);
                boolean success = myPark.addFood(p);
                if (success) {
                    v.gv.createFoodInCell(cell);
                    synchronized (myPark) {
                        System.out.println("Je réveille les pigeons");
                        myPark.notifyAll();
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("mousePressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println("mouseReleased");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("mouseExited");
    }

    public void notifyPigeonMoved() {
        v.gv.refreshPigeonsPosition();
        v.gv.con.repaint();
    }

}
