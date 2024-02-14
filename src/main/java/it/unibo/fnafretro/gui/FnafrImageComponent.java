package it.unibo.fnafretro.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.function.Function;

/**
 * Astrazione di una componente personalizzata che utilizza una o più immagini.
 * @author  Marco Buda
 */
public abstract class FnafrImageComponent extends FnafrComponent {

    private Dimension gameSize;

    /**
     * Crea una componente con i limiti specificati.
     * @param   gameBounds  la posizione e dimensione (misurate in pixel di
     *                      gioco)
     */
    public FnafrImageComponent(final Rectangle gameBounds) {
        super(gameBounds.getLocation());
        this.gameSize = gameBounds.getSize();
    }

    /**
     * Crea una componente senza specificarne i limit. Si assume che vengano
     * impostati prima che venga disegnata.
     */
    public FnafrImageComponent() {
        super();
    }

    /**
     * Imposta le dimensioni (misurate in pixel di gioco) per questa componente.
     * @param   gameSize    le nuove dimensioni
     */
    public void setGameSize(final Dimension gameSize) {
        this.gameSize = new Dimension(gameSize);
    }

    /**
     * @return  il fattore di scala per questa componente
     */
    protected int getScale() {
        return 1;
    }

    /**
     * Segnala di processare le immagini di questa componente tramite
     * l'operatore fornito. Si tratta di una funzione che ritaglia e
     * ridimensiona correttamente le immagini.
     * @param   processor   la funzione per trasformare le immagini
     */
    protected abstract void processImages(
        Function<BufferedImage, Optional<Image>> processor
    );

    /**
     * Imposta la dimensione della componente Swing vera e propria.
     * @param   windowSize  la dimensione (misurata in pixel dello schermo)
     */
    protected abstract void setWindowSize(Dimension windowSize);

    @Override
    public final void update(
        final Point windowOffset,
        final int scale,
        final int gameOffsetX
    ) {
        final Point gamePosition = (Point) this.getGamePosition();
        gamePosition.x -= gameOffsetX;
        final int boundedX = Math.max(gamePosition.x, 0);
        final int boundedY = Math.max(gamePosition.y, 0);
        final int gameWidth = Math.max(this.gameSize.width * this.getScale() - boundedX + gamePosition.x, 0);
        final int gameHeight = Math.max(this.gameSize.height * this.getScale() - boundedY + gamePosition.y, 0);
        final int boundedWidth = Math.min(gameWidth, FnafrWindow.GAME_WIDTH - boundedX);
        final int boundedHeight = Math.min(gameHeight, FnafrWindow.GAME_HEIGHT - boundedY);
        this.setWindowPosition(new Point(
            windowOffset.x + boundedX * scale,
            windowOffset.y + boundedY * scale
        ));
        this.setWindowSize(new Dimension(
            boundedWidth * scale,
            boundedHeight * scale
        ));
        if (boundedWidth <= 0 || boundedHeight <= 0) {
            this.processImages(image -> Optional.empty());
        } else {
            this.processImages(image -> {
                final BufferedImage scaled = FnafrComponent.cloneImage(
                    image.getScaledInstance(
                        this.gameSize.width * this.getScale(),
                        this.gameSize.height * this.getScale(),
                        Image.SCALE_FAST
                    )
                );
                final BufferedImage cropped = scaled.getSubimage(
                    boundedX - gamePosition.x,
                    boundedY - gamePosition.y,
                    boundedWidth,
                    boundedHeight
                );
                return Optional.of(cropped.getScaledInstance(
                    boundedWidth * scale,
                    boundedHeight * scale,
                    Image.SCALE_FAST
                ));
            });
        }
    }

}
