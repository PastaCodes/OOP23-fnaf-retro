package it.unibo.fnafretro.ai;

import it.unibo.fnafretro.game.Game;
import it.unibo.fnafretro.map.Room;

/**
 * Implementazione semplice ed espandibile di una AI.
 * @author  Marco Buda
 */
abstract class AiBase implements Ai {

    private final AiDescriptor descriptor;
    private int level;
    private Room position;

    AiBase(
        final AiDescriptor descriptor,
        final int initialLevel,
        final Game game
    ) {
        this.descriptor = descriptor;
        this.level = initialLevel;
        this.position = game.rooms().getRoom(descriptor.startingRoom());
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public Room getPosition() {
        return this.position;
    }

    @Override
    public void increaseLevel() {
        this.level++;
    }

    @Override
    public AiDescriptor descriptor() {
        return this.descriptor;
    }

    /**
     * Sposta l'AI in una certa stanza.
     * @param   newPosition la stanza specificata
     */
    protected void move(final Room newPosition) {
        this.position = newPosition;
    }

}
