package it.unibo.fnafretro.ai;

import java.util.Set;

import it.unibo.fnafretro.game.Game;

/**
 * Rappresenta un'AI prima che inizi una partita.
 * Può essere usato per realizzare un menù di selezione delle AI con la
 * possibilità di impostarne i rispettivi AI level.
 * @author  Marco Buda
 */
public interface AiDescriptor {

    /**
     * Crea un'istanza di questa AI per la partita selezionata, con l'AI level
     * iniziale specificato.
     * @param   game            la partita che sta venendo inizializzata
     * @param   initialLevel    l'AI level iniziale personalizzato
     * @return                  l'istanza creata
     */
    Ai create(Game game, int initialLevel);

    /**
     * Restituisce il tempo di attesa fra le opportunità di azione, come
     * descritto nel comportamento delle {@link Ai}.
     * Questa proprietà non è un metodo dell'interfaccia {@link Ai} perché è
     * costante e slegata dalle singole istanze.
     * @return  il cooldown tra le azioni dell'AI espresso in game tick
     */
    int cooldown();

    /**
     * @return  il nome della stanza di partenza di questa AI
     */
    String startingRoom();

    /**
     * @return  le ore della notte in cui questa AI aumenta di livello
     */
    Set<Integer> levelUpHours();

}
