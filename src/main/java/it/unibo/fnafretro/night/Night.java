package it.unibo.fnafretro.night;

import it.unibo.fnafretro.game.EventThread;
import it.unibo.fnafretro.game.Game;

public interface Night {
    final int NIGHT_DURATION = 120; // MEtti nei commenti che il totale è in secondi
    final int TICKS_PER_GAME_HOUR = EventThread.TICKS_PER_SECOND * (Night.NIGHT_DURATION / 6);

    static Night create(Game game) {
        return new NightImpl(game);
    }

    void advance();
    int getHour();
  
}
