package it.unibo.fnafretro.device;

import it.unibo.fnafretro.power.Power;

/**
 * Classe per la gestione e sincronizzazione fra le luci con la classe Light 
 * innestata (viene usata solo dentro Lights).
 * @author  Luca Ponseggi
 */
public final class Lights {

    private final Device left;
    private final Device right;

    /**
     * Costruisce un'istanza di Lights che gestisce due luci, sinistra e destra.
     * @param power gestione dell'energia
     */
    public Lights(final Power power) {
        this.left = new Light(power);
        this.right = new Light(power);
    }

    /**
     * Spegne la luce a destra e accende la luce a sinistra.
     */
    public void switchOnLeftLight() {
        this.right.switchOff();
        this.left.switchOn();
    }

    /**
     * Spegne la luce a sinistra e accende la luce a destra.
     */
    public void switchOnRightLight() {
        this.left.switchOff();
        this.right.switchOn();
    }

    /**
     * Spegne entrambe le luci.
     * Non esistono funzioni che gestiscono lo spegnimento separato delle luci
     * in quanto non esiste un caso in cui entrambe sono accese e voglio
     * spegnerne solo una ma ne viene accesa sempre e solo una alla volta.
     */
    public void switchOffLight() {
        this.right.switchOff();
        this.left.switchOff();
    }

    /**
     * Verifica se la luce sinistra è accesa.
     * @return true se la luce sinistra è accesa, altrimenti false
     */
    public boolean isLeftLightOn() {
        return this.left.isSwitchedOn();
    }

    /**
     * Verifica se la luce destra è accesa.
     * @return true se la luce destra è accesa, altrimenti false
     */
    public boolean isRightLightOn() {
        return this.right.isSwitchedOn();
    }

    /**
    * Classe interna privata che rappresenta una singola luce.
    * Estende DeviceBase per ereditare il comportamento di un dispositivo
    * generico ed utilizza un'istanza di Power per controllare l'alimentazione
    * della luce.
    */
    private static class Light extends DeviceBase {

        Light(final Power power) {
            super(power);
        }

    }

}
