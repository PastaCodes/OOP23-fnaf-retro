package it.unibo.fnafretro.game;

/**
 * Rappresenta il ciclo che esegue gli eventi del gioco.
 * @author  Marco Buda
 */
public interface EventThread {

    /**
     * Il numero di game tick eseguiti ogni secondo.
     */
    int TICKS_PER_SECOND = 20;

    /**
     * Crea un'istanza pronta ad essere utilizzata.
     * @return  l'istanza creata
     */
    static EventThread create() {
        return new EventThreadImpl();
    }

    /**
     * Pianifica l'esecuzione di un'azione fra un numero specificato di game
     * tick.
     * IMPORTANTE: Si assume che questo metodo venga chiamato esclusivamente da
     * codice eseguito sul thread di gioco, oppure dal codice che si occupa di
     * creare la partita, ma prima che questa venga avviata.
     * @param   delay   il numero di game tick da attendere prima che l'azione
     *                  venga eseguita
     * @param   action  l'azione che si desidera pianificare
     */
    void schedule(int delay, Runnable action);

    /**
     * Pianifica l'esecuzione periodica di un'azione, con il periodo in game
     * tick specificato.
     * IMPORTANTE: Si assume che questo metodo venga chiamato esclusivamente da
     * codice eseguito sul thread di gioco, oppure dal codice che si occupa di
     * creare la partita, ma prima che questa venga avviata.
     * @param   period  il numero di game tick da attendere prima della prima
     *                  esecuzione dell'azione specificata, nonché tra una
     *                  esecuzione e la successiva
     * @param   action  l'azione che si desidera pianificare
     */
    void scheduleRepeating(int period, Runnable action);

    /**
     * Avvia il thread di gioco.
     */
    void start();

}
