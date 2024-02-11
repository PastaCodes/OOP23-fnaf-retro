package it.unibo.fnafretro.device;

import it.unibo.fnafretro.power.Power;

/**
 * Classe per la gestione e sincronizzazione fra le luci con
 * la classe Light innestata (viene usata solo dentro Lights).
 * @author Luca Ponseggi
 */

public class Lights {

    private Device left;
    private Device right;

    public Lights(Power power) {
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
    
    public boolean isLeftLightOn() {
        return this.left.isSwitchedOn();
    }

    public boolean isRightLightOn() {
        return this.right.isSwitchedOn();
    }

    private class Light extends DeviceBase {

        public Light(Power power) {
            super(power);
        }

    }

}
