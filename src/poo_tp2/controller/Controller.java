/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import poo_tp2.model.*;
import poo_tp2.Position;
import poo_tp2.view.View;

/**
 *
 * @author clair
 */
public class Controller implements MouseListener {

    View v;
    Park myPark;
    ArrayList<Pigeon> pigeons;
    ArrayList<Position> pigeonPositions;

    public Controller(Park park, int nbPigeons, int mapSize) {
        this.myPark = park;
        this.pigeons = new ArrayList<>();
        this.pigeonPositions = new ArrayList<>();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int nbPigeons = Integer.parseInt(args[0]);
        int mapSize = Integer.parseInt(args[1]);
        Park myPark = new Park(mapSize);

        Controller c = new Controller(myPark, nbPigeons, mapSize);

        myPark.setController(c);

        for (int i = 0; i < nbPigeons; i++) {
            //addPigeon(myPark, pigeons);
            Pigeon pg = myPark.addPigeon(i);
            c.pigeons.add(pg);
            c.pigeonPositions.add(pg.getPosition());
        }
        //création de la vue
        c.v = new View(mapSize, mapSize, c.pigeonPositions, c);
        //c.v.gv.controller = c;
        for (int i = 0; i < nbPigeons; i++) {
            Thread threadPigeon = new Thread(c.pigeons.get(i));
            threadPigeon.start();
        }

        //Child child = new Child(c.pigeons);
        //(new Thread(child)).start();
    }

    /**
     * Action to do when the user clicks
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        synchronized (myPark.getFoodAvailable()) {
            if (e.getSource() instanceof JPanel) {
                JPanel cell = (JPanel) e.getSource();
                if (cell.getComponentCount() == 2) {
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

    /**
     * Used by the pigeons to tell that they moved
     * Get the needed data to refresh the view and refresh it
     * @param pigeonNumber
     * @param newPosition 
     */
    public void notifyPigeonMoved(int pigeonNumber, Position newPosition) {
        pigeonPositions.set(pigeonNumber, newPosition);
        v.gv.refreshEntitiesPosition(pigeonPositions);
        ArrayList<Position> targets = new ArrayList<>();
        ArrayList<Boolean> targetValidity = new ArrayList<>();
        synchronized (myPark.getFoodAvailable()) {
            for (Food f : myPark.getFoodAvailable()) {
                targets.add(f.getPosition());
                targetValidity.add(f.isFresh());
            }
        }
        v.gv.refreshTargetImage(targets, targetValidity);
        v.gv.con.repaint();
    }

}
