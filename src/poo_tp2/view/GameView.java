/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import poo_tp2.controller.GameController;
import poo_tp2.Position;

/**
 *
 * @author tiff9
 */
public class GameView extends JFrame {

    public GameController controller;
    public Container con;
    public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public int heightView =(int) screenSize.getHeight() * 80/100;
    public int widthView = (int)screenSize.getWidth() * 60/100;
    public ImageIcon iconEntity = new ImageIcon(new ImageIcon("src/poo_tp2/img/entity_icon.png")
            .getImage().getScaledInstance(52, 80, Image.SCALE_DEFAULT));
    public ImageIcon iconBackground = new ImageIcon(new ImageIcon("src/poo_tp2/img/background.jpg")
            .getImage().getScaledInstance(widthView, heightView, Image.SCALE_DEFAULT));
    public ImageIcon iconBackground2 = new ImageIcon(new ImageIcon("src/poo_tp2/img/background2.jpg")
            .getImage().getScaledInstance(widthView, heightView, Image.SCALE_DEFAULT));
    public ImageIcon iconValidTarget = new ImageIcon(new ImageIcon("src/poo_tp2/img/target_icon.png")
            .getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT));
    public ImageIcon iconUnvalidTarget = new ImageIcon(new ImageIcon("src/poo_tp2/img/rottentarget_icon.png")
            .getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT));
    public JPanel cells[][];
    int[][] previousEntitiesPositions;
    public JPanel panelBackground;
    public JPanel panelPark;
    public boolean isScary = false;


    public GameView(int rows, int columns, ArrayList<Position> entitiesPositions, GameController c) {
        this.controller = c;
        
        setSize(widthView, heightView);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setIconImage((new ImageIcon("src/poo_tp2/img/app_icon.png")).getImage());
        setTitle("Feeding Pigeons Minigame");

        this.con = getContentPane();

        panelBackground = new JPanel();
        panelBackground.setBackground(new Color(0, 0, 0, 0));

        
        panelPark = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                 if (isScary){
                    g.drawImage(GameView.this.iconBackground2.getImage(), 0, 0, null);
                 }
                 else
                 {
                     g.drawImage(GameView.this.iconBackground.getImage(), 0, 0, null);
                 }
                super.paintComponent(g);
            }
            
        };
        
        panelPark.setBackground(new Color(0, 0, 0, 0));
        panelPark.setLayout(new GridLayout(rows, columns, -1, -1));
        panelPark.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

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
                panelPark.add(cells[i][j]);
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

        con.add(panelPark);
        con.setVisible(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void changeBackground(boolean isScary){
        this.isScary = isScary;
        panelPark.repaint();
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
            panelPark.repaint();
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
                            panelPark.repaint();
                        }
                    }

                    //supprimer fresh food 
                    //cas 3 components: position + fresh food
                    if (cells[x][y].getComponentCount() == 3) {
                        cells[x][y].remove(2);
                        cells[x][y].revalidate();
                        cells[x][y].add(labelEntity);
                        panelPark.repaint();
                    } //cas 4 components: position + unvalid+ rotten food
                    else if (cells[x][y].getComponentCount() >= 4) {
                        if (cells[x][y].getComponent(2) instanceof JLabel) {
                            JLabel label = (JLabel) cells[x][y].getComponent(2);
                            //if (label.getText() != null && label.getText().equals("unvalid")) {
                            System.out.println("On affiche du pourri");
                            JLabel labelUnvalidTarget = (JLabel) cells[x][y].getComponent(3);

                            int compCount = cells[x][y].getComponentCount();
                            if (compCount >= 5) {
                                for (int k = compCount; k > 5; k--) {
                                    cells[x][y].remove(k - 1);
                                    cells[x][y].revalidate();
                                    panelPark.repaint();
                                }
                            }
                            cells[x][y].remove(3);
                            cells[x][y].revalidate();
                            panelPark.repaint();

                            cells[x][y].remove(2);
                            cells[x][y].revalidate();
                            panelPark.repaint();

                            cells[x][y].add(labelEntity);
                            cells[x][y].revalidate();
                            panelPark.repaint();
                            cells[x][y].add(label);
                            cells[x][y].add(labelUnvalidTarget);

                            cells[x][y].revalidate();
                            panelPark.repaint();
                        }
                    } else {
                        cells[x][y].add(labelEntity);
                        cells[x][y].revalidate();
                        panelPark.repaint();
                    }

                    cells[x][y].revalidate();
                    panelPark.repaint();
                }
                previousEntitiesPositions[i][0] = x;
                previousEntitiesPositions[i][1] = y;
                panelPark.repaint();
            }
        }
        panelPark.repaint();
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
                        panelPark.repaint();
                    } else {
                        cells[x][y].remove(2);
                        cells[x][y].revalidate();
                        panelPark.repaint();

                        JLabel labelRotten = new JLabel("unvalid");
                        labelRotten.setVisible(false);
                        cells[x][y].add(labelRotten);
                        JLabel labelValidTarget = new JLabel(iconUnvalidTarget);
                        cells[x][y].add(labelValidTarget);
                        cells[x][y].revalidate();
                        panelPark.repaint();
                    }

                }
            }
            panelPark.repaint();
        }
    }
}
