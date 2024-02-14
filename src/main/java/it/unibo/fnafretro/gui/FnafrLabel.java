package it.unibo.fnafretro.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * Un'etichetta personalizzata che utilizza un font retro.
 * @author  Davide Sancisi
 */
public final class FnafrLabel extends FnafrComponent {

    /**
     * L'allineamento orizzontale di un'etichetta.
     */
    public enum Alignment {
        /**
         * Allineamento a sinistra.
         */
        LEFT,
        /**
         * Allineamento al centro.
         */
        CENTER,
        /**
         * Allineamento a destra.
         */
        RIGHT
    }

    private static final float FONT_SCALE = 4.0f;
    private static final Font BASE_FONT;
    static {
        try {
            BASE_FONT = Font.createFont(
                Font.TRUETYPE_FONT,
                ClassLoader.getSystemResourceAsStream(
                    FnafrComponent.RESOURCE_ROOT + "Retro Gaming.ttf"
                )
            );
        } catch (final IOException | FontFormatException e) {
            throw new IllegalStateException(e);
        }

        UIManager.put("Label.disabledForeground", Color.WHITE);
    }

    private final JLabel label = new JLabel();
    private Alignment alignment = Alignment.LEFT;

    /**
     * Crea un'etichetta alla posizione specificata.
     * @param   gamePosition    la posizione iniziale (espressa in pixel di
     *                          gioco)
     */
    public FnafrLabel(final Point2D gamePosition) {
        super(gamePosition);
        this.label.setEnabled(false);
    }

    /**
     * Crea un'etichetta con il testo specificato e la posizione specificata.
     * @param   text            il testo iniziale
     * @param   gamePosition    la posizione iniziale (espressa in pixel di
     *                          gioco)
     */
    public FnafrLabel(final String text, final Point2D gamePosition) {
        this(gamePosition);
        this.setText(text);
    }

    /**
     * Specifica l'allineamento orizzontale per questa etichetta.
     * @param   alignment   l'allineamento orizzontale
     */
    public void setAlignment(final Alignment alignment) {
        this.alignment = alignment;
    }

    /**
     * Modifica il testo da visualizzare in questa etichetta.
     * @param   newText il nuovo testo
     */
    public void setText(final String newText) {
        this.label.setText(newText);
    }

    @Override
    public void registerSwingComponent(final Consumer<Component> register) {
        register.accept(this.label);
    }

    @Override
    protected void setWindowPosition(final Point windowPosition) {
        windowPosition.translate(0, -(int) this.label.getSize().getHeight() / 2);
        if (this.alignment == Alignment.CENTER) {
            windowPosition.translate(-(int) this.label.getSize().getWidth() / 2, 0);
        } else if (this.alignment == Alignment.RIGHT) {
            windowPosition.translate(-(int) this.label.getSize().getWidth(), 0);
        }
        this.label.setLocation(windowPosition);
    }

    @Override
    public void update(
        final Point windowOffset,
        final int scale,
        final int gameOffsetX
    ) {
        final Font scaledFont = BASE_FONT.deriveFont(FONT_SCALE * scale);
        this.label.setFont(scaledFont);
        final FontMetrics fontMetrics = this.label.getFontMetrics(scaledFont);
        this.label.setSize(
            fontMetrics.stringWidth(this.label.getText()),
            fontMetrics.getHeight()
        );
        super.update(windowOffset, scale, gameOffsetX);
    }

}
