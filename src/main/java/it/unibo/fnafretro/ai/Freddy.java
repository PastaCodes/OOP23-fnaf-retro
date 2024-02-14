package it.unibo.fnafretro.ai;

import java.util.Collections;
import java.util.Map;

import it.unibo.fnafretro.game.Game;

/**
 * Oltre ad aggredire il giocatore quando esaurisce l'energia, Freddy segue un
 * percorso prestabilito e reagisce all'uso delle telecamere, in base alla sua
 * fase di attacco attuale.
 * Nella fase normale (phase=0) Freddy si avvicina al giocatore dal lato destro
 * della mappa, muovendosi solo quando il giocatore non sta guardando le
 * telecamere. Raggiunta la stanza 4B entra in fase di attacco (phase=1). In
 * questa fase cerca di entrare nell'ufficio, ma può farlo solo se il giocatore
 * sta guardando una telecamera che non sia la 4B.
 * Se nel tentativo di entrare trova la porta (destra) chiusa, torna alla stanza
 * 4A e torna alla fase normale.
 * Se invece riesce ad entrare nell'ufficio (phase=2), cerca di aggredire il
 * giocatore, ma solo se non sta guardando le telecamere.
 * @author  Marco Buda
 */
public final class Freddy extends AiDescriptorBase {

    private static final int COOLDOWN = 62;
    private static final Map<String, String> PATH = Map.of(
        "1A", "1B",
        "1B", "4A",
        "4A", "4B"
    );

    /**
     * Costruisce le caratteristiche di Freddy.
     */
    public Freddy() {
        super(Freddy.COOLDOWN, Collections.emptySet(), "freddy");
    }

    @Override
    public Ai create(final Game game, final int initialLevel) {
        return new AiBase(this, initialLevel, game) {

            private int phase;

            @Override
            public boolean isActive() {
                if (this.phase == 1) {
                    /*
                     * Nella fase di attacco può muoversi solo se il giocatore
                     * sta guardando le telecamere, ma non la 4B.
                     */
                    return  game.cameras().areActive()
                    &&      !"4B".equals(game.cameras().getCurrentRoom());
                }
                /*
                 * Nella fase normale, oppure quando si trova nell'ufficio,
                 * non può muoversi se il giocatore sta guardando le telecamere.
                 */
                return !game.cameras().areActive();
            }

            @Override
            public void act() {
                switch (this.phase) {
                    case 0 -> {
                        /*
                         * Nella fase normale segue il percorso prestabilito.
                         */
                        final String current = this.getPosition().getRoomName();
                        final String next = Freddy.PATH.get(current);
                        this.move(game.rooms().getRoom(next));
                        /*
                         * Arrivato nella stanza 4B entra in fase di attacco.
                         */
                        if ("4B".equals(next)) {
                            this.phase = 1;
                        }
                    }
                    case 1 -> {
                        /*
                         * Nella fase di attacco cerca di entrare nell'ufficio.
                         * Se trova la porta chiusa ritorna all'inizio del
                         * corridoio e torna nella fase normale.
                         */
                        if (game.rightDoor().isSwitchedOn()) {
                            this.move(game.rooms().getRoom("4A"));
                            this.phase = 0;
                        } else {
                            this.move(game.rooms().getRoom("YOU"));
                            this.phase = 2;
                        }
                    }
                    case 2 -> {
                        /*
                         * Se si trova dentro l'ufficio attacca il giocatore.
                         */
                        game.end(new Game.JumpscareEnding(Freddy.this));
                    }
                    default -> throw new IllegalStateException();
                }
            }

        };
    }

}
