package it.unibo.fnafretro.device;

/**
 * Interfaccia base per i devices del gioco (es. porte luci ventilatore)
 * @author Davide Sancisi
 */
public interface Device {

    /**
     * accende il dispositivo.
     */
    void switchOn();

    /**
     * spegne il dispositivo.
     */
    void switchOff();

    /**
     * @return true se il dispositivo acceso, false se il dispositivo è spento.
     */
    boolean isSwitchedOn();

}
