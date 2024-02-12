package it.unibo.fnafretro.device;

import it.unibo.fnafretro.power.Power;

/**
 * Classe astratta base per i device del gioco.
 * @author Luca Ponseggi
 */
abstract class DeviceBase implements Device {

    private final Power power;
    private boolean status;

    DeviceBase(final Power power) {
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
