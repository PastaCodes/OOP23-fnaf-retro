package it.unibo.fnafretro.game;

import java.util.List;
import java.util.function.Function;
import java.util.random.RandomGenerator;

import it.unibo.fnafretro.ai.AiDescriptor;

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
        final List<AiDescriptor> aiSet,
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

}
