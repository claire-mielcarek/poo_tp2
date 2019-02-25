/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import poo_tp2.controller.Controller;
import poo_tp2.model.Food;
import poo_tp2.model.Pigeon;

/**
 *
 * @author clair
 */
public class View {
  public GameView gv;
  

  
  public View(int rows, int columns, ArrayList<Pigeon> pigeons, ArrayList<Food> availableFood, Controller c){
      this.gv = new GameView(rows, columns, pigeons, availableFood, c);
  }
  

}
