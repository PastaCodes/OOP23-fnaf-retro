package it.unibo.fnafretro.power;

/** 
 * Power: rappresenta il livello di energia del gioco. 
 * Il giocatore per azionare porte, ventilatore, telecamere utilizza energia.
 * L'energia si ricarica col tempo.
 * @author Davide Sancisi
*/

interface Power {

    /**
     * @param deviceSwitched quando un oggetto viene acceso/spento i "tick" di 
     * usage aumentano/diminuiscono di 1. In base a quanti "tick" di usage sono 
     * ogni 200 tick del gioco l'energia scala in base a quanti tick di usage sono
     * attivi. Un tick di usage è sempre attivo. I tick di usage possono essere al
     * massimo 5.
     */
    void usageTicks();

    void init();

    void addTick();

    void removeTick();
    
}


