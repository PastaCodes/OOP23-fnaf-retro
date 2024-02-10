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
        Ai.initAis(aiSet, levels, this);
    }

    @Override
    public EventThread events() {
        return this.eventThread;
    }

    @Override
    public RandomGenerator random() {
        return this.random;
    }

}
