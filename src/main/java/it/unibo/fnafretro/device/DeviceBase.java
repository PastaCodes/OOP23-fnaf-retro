package it.unibo.fnafretro.device;

import it.unibo.fnafretro.power.PowerImpl;

/**
 * Classe astratta base per i device del gioco.
 * @author Luca Ponseggi
 */

public abstract class DeviceBase implements Device {

    private PowerImpl power;
    private boolean status;

    public DeviceBase(PowerImpl power) {
        this.power = power;
    }

    @Override
    public boolean isSwitchedOn() {
        return this.status;
    }

    @Override
    public void switchOff() {
        this.status = false;
        this.power.removeTick();
    }

    @Override
    public void switchOn() {
        this.status = true;
        this.power.addTick();
    }

}
