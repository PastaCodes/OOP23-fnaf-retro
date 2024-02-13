package it.unibo.fnafretro.gui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.function.Function;
import javax.swing.JButton;

/**
 * 
 */
public final class ImageButton extends ImageComponent {

    private final JButton button = new JButton();
    private final BufferedImage imageOff;
    private final BufferedImage imageOn;

    {
        this.button.setBorderPainted(false);
        this.button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public ImageButton(
        final Rectangle gameBounds,
        final String imageOff,
        final String imageOn
    ) {
        super(gameBounds);
        this.imageOff = ImageComponent.loadImage(imageOff);
        this.imageOn = ImageComponent.loadImage(imageOn);
    }

    @Override
    public void setWindowBounds(final Rectangle windowBounds) {
        this.button.setBounds(windowBounds);
    }

    @Override
    public void updateImages(
        final Function<BufferedImage, Optional<Image>> processor
    ) {
        this.button.setIcon(ImageComponent.imageToNullableIcon(
            processor.apply(this.imageOff)
        ));
        this.button.setPressedIcon(ImageComponent.imageToNullableIcon(
            processor.apply(this.imageOn)
        ));
    }

    @Override
    public Component getSwingComponent() {
        return this.button;
    }

}
