package it.unibo.fnafretro.power;

import it.unibo.fnafretro.game.EventThread;
import it.unibo.fnafretro.game.Game;

/**
 * Implementazione dell'energia. 
 * @author Davide Sancisi
 */
class PowerImpl implements Power {

    private int energyTicks = 1;
    private double energyLevel = 1.000;
    private static final double ENERGY_TICK_COST = 0.002;

    @Override
    public void applyEnergyCost() {
        double totalEnergyCost;
        totalEnergyCost = this.energyTicks * ENERGY_TICK_COST;
        if (this.energyLevel > totalEnergyCost) {
            this.energyLevel -= totalEnergyCost;
        } else {
            this.energyLevel = 0.000;
        }
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
    public void init(final Game game) {
        game.events().scheduleRepeating(EventThread.TICKS_PER_SECOND, this::applyEnergyCost);
    }

    @Override
    public int getTicks() {
        return this.energyTicks;
    }

    @Override
    public void subtractEnergy(final double amount) {
        if (this.energyLevel > amount) {
            this.energyLevel -= amount;
        } else {
            this.energyLevel = 0.000;
        }
    }

}
