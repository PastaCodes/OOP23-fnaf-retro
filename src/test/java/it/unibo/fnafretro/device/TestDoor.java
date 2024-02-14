package it.unibo.fnafretro.device;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.fnafretro.game.Game;

/**
 * @author  Luca Ponseggi
 */
class TestDoor {

    private Door porta;

    @BeforeEach
    void init() {
        final Game fauxGame = Game.create(Set.of(), null, () -> { }, e -> { });
        this.porta = new Door(fauxGame);
    }

    @Test
    void test() {
        /*
         * Il gioco parte con la porta aperta
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
