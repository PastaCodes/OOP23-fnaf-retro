package it.unibo.fnafretro;

import java.io.IOException;

import it.unibo.fnafretro.gui.Gui;

/**
 * Classe di utility contenente l'entry-point del programma.
 * @author  Marco Buda
 */
public final class Program {
    private Program() { }

    /**
     * L'entry-point del programma.
     * @param   args    non utilizzati
     * @throws IOException 
     */
    public static void main(final String[] args) throws IOException {
        Gui gui = new Gui();
    }

}