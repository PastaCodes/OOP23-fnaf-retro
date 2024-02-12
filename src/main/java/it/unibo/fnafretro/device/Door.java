package it.unibo.fnafretro.device;

import it.unibo.fnafretro.power.Power;

/**
 * Classe per la gestione della porta.
 * @author Luca Ponseggi
 */
public class Door extends DeviceBase {

    /**
     * Costruisce un'istanza di Door che gestisce l'apertura e la chiusura di
     * una porta.
     * @param power gestione dell'energia
     */
    public Door(final Power power) {
        super(power);
    }

}
