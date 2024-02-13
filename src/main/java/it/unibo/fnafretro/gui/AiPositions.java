package it.unibo.fnafretro.gui;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import it.unibo.fnafretro.ai.Ai;
import it.unibo.fnafretro.ai.AiDescriptor;
import it.unibo.fnafretro.ai.Bonnie;
import it.unibo.fnafretro.ai.Chica;
import it.unibo.fnafretro.ai.Foxy;
import it.unibo.fnafretro.ai.Freddy;
import it.unibo.fnafretro.map.Room;

public class AiPositions {

    private static final Map<Class<? extends AiDescriptor>, Map<String, Point>> POSITIONS = Map.of(
        Freddy.class, Map.of(
            "1A", new Point(88, 17),
            "1B", new Point(28, -21),
            "4A", new Point(109, 21),
            "4B", new Point(7, -12),
            "YOU", new Point(-16, 8) // nascosto
        ),
        Bonnie.class, Map.of(
            "1A", new Point(62, 9),
            "1B", new Point(101, 11),
            "2A", new Point(50, 22),
            "2B", new Point(71, -22),
            "3", new Point(84, 4),
            "5", new Point(114, 22),
            "YOU", new Point(27, 9)
        ),
        Chica.class, Map.of(
            "1A", new Point(106, 9),
            "1B", new Point(50, 40),
            "4A", new Point(56, 25),
            "4B", new Point(87, 2),
            "7", new Point(37, 11),
            "YOU", new Point(152, 9)
        )
    );

    private static final Map<Class<? extends AiDescriptor>, Map<String, Integer>> SCALES = Map.of(
        Freddy.class, Map.of(
            "4B", 3
        ),
        Bonnie.class, Map.of(
            "2B", 3,
            "3", 2
        ),
        Chica.class, Map.of(
            "4B", 2
        )
    );

    private static final List<Point> FOXY_PHASES = List.of(
        new Point(45, 15),
        new Point(54, 15),
        new Point(59, 15)
    );

    public static Point getPosition(final Ai ai, final Room room) {
        if (ai instanceof Foxy.FoxyAi foxy) {
            // Assumo che sia nella stanza 1C.
            return AiPositions.FOXY_PHASES.get(foxy.getPhase());
        }
        return AiPositions.POSITIONS.get(ai.descriptor().getClass())
            .get(room.getRoomName());
    }

    public static int getScale(final Ai ai, final Room room) {
        if (ai instanceof Foxy.FoxyAi) {
            return 1;
        }
        return AiPositions.SCALES.get(ai.descriptor().getClass())
            .getOrDefault(room.getRoomName(), 1);
    }

}
