package it.unibo.fnafretro.game;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.random.RandomGenerator;

import it.unibo.fnafretro.ai.Ai;
import it.unibo.fnafretro.ai.AiDescriptor;
import it.unibo.fnafretro.map.GameMap;
import it.unibo.fnafretro.night.Night;

/**
 * Implementazione di una partita in corso.
 * @author  Marco Buda
 */
class GameImpl implements Game {

    private final EventThread eventThread = EventThread.create();
    private final RandomGenerator random = new Random();
    private final Night night;
    private final GameMap map = null; // TODO
    private final Set<Ai> ais;

    GameImpl(
        final Set<AiDescriptor> aiSet,
        final Function<AiDescriptor, Integer> levels
    ) {
        this.night = Night.create(this);
        this.ais = Ai.initAis(aiSet, levels, this);
    }

    @Override
    public EventThread events() {
        return this.eventThread;
    }

    @Override
    public RandomGenerator random() {
        return this.random;
    }

    @Override
    public GameMap rooms() {
        return this.map;
    }

    @Override
    public void end(final boolean hasWon) {
        this.eventThread.stop();
        // TODO
    }

}
