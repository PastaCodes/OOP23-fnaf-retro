package it.unibo.fnafretro.power;

/**
 * Implementazione dell'energia. 
 * @author Davide Sancisi
 */


public class PowerImpl implements Power{

    private int ticks = 1;
    private double energyLevel = 1.000;
    final double ENERGY_TICK_COST = 0.002;

    @Override
    public void applyEnergyCost() {

        double totalEnergyCost;
        totalEnergyCost = this.ticks*this.ENERGY_TICK_COST;
        this.energyLevel -= totalEnergyCost;

    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

    @Override
    public void addTick() {
        this.ticks++;
    }

    @Override
    public void removeTick() {
        this.ticks--;
    }

    @Override
    public double getEnergyLevel() {
        return this.energyLevel;
    }
    
}
