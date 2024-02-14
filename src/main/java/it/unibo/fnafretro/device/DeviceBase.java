package it.unibo.fnafretro.device;

import it.unibo.fnafretro.game.Game;

/**
 * Classe astratta base per i device del gioco.
 * @author Luca Ponseggi
 */
abstract class DeviceBase implements Device {

    private final Game game;
    private boolean status;

    DeviceBase(final Game game) {
        this.game = game;
        this.status = false;
    }

    @Override
    public boolean isSwitchedOn() {
        return this.status;
    }

    @Override
    public void switchOff() {
        if (this.status) {
            this.status = false;
            this.game.power().removeTick();
            this.game.update();
        }
    }

    @Override
    public void switchOn() {
        if (!this.status) {
            this.status = true;
            this.game.power().addTick();
            this.game.update();
        }
    }

}
