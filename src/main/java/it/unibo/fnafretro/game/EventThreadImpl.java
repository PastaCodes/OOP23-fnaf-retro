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

    {
        this.setDaemon(true);
    }

    @Override
    public void schedule(final int delay, final Runnable action) {
        /*
         * Tecnicamente la sincronizzazione non sarebbe necessaria, dato che si
         * assume che questo metodo venga chiamato esclusivamente da eventi sul
         * thread di gioco, che sono già sincronizzati.
         */
        synchronized (this) {
            this.eventQueue.insert(this.tick + delay, action);
        }
    }

    @Override
    public void scheduleRepeating(final int period, final Runnable action) {
        this.schedule(period, () -> {
            action.run();
            this.scheduleRepeating(period, action);
        });
    }

    @Override
    public void scheduleSignal(final Runnable action) {
        synchronized (this) {
            this.eventQueue.insert(-1, action);
        }
        this.interrupt();
    }

    private static long time() {
        return System.currentTimeMillis();
    }

    private static long tickToTime(final int tick, final long baseTime) {
        return 1000 * tick / TICKS_PER_SECOND + baseTime;
    }

    private static int timeToTick(final long time, final long baseTime) {
        return (int) Math.floorDiv((time - baseTime) * TICKS_PER_SECOND, 1000);
    }

    @Override
    public void run() {
        final long time0 = time();
        Optional<Integer> nextTick = this.eventQueue.nextTick();
        while (nextTick.isPresent()) {
            while (true) {
                final long delay = tickToTime(nextTick.get(), time0) - time();
                try {
                    if (delay > 0) {
                        Thread.sleep(delay);
                    }
                    break;
                } catch (final InterruptedException e) {
                    synchronized (this) {
                        this.tick = timeToTick(time(), time0);
                        nextTick = Optional.of(this.tick + 1);
                    }
                }
            }
            synchronized (this) {
                this.tick = nextTick.get();
                this.eventQueue.pullBefore(this.tick).forEach(Runnable::run);
                nextTick = this.eventQueue.nextTick();
            }
        }
    }

}
