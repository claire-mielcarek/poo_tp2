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
public class Controller implements MouseListener{

    View v;
    Park myPark;
    

    public Controller(Park park){
        this.myPark = park;
    }
    
    
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
            //addPigeon(myPark, pigeons);
            Pigeon pg = myPark.addPigeon();
            pigeons.add(pg);
        }
        
        Controller c = new Controller(myPark);
        //création de la vue
        c.v = new View(mapSize, mapSize, pigeons,myPark.getFoodAvailable(), c);
        //c.v.gv.controller = c;
        for (int i = 0; i < nbPigeons; i++) {
            Thread threadPigeon = new Thread(pigeons.get(i));
            threadPigeon.start();
        }
        
    }

    private static void addPigeon(Park myPark, ArrayList<Pigeon> pigeons) {
        Pigeon p = myPark.addPigeon();
        pigeons.add(p);
        Thread threadPigeon = new Thread(p);
        threadPigeon.start();

    }

    /**
     * Implémente action quand l'utilisateur clique sur une cellule du parc
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JPanel)
        {
            JPanel cell = (JPanel) e.getSource();
            JLabel labelX = (JLabel)cell.getComponent(1);
            JLabel labelY = (JLabel)cell.getComponent(0);
            int x = Integer.parseInt(labelX.getText());
            int y = Integer.parseInt(labelY.getText());
            Position p = new Position(x, y);
            myPark.addFood(p);
            v.gv.createFoodInCell(cell);
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

}
