package it.unibo.fnafretro.ai;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import it.unibo.fnafretro.game.Game;
import it.unibo.fnafretro.map.Room;

/**
 * Rappresenta un'AI nemica all'interno di una partita in corso.
 * Ogni AI si comporta nello stesso modo: periodicamente le viene data
 * un'opportunità di agire (la durata dell'attesa fra un'opportunità e la
 * prossima è specificata da {@link AiDescriptor#cooldown()}). A quel punto l'AI
 * estrae un numero, il quale, confrontato all'AI level attuale, determinerà se
 * l'opportunità va a buon fine e l'AI può effettivamente agire.
 * L'AI level è un numero compreso tra 0 e 20 (estremi inclusi) che influenza
 * l'aggressività di una singola AI. Maggiore è questo numero, maggiore sarà la
 * difficoltà per il giocatore nel contrastare l'AI.
 * Un AI level di 0 indica un'AI disattivata.
 * Alcune AI possono agire solo in determinate condizioni, verificate dal
 * metodo {@link #isActive()}. Se l'AI non è attiva, tutte le opportunità
 * falliranno automaticamente.
 * Il termine "agire" è appositamente generico, poiché AI diverse possono
 * sfruttare questa occasione per fare cose diverse, come ad esempio muoversi,
 * avanzare di stadio, o altro.
 * @author  Marco Buda
 */
public interface Ai {

    /**
     * L'AI level massimo. Comporta che ogni estrazione abbia successo.
     */
    int MAX_LEVEL = 20;

    /**
     * Registra gli eventi periodici necessari per realizzare il comportamento
     * delle AI all'interno della partita specificata.
     * @param   aiSet   le AI selezionate per questa partita
     * @param   levels  una funzione che specifichi un AI level iniziale per
     *                  ogni AI
     * @param   game    la partita da inizializzare
     * @return          le instanze delle AI all'interno della partita
     */
    static Set<Ai> initAis(
        final Set<AiDescriptor> aiSet,
        final Function<AiDescriptor, Integer> levels,
        final Game game
    ) {
        return aiSet.stream().map(aiDescr -> {
            final Ai ai = aiDescr.create(game, levels.apply(aiDescr));
            game.events().scheduleRepeating(
                aiDescr.cooldown(),
                () -> {
                    if (
                        ai.isActive()
                    &&  game.random().nextInt(Ai.MAX_LEVEL) < ai.getLevel()
                    ) {
                        ai.act();
                    }
                }
            );
            return ai;
        }).collect(Collectors.toSet());
    }

    /**
     * Aumenta l'AI level di 1 punto.
     */
    void increaseLevel();

    /**
     * @return  l'AI level attuale
     */
    int getLevel();

    /**
     * @return  la stanza in cui si trova attualmente l'AI
     */
    Room getPosition();

    /**
     * Determina se l'AI è attualmente attiva e può agire.
     * @return  {@code true} se è attiva, {@code false} altrimenti
     */
    boolean isActive();

    /**
     * Segnala all'AI di agire, tipicamente muovendosi.
     */
    void act();

    /**
     * Fornisce le informazioni del personaggio istanziato da questa AI.
     * @return  l'{@link AiDescriptor} di questa AI
     */
    AiDescriptor descriptor();

}
