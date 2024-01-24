package it.unibo.fnafretro.game;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.random.RandomGenerator;

import it.unibo.fnafretro.ai.Ai;
import it.unibo.fnafretro.ai.AiDescriptor;

/**
 * Implementazione di una partita in corso.
 * @author  Marco Buda
 */
class GameImpl implements Game {

    private final EventThread eventThread = EventThread.create();
    private final RandomGenerator random = new Random();

    GameImpl(
        final List<AiDescriptor> aiSet,
        final Function<AiDescriptor, Integer> levels
    ) {
        this.initAis(aiSet, levels);
    }

    /**
     * Registra gli eventi periodici necessari per realizzare il comportamento
     * delle {@link Ai}.
     * @param   aiSet   le AI selezionate per questa partita
     * @param   levels  una funzione che specifichi un AI level iniziale per
     *                  ogni AI
     */
    private void initAis(
        final List<AiDescriptor> aiSet,
        final Function<AiDescriptor, Integer> levels
    ) {
        aiSet.forEach(aiDescr -> {
            final Ai ai = aiDescr.create(this, levels.apply(aiDescr));
            this.eventThread.scheduleRepeating(
                aiDescr.cooldown(),
                Ai.opportunityEvent(ai, this.random)
            );
        });
    }

    @Override
    public EventThread events() {
        return this.eventThread;
    }

}
