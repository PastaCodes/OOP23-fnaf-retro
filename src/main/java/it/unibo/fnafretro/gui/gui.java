package it.unibo.fnafretro.gui;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Parte grafica del gioco 
 */

public class Gui extends JFrame{

    int size = 1;

    public Gui() throws IOException{
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(2080*size, 976*size); //Originale: 208x90
        this.setTitle("Five Nights at Freddy's: Retro");

        BufferedImage gameIcon = ImageIO.read(ClassLoader.getSystemResource("it/unibo/fnafretro/icon.png"));
        this.setIconImage(gameIcon);

        this.getContentPane().setBackground(Color.BLACK); 

        BufferedImage img = ImageIO.read(ClassLoader.getSystemResource("it/unibo/fnafretro/map/main.png"));

        JLabel background = new JLabel();
        background.setSize(2080*size, 900*size);
        

        Image dimg = img.getScaledInstance(background.getWidth(), background.getHeight(),
        Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);
        

        background.setIcon(imageIcon);
        
        this.add(background);
        this.setVisible(true);
    }

}
