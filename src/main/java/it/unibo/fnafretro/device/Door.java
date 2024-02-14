package it.unibo.fnafretro.device;

import it.unibo.fnafretro.game.Game;

/**
 * Classe per la gestione della porta.
 * @author Luca Ponseggi
 */
public class Door extends DeviceBase {

    /**
     * Costruisce un'istanza di Door che gestisce l'apertura e la chiusura di
     * una porta.
     * @param   game    la partita in cui viene istanziata la porta
     */
    public Door(final Game game) {
        super(game);
    }

}
