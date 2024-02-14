package it.unibo.fnafretro.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * implementazione dell'interfaccia GameMap.
 * @author Filippo Del Bianco
 */
public final class GameMapImpl implements GameMap {

    private final List<Room> rooms;
    private final Map<Room, List<Room>> adjacencies;
    private final List<String> roomsValues;

    GameMapImpl() {
        this.rooms = new ArrayList<>();
        this.adjacencies = new HashMap<>();
        this.roomsValues = new ArrayList<>();
        initRoomsValues();
        setRoomsValues();
    }

    private void initRoomsValues() {
        this.roomsValues.add("1A none 1B");
        this.roomsValues.add("1B both 1A 5 7 1C 6 2A 4A");
        this.roomsValues.add("1C none 1B");
        this.roomsValues.add("5 left 1B");
        this.roomsValues.add("7 right 1B");
        this.roomsValues.add("3 left 2A");
        this.roomsValues.add("6 right 1B");
        this.roomsValues.add("2A left 2B 3 1B");
        this.roomsValues.add("2B left 2A YOU");
        this.roomsValues.add("4A right 1B 4B");
        this.roomsValues.add("4B right 4A YOU");
        this.roomsValues.add("YOU none 2B 4B");
    }

    private void setRoomsValues() {
        String[] room;
        final List<Room> adjacentRooms = new ArrayList<>();
        for (final String values : this.roomsValues) {
            room = values.split(" ");
            this.rooms.add(new RoomImpl(room[0], room[1]));
        }
        for (final String values : roomsValues) {
            room = values.split(" ");
            for (int i = 2; i < room.length; i++) {
                adjacentRooms.add(getRoom(room[i]));
            }
            this.adjacencies.put(getRoom(room[0]), List.copyOf(adjacentRooms));
            adjacentRooms.clear();
        }
    }

    @Override
    public Room getRoom(final String roomName) {
        for (final Room room : this.rooms) {
            if (room.getRoomName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

    @Override
    public List<Room> getAllRooms() {
        return List.copyOf(this.rooms);
    }

    @Override
    public List<Room> getAdjacencies(final Room room) {
        return List.copyOf(this.adjacencies.get(room));
    }
}
