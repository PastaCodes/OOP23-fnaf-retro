package it.unibo.fnafretro.night;

import it.unibo.fnafretro.game.Game;

public class NightImpl implements Night {
    private int hour; // va da 0 a 6, la parte in cui viene scritto il numero va nella grafica
    private final Game game;

    public NightImpl(final Game game) {
        hour = 0;
        this.game = game;
        
        // passo a schedulerepeating i tick per ogni ora di gioco e una
        // lambda con l'azione che deve eseguire al passare di ogni ora
        this.game.events().scheduleRepeating(Night.TICKS_PER_GAME_HOUR, this::advance);

    }

    @Override
    public void advance() {
        this.hour++;
        
        //Ogni ai ha il proprio orario a cui aumenta il livello, lo controllo
        game.ais().forEach(ai -> {
            if (ai.descriptor().levelUpHours().contains(this.hour)) {
                ai.increaseLevel();
            }
        });

        if(this.hour == 6) {
            game.end(true);
        }
    }

    @Override
    public int getHour() {
        return hour;
    }

}
