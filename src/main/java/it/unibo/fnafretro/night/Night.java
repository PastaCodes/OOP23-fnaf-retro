package it.unibo.fnafretro.night;

import it.unibo.fnafretro.game.EventThread;
import it.unibo.fnafretro.game.Game;

/**
 * Rappresenta la notte di una partita.
 * @author  Luca Ponseggi
 */
public interface Night {

    /**
     * Durata totale di una notte di gioco espressa in ore di gioco.
     */
    int HOURS_PER_NIGHT = 6;

    /**
     * Durata totale di una notte di gioco espressa in secondi reali.
     */
    int NIGHT_DURATION = 120;

    /**
     * Durata di un'ora di gioco espressa in tick.
     */
    int TICKS_PER_GAME_HOUR = EventThread.TICKS_PER_SECOND
        * (Night.NIGHT_DURATION / Night.HOURS_PER_NIGHT);

    /**
     * Crea un'istanza di una notte di gioco.
     * @param game  la partita attuale
     * @return  l'istanza creata
     */
    static Night create(Game game) {
        return new NightImpl(game);
    }

    /**
     * Avanzamento di un'ora di gioco.
     */
    void advance();

    /**
     * Restituisce l'ora attuale del gioco.
     * @return  l'ora attuale
     */
    int getHour();

}
