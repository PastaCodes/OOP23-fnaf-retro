package it.unibo.fnafretro.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JLabel;

/**
 * Un'immagine personalizzata che può essere modificata e scalata.
 * @author  Davide Sancisi
 */
public final class FnafrImage extends FnafrImageComponent {

    private final JLabel label = new JLabel();
    private BufferedImage baseImage;
    private int scale = 1;

    /**
     * Crea un'immagine con i limiti specificati e il contenuto specificato.
     * @param   gameBounds  la posizione e dimensione (misurate in pixel di
     *                      gioco) di questa immagine
     * @param   image       il contenuto dell'immagine
     */
    public FnafrImage(final Rectangle gameBounds, final BufferedImage image) {
        super(gameBounds);
        this.setImage(image);
    }

    /**
     * Crea un'immagine con i limiti specificati e caricando la risorsa con il
     * nome specificato.
     * @param   gameBounds  la posizione e dimensione (misurate in pixel di
     *                      gioco) di questa immagine
     * @param   imageName   il nome della risorsa da caricare
     */
    public FnafrImage(final Rectangle gameBounds, final String imageName) {
        this(gameBounds, FnafrComponent.loadImage(imageName));
    }

    /**
     * Crea un immagine senza inizializzarne né la posizione né il contenuto.
     * Si assume che vengano impostati prima che questa venga disegnata.
     */
    public FnafrImage() {
        super();
    }

    /**
     * Imposta l'immagine da mostrare.
     * @param   newImage    la nuova immagine
     */
    public void setImage(final BufferedImage newImage) {
        this.baseImage = FnafrComponent.cloneImage(newImage);
    }

    /**
     * Imposta la visibilità dell'immagine.
     * @param   visible se impostato a {@code true} mostra l'immagine,
     *                  altrimenti la nasconde
     */
    public void setVisible(final boolean visible) {
        this.label.setVisible(visible);
    }

    /**
     * Inverte lo stato attuale di visibilità dell'immagine.
     */
    public void toggleVisible() {
        this.label.setVisible(!this.label.isVisible());
    }

    /**
     * Imposta il fattore di scala da utilizzare per disegnare questa immagine.
     * @param   scale   il fattore di scala
     */
    public void setScale(final int scale) {
        this.scale = scale;
    }

    @Override
    protected void processImages(final Function<BufferedImage, Optional<Image>> processor) {
        this.label.setIcon(FnafrComponent.optionalImageToNullableIcon(
            processor.apply(this.baseImage)
        ));
    }

    @Override
    public void registerSwingComponent(final Consumer<Component> register) {
        register.accept(this.label);
    }

    @Override
    protected void setWindowPosition(final Point windowPosition) {
        this.label.setLocation(windowPosition);
    }

    @Override
    protected void setWindowSize(final Dimension windowSize) {
        this.label.setSize(windowSize);
    }

    @Override
    protected int getScale() {
        return this.scale;
    }

}