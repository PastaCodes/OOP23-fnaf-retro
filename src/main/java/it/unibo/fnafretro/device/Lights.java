package it.unibo.fnafretro.device;

import it.unibo.fnafretro.power.PowerImpl;

/**
 * Classe per la gestione e sincronizzazione fra le luci con
 * la classe Light innestata (viene usata solo dentro Lights).
 * @author Luca Ponseggi
 */

public class Lights {

    private Device left;
    private Device right;

    public Lights(PowerImpl power) {
        this.left = new Light(power);
        this.right = new Light(power);
    }

    public void switchOnLeftLight() {
        this.right.switchOff();
        this.left.switchOn();
    }

    public void switchOnRightLight() {
        this.left.switchOff();
        this.right.switchOn();
    }

    public void switchOffLight() {
        this.right.switchOff();
        this.left.switchOff();
    }

    private class Light extends DeviceBase {

        public Light(PowerImpl power) {
            super(power);
        }

    }

}
