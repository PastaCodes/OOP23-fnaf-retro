package it.unibo.fnafretro.gui;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Parte grafica del gioco 
 */

public class GameGUI extends JFrame{

    public static final int GAME_WIDTH = 160;
    public static final int GAME_HEIGHT = 90;
    private static final int DEAD_ZONE = 24;
    private static final int FULL_OFFSET = 48;
    private int gameOffsetX = GameGUI.FULL_OFFSET / 2;
    private int scale = 1;

    public GameGUI(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setBounds(
            GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getMaximumWindowBounds()
        );      
        Insets insets = this.getInsets();
        this.setMinimumSize(new Dimension(160+insets.left+insets.right, 90+insets.top+insets.bottom));
        this.setTitle("Five Nights at Freddy's: Retro");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setIconImage(ImageComponent.loadImage("icon"));
        this.setContentPane(new JLayeredPane());
        this.setBackground(Color.BLACK); 
        this.setLayout(null);

        final ImageLabel background = new ImageLabel(new Rectangle(0, 0, 208, 90), "map/main");
        this.add(background.getSwingComponent(), Integer.valueOf(1));

        final ImageButton leftDoorButton = new ImageButton(new Rectangle(3, 39, 6, 6), "door_button_off", "door_button_on");
        this.add(leftDoorButton.getSwingComponent(), Integer.valueOf(2));
        final ImageButton leftLightsButton = new ImageButton(new Rectangle(3, 46, 6, 6), "lights_button_off", "lights_button_on");
        this.add(leftLightsButton.getSwingComponent(), Integer.valueOf(2));

        final ImageButton rightDoorButton = new ImageButton(new Rectangle(199, 39, 6, 6), "door_button_off", "door_button_on");
        this.add(rightDoorButton.getSwingComponent(), Integer.valueOf(2));
        final ImageButton rightLightsButton = new ImageButton(new Rectangle(199, 46, 6, 6), "lights_button_off", "lights_button_on");
        this.add(rightLightsButton.getSwingComponent(), Integer.valueOf(2));

        final Runnable update = () -> {
            final Dimension size = GameGUI.this.getContentPane().getSize();
            final int widthScale = size.width / GameGUI.GAME_WIDTH;
            final int heightScale = size.height / GameGUI.GAME_HEIGHT;
            GameGUI.this.scale = Math.min(widthScale, heightScale);
            final int windowOffsetX = (size.width - GameGUI.GAME_WIDTH * GameGUI.this.scale) / 2;
            final int windowOffsetY = (size.height - GameGUI.GAME_HEIGHT * GameGUI.this.scale) / 2;
            background.update(GameGUI.this.scale, GameGUI.this.gameOffsetX, windowOffsetX, windowOffsetY);
            leftDoorButton.update(GameGUI.this.scale, GameGUI.this.gameOffsetX, windowOffsetX, windowOffsetY);
            rightDoorButton.update(GameGUI.this.scale, GameGUI.this.gameOffsetX, windowOffsetX, windowOffsetY);
            leftLightsButton.update(GameGUI.this.scale, GameGUI.this.gameOffsetX, windowOffsetX, windowOffsetY);
            rightLightsButton.update(GameGUI.this.scale, GameGUI.this.gameOffsetX, windowOffsetX, windowOffsetY);
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
                final int windowOffsetX = (size.width - GameGUI.GAME_WIDTH * GameGUI.this.scale) / 2;
                final int pixelX = (e.getX() - windowOffsetX) / GameGUI.this.scale;
                if (pixelX < GameGUI.DEAD_ZONE + GameGUI.FULL_OFFSET) {
                    final int newOffset = pixelX - GameGUI.DEAD_ZONE;
                    if (newOffset < GameGUI.this.gameOffsetX) {
                        GameGUI.this.gameOffsetX = Math.max(newOffset, 0);
                        update.run();
                    }
                } else if (pixelX > GameGUI.GAME_WIDTH - GameGUI.DEAD_ZONE - GameGUI.FULL_OFFSET) {
                    final int newOffset = pixelX - GameGUI.GAME_WIDTH + GameGUI.DEAD_ZONE + GameGUI.FULL_OFFSET;
                    if (newOffset > GameGUI.this.gameOffsetX) {
                        GameGUI.this.gameOffsetX = Math.min(newOffset, GameGUI.FULL_OFFSET);
                        update.run();
                    }
                }
            }

        });

        this.setVisible(true);
    }

}