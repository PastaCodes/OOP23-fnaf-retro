package it.unibo.fnafretro.game;

import java.util.Optional;

/**
 * Un'implementazione del thread di gioco basata su una {@link EventQueue}.
 * Attualmente il loop di gioco non permette di essere messo in pausa.
 * @author  Marco Buda
 */
class EventThreadImpl extends Thread implements EventThread {

    private final EventQueue eventQueue = EventQueue.empty();
    private int tick;

    @Override
    public void schedule(final int delay, final Runnable action) {
        this.eventQueue.insert(this.tick + delay, action);
    }

    @Override
    public void scheduleRepeating(final int period, final Runnable action) {
        this.schedule(period, () -> {
            action.run();
            this.scheduleRepeating(period, action);
        });
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        Optional<Integer> nextTick = this.eventQueue.nextTick();
        while (nextTick.isPresent()) {
            final int delayTicks = nextTick.get() - this.tick;
            long delay = 1000 * delayTicks / EventThread.TICKS_PER_SECOND;
            delay -= System.currentTimeMillis() - time;
            while (delay > 0) {
                time = System.currentTimeMillis();
                try {
                    Thread.sleep(delay);
                    delay = 0;
                } catch (final InterruptedException e) {
                    delay -= System.currentTimeMillis() - time;
                }
            }
            this.tick = nextTick.get();
            time = System.currentTimeMillis();
            this.eventQueue.pullCurrent().forEach(Runnable::run);
            nextTick = this.eventQueue.nextTick();
        }
    }

}
