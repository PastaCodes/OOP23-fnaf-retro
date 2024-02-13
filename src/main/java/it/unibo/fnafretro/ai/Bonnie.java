package it.unibo.fnafretro.ai;

import java.util.List;
import java.util.Set;

import it.unibo.fnafretro.game.Game;
import it.unibo.fnafretro.map.Room;

/**
 * Bonnie ha un movimento simile a quello di {@link Chica}, ma è in grado di
 * teletrasportarsi anche a stanze non adiacenti. Inoltre preferisce aggirarsi
 * nel lato sinistro della mappa.
 * @author  Marco Buda
 */
public final class Bonnie extends AiDescriptorBase {

    private static final int COOLDOWN = 98;

    Bonnie() {
        super(Bonnie.COOLDOWN, Set.of(2, 3, 4));
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
                        if (game.leftDoor().isSwitchedOn()) {
                            this.move(game.rooms().getRoom("1B"));
                        } else {
                            game.end(new Game.JumpscareEnding(Bonnie.this));
                        }
                    }
                    /*
                     * Se è subito fuori dalla porta, si affaccia alla porta.
                     */
                    case "2B" -> {
                        this.move(game.rooms().getRoom("YOU"));
                    }
                    /*
                     * Normalmente si teletrasporta ad una qualsiasi stanza nel
                     * lato sinistro della mappa.
                     */
                    default -> {
                        final List<Room> rooms = game.rooms()
                            .getAllRooms().stream()
                            .filter(room ->
                                !this.getPosition().getRoomName()
                                    .equals(room.getRoomName())
                            &&  (
                                    "left".equals(room.getSide())
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
