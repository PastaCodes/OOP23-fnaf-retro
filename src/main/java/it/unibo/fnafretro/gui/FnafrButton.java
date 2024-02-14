package it.unibo.fnafretro.gui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

/**
 * Un bottone che utilizza due immagini a seconda dello stato attuale.
 * Può essere impostato per funzionare come un toggle.
 * @author  Luca Ponseggi
 */
public final class FnafrButton extends FnafrImageComponent {

    private final AbstractButton button;
    private final BufferedImage imageOff;
    private final BufferedImage imageOn;
    private final boolean toggleable;

    /**
     * Costruisce un bottone con tutte le informazioni specificate.
     * @param gameBounds    i limiti in cui posizionare il bottone
     * @param imageOff      l'immagine da disegnare quando il bottone è spento
     * @param imageOn       l'immagine da disegnare quando il bottone è acceso
     * @param toggleable    se impostato a {@code true} comporta che premere il
     *                      bottone faccia alternare il suo stato
     */
    public FnafrButton(
        final Rectangle gameBounds,
        final BufferedImage imageOff,
        final BufferedImage imageOn,
        final boolean toggleable
    ) {
        super(gameBounds);
        this.imageOff = FnafrComponent.cloneImage(imageOff);
        this.imageOn = FnafrComponent.cloneImage(imageOn);
        this.toggleable = toggleable;
        this.button = toggleable ? new JToggleButton() : new JButton();
        this.button.setBorderPainted(false);
        this.button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Costruisce un bottone con comportamento classico.
     * @param gameBounds    i limiti in cui posizionare il bottone
     * @param imageOff      l'immagine da disegnare quando il bottone è spento
     * @param imageOn       l'immagine da disegnare quando il bottone è acceso
     */
    public FnafrButton(
        final Rectangle gameBounds,
        final BufferedImage imageOff,
        final BufferedImage imageOn
    ) {
        this(gameBounds, imageOff, imageOn, false);
    }

    /**
     * Costruisce un bottone caricando le immagini con i nomi specificati.
     * @param gameBounds    i limiti in cui posizionare il bottone
     * @param imageOffName  l'immagine da disegnare quando il bottone è spento
     * @param imageOnName   l'immagine da disegnare quando il bottone è acceso
     */
    public FnafrButton(
        final Rectangle gameBounds,
        final String imageOffName,
        final String imageOnName
    ) {
        this(
            gameBounds,
            FnafrComponent.loadImage(imageOffName),
            FnafrComponent.loadImage(imageOnName)
        );
    }

    /**
     * Imposta l'azione che viene eseguita quando il bottone viene attivato.
     * @param   action  l'azione da eseguire
     */
    public void setAction(final Runnable action) {
        if (this.toggleable) {
            this.button.addActionListener(e -> action.run());
        } else {
            this.button.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(final MouseEvent e) {
                    action.run();
                }

                @Override
                public void mouseReleased(final MouseEvent e) {
                    action.run();
                }

            });
        }
    }

    /**
     * Reimposta lo stato del bottone a deselezionato.
     */
    public void reset() {
        this.button.setSelected(false);
    }

    @Override
    protected void processImages(final Function<BufferedImage, Optional<Image>> processor) {
        this.button.setIcon(FnafrComponent.optionalImageToNullableIcon(
            processor.apply(this.imageOff)
        ));
        final Icon onIcon = FnafrComponent.optionalImageToNullableIcon(
            processor.apply(this.imageOn)
        );
        if (this.toggleable) {
            this.button.setSelectedIcon(onIcon);
        } else {
            this.button.setPressedIcon(onIcon);
        }
    }

    @Override
    public void registerSwingComponent(final Consumer<Component> register) {
        register.accept(this.button);
    }

    @Override
    protected void setWindowPosition(final Point windowPosition) {
        this.button.setLocation(windowPosition);
    }

    @Override
    protected void setWindowSize(final Dimension windowSize) {
        this.button.setSize(windowSize);
    }

}
