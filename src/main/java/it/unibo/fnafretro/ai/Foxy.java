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

    Foxy() {
        super(Foxy.COOLDOWN, Set.of(3, 4));
    }

    @Override
    public String startingRoom() {
        return "1C";
    }

    @Override
    public Ai create(final Game game, final int initialLevel) {
        return new AiBase(this, initialLevel, game) {

            private int phase;

            @Override
            public boolean isActive() {
                /*
                 * Non può agire se il giocatore sta guardando le telecamere.
                 */
                return !game.rooms().getCameras().getStatus();
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
                    if (game.leftDoor().isSwitchedOn()) {
                        // TODO ruba un po' di energia
                        this.phase = game.random().nextInt(2);
                    } else {
                        game.end(new Game.JumpscareEnding(Foxy.this));
                    }
                }
            }

        };
    }

}
