package it.unibo.fnafretro;

import it.unibo.fnafretro.gui.GameGUI;

/**
 * Classe di utility contenente l'entry-point del programma.
 * @author  Marco Buda
 */
public final class Program {
    private Program() { }

    /**
     * L'entry-point del programma.
     * @param   args    non utilizzati
     */
    public static void main(final String[] args) {
        new GameGUI();
    }

}