package it.unibo.fnafretro.game;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.random.RandomGenerator;

import it.unibo.fnafretro.ai.Ai;
import it.unibo.fnafretro.ai.AiDescriptor;
import it.unibo.fnafretro.device.Door;
import it.unibo.fnafretro.device.Lights;
import it.unibo.fnafretro.map.Cameras;
import it.unibo.fnafretro.map.GameMap;
import it.unibo.fnafretro.night.Night;
import it.unibo.fnafretro.power.Power;

/**
 * Rappresenta una partita in corso.
 * @author  Marco Buda
 */
public interface Game {

    /**
     * Indica che il giocatore è sopravvissuto fino alle 6.
     */
    Ending VICTORY = new Ending();

    /**
     * Indica il modo in cui si è conclusa una partita.
     */
    class Ending {

        private Ending() { }

    }

    /**
     * Indica che il giocatore è stato aggredito da un mostro.
     */
    final class JumpscareEnding extends Ending {

        private final AiDescriptor attacker;

        /**
         * Crea un finale per la partita in cui il giocatore viene aggredito dal
         * mostro specificato.
         * @param   attacker    il mostro che ha aggredito il giocatore
         */
        public JumpscareEnding(final AiDescriptor attacker) {
            this.attacker = attacker;
        }

        /**
         * @return  il mostro che ha aggredito il giocatore
         */
        public AiDescriptor getAttacker() {
            return this.attacker;
        }

    }

    /**
     * Crea una partita secondo le configurazioni specificate.
     * @param   aiSet           le AI da inizializzare in questa partita
     * @param   levels          una funzione che determini l'AI level iniziale
     *                          per le singole AI
     * @param   updateSignal    l'azione invocata per avvisare le interfacce
     *                          grafiche che c'è stato un cambiamento
     * @param   endSignal       l'azione invocata per avvisare le interfacce
     *                          grafiche cha la partita è terminata
     * @return                  la partita creata
     */
    static Game create(
        final Set<AiDescriptor> aiSet,
        final Function<AiDescriptor, Integer> levels,
        final Runnable updateSignal,
        final Consumer<Game.Ending> endSignal
    ) {
        return new GameImpl(aiSet, levels, updateSignal, endSignal);
    }

    /**
     * @return  il gestore di eventi di questa partita
     */
    EventThread events();

    /**
     * @return  il generatore casuale usato in questa partita
     */
    RandomGenerator random();

    /**
     * @return  le stanze nella mappa di questa partita
     */
    GameMap rooms();

    /**
     * @return  le telecamere presenti nella mappa di gioco
     */
    Cameras cameras();

    /**
     * @return  le AI all'interno di questa partita
     */
    Set<Ai> ais();

    /**
     * @return  l'energia all'interno di questa partita
     */
    Power power();

    /**
     * @return  la porta sinistra controllata dal giocatore
     */
    Door leftDoor();

    /**
     * @return  la porta destra controllata dal giocatore
     */
    Door rightDoor();

    /**
     * @return  le luci controllate dal giocatore
     */
    Lights lights();

    /**
     * @return  l'oggetto che gestisce la progressione della notte
     */
    Night nightProgression();

    /**
     * Ordina alla partita di terminare.
     * Si assume che venga chiamato dal thread di gioco.
     * @param   ending  indica il motivo per cui è terminata la partita; può
     *                  essere {@link Game#VICTORY} se il giocatore è
     *                  sopravvissuto fino alle 6, oppure una
     *                  {@link JumpscareEnding} se è stato aggredito da un
     *                  mostro
     */
    void end(Ending ending);

    /**
     * Informa le interfacce grafiche che c'è stato un cambiamento.
     */
    void update();

}
