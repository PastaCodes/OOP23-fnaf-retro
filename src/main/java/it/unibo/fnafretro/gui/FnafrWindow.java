package it.unibo.fnafretro.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import it.unibo.fnafretro.ai.Ai;
import it.unibo.fnafretro.ai.AiDescriptor;
import it.unibo.fnafretro.ai.Bonnie;
import it.unibo.fnafretro.ai.Chica;
import it.unibo.fnafretro.ai.Foxy;
import it.unibo.fnafretro.ai.Freddy;
import it.unibo.fnafretro.game.Game;
import it.unibo.fnafretro.map.Room;

/*
 * Questa implementazione fa largo uso di magic number, ma questi non fanno
 * altro che rispecchiare le informazioni contenute nelle immagini del gioco.
 */
// CHECKSTYLE: MagicNumber OFF

/**
 * Rappresenta la schermata di gioco del nostro progetto.
 * @author  Marco Buda
 * @author  Filippo Del Bianco
 */
public final class FnafrWindow {

    /**
     * La larghezza della schermata di gioco, espressa in pixel di gioco.
     */
    public static final int GAME_WIDTH = 160;
    /**
     * L'altezza della schermata di gioco, espressa in pixel di gioco.
     */
    public static final int GAME_HEIGHT = 90;
    private static final int DEAD_ZONE = 24;
    private static final int FULL_OFFSET = 48;

    private static final AiDescriptor FREDDY = new Freddy();
    private static final AiDescriptor BONNIE = new Bonnie();
    private static final AiDescriptor CHICA = new Chica();
    private static final AiDescriptor FOXY = new Foxy();

    private static final Map<String, Point> CAMERA_POS = Map.ofEntries(
        Map.entry("1A", new Point(114, 45)),
        Map.entry("1B", new Point(124, 50)),
        Map.entry("1C", new Point(99, 58)),
        Map.entry("2A", new Point(100, 69)),
        Map.entry("2B", new Point(110, 66)),
        Map.entry("3", new Point(89, 69)),
        Map.entry("4A", new Point(121, 66)),
        Map.entry("4B", new Point(131, 69)),
        Map.entry("5", new Point(89, 49)),
        Map.entry("6", new Point(147, 61)),
        Map.entry("7", new Point(139, 51))
    );

    private static final Map<String, String> FULL_NAMES = Map.ofEntries(
        Map.entry("1A", "Palco"),
        Map.entry("1B", "Sala"),
        Map.entry("1C", "Baia del pirata"),
        Map.entry("2A", "Corridoio ovest"),
        Map.entry("2B", "Angolo ovest"),
        Map.entry("3", "Ripostiglio"),
        Map.entry("4A", "Corridoio est"),
        Map.entry("4B", "Angolo est"),
        Map.entry("5", "Retroscena"),
        Map.entry("6", "Cucina"),
        Map.entry("7", "Bagni")
    );

    private final JFrame frame = new JFrame("Five Nights at Freddy's Retro");

    private final CardLayout cards = new CardLayout();
    private final FnafrCard menuCard, mainCard, camerasCard, endCard;
    private FnafrCard currentCard;

    private Point windowOffset;
    private int gameOffsetX = FnafrWindow.FULL_OFFSET / 2;
    private int scale;

    private FnafrImage fan;
    private Timer fanTimer;
    private FnafrImage noise1, noise2;
    private Timer noiseTimer;
    private FnafrImage cameras1, cameras2;
    private Timer camerasTimer;

    private Game game;

    private FnafrButton leftDoorButton;
    private FnafrButton rightDoorButton;
    private FnafrImage leftDoor;
    private FnafrImage rightDoor;
    private FnafrImage leftLight;
    private FnafrImage rightLight;

    private FnafrImage roomBackground;
    private FnafrImage roomOverlay;
    private FnafrLabel roomName;

    private final Map<AiDescriptor, FnafrImage> aiLayers = new HashMap<>();

    private FnafrImage bonniePeaking;
    private FnafrImage chicaPeaking;

    private FnafrLabel six;
    private FnafrLabel endText;
    private FnafrImage jumpscare;

    private final Map<FnafrCard, FnafrLabel> time = new HashMap<>();
    private final Map<FnafrCard, FnafrLabel> power = new HashMap<>();
    private final Map<FnafrCard, FnafrImage[]> usage = new HashMap<>();

