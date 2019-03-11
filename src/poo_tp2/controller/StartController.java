/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import poo_tp2.Position;
import poo_tp2.model.Park;
import poo_tp2.model.Pigeon;
import poo_tp2.view.StartView;
import poo_tp2.view.View;

/**
 *
 * @author tiff9
 */
public class StartController implements ActionListener {

    public StartView sv;

    /**
     *
     */
    public StartController() {
        this.sv = new StartView(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.sv.buttonPlay) {
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
        }
    }

}
