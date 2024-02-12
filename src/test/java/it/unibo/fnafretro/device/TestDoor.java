package it.unibo.fnafretro.device;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.fnafretro.power.Power;

/**
 * @author  Luca Ponseggi
 */
class TestDoor {

    private Door porta;

    @BeforeEach
    void init() {
        this.porta = new Door(Power.create());
    }

    @Test
    void test() {
        /*
         * Il gioco inizia con la porta aperta
        */
        assertFalse(this.porta.isSwitchedOn());

        /*
         * Chiudo la porta
         * controllo che sia chiusa
         */
        this.porta.switchOn();
        assertTrue(this.porta.isSwitchedOn());

        /*
         * Apro la porta
         * controllo che sia aperta
         */
        this.porta.switchOff();
        assertFalse(this.porta.isSwitchedOn());
    }

}
