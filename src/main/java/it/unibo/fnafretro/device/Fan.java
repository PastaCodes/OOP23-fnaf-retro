package it.unibo.fnafretro.device;
import it.unibo.fnafretro.power.Power;

/**
 * Ventilatore. Ogni tot "tick" fa diminuire la temperatura di tot °.
 * @author Davide Sancisi
 */

public class Fan extends DeviceBase{
    Power power;
    boolean status;
    public Fan(Power power) {
        super(power);
    }
    
}
