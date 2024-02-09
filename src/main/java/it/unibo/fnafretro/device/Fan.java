package it.unibo.fnafretro.device;
import it.unibo.fnafretro.power.PowerImpl;

/**
 * Ventilatore. Ogni tot "tick" fa diminuire la temperatura di tot °.
 * @author Davide Sancisi
 */

public class Fan extends DeviceBase{
    PowerImpl power;
    boolean status;
    public Fan(PowerImpl power) {
        super(power);
    }
    
}
