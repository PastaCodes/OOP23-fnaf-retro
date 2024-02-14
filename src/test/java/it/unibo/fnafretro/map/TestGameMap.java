package it.unibo.fnafretro.map;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author  Filippo Del Bianco
 */
class TestGameMap {

    private static final String MAIN_ROOM = "YOU";

    @Test void test1() {
        final GameMap map = GameMap.create();
        assertEquals("1A", map.getRoom("1A").getRoomName());
        assertEquals("1B", map.getRoom("1B").getRoomName());
        assertEquals("5", map.getRoom("5").getRoomName());
        assertEquals("7", map.getRoom("7").getRoomName());
        assertEquals("3", map.getRoom("3").getRoomName());
        assertEquals(MAIN_ROOM, map.getRoom(MAIN_ROOM).getRoomName());

        assertEquals("both", map.getRoom("1B").getSide());
        assertEquals("left", map.getRoom("3").getSide());
        assertEquals("right", map.getRoom("4A").getSide());
        assertEquals("none", map.getRoom(MAIN_ROOM).getSide());

        assertEquals(List.of(map.getRoom("1B")), map.getAdjacencies(map.getRoom("1C")));
        assertEquals(List.of(map.getRoom("2B"), map.getRoom("3"), map.getRoom("1B")), map.getAdjacencies(map.getRoom("2A")));
        assertEquals(List.of(map.getRoom("2B"), map.getRoom("4B")), map.getAdjacencies(map.getRoom(MAIN_ROOM)));
    }
}
