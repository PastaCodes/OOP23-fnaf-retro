package it.unibo.fnafretro.night;

import it.unibo.fnafretro.ai.Ai;
import it.unibo.fnafretro.game.Game;

/**
 * implementazione dell'interfaccia Night.
 * @author  Luca Ponseggi
 */
class NightImpl implements Night {

    /**
     * intero che indica in numero di ore passate dalla mezzanotte.
     */
    private int hour;

    private final Game game;

    NightImpl(final Game game) {
        hour = 0;
        this.game = game;

        /*
         * passo a scheduleRepeating i tick per ogni ora di gioco e una
         * lambda con l'azione che deve eseguire al passare di ogni ora
         */
        this.game.events().scheduleRepeating(
            Night.TICKS_PER_GAME_HOUR, this::advance
        );
    }

    @Override
    public final void advance() {
        /*
         * Incremento il numero di ore passate
         */
        this.hour++;

        /*
         * controllo che il numero di ore sia uguale al numero totali di ore a
         * notte, in tal caso la partita finisce.
         */
        if (this.hour == Night.HOURS_PER_NIGHT) {
            this.game.end(Game.VICTORY);
            return;
        }

        /*
         * controllo per ogni ai se deve aumentare il proprio livello
         */
        for (final Ai ai : game.ais()) {
            if (
                ai.descriptor().levelUpHours().contains(this.hour)
            &&  ai.getLevel() > 0
            ) {
                ai.increaseLevel();
            }
        }

        this.game.update();
    }

    @Override
    public int getHour() {
        return this.hour;
    }

}
