package it.unibo.fnafretro.game;

import java.util.List;
import java.util.Optional;

/**
 * Rappresenta una coda in cui è possibile memorizzare degli eventi,
 * associandoli al game tick in cui si vuole che vengano eseguiti.
 * Questa struttura non si occupa effettivamente di eseguire gli eventi, ma
 * fornisce metodi utili per richiederli in ordine di imminenza.
 * @author  Marco Buda
 */
interface EventQueue {

    /**
     * Crea una coda vuota pronta ad essere utilizzata.
     * @return  la coda creata
     */
    static EventQueue empty() {
        return new EventQueueImpl();
    }

    /**
     * Restituisce il game tick associato all'evento più imminente, a meno che
     * la coda non sia vuota.
     * @return  il game tick dell'evento più imminente, se esiste
     */
    Optional<Integer> nextTick();

    /**
     * Rimuove dalla coda e restituisce tutti gli eventi associati a un game
     * tick precedente o uguale a quello specificato.
     * @param   tick    il game tick di riferimento
     * @return          una lista contenente gli eventi precedenti al game tick
     *                  di riferimento, possibilmente vuota
     */
    List<Runnable> pullBefore(int tick);

    /**
     * Inserisce un evento nella coda, associandogli un certo game tick.
     * @param   tick    il game tick in cui dovrà essere eseguito l'evento
     * @param   action  l'evento da inserire
     */
    void insert(int tick, Runnable action);

}
