/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import poo_tp2.controller.Controller;
import poo_tp2.Position;

/**
 *
 * @author tiff9
 */
public class GameView extends JFrame {

    public Controller controller;
    public Container con;
    public ImageIcon iconEntity = new ImageIcon(new ImageIcon("src/poo_tp2/img/pigeon_icon.png").getImage().getScaledInstance(52, 80, Image.SCALE_DEFAULT));
    public ImageIcon iconBackground = new ImageIcon(new ImageIcon("src/poo_tp2/img/background.jpg").getImage().getScaledInstance(1000, 800, Image.SCALE_DEFAULT));
    public ImageIcon iconValidTarget = new ImageIcon(new ImageIcon("src/poo_tp2/img/food_icon.png").getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT));
    public ImageIcon iconUnvalidTarget = new ImageIcon(new ImageIcon("src/poo_tp2/img/rottenfood_icon.png").getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT));
    public JPanel cells[][];
    int[][] previousEntitiesPositions;

    public GameView(int rows, int columns, ArrayList<Position> entitiesPositions, Controller c) {
        this.controller = c;
        setSize(1000, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setIconImage((new ImageIcon("src/poo_tp2/img/app_icon.png")).getImage());
        setTitle("Feeding Pigeons Minigame");

        this.con = getContentPane();

        JPanel park = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(GameView.this.iconBackground.getImage(), 0, 0, null);
                super.paintComponent(g);
            }
        };
        park.setBackground(new Color(0, 0, 0, 0));
        park.setLayout(new GridLayout(rows, columns, -1, -1));
        park.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        Border blackline = BorderFactory.createLineBorder(Color.black, 1);

        cells = new JPanel[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new JPanel();
                cells[i][j].setBackground(new Color(0, 0, 0, 0));

                //lien controller-view
                cells[i][j].addMouseListener(this.controller);
                cells[i][j].setBorder(blackline);
                JLabel labelX = new JLabel("" + j);
                JLabel labelY = new JLabel("" + i);
                labelX.setVisible(false);
                labelY.setVisible(false);
                cells[i][j].add(labelX);
                cells[i][j].add(labelY);
                park.add(cells[i][j]);
            }

        }
        
        previousEntitiesPositions = new int[entitiesPositions.size()][2];
        for (int i = 0; i < entitiesPositions.size(); i++) {
            int x = entitiesPositions.get(i).getX();
            int y = entitiesPositions.get(i).getY();
            JLabel labelEntity = new JLabel(iconEntity);
            cells[x][y].add(labelEntity);
            previousEntitiesPositions[i][0] = x;
            previousEntitiesPositions[i][1] = y;
        }

        con.add(park);
        con.setVisible(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Add an valid target image where the user clicked
     *
     * @param cell
     */
    public void createFoodInCell(JPanel cell) {
        JLabel labelValidTarget = new JLabel(iconValidTarget);
        synchronized (cell) {
            cell.add(labelValidTarget);
            cell.revalidate();
            this.con.repaint();
        }
    }

    /**
     * Refresh the position of all entities on the interface
     * 
     * @param positions of all the entities
     */
    public void refreshEntitiesPosition(ArrayList<Position> positions) {
        synchronized (cells) {
            for (int i = 0; i < positions.size(); i++) {
                this.con.repaint();
                int x = positions.get(i).getX();
                int y = positions.get(i).getY();
                JLabel labelEntity = new JLabel(iconEntity);

                int pX = previousEntitiesPositions[i][0];
                int pY = previousEntitiesPositions[i][1];
                synchronized (cells[pX][pY]) {

                    synchronized (cells[x][y]) {

                        //supprimer l'img du pigeon sur la case précédente
                        if (cells[pX][pY].getComponentCount() >= 3) {
                            cells[pX][pY].remove(2);
                            cells[pX][pY].revalidate();
                            this.con.repaint();
                        }
                    }

                    //supprimer fresh food 
                    //cas 3 components: position + fresh food
                    if (cells[x][y].getComponentCount() == 3) {
                        cells[x][y].remove(2);
                        cells[x][y].revalidate();
                        this.con.repaint();
                        cells[x][y].add(labelEntity);
                    } //cas 4 components: position + unvalid+ rotten food
                    else if (cells[x][y].getComponentCount() >= 4) {
                        if (cells[x][y].getComponent(2) instanceof JLabel) {
                            JLabel label = (JLabel) cells[x][y].getComponent(2);
                            //if (label.getText() != null && label.getText().equals("unvalid")) {
                            System.out.println("On affiche du pourri");
                            JLabel labelUnvalidTarget = (JLabel) cells[x][y].getComponent(3);

                            int compCount = cells[x][y].getComponentCount();
                            System.out.println("count: " + compCount);
                            if (compCount >= 5) {
                                for (int k = compCount; k > 5; k--) {
                                    cells[x][y].remove(k - 1);
                                    cells[x][y].revalidate();
                                    this.con.repaint();
                                }
                            }
                            cells[x][y].remove(3);
                            cells[x][y].revalidate();
                            this.con.repaint();

                            cells[x][y].remove(2);
                            cells[x][y].revalidate();
                            this.con.repaint();

                            cells[x][y].add(labelEntity);
                            cells[x][y].revalidate();
                            this.con.repaint();
                            cells[x][y].add(label);
                            cells[x][y].add(labelUnvalidTarget);

                            cells[x][y].revalidate();
                            this.con.repaint();
                            //}
                        }
                    } else {
                        cells[x][y].add(labelEntity);
                        System.out.println("pigeon: " + cells[x][y].getComponent(cells[x][y].getComponentCount() - 1));
                        cells[x][y].revalidate();
                        this.con.repaint();
                    }

                    cells[x][y].revalidate();
                    this.con.repaint();
                }
                previousEntitiesPositions[i][0] = x;
                previousEntitiesPositions[i][1] = y;
            }
        }
        this.con.repaint();
    }

    /**
     * Refresh image corresponding to the target (change image whether target is still valid or not)
     * @param targets position of the targets
     * @param targetValidity validity of the targets
     */
    public void refreshTargetImage(ArrayList<Position> targets, ArrayList<Boolean> targetValidity) {
        for (int i = 0; i < targets.size(); i++) {
            int x = targets.get(i).getX();
            int y = targets.get(i).getY();
            if (!targetValidity.get(i)) {
                if (cells[x][y].getComponentCount() == 3) {
                    JLabel label = (JLabel) cells[x][y].getComponent(2);

                    if (label.getText() != null && label.getText().equals("unvalid")) {
                        //food already rotten
                    } else {
                        cells[x][y].remove(2);
                        cells[x][y].revalidate();
                        this.con.repaint();

                        JLabel labelRotten = new JLabel("unvalid");
                        labelRotten.setVisible(false);
                        cells[x][y].add(labelRotten);
                        JLabel labelValidTarget = new JLabel(iconUnvalidTarget);
                        cells[x][y].add(labelValidTarget);
                        cells[x][y].revalidate();
                        this.con.repaint();
                    }

                }
            }
            this.con.repaint();
        }
    }
}
