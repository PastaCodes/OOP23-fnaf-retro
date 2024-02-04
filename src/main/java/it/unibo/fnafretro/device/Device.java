package it.unibo.fnafretro.device;

/**
 * Interfaccia base per i devices del gioco (es. porte luci ventilatore)
 * @author Davide Sancisi
 */

public interface Device {
    
    void switchOn();

    void switchOff();

    boolean isSwitchedOn();

}
