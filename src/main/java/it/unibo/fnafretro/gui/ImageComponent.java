package it.unibo.fnafretro.gui;

import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public abstract class ImageComponent {

    public static BufferedImage loadImage(final String name) {
        try {
            return ImageIO.read(ClassLoader.getSystemResource(
                "it/unibo/fnafretro/" + name + ".png"
            ));
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Icon imageToNullableIcon(final Optional<Image> image) {
        return image.map(i -> new ImageIcon(i)).orElse(null);
    }

    private final Rectangle gameBounds;

    public ImageComponent(final Rectangle gameBounds) {
        this.gameBounds = gameBounds;
    }

    public abstract void setWindowBounds(final Rectangle windowBounds);

    public abstract void updateImages(
        final Function<BufferedImage, Optional<Image>> processor
    );

    public void update(
        final int scale,
        final int gameOffsetX,
        final int windowOffsetX,
        final int windowOffsetY
    ) {
        final int gameX = this.gameBounds.x - gameOffsetX;
        final int boundedX = Math.max(gameX, 0);
        final int gameWidth = Math.max(this.gameBounds.width - boundedX + gameX, 0);
        final int boundedWidth = Math.min(gameWidth, GameGUI.GAME_WIDTH - boundedX);
        this.setWindowBounds(new Rectangle(
            windowOffsetX + boundedX * scale,
            windowOffsetY + this.gameBounds.y * scale,
            boundedWidth * scale,
            this.gameBounds.height * scale
        ));
        if (boundedWidth <= 0) {
            this.updateImages(image -> Optional.empty());
        } else {
            this.updateImages(image -> Optional.of(image.getSubimage(
                boundedX - gameX, 0,
                boundedWidth, this.gameBounds.height
            ).getScaledInstance(
                boundedWidth * scale,
                this.gameBounds.height * scale,
                Image.SCALE_FAST
            )));
        }
    }

    public abstract Component getSwingComponent();

}
