package it.unibo.fnafretro.power;

import org.junit.jupiter.api.Test;

import it.unibo.fnafretro.game.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;

/**
 * @author  Davide Sancisi
 */
class TestPower {

    private Power power;

    @BeforeEach void init() {
        this.power = Power.create(Game.create(Set.of(), null, () -> { }, e -> { }));
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
        assertEquals(1 - Power.ENERGY_TICK_COST, power.getEnergyLevel());
    }

}
