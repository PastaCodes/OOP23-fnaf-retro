package it.unibo.fnafretro.power;

/**
 * Implementazione dell'energia. 
 * @author Davide Sancisi
 */


public class PowerImpl implements Power{

    private int Ticks = 1;

    @Override
    public void usageTicks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'usageTicks'");
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

    @Override
    public void addTick() {
        this.Ticks++;
    }

    @Override
    public void removeTick() {
        this.Ticks--;
    }
    
}
