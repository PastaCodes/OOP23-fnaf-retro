package it.unibo.fnafretro.map;

/**
 * rappresenta le telecamere all'interno del gioco.
 * grazie ad esse all' interno del gioco si potrÃ  visualizzare una sola stanza per volta
 * e potranno essere attive/disattive oppure attive ma temporaneamente disabilitate
 * @author Filippo Del Bianco
 */
public interface Cameras {

    /**
     * @return lo stato delle telecamere(spente o accese)
     */
    boolean areActive();

    /**
     * imposta lo stato delle telecamere(spente o accese).
     * @param active lo stato che passiamo in input
     */
    void setActive(boolean active);

    /**
     * @return se le telecamere sono nello stato greyscreen ossia temporaneamente disabilitate
     */
    boolean areDisturbed();

    /**
     * per disabilitare o riabilitare temporaneamente le telecamere.
     * @param disturbed lo stato greyscreen passato come input
     */
    void setDisturbed(boolean disturbed);

    /**
     * @return la stanza che stanno visualizzando le telecamere
     * 
     */
    String getCurrentRoom();

    /**
     * per impostare la stanza da far visualizare dalle telecamere.
     * @param newRoom la stanza da far visualizzare alle telecamere
     */
    void setCurrentRoom(String newRoom);

    /**
     * @return  un'istanza delle telecamere nella mappa di gioco
     */
    static Cameras create() {
        return new CamerasImpl("1A");
    }
}
