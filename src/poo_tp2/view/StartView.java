/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_tp2.view;

import java.awt.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import poo_tp2.controller.StartController;

/**
 *
 * @author tiff9
 */
public class StartView extends JFrame {
    public Container con;
    public JPanel mainPan;
    public JSpinner spinnerNumber;
    public JSpinner spinnerSize;
    public StartController sc;
    public JButton buttonPlay;
    public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public int heightView =(int) screenSize.getHeight() * 30/100;
    public int widthView = (int)screenSize.getWidth() * 20/100;
    
    public StartView(StartController sc){
        
        setResizable(false);
        setSize(widthView, heightView);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setTitle("Start Minigame");
        setIconImage((new ImageIcon("src/poo_tp2/img/app_icon.png")).getImage());
        
        con = this.getContentPane();
        
        mainPan = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        
        JLabel labelMapSize = new JLabel("Choose a size for the map: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.EAST;
        mainPan.add(labelMapSize, gbc);
        
        JLabel labelEntitiesNumber = new JLabel("Number of pigeons: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.EAST;
        mainPan.add(labelEntitiesNumber, gbc);
        
        //map size
        int currentSize = 5;
        int minSize = 3;
        int maxSize = 8;
        int stepSize = 1;
        SpinnerNumberModel snmSize = new SpinnerNumberModel(currentSize, minSize, maxSize, stepSize);
        spinnerSize = new JSpinner(snmSize);        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.CENTER;
        mainPan.add(spinnerSize, gbc);
        
        //number of pigeons
        int currentNumber = 2;
        int minNumber = 1;
        int maxNumber = 10;
        int stepNumber = 1;
        SpinnerNumberModel snmNumber = new SpinnerNumberModel(currentNumber, minNumber, maxNumber, stepNumber);
        spinnerNumber = new JSpinner(snmNumber);        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.CENTER;
        mainPan.add(spinnerNumber, gbc);

        
        buttonPlay = new JButton("Play");
        gbc.gridx = 0;
        gbc.gridy = 4;
        //gbc.weightx = 2;
        //gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.fill = GridBagConstraints.CENTER;
        buttonPlay.addActionListener(sc);
        mainPan.add(buttonPlay, gbc);
        
        con.add(mainPan);
        con.setVisible(true);
        setVisible(true);
    }
    

    public int getMapSize(){
       
        try {
            spinnerSize.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(StartView.class.getName()).log(Level.SEVERE, null, ex);
        }
        int value = (Integer) spinnerSize.getValue();
        return value;
    }
    
    
    public int getEntitiesNumber(){
        try {
            spinnerNumber.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(StartView.class.getName()).log(Level.SEVERE, null, ex);
        }
        int value = (Integer) spinnerNumber.getValue();
        return value;
    }
    
    public void closeStartView(){
        dispose();
    }
    

    
}
