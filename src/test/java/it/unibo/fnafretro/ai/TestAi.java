package it.unibo.fnafretro.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import it.unibo.fnafretro.game.Game;

/**
 * @author  Marco Buda
 */
class TestAi {

    private static final int ITERATIONS = 120;

    private Ai ai;

    @BeforeEach void init() {
        final AiDescriptor fauxDescriptor = new AiDescriptorBase(0, Set.of()) {

            @Override
            public Ai create(final Game game, final int initialLevel) {
                return new AiBase(this, initialLevel, game) {

                    @Override
                    public void act() {
                        this.move(game.rooms().getRoom("1B"));
                    }

                };
            }

        };
        final Game fauxGame = Game.create(Set.of(fauxDescriptor), any -> 0);
        this.ai = fauxGame.ais().stream().findFirst().get();
    }

    @Test void testCreate() {
        assertEquals("1A", this.ai.getPosition().getRoomName());
        assertEquals(0, this.ai.getLevel());
    }

    @Test void testLevel() {
        this.ai.increaseLevel();
        assertEquals(1, this.ai.getLevel());
        this.ai.increaseLevel();
        this.ai.increaseLevel();
        assertEquals(3, this.ai.getLevel());
        for (int i = 0; i < ITERATIONS; i++) {
            this.ai.increaseLevel();
        }
        assertEquals(3 + ITERATIONS, this.ai.getLevel());
    }

    @Test void testMove() {
        this.ai.act();
        assertEquals("1B", this.ai.getPosition().getRoomName());
    }

}
