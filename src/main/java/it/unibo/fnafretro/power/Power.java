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
     * Quando un dispositivo viene acceso/spento le tacche di 
     * uso aumentano/diminuiscono di 1. 
     * Ogni {@link EventThread#TICKS_PER_SECOND} dall'energia totale viene tolta una porzione calcolata
     * in base a quanti dispositivi ci sono attivi.
     * Una tacca di utilizzo è sempre attiva.
     * Numero massimo di tacche di utilizzo: 5.
     */
    void applyEnergyCost();

    /**
     * aggiunge la schedulazione dell'evento applyEnergyCost al gioco.
     * @param game la classe che gestisce la schedulazione degli eventi del gioco.
     */
    void init(Game game);

    /**
     * aggiunge un tick di energia in utilizzo.
     */
    void addTick();

    /**
     * toglie un tick di energia in utilizzo.
     */
    void removeTick();

    /**
     * @return il numero di tick di energia attivi al momento.
     */
    int getTicks();

    /**
     * Sottrae una quantità di energia dall'energia totale.
     * @param amount la quantità di energia da sottrarre all'energia totale.
     */
    void subtractEnergy(double amount); 

    /**
     * @return il livello di energia attuale.
     */
    double getEnergyLevel();

    /**
     * Funzione statica usata nei Test.
     * @return una nuova istanza di PowerImpl.
     */
    static Power create() {
        return new PowerImpl();
    }

}
