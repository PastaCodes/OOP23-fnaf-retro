package it.unibo.fnafretro.gui;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Parte grafica del gioco 
 */

public class GameGUI extends JFrame{

    int size = 1;
    private static final int WIDTH = 160;
    private static final int HEIGHT = 90;
    private static final int DEAD_ZONE = 24;
    private static final int FULL_OFFSET = 48;
    private int offset = GameGUI.FULL_OFFSET / 2;

    private static BufferedImage loadImage(final String name) {
        try {
            return ImageIO.read(ClassLoader.getSystemResource(
                "it/unibo/fnafretro/" + name + ".png"
            ));
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public GameGUI() throws IOException{
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(2080*size, 976*size); //Originale: 208x90
        this.setTitle("Five Nights at Freddy's: Retro");

        
        this.setIconImage(loadImage("icon"));

        this.getContentPane().setBackground(Color.BLACK); 

        final BufferedImage img = loadImage("map/main");

        final JLabel background = new JLabel();
        background.setHorizontalAlignment(JLabel.CENTER);
        background.setVerticalAlignment(JLabel.CENTER);
        this.add(background, BorderLayout.CENTER);

        final JButton b = new JButton("franco");
        b.setBounds(400, 400, 200, 200);
        this.add(b);
        
        final Runnable update = () -> {
            final Dimension size = GameGUI.this.getContentPane().getSize();
            final int widthScale = size.width / GameGUI.WIDTH;
            final int heightScale = size.height / GameGUI.HEIGHT;
            final int scale = Math.min(widthScale, heightScale);
            final Image cropped = img.getSubimage(
                GameGUI.this.offset, 0, GameGUI.WIDTH, GameGUI.HEIGHT
            );
            final Image scaled = cropped.getScaledInstance(
                GameGUI.WIDTH * scale,
                GameGUI.HEIGHT * scale,
                Image.SCALE_FAST
            );
            background.setIcon(new ImageIcon(scaled));
        };

        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(final ComponentEvent e) {
                update.run();
            }

        });

        this.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(final MouseEvent e) {
                final Dimension size = GameGUI.this.getContentPane().getSize();
                final int imgWidth = background.getIcon().getIconWidth();
                final int offsetX = (int) (size.getWidth() - imgWidth) / 2;
                final int scale = imgWidth / GameGUI.WIDTH;
                final int pixelX = (e.getX() - offsetX) / scale;
                if (pixelX < GameGUI.DEAD_ZONE + GameGUI.FULL_OFFSET) {
                    final int newOffset = pixelX - GameGUI.DEAD_ZONE;
                    if (newOffset < GameGUI.this.offset) {
                        GameGUI.this.offset = Math.max(newOffset, 0);
                        update.run();
                    }
                } else if (pixelX > GameGUI.WIDTH - GameGUI.DEAD_ZONE - GameGUI.FULL_OFFSET) {
                    final int newOffset = pixelX - GameGUI.WIDTH + GameGUI.DEAD_ZONE + GameGUI.FULL_OFFSET;
                    if (newOffset > GameGUI.this.offset) {
                        GameGUI.this.offset = Math.min(newOffset, GameGUI.FULL_OFFSET);
                        update.run();
                    }
                }
            }

        });


        this.add(background);
        this.setVisible(true);
    }

}