    private final Map<String, BufferedImage> roomBackgrounds = new HashMap<>();
    private final Map<String, BufferedImage> roomOverlays = new HashMap<>();

    /**
     * Istanzia una finestra di gioco.
     */
    public FnafrWindow() {
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack(); // Genera gli insets
        this.frame.setBounds(
            GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getMaximumWindowBounds()
        );
        final Insets insets = this.frame.getInsets();
        this.frame.setMinimumSize(new Dimension(
            FnafrWindow.GAME_WIDTH + insets.left + insets.right,
            FnafrWindow.GAME_HEIGHT + insets.top + insets.bottom
        ));
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setIconImage(FnafrComponent.loadImage("icon"));
        this.frame.getContentPane().setBackground(Color.BLACK);
        this.frame.getContentPane().setLayout(this.cards);

        this.menuCard = new FnafrCard("MENU", this.frame);
        this.initMenuCard();
        this.mainCard = new FnafrCard("MAIN", this.frame);
        this.camerasCard = new FnafrCard("CAMERAS", this.frame);
        this.initMainCard();
        this.initCamerasCard();
        this.endCard = new FnafrCard("END", this.frame);
        this.initEndCard();

        this.setCard(this.menuCard);

        this.frame.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(final ComponentEvent e) {
                final Dimension size = FnafrWindow.this.frame.getContentPane().getSize();
                final int widthScale = size.width / FnafrWindow.GAME_WIDTH;
                final int heightScale = size.height / FnafrWindow.GAME_HEIGHT;
                FnafrWindow.this.scale = Math.min(widthScale, heightScale);
                FnafrWindow.this.windowOffset = new Point(
                    (size.width - FnafrWindow.GAME_WIDTH * FnafrWindow.this.scale) / 2,
                    (size.height - FnafrWindow.GAME_HEIGHT * FnafrWindow.this.scale) / 2
                );
                FnafrWindow.this.update();
            }

        });

        this.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "quit");
        this.frame.getRootPane().getActionMap().put("quit", new AbstractAction() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                if (FnafrWindow.this.game != null) {
                    FnafrWindow.this.game.events().scheduleSignal(() -> {
                        FnafrWindow.this.game.events().abort();
                        FnafrWindow.this.game = null;
                    });
                }
                FnafrWindow.this.setCard(FnafrWindow.this.menuCard);
            }

        });

        this.frame.setVisible(true);
    }

    private void update() {
        if (this.currentCard.equals(this.mainCard)) {
            this.leftDoor.setVisible(this.game.leftDoor().isSwitchedOn());
            this.rightDoor.setVisible(this.game.rightDoor().isSwitchedOn());
            this.leftLight.setVisible(this.game.lights().isLeftLightOn());
            this.rightLight.setVisible(this.game.lights().isRightLightOn());

            for (final Ai ai : this.game.ais()) {
                if (ai.descriptor() == BONNIE) {
                    this.bonniePeaking.setVisible(
                        "YOU".equals(ai.getPosition().getRoomName())
                    &&  this.game.lights().isLeftLightOn()
                    );
                } else if (ai.descriptor() == CHICA) {
                    this.chicaPeaking.setVisible(
                        "YOU".equals(ai.getPosition().getRoomName())
                    &&  this.game.lights().isRightLightOn()
                    );
                }
            }
        } else if (this.currentCard.equals(this.camerasCard)) {
            final String currentRoom = this.game.cameras().getCurrentRoom();
            this.roomName.setText(FULL_NAMES.get(currentRoom));
            if ("6".equals(currentRoom) || this.game.cameras().areDisturbed()) {
                this.roomBackground.setVisible(false);
                this.roomOverlay.setVisible(false);
                this.aiLayers.values().forEach(layer -> layer.setVisible(false));
            } else {
                this.roomBackground.setVisible(true);
                this.roomBackground.setImage(this.roomBackgrounds.get(currentRoom));
                if (this.roomOverlays.containsKey(currentRoom)) {
                    this.roomOverlay.setImage(this.roomOverlays.get(currentRoom));
                    this.roomOverlay.setVisible(true);
                } else {
                    this.roomOverlay.setVisible(false);
                }
                for (final Ai ai : this.game.ais()) {
                    if (ai.getPosition().getRoomName().equals(currentRoom)) {
                        this.aiLayers.get(ai.descriptor()).setScale(AiPositions.getScale(ai, currentRoom));
                        this.aiLayers.get(ai.descriptor()).setGamePosition(AiPositions.getPosition(ai, currentRoom));
                        this.aiLayers.get(ai.descriptor()).setVisible(true);
                    } else {
                        this.aiLayers.get(ai.descriptor()).setVisible(false);
                    }
                }
            }
        }
        if (this.currentCard.equals(this.mainCard) || this.currentCard.equals(this.camerasCard)) {
            int hour = this.game.nightProgression().getHour();
            if (hour == 0) {
                hour = 12;
            }
            this.time.get(this.currentCard).setText(hour + " AM");
            final int power = (int) (this.game.power().getEnergyLevel() * 100 + 0.5);
            this.power.get(this.currentCard).setText("Energia: " + power + "%");
            final int usageTicks = this.game.power().getTicks();
            for (int i = 0; i < 5; i++) {
                this.usage.get(this.currentCard)[i].setVisible(usageTicks > i);
            }
        }
        this.currentCard.update(
            this.windowOffset,
            this.scale,
            this.gameOffsetX
        );
    }

    private void setCard(final FnafrCard card) {
        this.cards.show(this.frame.getContentPane(), card.getCardName());
        this.currentCard = card;
        if (this.scale != 0) {
            this.update();
        }
        if (card.equals(this.mainCard)) {
            this.fanTimer.start();
        } else {
            this.fanTimer.stop();
        }
        if (card.equals(this.camerasCard)) {
            this.noiseTimer.start();
            this.camerasTimer.start();
        } else {
            this.noiseTimer.stop();
            this.camerasTimer.stop();
        }
    }

    private void initMenuCard() {
        final BufferedImage boxImage = FnafrComponent.loadImage("menu/box");
        final BufferedImage decreaseImage = FnafrComponent.loadImage("menu/decrease_button");
        final BufferedImage increaseImage = FnafrComponent.loadImage("menu/increase_button");
        final Map<AiDescriptor, Integer> levels = new HashMap<>();
        final List<AiDescriptor> ais = List.of(FREDDY, BONNIE, CHICA, FOXY);
        for (int i = 0; i < ais.size(); i++) {
            final AiDescriptor ai = ais.get(i);
            levels.put(ai, 0);
            final FnafrImage box = new FnafrImage(new Rectangle(16 + 33 * i, 16, 29, 37), boxImage);
            this.menuCard.add(box, 1);
            final FnafrLabel level = new FnafrLabel("0", new Point2D.Float(30.5f + 33 * i, 48.5f));
            level.setAlignment(FnafrLabel.Alignment.CENTER);
            this.menuCard.add(level, 2);
            final FnafrButton decrease = new FnafrButton(
                new Rectangle(16 + 33 * i, 44, 9, 9),
                decreaseImage, decreaseImage,
                true
            );
            this.menuCard.add(decrease, 2);
            decrease.setAction(() -> {
                final int decreased = Math.max(levels.get(ai) - 1, 0);
                levels.put(ai, decreased);
                level.setText(String.valueOf(decreased));
                this.update();
            });
            final FnafrButton increase = new FnafrButton(
                new Rectangle(36 + 33 * i, 44, 9, 9),
                increaseImage, increaseImage,
                true
            );
            this.menuCard.add(increase, 2);
            increase.setAction(() -> {
                final int increased = Math.min(levels.get(ai) + 1, 20);
                levels.put(ai, increased);
                level.setText(String.valueOf(increased));
                this.update();
            });
            final FnafrImage face = new FnafrImage(new Rectangle(18 + 33 * i, 11, 25, 30), "menu/" +  ai.resourceName());
            this.menuCard.add(face, 2);
        }
        final BufferedImage startImage = FnafrComponent.loadImage("menu/start_button");
        final FnafrButton startButton = new FnafrButton(new Rectangle(57, 65, 46, 13), startImage, startImage, true);
        this.menuCard.add(startButton, 1);
        final FnafrLabel startLabel = new FnafrLabel("Gioca", new Point2D.Float(80f, 71.5f));
        startLabel.setAlignment(FnafrLabel.Alignment.CENTER);
        this.menuCard.add(startLabel, 2);
        startButton.setAction(() -> {
            this.game = Game.create(new HashSet<>(ais), ai -> levels.get(ai), this::update, this::end);
            for (final Room room : this.game.rooms().getAllRooms()) {
                try {
                    this.roomBackgrounds.put(room.getRoomName(), FnafrComponent.loadImage("map/" + room.getRoomName()));
                    this.roomOverlays.put(room.getRoomName(), FnafrComponent.loadImage("map/" + room.getRoomName() + "_overlay"));
                } catch (final IllegalArgumentException e) {
                    continue;
                }
            }
            this.leftDoorButton.reset();
            this.rightDoorButton.reset();
            this.game.events().start();
            this.setCard(this.mainCard);
        });
    }

    private void initOverlay(final FnafrCard card, final FnafrCard target) {
        final BufferedImage camerasButtonImage = FnafrComponent.loadImage("cameras_button");
        final FnafrButton camerasButton = new FnafrButton(
            new Rectangle(56, 82, 48, 6),
            camerasButtonImage, camerasButtonImage,
            true
        );
        card.add(camerasButton, 7);
        camerasButton.setAction(() -> {
            this.setCard(target);
            if (target.equals(this.camerasCard)) {
                this.game.events().scheduleSignal(() -> {
                    this.game.cameras().setActive(true);
                    this.game.cameras().setDisturbed(false);
                    this.game.power().addTick();
                });
            } else {
                this.game.events().scheduleSignal(() -> {
                    this.game.cameras().setActive(false);
                    this.game.power().removeTick();
                });
            }
        });

        final FnafrLabel time = new FnafrLabel("12 AM", new Point2D.Float(154, 7));
        time.setAlignment(FnafrLabel.Alignment.RIGHT);
        card.add(time, 6);
        this.time.put(card, time);

        final FnafrLabel power = new FnafrLabel("Energia: 99%", new Point2D.Float(6f, 76.5f));
        card.add(power, 6);
        this.power.put(card, power);

        card.add(new FnafrLabel("Consumo:", new Point2D.Float(6f, 82.5f)), 6);

        final BufferedImage usageLow = FnafrComponent.loadImage("usage_low");
        final BufferedImage usageMedium = FnafrComponent.loadImage("usage_medium");
        final BufferedImage usageHigh = FnafrComponent.loadImage("usage_high");
        final FnafrImage[] powerTicks = new FnafrImage[5];
        for (int i = 0; i < 5; i++) {
            powerTicks[i] = new FnafrImage(new Rectangle(31 + 4 * i, 80, 3, 5), switch (i) {
                case 0, 1 -> usageLow;
                case 2 -> usageMedium;
                case 3, 4 -> usageHigh;
                default -> throw new IllegalStateException();
            });
            card.add(powerTicks[i], 6);
        }
        this.usage.put(card, powerTicks);
    }

    private void initMainCard() {
        this.mainCard.add(new FnafrImage(new Rectangle(0, 0, 208, 90), "map/main"), 1, true);

        this.fan = new FnafrImage(new Rectangle(99, 35, 17, 21), "fan");
        this.mainCard.add(this.fan, 2, true);
        this.fanTimer = new Timer(150, e -> {
            this.fan.toggleVisible();
        });

        this.leftLight = new FnafrImage(new Rectangle(0, 17, 51, 73), "light_left");
        this.leftLight.setVisible(false);
        this.mainCard.add(this.leftLight, 2, true);
        this.rightLight = new FnafrImage(new Rectangle(157, 17, 51, 73), "light_right");
        this.rightLight.setVisible(false);
        this.mainCard.add(this.rightLight, 2, true);

        this.bonniePeaking = new FnafrImage(new Rectangle(27, 9, 29, 63), "ai/bonnie");
        this.mainCard.add(this.bonniePeaking, 3, true);
        this.bonniePeaking.setVisible(false);
        this.chicaPeaking = new FnafrImage(new Rectangle(152, 9, 29, 63), "ai/chica");
        this.mainCard.add(this.chicaPeaking, 3, true);
        this.chicaPeaking.setVisible(false);

        this.mainCard.add(new FnafrImage(new Rectangle(0, 0, 208, 90), "map/main_overlay"), 4, true);

        final BufferedImage doorImage = FnafrComponent.loadImage("door");
        this.leftDoor = new FnafrImage(new Rectangle(12, 16, 32, 62), doorImage);
        this.leftDoor.setVisible(false);
        this.mainCard.add(this.leftDoor, 4, true);
        this.rightDoor = new FnafrImage(new Rectangle(164, 16, 32, 62), doorImage);
        this.rightDoor.setVisible(false);
        this.mainCard.add(this.rightDoor, 4, true);

        final BufferedImage lightButtonOff = FnafrComponent.loadImage("lights_button_off");
        final BufferedImage lightButtonOn = FnafrComponent.loadImage("lights_button_on");
        final FnafrButton lightButtonLeft = new FnafrButton(new Rectangle(3, 46, 6, 6), lightButtonOff, lightButtonOn);
        this.mainCard.add(lightButtonLeft, 5, true);
        lightButtonLeft.setAction(() -> {
            if (this.game.lights().isLeftLightOn()) {
                this.game.events().scheduleSignal(() -> {
                    this.game.lights().switchOffLight();
                });
            } else {
                this.game.events().scheduleSignal(() -> {
                    this.game.lights().switchOnLeftLight();
                });
            }
        });
        final FnafrButton lightButtonRight = new FnafrButton(new Rectangle(199, 46, 6, 6), lightButtonOff, lightButtonOn);
        this.mainCard.add(lightButtonRight, 5, true);
        lightButtonRight.setAction(() -> {
            if (this.game.lights().isRightLightOn()) {
                this.game.events().scheduleSignal(() -> {
                    this.game.lights().switchOffLight();
                });
            } else {
                this.game.events().scheduleSignal(() -> {
                    this.game.lights().switchOnRightLight();
                });
            }
        });

        final BufferedImage doorButtonOff = FnafrComponent.loadImage("door_button_off");
        final BufferedImage doorButtonOn = FnafrComponent.loadImage("door_button_on");
        this.leftDoorButton = new FnafrButton(new Rectangle(3, 39, 6, 6), doorButtonOff, doorButtonOn, true);
        this.mainCard.add(this.leftDoorButton, 5, true);
        this.leftDoorButton.setAction(() -> {
            if (this.game.leftDoor().isSwitchedOn()) {
                this.game.events().scheduleSignal(() -> {
                    this.game.leftDoor().switchOff();
                });
            } else {
                this.game.events().scheduleSignal(() -> {
                    this.game.leftDoor().switchOn();
                });
            }
        });
        this.rightDoorButton = new FnafrButton(new Rectangle(199, 39, 6, 6), doorButtonOff, doorButtonOn, true);
        this.mainCard.add(this.rightDoorButton, 5, true);
        this.rightDoorButton.setAction(() -> {
            if (this.game.rightDoor().isSwitchedOn()) {
                this.game.events().scheduleSignal(() -> {
                    this.game.rightDoor().switchOff();
                });
            } else {
                this.game.events().scheduleSignal(() -> {
                    this.game.rightDoor().switchOn();
                });
            }
        });

        this.initOverlay(this.mainCard, this.camerasCard);

        this.mainCard.onMouseMoved(x -> {
            final int pixelX = (x - this.windowOffset.x) / this.scale;
            if (pixelX < FnafrWindow.DEAD_ZONE + FnafrWindow.FULL_OFFSET) {
                final int newOffset = pixelX - FnafrWindow.DEAD_ZONE;
                if (newOffset < this.gameOffsetX) {
                    this.gameOffsetX = Math.max(newOffset, 0);
                    this.update();
                }
            } else if (pixelX > FnafrWindow.GAME_WIDTH - FnafrWindow.DEAD_ZONE - FnafrWindow.FULL_OFFSET) {
                final int newOffset = pixelX - FnafrWindow.GAME_WIDTH + FnafrWindow.DEAD_ZONE + FnafrWindow.FULL_OFFSET;
                if (newOffset > this.gameOffsetX) {
                    this.gameOffsetX = Math.min(newOffset, FnafrWindow.FULL_OFFSET);
                    this.update();
                }
            }
        });
    }

    private void initCamerasCard() {
        this.roomBackground = new FnafrImage(new Rectangle(0, 0, 160, 90), "map/1A");
        this.camerasCard.add(this.roomBackground, 1);
        this.roomOverlay = new FnafrImage(new Rectangle(0, 0, 160, 90), "map/1A_overlay");
        this.camerasCard.add(this.roomOverlay, 4);

        final FnafrImage freddy = new FnafrImage(new Rectangle(0, 0, 29, 63), "ai/freddy");
        this.camerasCard.add(freddy, 3);
        freddy.setVisible(false);
        this.aiLayers.put(FREDDY, freddy);
        final FnafrImage bonnie = new FnafrImage(new Rectangle(0, 0, 29, 63), "ai/bonnie");
        this.camerasCard.add(bonnie, 2);
        bonnie.setVisible(false);
        this.aiLayers.put(BONNIE, bonnie);
        final FnafrImage chica = new FnafrImage(new Rectangle(0, 0, 29, 63), "ai/chica");
        this.camerasCard.add(chica, 2);
        chica.setVisible(false);
        this.aiLayers.put(CHICA, chica);
        final FnafrImage foxy = new FnafrImage(new Rectangle(0, 0, 29, 63), "ai/foxy");
        this.camerasCard.add(foxy, 3);
        foxy.setVisible(false);
        this.aiLayers.put(FOXY, foxy);

        this.noise1 = new FnafrImage(new Rectangle(0, 0, 160, 90), "noise1");
        this.noise2 = new FnafrImage(new Rectangle(0, 0, 160, 90), "noise2");
        this.camerasCard.add(this.noise1, 5);
        this.camerasCard.add(this.noise2, 5);
        this.noise2.setVisible(false);
        this.noiseTimer = new Timer(150, e -> {
            this.noise1.toggleVisible();
            this.noise2.toggleVisible();
        });

        this.cameras1 = new FnafrImage(new Rectangle(0, 0, 160, 90), "cameras1");
        this.cameras2 = new FnafrImage(new Rectangle(0, 0, 160, 90), "cameras2");
        this.camerasCard.add(this.cameras1, 6);
        this.camerasCard.add(this.cameras2, 6);
        this.cameras2.setVisible(false);
        this.camerasTimer = new Timer(1000, e -> {
            this.cameras1.toggleVisible();
            this.cameras2.toggleVisible();
        });

        this.camerasCard.add(new FnafrImage(new Rectangle(89, 39, 64, 44), "map"), 6);

        final Dimension cameraDimension = new Dimension(6, 6);
        final BufferedImage cameraOff = FnafrComponent.loadImage("map_button_off");
        final BufferedImage cameraOn = FnafrComponent.loadImage("map_button_on");
        for (final var entry : CAMERA_POS.entrySet()) {
            final FnafrButton camera = new FnafrButton(
                new Rectangle(entry.getValue(), cameraDimension),
                cameraOff,
                cameraOn
            );
            this.camerasCard.add(camera, 7);
            camera.setAction(() -> {
                this.game.events().scheduleSignal(() -> {
                    this.game.cameras().setCurrentRoom(entry.getKey());
                });
                this.update();
            });
        }

        this.roomName = new FnafrLabel(new Point2D.Float(15.5f, 9.5f));
        this.camerasCard.add(this.roomName, 6);

        this.initOverlay(this.camerasCard, this.mainCard);
    }

    private void end(final Game.Ending ending) {
        if (ending.equals(Game.VICTORY)) {
            this.six.setText("6 AM");
            this.endText.setText("Hai vinto!");
            this.jumpscare.setVisible(false);
        } else if (ending instanceof Game.JumpscareEnding jumpscareEnding) {
            this.six.setText("");
            this.endText.setText("Hai perso.");
            this.jumpscare.setImage(FnafrComponent.loadImage(
                "menu/" + jumpscareEnding.getAttacker().resourceName()
            ));
            this.jumpscare.setVisible(true);
        }
        this.setCard(this.endCard);
    }

    private void initEndCard() {
        this.six = new FnafrLabel("", new Point2D.Float(80, 45));
        this.six.setAlignment(FnafrLabel.Alignment.CENTER);
        this.endCard.add(this.six, 1);

        this.endText = new FnafrLabel("", new Point2D.Float(80, 75));
        this.endText.setAlignment(FnafrLabel.Alignment.CENTER);
        this.endCard.add(this.endText, 1);

        this.jumpscare = new FnafrImage(new Rectangle(42, -17, 25, 30), "menu/freddy");
        this.jumpscare.setScale(3);
        this.endCard.add(this.jumpscare, 1);
    }

}

// CHECKSTYLE: MagicNumber ON
