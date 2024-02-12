package it.unibo.fnafretro.gui;

import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.function.Function;

import javax.swing.JLabel;

public class ImageLabel extends ImageComponent {

    private final JLabel label = new JLabel();
    private final BufferedImage image;

    public ImageLabel(final Rectangle gameBounds, final String image) {
        super(gameBounds);
        this.image = ImageComponent.loadImage(image);
    }

    @Override
    public void setWindowBounds(final Rectangle windowBounds) {
        this.label.setBounds(windowBounds);
    }

    @Override
    public void updateImages(
        final Function<BufferedImage, Optional<Image>> processor
    ) {
        this.label.setIcon(ImageComponent.imageToNullableIcon(
            processor.apply(this.image)
        ));
    }

    @Override
    public Component getSwingComponent() {
        return this.label;
    }
    
}