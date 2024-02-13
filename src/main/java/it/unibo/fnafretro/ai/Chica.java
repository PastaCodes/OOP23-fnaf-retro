package it.unibo.fnafretro.ai;

import java.util.List;
import java.util.Set;

import it.unibo.fnafretro.game.Game;
import it.unibo.fnafretro.map.Room;

/**
 * Chica si muove casualmente sul lato destro della mappa, spostandosi fra
 * stanze adiacenti. Se arriva alla stanza 4B (subito fuori dall'ufficio) si
 * affaccia alla porta e prova ad attaccare. Se nel farlo trova la porta
 * (destra) chiusa, ritorna alla stanza centrale (1B).
 * @author  Marco Buda
 */
public final class Chica extends AiDescriptorBase {

    private static final int COOLDOWN = 99;

    Chica() {
        super(Chica.COOLDOWN, Set.of(3, 4));
    }

    @Override
    public Ai create(final Game game, final int initialLevel) {
        return new AiBase(this, initialLevel, game) {

            @Override
            public void act() {
                switch (this.getPosition().getRoomName()) {
                    /*
                     * Se è affacciata alla porta tenta di entrare e se trova la
                     * porta chiusa torna alla sala, altrimenti attacca il
                     * giocatore.
                     */
                    case "YOU" -> {
                        if (game.rightDoor().isSwitchedOn()) {
                            this.move(game.rooms().getRoom("1B"));
                        } else {
                            game.end(new Game.JumpscareEnding(Chica.this));
                        }
                    }
                    /*
                     * Se è subito fuori dalla porta, si affaccia alla porta.
                     */
                    case "4B" -> {
                        this.move(game.rooms().getRoom("YOU"));
                    }
                    /*
                     * Normalmente si sposta ad una stanza adiacente a quella
                     * dove si trova attualmente, restando nel lato destro della
                     * mappa.
                     */
                    default -> {
                        final List<Room> rooms = game.rooms()
                            .getAdjacencies(this.getPosition()).stream()
                            .filter(room ->
                                !this.getPosition().getRoomName()
                                    .equals(room.getRoomName())
                            &&  (
                                    "right".equals(room.getSide())
                                ||  "both".equals(room.getSide())
                                )
                            )
                            .toList();
                        final int index = game.random().nextInt(rooms.size());
                        this.move(rooms.get(index));
                    }
                }
            }

        };
    }

}
