package it.unibo.fnafretro.ai;

import java.util.Set;

import it.unibo.fnafretro.game.Game;

/**
 * Foxy non si sposta mai dalla stanza 1C, se non per aggredire il giocatore se
 * usa troppo poco le telecamere.
 * Parte dalla fase 0 e periodicamente la aumenta, a meno che il giocatore non
 * stia guardando le telecamere. Arrivato alla fase 3 cerca di aggredire il
 * giocatore. Se trova la porta (sinistra) chiusa, ruba un po' di energia e
 * ritorna alla fase 0 o 1.
 * @author  Marco Buda
 */
public final class Foxy extends AiDescriptorBase {

    private static final int COOLDOWN = 101;
    private static final double STEAL_AMOUNT = 0.02;

    /**
     * Permette di accedere alla fase di attacco attuale di Foxy.
     */
    public final class FoxyAi extends AiBase {

        private final Game game;
        private int phase;

        FoxyAi(final int initialLevel, final Game game) {
            super(Foxy.this, initialLevel, game);
            this.game = game;
        }

        @Override
        public boolean isActive() {
            /*
             * Non può agire se il giocatore sta guardando le telecamere.
             */
            return !this.game.cameras().areActive();
        }

        @Override
        public void act() {
            /*
             * Usa le opportunità di azione per avanzare alla fase
             * successiva. Dopo 3 avanzamenti prova ad attaccare il
             * giocatore. Se trova la porta chiusa ruba un po' di energia e
             * torna alla sua stanza di partenza, tornando alla fase 0 o 1.
             */
            this.phase++;
            if (this.phase == 3) {
                if (this.game.leftDoor().isSwitchedOn()) {
                    this.game.power().subtractEnergy(Foxy.STEAL_AMOUNT);
                    this.phase = this.game.random().nextInt(2);
                } else {
                    this.game.end(new Game.JumpscareEnding(Foxy.this));
                }
            }
        }

        /**
         * @return  la fase di attacco attuale, compresa tra 0 e 2
         */
        public int getPhase() {
            return this.phase;
        }

    }

    /**
     * Costruisce le caratteristiche di Foxy.
     */
    public Foxy() {
        super(Foxy.COOLDOWN, Set.of(3, 4), "foxy");
    }

    @Override
    public String startingRoom() {
        return "1C";
    }

    @Override
    public Ai create(final Game game, final int initialLevel) {
        return new FoxyAi(initialLevel, game);
    }

}
