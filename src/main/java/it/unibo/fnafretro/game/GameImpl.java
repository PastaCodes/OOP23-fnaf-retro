package it.unibo.fnafretro.game;

import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.random.RandomGenerator;

import it.unibo.fnafretro.ai.Ai;
import it.unibo.fnafretro.ai.AiDescriptor;
import it.unibo.fnafretro.device.Door;
import it.unibo.fnafretro.device.Lights;
import it.unibo.fnafretro.map.Cameras;
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
    private final Cameras cameras = Cameras.create();
    private final Set<Ai> ais;
    private final Power power;
    private final Door leftDoor;
    private final Door rightDoor;
    private final Lights lights;
    private final Runnable updateSignal;
    private final Consumer<Game.Ending> endSignal;

    GameImpl(
        final Set<AiDescriptor> aiSet,
        final Function<AiDescriptor, Integer> levels,
        final Runnable updateSignal,
        final Consumer<Game.Ending> endSignal
    ) {
        this.power = Power.create(this);
        this.leftDoor = new Door(this);
        this.rightDoor = new Door(this);
        this.lights = new Lights(this);
        this.night = Night.create(this);
        this.ais = Ai.initAis(aiSet, levels, this);
        this.updateSignal = updateSignal;
        this.endSignal = endSignal;
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
    public Cameras cameras() {
        return this.cameras;
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
    public Night nightProgression() {
        return this.night;
    }

    @Override
    public void end(final Game.Ending ending) {
        this.eventThread.abort();
        this.endSignal.accept(ending);
    }

    @Override
    public void update() {
        this.updateSignal.run();
    }

}
