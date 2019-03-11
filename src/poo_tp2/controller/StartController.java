/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import poo_tp2.model.Park;
import poo_tp2.view.StartView;
import poo_tp2.view.View;

/**
 * Controller of the start window
 * @author Claire and Tiffany
 */
public class StartController implements ActionListener {

    public StartView sv;

    /**
     * Maint constructor that launches the view of the start window
     */
    public StartController() {
        this.sv = new StartView(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //implements the actions if the play button is pushed
        if (e.getSource() == this.sv.buttonPlay) {
            try {
                //creating map and generating pigeons
                int mapSize = this.sv.getMapSize();
                int entitiesNumber = this.sv.getEntitiesNumber();
                Park myPark = new Park(mapSize);
                GameController c = new GameController(myPark, entitiesNumber, mapSize);
                this.sv.closeStartView();
                
                myPark.setController(c);
                
                //creating GameView
                c.setView(new View(mapSize, mapSize, c.getPigeonPositions(), c));
                for (int i = 0; i < entitiesNumber; i++) {
                    Thread threadPigeon = new Thread(c.getPigeons().get(i));
                    threadPigeon.start();
                }
                
                //start randomly scaring pigeons
                c.scareEntities(myPark);
            } catch (Exception ex) {
                Logger.getLogger(StartController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
