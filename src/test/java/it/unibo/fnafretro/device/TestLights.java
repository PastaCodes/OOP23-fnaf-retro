package it.unibo.fnafretro.device;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.fnafretro.power.Power;

/**
 * @author  Luca Ponseggi
 */
class TestLights {

    private Lights luci;

    @BeforeEach
    void init() {
        this.luci = new Lights(Power.create());
    }

    @Test
    void test() {
        /*
         * Il gioco parte con le luci spente
         */
        assertFalse(this.luci.isLeftLightOn());
        assertFalse(this.luci.isRightLightOn());

        /*
         * Accendo la luce sinistra
         * controllo che si sia accesa
         */
        this.luci.switchOnLeftLight();
        assertTrue(this.luci.isLeftLightOn());

        /*
         * Accendo la luce a destra: quella a sinistra dovrà spegnersi
         */
        this.luci.switchOnRightLight();
        assertTrue(this.luci.isRightLightOn());
        assertFalse(this.luci.isLeftLightOn());

        /*
         * Spengo entrambe le luci
         * controllo che si siano spente
         */
        this.luci.switchOffLight();
        assertFalse(this.luci.isLeftLightOn());
        assertFalse(this.luci.isRightLightOn());
    }

}
