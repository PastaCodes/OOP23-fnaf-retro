package it.unibo.fnafretro.map;

/**
 * implementazione dell'interfaccia Room.
 * @author Filippo Del Bianco
 */
public final class RoomImpl implements Room {
    private final String roomName;
    private final String side;

    RoomImpl(final String roomName, final String side) {
        this.roomName = roomName;
        this.side = side;
    }

    @Override
    public String getRoomName() {
        return this.roomName;
    }

    @Override
    public String getSide() {
        return this.side;
    }
}
