package it.unibo.fnafretro.night;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.fnafretro.game.Game;

/**
 * @author  Luca Ponseggi
 */
class TestNight {
    private Night notte;

    @BeforeEach
    void init() {
        this.notte = Night.create(Game.create(Set.of(), null));
    }

     @Test
    void test() {
        /*
         * Il gioco parte con 0 ore di gioco passate (ovviamente)
         */
        assertEquals(0, this.notte.getHour());

        /*
         * Incremento la notte di un'ora
         * controllo in numero di ore passate
         */
        this.notte.advance();
        assertEquals(1, this.notte.getHour());

    }
}
