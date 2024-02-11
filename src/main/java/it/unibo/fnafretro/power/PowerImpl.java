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
    final double ENERGY_TICK_COST = 0.002;

    @Override
    public void applyEnergyCost() {
        double totalEnergyCost;
        totalEnergyCost = this.energyTicks*this.ENERGY_TICK_COST;
        this.energyLevel -= totalEnergyCost;
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
    public void init(Game game) {
        game.events().scheduleRepeating(EventThread.TICKS_PER_SECOND, this::applyEnergyCost);
    }

    @Override
    public int getTicks() {
        return this.energyTicks;
    }
    
}
