package it.unibo.fnafretro.game;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.random.RandomGenerator;

import it.unibo.fnafretro.ai.Ai;
import it.unibo.fnafretro.ai.AiDescriptor;
import it.unibo.fnafretro.device.Door;
import it.unibo.fnafretro.device.Lights;
import it.unibo.fnafretro.map.GameMap;
import it.unibo.fnafretro.night.Night;
import it.unibo.fnafretro.power.Power;

/**
 * Implementazione di una partita in corso.
 * @author  Marco Buda
 */
class GameImpl implements Game {

    private final EventThread eventThread = EventThread.create();
    private final RandomGenerator random = new Random();
    private final Night night;
    private final GameMap map = GameMap.create();
    private final Set<Ai> ais;
    private final Power power = Power.create();
    private final Door leftDoor = new Door(this.power);
    private final Door rightDoor = new Door(this.power);
    private final Lights lights = new Lights(this.power);

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
    public Set<Ai> ais() {
        return this.ais;
    }

    @Override
    public Power power() {
        return this.power;
    }

    @Override
    public Door leftDoor() {
        return this.leftDoor;
    }

    @Override
    public Door rightDoor() {
        return this.rightDoor;
    }

    @Override
    public Lights lights() {
        return this.lights;
    }

    @Override
    public void end(final Game.Ending ending) {
        this.eventThread.stop();
        // TODO
    }

}
