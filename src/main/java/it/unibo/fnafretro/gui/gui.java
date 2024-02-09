package it.unibo.fnafretro.gui;

import javax.swing.JFrame;

/**
 * Parte grafica del gioco 
 */

public class gui extends JFrame{

    public gui(int size){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);

        //WIP

        this.setVisible(true);
    }

}
