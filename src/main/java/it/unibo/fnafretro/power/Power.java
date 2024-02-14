package it.unibo.fnafretro.power;

import it.unibo.fnafretro.game.Game;

/** 
 * Power: rappresenta il livello di energia del gioco. 
 * Il giocatore per azionare porte, ventilatore, telecamere utilizza energia.
 * L'energia si ricarica col tempo.
 * @author Davide Sancisi
*/
public interface Power {

    /**
     * La quantità di energia che viene detratta per ogni tacca di utilizzo, una
     * volta ogni secondo.
     */
    double ENERGY_TICK_COST = 0.004;

    /**
     * Quando un dispositivo viene acceso/spento le tacche di 
     * uso aumentano/diminuiscono di 1. 
     * Ogni {@link EventThread#TICKS_PER_SECOND} dall'energia totale viene tolta una porzione calcolata
     * in base a quanti dispositivi ci sono attivi.
     * Una tacca di utilizzo è sempre attiva.
     * Numero massimo di tacche di utilizzo: 5.
     */
    void applyEnergyCost();

    /**
     * Rimuove la quantità di energia specificata dalla riserva.
     * @param   amount  la quantità da sottrarre
     */
    void subtractEnergy(double amount);

    /**
     * Aggiunge una tacca di utilizzo dell'energia.
     */
    void addTick();

    /**
     * Rimuove una tacca di utilizzo dell'energia.
     */
    void removeTick();

    /**
     * @return  il numero di tacche dell'energia attualmente in uso
     */
    int getTicks();

    /**
     * @return  il livello di energia rimasto, compreso nel range [0,1]
     */
    double getEnergyLevel();

    /**
     * Crea un gestore dell'energia per la partita specificata.
     * @param   game    la partita che sta venendo inizializzata
     * @return          un gestore dell'energia
     */
    static Power create(final Game game) {
        return new PowerImpl(game);
    }

}
