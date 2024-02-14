package it.unibo.fnafretro.gui;

import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Una componente personalizzata che viene ridimensionata in base alle
 * dimensioni della finestra.
 * @author  Marco Buda
 */
public abstract class FnafrComponent {

    /**
     * Il percorso all'interno del classpath dove si trovano le risorse.
     */
    public static final String RESOURCE_ROOT = "it/unibo/fnafretro/";

    private Point2D gamePosition;

    /**
     * Carica l'immagine con il nome specificato dalle risorse del classpath.
     * Perché il gioco funzioni correttamente si assume che tutte le risorse
     * vengano trovate e caricate con successo.
     * @param   name    il nome dell'immagine da caricare
     * @return          l'immagine caricata
     */
    public static BufferedImage loadImage(final String name) {
        try {
            return ImageIO.read(ClassLoader.getSystemResource(
                FnafrComponent.RESOURCE_ROOT + name + ".png"
            ));
        } catch (final IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    /**
     * Crea una copia dell'imagine specificata.
     * @param   image   l'immagine da copiare
     * @return          la copia creata
     */
    public static BufferedImage cloneImage(final Image image) {
        final BufferedImage clone = new BufferedImage(
            image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB
        );
        clone.getGraphics().drawImage(image, 0, 0, null);
        return clone;
    }

    /**
     * Crea una componente alla posizione specificata.
     * @param   gamePosition    la posizione (misurata in pixel di gioco)
     */
    public FnafrComponent(final Point2D gamePosition) {
        this.setGamePosition(gamePosition);
    }

    /**
     * Crea una componente con posizione iniziale vuota.
     * Si assume che verrà impostata prima che la componente debba essere
     * disegnata.
     */
    public FnafrComponent() {
        // Non inizializzo la posizione
    }

    /**
     * @return  la posizione di riferimento (misurata in pixel di gioco) della
     *          componente
     */
    public final Point2D getGamePosition() {
        return (Point2D) this.gamePosition.clone();
    }

    /**
     * Imposta la posizione di riferimento (misurata in pixel di gioco) della
     * componente.
     * @param   newPosition la nuova posizione
     */
    public final void setGamePosition(final Point2D newPosition) {
        this.gamePosition = (Point2D) newPosition.clone();
    }

    /**
     * Registra la componente Swing sottostante.
     * @param   register    il ricevente della componente
     */
    public abstract void registerSwingComponent(Consumer<Component> register);

    /**
     * Imposta la posizione nella finestra della componente Swing vera e
     * propria.
     * @param   windowPosition  la posizione (misurata in pixel dello schermo)
     *                          in cui collocare la componente
     */
    protected abstract void setWindowPosition(Point windowPosition);

    /**
     * Aggiorna la componente in base alle informazioni ricavate dalle
     * dimensioni della finestra.
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
        final double gameX = this.gamePosition.getX() - gameOffsetX;
        final double boundedX = Math.max(gameX, 0);
        this.setWindowPosition(new Point(
            (int) (windowOffset.x + boundedX * scale),
            (int) (windowOffset.y + this.gamePosition.getY() * scale)
        ));
    }

    /**
     * Converte un'immagine in un'icona con quell'immagine. Se l'immagine
     * passata è vuota, restituisce un'icona nulla.
     * @param   image   un'immagine che potrebbe non essere presente
     * @return          un'icona contenente l'immagine, oppure {@code null} se
     *                  l'immagine non è presente
     */
    public static Icon optionalImageToNullableIcon(final Optional<Image> image) {
        return image.map(i -> new ImageIcon(i)).orElse(null);
    }

}
