package it.unibo.fnafretro.map;

import java.util.List;

/**
 * rappresenta la mappa all'interno del gioco che contiene le stanze,
 * le telecamere e come le stanze sono collegate fra di esse.
 * @author Filippo Del Bianco
 */
public interface GameMap {

    /**
     * @return un'istanza della classe GameMapImpl
     */
    static GameMap create() {
        return new GameMapImpl();
    }

    /**
     * restituisce se esiste la stanza richiesta attraverso il nome della stanza.
     * @param roomName nome della stanzache si vuole ottenere
     * @return la stanza con il nome passato come input se esiste
     */
    Room getRoom(String roomName);

    /**
     * @return tutte le stanze presenti nella mappa
     */
    List<Room> getAllRooms();

    /**
     * 
     * @param room Ã¨ la stanza della quale si volgiono sapere le stanze adiacenti
     * @return le stanze adiacenti a quella fornita in input
     * 
     */
    List<Room> getAdjacencies(Room room);
}
