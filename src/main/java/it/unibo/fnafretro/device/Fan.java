package it.unibo.fnafretro.device;
import it.unibo.fnafretro.power.PowerImpl;

/**
 * Ventilatore. Ogni tot "tick" fa diminuire la temperatura di tot °.
 * @author Davide Sancisi
 */

public class Fan implements Device{
    PowerImpl power;
    boolean status;
    public Fan(PowerImpl power){
        this.status = false;
        this.power = power;
    }

    @Override
    public void switchOn() {
        this.status = true;

    }

    @Override
    public void switchOff() {
        this.status = false;
    }

    @Override
    public boolean isSwitchedOn() {
        return this.status;
    }
    
}
