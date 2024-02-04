package it.unibo.fnafretro.device;

import it.unibo.fnafretro.power.PowerImpl;

/**
 * Classe astratta base per i device del gioco.
 * @author Luca Ponseggi
 */

public abstract class DeviceBase implements Device {
    PowerImpl power;
    boolean status;

    @Override
    public boolean isSwitchedOn() {
        return this.status;
    }

    @Override
    public void switchOff() {
        this.status = false;
    }

    @Override
    public void switchOn() {
        this.status = true;
    }

}
