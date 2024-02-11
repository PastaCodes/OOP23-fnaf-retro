package it.unibo.fnafretro.map;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

public class TestGameMap {

    private GameMap map;

    @Test void test1() {

        map = new GameMapImpl();
        assertEquals("1A", map.getRoom("1A").getRoomName());
        assertEquals("1B", map.getRoom("1B").getRoomName());
        assertEquals("5", map.getRoom("5").getRoomName());
        assertEquals("7", map.getRoom("7").getRoomName());
        assertEquals("3", map.getRoom("3").getRoomName());
        assertEquals("YOU", map.getRoom("YOU").getRoomName());

        assertEquals("both", map.getRoom("1B").getSide());
        assertEquals("left", map.getRoom("3").getSide());
        assertEquals("right", map.getRoom("4A").getSide());
        assertEquals("none", map.getRoom("YOU").getSide());

        assertEquals(List.of(map.getRoom("1B")), map.getAdjacencies(map.getRoom("1C")));
        assertEquals(List.of(map.getRoom("2B"), map.getRoom("3"), map.getRoom("1B")), map.getAdjacencies(map.getRoom("2A")));
        assertEquals(List.of(map.getRoom("2B"), map.getRoom("4B")), map.getAdjacencies(map.getRoom("YOU")));
    }
}
