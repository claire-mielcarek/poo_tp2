/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import poo_tp2.model.*;
import poo_tp2.Position;
import poo_tp2.view.View;

/**
 *
 * @author clair
 */
public class GameController implements MouseListener {

    private View v;
    private Park myPark;
    private ArrayList<Pigeon> pigeons;
    private ArrayList<Position> pigeonPositions;

    public GameController(Park park, int nbPigeons, int mapSize) {
        this.myPark = park;
        this.pigeons = new ArrayList<>();
        this.pigeonPositions = new ArrayList<>();

        for (int i = 0; i < nbPigeons; i++) {
            Pigeon pg = myPark.addPigeon(i);
            this.pigeons.add(pg);
            this.pigeonPositions.add(pg.getPosition());
        }
    }

    public View getView() {
        return this.v;
    }

    public Park getPark() {
        return this.myPark;
    }

    public ArrayList<Pigeon> getPigeons() {
        return this.pigeons;
    }

    public void setView(View v) {
        this.v = v;
    }

    public void setPark(Park park) {
        this.myPark = park;
    }

    public void setPigeons(ArrayList<Pigeon> pigeons) {
        this.pigeons = pigeons;
    }

    public ArrayList<Position> getPigeonPositions() {
        return this.pigeonPositions;
    }

    public void setPigeonPositions(ArrayList<Position> positions) {
        this.pigeonPositions = positions;
    }

    /**
     *
     * @param myPark
     */
    public void scareEntities(Park myPark) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    int time = (int) (5000 + Math.random() * 10000);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(StartController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    myPark.isScary = true;
                    //changing background color
                    getView().gv.changeBackground(myPark.isScary);
                    System.out.println("The park gets scary.");
                    synchronized (myPark) {
                        System.out.println("The pigeons are awake.");
                        myPark.notifyAll();
                    }
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    myPark.isScary = false;
                    //changing background color
                    getView().gv.changeBackground(myPark.isScary);
                }
            }
        };
        thread.start();
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
                    try {
                        myPark.addFood(p);
                        v.gv.createFoodInCell(cell);
                        synchronized (myPark) {
                            System.out.println("Je réveille les pigeons");
                            myPark.notifyAll();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.WARNING, null, ex);
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
     * Used by the pigeons to tell that they moved Get the needed data to
     * refresh the view and refresh it
     *
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
        v.gv.panelPark.repaint();
    }

}
