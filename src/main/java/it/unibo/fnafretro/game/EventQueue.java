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
     * Tutti gli eventi associati al game tick attuale vengono rimossi dalla
     * coda e restituiti.
     * Si assume che questo metodo venga chiamato quando il game tick attuale
     * coincide con quello dell'evento più imminente nella coda.
     * Si assume anche che la coda non sia vuota.
     * @return  una lista contenente gli eventi attuali
     */
    List<Runnable> pullCurrent();

    /**
     * Inserisce un evento nella coda, associandogli un certo game tick.
     * @param   tick    il game tick in cui dovrà essere eseguito l'evento
     * @param   action  l'evento da inserire
     */
    void insert(int tick, Runnable action);

}
