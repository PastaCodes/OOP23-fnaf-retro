package it.unibo.fnafretro.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Un'implementazione di una coda di eventi basata su un min-heap.
 * @author  Marco Buda
 */
class EventQueueImpl implements EventQueue {

    private record Event(int tick, Runnable action) { }

    private final Queue<Event> queue = new PriorityQueue<>(
        Comparator.comparingInt(Event::tick)
    );

    @Override
    public Optional<Integer> nextTick() {
        return Optional.ofNullable(this.queue.peek()).map(Event::tick);
    }

    @Override
    public List<Runnable> pullCurrent() {
        final int now = this.queue.peek().tick;
        final List<Runnable> out = new ArrayList<>();
        while (!this.queue.isEmpty() && this.queue.peek().tick == now) {
            out.add(this.queue.poll().action());
        }
        return out;
    }

    @Override
    public void insert(final int tick, final Runnable action) {
        this.queue.add(new Event(tick, action));
    }

}
