package it.unibo.fnafretro.map;

/**
 * rappresenta le telecamere all'interno del gioco.
 * grazie ad esse all' interno del gioco si potrà visualizzare una sola stanza per volta
 * e potranno essere attive/disattive oppure attive ma temporaneamente disabilitate
 * @author Filippo Del Bianco
 */
public interface Cameras {

    /**
     * @return lo stato delle telecamere(spente o accese)
     */
    boolean getStatus();

    /**
     * imposta lo stato delle telecamere(spente o accese).
     * @param status lo stato che passiamo in input
     */
    void setStatus(boolean status);

    /**
     * @return se le telecamere sono nello stato greyscreen ossia temporaneamente disabilitate
     */
    boolean getGreyScreen();

    /**
     * per disabilitare o riabilitare temporaneamente le telecamere.
     * @param greyScreen lo stato greyscreen passato come input
     */
    void setGreyScreen(boolean greyScreen);

    /**
     * @return la stanza che stanno visualizzando le telecamere
     * 
     */
    Room getCurrentRoom();

    /**
     * per impostare la stanza da far visualizare dalle telecamere.
     * @param newRoom la stanza da far visualizzare alle telecamere
     */
    void setCurrentRoom(Room newRoom);
}
