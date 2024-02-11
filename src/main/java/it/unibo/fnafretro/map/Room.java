package it.unibo.fnafretro.map;

/**
 * rappresenta una stanza all' interno della mappa di gioco.
 * @author Filippo Del Bianco
 */
public interface Room {

    /**
     * @return il nome della stanza
     */
    String getRoomName();

    /**
     * @return il lato in cui si trova la stanza all' interno della mappa
     */
    String getSide();
}
