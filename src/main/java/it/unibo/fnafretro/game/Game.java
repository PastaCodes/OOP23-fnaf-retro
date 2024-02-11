package it.unibo.fnafretro.game;

import java.util.Set;
import java.util.function.Function;
import java.util.random.RandomGenerator;

import it.unibo.fnafretro.ai.AiDescriptor;
import it.unibo.fnafretro.map.GameMap;

/**
 * Rappresenta una partita in corso.
 * @author  Marco Buda
 */
public interface Game {

    /**
     * Crea una partita secondo le configurazioni specificate.
     * @param   aiSet   le AI da inizializzare in questa partita
     * @param   levels  una funzione che determini l'AI level iniziale per le
     *                  singole AI
     * @return          la partita creata
     */
    static Game create(
        final Set<AiDescriptor> aiSet,
        final Function<AiDescriptor, Integer> levels
    ) {
        return new GameImpl(aiSet, levels);
    }

    /**
     * @return  il gestore di eventi di questa partita
     */
    EventThread events();

    /**
     * @return  il generatore casuale usato in questa partita
     */
    RandomGenerator random();

    /**
     * @return  le stanze nella mappa di questa partita
     */
    GameMap rooms();

    /**
     * Ordina alla partita di terminare.
     * @param   hasWon  se impostato a {@code true} segnala che il giocatore ha
     *                  vinto (ossia la notte è terminata); se impostato a
     *                  {@code false} segnala che il giocatore ha perso
     */
    void end(boolean hasWon);

}
