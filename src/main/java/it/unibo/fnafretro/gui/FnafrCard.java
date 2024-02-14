package it.unibo.fnafretro.gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

/**
 * Rappresenta una scheda della finestra di gioco.
 * @author  Luca Ponseggi
 */
public final class FnafrCard {

    private final JLayeredPane panel = new JLayeredPane();
    private final String name;
    private final Map<FnafrComponent, Boolean> components = new HashMap<>();

    /**
     * Crea una scheda con il nome specificato, all'interno di una finestra.
     * @param   name    il nome per questa scheda
     * @param   window  la finestra di gioco
     */
    public FnafrCard(final String name, final JFrame window) {
        this.name = name;
        window.getContentPane().add(this.panel, name);
        this.panel.setBackground(Color.BLACK);
    }

    /**
     * @return  il nome associato a questa scheda
     */
    public String getCardName() {
        return this.name;
    }

    /**
     * Aggiorna la scheda insieme a tutte le componenti figlie.
     * @param windowOffset  l'offset (misurato in pixel dello schermo) tra
     *                      l'angolo in alto a sinistra della finestra e
     *                      l'angolo in alto a sinistra della schermata di gioco
     * @param scale         il fattore di scala dai pixel di gioco ai pixel
     *                      dello schermo
     * @param gameOffsetX   lo scostamento orizzontale causato dalla "rotazione"
     *                      della visuale
     */
    public void update(
        final Point windowOffset,
        final int scale,
        final int gameOffsetX
    ) {
        for (final var entry : this.components.entrySet()) {
            entry.getKey().update(
                windowOffset,
                scale,
                entry.getValue() ? gameOffsetX : 0
            );
        }
    }

    /**
     * Aggiunge una componente alla scheda, attribuendogli un livello.
     * @param component         la componente da aggiungere
     * @param zOrder            il livello su cui disegnarla
     * @param followsGameOffset determina se questa componente deve spostarsi
     *                          seguendo la "rotazione" della visuale
     */
    public void add(
        final FnafrComponent component,
        final int zOrder,
        final boolean followsGameOffset
    ) {
        component.registerSwingComponent(swingComponent ->
            this.panel.add(swingComponent, Integer.valueOf(zOrder))
        );
        this.components.put(component, followsGameOffset);
    }

    /**
     * Aggiunge una componente fissa alla scheda, attribuendogli un livello.
     * @param component         la componente da aggiungere
     * @param zOrder            il livello su cui disegnarla
     */
    public void add(final FnafrComponent component, final int zOrder) {
        this.add(component, zOrder, false);
    }

    /**
     * Registra un'azione che reagisca al movimento orizzontale del mouse.
     * @param   action  l'azione da registrare
     */
    public void onMouseMoved(final Consumer<Integer> action) {
        this.panel.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(final MouseEvent e) {
                action.accept(e.getX());
            }

        });
    }

}
