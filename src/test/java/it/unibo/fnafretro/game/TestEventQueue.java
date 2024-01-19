package it.unibo.fnafretro.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;

class TestEventQueue {

    private EventQueue eq;

    @BeforeEach void init() {
        this.eq = EventQueue.empty();
    }

    private static Runnable nop() {
        return () -> { };
    }

    @Test void test1() {
        final Runnable a = nop(), b = nop(), c = nop(), d = nop();
        eq.insert(2, a);
        eq.insert(1, b);
        eq.insert(4, c);
        eq.insert(3, d);
        assertEquals(Optional.of(1), eq.nextTick());
        assertEquals(List.of(b), eq.pullCurrent());
        assertEquals(Optional.of(2), eq.nextTick());
        assertEquals(List.of(a), eq.pullCurrent());
        assertEquals(Optional.of(3), eq.nextTick());
        assertEquals(List.of(d), eq.pullCurrent());
        assertEquals(Optional.of(4), eq.nextTick());
        assertEquals(List.of(c), eq.pullCurrent());
        assertEquals(Optional.empty(), eq.nextTick());
    }

    @Test void test2() {
        final Runnable a = nop(), b = nop(), c = nop();
        eq.insert(2, a);
        eq.insert(2, b);
        eq.insert(2, c);
        assertEquals(Optional.of(2), eq.nextTick());
        assertEquals(List.of(a, b, c), eq.pullCurrent());
        assertEquals(Optional.empty(), eq.nextTick());
    }

    @Test void test3() {
        final Runnable a = nop(), b = nop(), c = nop(),
            d = nop(), e = nop(), f = nop();
        eq.insert(2, a);
        eq.insert(3, b);
        eq.insert(1, c);
        eq.insert(2, d);
        eq.insert(4, e);
        eq.insert(3, f);
        assertEquals(Optional.of(1), eq.nextTick());
        assertEquals(List.of(c), eq.pullCurrent());
        assertEquals(Optional.of(2), eq.nextTick());
        assertEquals(List.of(a, d), eq.pullCurrent());
        assertEquals(Optional.of(3), eq.nextTick());
        assertEquals(List.of(b, f), eq.pullCurrent());
        assertEquals(Optional.of(4), eq.nextTick());
        assertEquals(List.of(e), eq.pullCurrent());
        assertEquals(Optional.empty(), eq.nextTick());
    }

}
