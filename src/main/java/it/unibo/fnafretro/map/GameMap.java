package it.unibo.fnafretro.map;

import java.util.List;

interface GameMap {
    public Room getRoom(String roomName);
    public Cameras getCameras();
    public List<Room> getAdjacencies(Room room);
}
