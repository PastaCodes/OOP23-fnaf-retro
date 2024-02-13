package it.unibo.fnafretro.power;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

/**
 * @author Davide Sancisi
 */
public class TestPower {
    Power power;

    @BeforeEach void init() {
        this.power = Power.create();
    }
 
    @Test void test1() {    
        assertEquals(1, this.power.getTicks());
        assertEquals(1.000, this.power.getEnergyLevel());
    }

    @Test void test2() {
        this.power.addTick();
        assertEquals(2, this.power.getTicks());
        this.power.removeTick();
        assertEquals(1, this.power.getTicks());
    }

    @Test void test3() {
        this.power.applyEnergyCost();
        assertEquals(0.998, this.power.getEnergyLevel());
    }

    @Test void test4() {
        this.power.subtractEnergy(0.005);
        assertEquals(0.995, this.power.getEnergyLevel());
    }

}
