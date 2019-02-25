/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import poo_tp2.controller.Controller;
import poo_tp2.model.Food;
import poo_tp2.model.Pigeon;

/**
 *
 * @author tiff9
 */
public class GameView  extends JFrame implements Runnable{
    
    public Controller controller;
    public Container con;
    public ImageIcon iconPigeon = new ImageIcon(new ImageIcon("src/pigeon_icon.png").getImage().getScaledInstance(52, 80, Image.SCALE_DEFAULT));
    public ImageIcon iconBackground = new ImageIcon(new ImageIcon("src/background.jpg").getImage().getScaledInstance(1000, 800, Image.SCALE_DEFAULT));
    public ImageIcon iconFood = new ImageIcon(new ImageIcon("src/food_icon.png").getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT));
    public JPanel cells[][];
    public ArrayList<Pigeon> pigeons;
    int sleepCounter = 0;
    int maxTimeSleeping = 5;
    int previousPigeonsPosition[][];
    
    
    public GameView(int rows, int columns, ArrayList<Pigeon> pigeons, ArrayList<Food> availableFood, Controller c) {
        this.controller = c;
        this.pigeons = pigeons;
        setSize(1000, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setIconImage((new ImageIcon("src/app_icon.png")).getImage());
        setTitle("Feeding Pigeons Minigame");
        
        this.con = getContentPane();
        
        /*JPanel background = new JPanel(){
             public void paintComponent(Graphics g){
              g.drawImage(GameView.this.iconBackground.getImage(), 0, 0, null);
              super.paintComponent(g);
            }
        };
        background.setBackground(new Color(0,0,0,0));*/
        //background.setBounds(0, 0, 1000, 800);
        
        /*JMenuBar mbMenu = new JMenuBar();
        JMenu mHelp = new JMenu("?");
        JMenuItem miAbout = new JMenuItem("About the game");
        mHelp.add(miAbout);
        mbMenu.add(mHelp);
        setJMenuBar(mbMenu);*/
        
        //park (gridLayout)
        JPanel park = new JPanel(){
             public void paintComponent(Graphics g){
              g.drawImage(GameView.this.iconBackground.getImage(), 0, 0, null);
              super.paintComponent(g);
            }
        };
        park.setBackground(new Color(0,0,0,0));
        park.setLayout(new GridLayout (rows,columns,-1, -1));
        //park.setBounds(0, 0, 800, 800);
        park.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        
        Border blackline = BorderFactory.createLineBorder(Color.black,1);
        
        
        //ArrayList<JPanel> cells = new ArrayList();
        cells = new JPanel[rows][columns];
        for(int i = 0; i<rows; i++)
        {
            for (int j = 0; j<columns; j++)
            {
                cells[i][j] = new JPanel();
                cells[i][j].setBackground(new Color(0,0,0,0));
                
                //lien controller-view
                cells[i][j].addMouseListener(this.controller);
                cells[i][j].setBorder(blackline);
                JLabel labelX = new JLabel(""+j);
                JLabel labelY = new JLabel(""+i);
                labelX.setVisible(false);
                labelY.setVisible(false);
                cells[i][j].add(labelX);
                cells[i][j].add(labelY);
                park.add(cells[i][j]);
            }
            

        }
        //park.setBorder(blackline);
        previousPigeonsPosition = new int[pigeons.size()][2];
        for (int i = 0; i < pigeons.size(); i++)
        {
            int x = pigeons.get(i).getPosition().getX();
            int y = pigeons.get(i).getPosition().getY();
            JLabel labelPigeon = new JLabel(iconPigeon);
            cells[x][y].add(labelPigeon);
            previousPigeonsPosition[i][0]=x;
            previousPigeonsPosition[i][1]=y;
        }
        
        for (int i = 0; i < availableFood.size(); i++)
        {
            int x = availableFood.get(i).getPosition().getX();
            int y = availableFood.get(i).getPosition().getY();
            JLabel labelFood = new JLabel(iconFood);
            cells[x][y].add(labelFood);
        }
        
        con.add(park);
        //background.add(park);
        //con.add(background);
        con.setVisible(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    

    /**
     *  Ajoute image de nourriture où l'utilisateur a cliqué
     * @param cell 
     */
    public void createFoodInCell(JPanel cell){
        JLabel labelFood = new JLabel(iconFood);
        //labelFood.setHorizontalAlignment(JLabel.CENTER);
        //labelFood.setVerticalAlignment(JLabel.CENTER);
        cell.add(labelFood);
        cell.revalidate();
        this.con.repaint();
    }
    
    public void refreshPigeonsPosition(ArrayList<Pigeon> pigeons){
        while (true)
        {
            for (int i = 0; i < pigeons.size(); i++)
            {
                int x = pigeons.get(i).getPosition().getX();
                int y = pigeons.get(i).getPosition().getY();
                JLabel labelPigeon = new JLabel(iconPigeon);
                //supprimer l'img du pigeon sur la case précédente
                int pX = previousPigeonsPosition[i][0];
                int pY = previousPigeonsPosition[i][1];
                if (cells[pX][pY].getComponentCount()>=3)
                {
                    cells[pX][pY].remove(2);
                    cells[pX][pY].revalidate();
                    this.con.repaint();
                }
                
                //supprimer food
                if (cells[x][y].getComponentCount()>=3)
                {
                    cells[x][y].remove(2);
                }
                cells[x][y].add(labelPigeon);
                cells[x][y].revalidate();
                this.con.repaint();
                previousPigeonsPosition[i][0]=x;
                previousPigeonsPosition[i][1]=y;
            }
            sleep(true);
        }
    }

    public void sleep(boolean incrementCounter){
        if (incrementCounter) {
            sleepCounter++;
            System.out.println("game sleep");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    @Override
    public void run() {
        refreshPigeonsPosition(pigeons);
        this.con.repaint();
    }
}
