package it.unibo.fnafretro.power;

import it.unibo.fnafretro.ai.Ai;
import it.unibo.fnafretro.ai.AiDescriptor;
import it.unibo.fnafretro.ai.Freddy;
import it.unibo.fnafretro.game.EventThread;
import it.unibo.fnafretro.game.Game;

/**
 * Implementazione dell'energia. 
 * @author Davide Sancisi
 */
final class PowerImpl implements Power {

    private int energyTicks = 1;
    private double energyLevel = 1.000;
    private final Game game;

    PowerImpl(final Game game) {
        this.game = game;
        game.events().scheduleRepeating(EventThread.TICKS_PER_SECOND, this::applyEnergyCost);
    }

    @Override
    public void subtractEnergy(final double amount) {
        if (this.energyLevel > amount) {
            this.energyLevel -= amount;
        } else {
            this.energyLevel = 0;
            /**
             * Se finisce l'energia Freddy attacca.
             */
            final AiDescriptor freddy = game.ais().stream()
                .map(Ai::descriptor).filter(ai -> ai instanceof Freddy)
                .findFirst().get();
            this.game.end(new Game.JumpscareEnding(freddy));
        }
        this.game.update();
    }

    @Override
    public void applyEnergyCost() {
        this.subtractEnergy(this.energyTicks * ENERGY_TICK_COST);
    }

    @Override
    public void addTick() {
        this.energyTicks++;
    }

    @Override
    public void removeTick() {
        this.energyTicks--;
    }

    @Override
    public double getEnergyLevel() {
        return this.energyLevel;
    }

    @Override
    public int getTicks() {
        return this.energyTicks;
    }

}