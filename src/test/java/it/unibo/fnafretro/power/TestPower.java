package it.unibo.fnafretro.power;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

public class TestPower {
    Power power;

    @BeforeEach void init() {
        this.power = Power.create();
    }
 
    @Test void test1() {    
        assertEquals(1, power.getTicks());
        assertEquals(1.000, power.getEnergyLevel());
    }

    @Test void test2() {
        this.power.addTick();
        assertEquals(2, power.getTicks());
        this.power.removeTick();
        assertEquals(1, power.getTicks());
    }

    @Test void test3() {
        this.power.applyEnergyCost();
        assertEquals(0.998, power.getEnergyLevel());
    }

}
