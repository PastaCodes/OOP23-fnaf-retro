package it.unibo.fnafretro.power;

import it.unibo.fnafretro.game.*;

/** 
 * Power: rappresenta il livello di energia del gioco. 
 * Il giocatore per azionare porte, ventilatore, telecamere utilizza energia.
 * L'energia si ricarica col tempo.
 * @author Davide Sancisi
*/

interface Power {

    /**
     * Quando un dispositivo viene acceso/spento le tacche di 
     * uso aumentano/diminuiscono di 1. 
     * Ogni {@link EventThread#TICKS_PER_SECOND} dall'energia totale viene tolta una porzione calcolata
     * in base a quanti dispositivi ci sono attivi.
     * Una tacca di utilizzo è sempre attiva.
     * Numero massimo di tacche di utilizzo: 5.
     */
    void applyEnergyCost();

    void init(Game game);

    void addTick();

    void removeTick();

    int getTicks();

    double getEnergyLevel();

    public static Power create() {
        return new PowerImpl();
    }
    
}


