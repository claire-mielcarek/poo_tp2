/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.view;

import java.util.ArrayList;
import poo_tp2.controller.GameController;
import poo_tp2.Position;

/**
 *
 * @author clair
 */
public class View {
  public GameView gv;
  public StartView sv;
  

  
  public View(int rows, int columns, ArrayList<Position> positions, GameController c){
      this.gv = new GameView(rows, columns, positions, c);
  }
  

}
