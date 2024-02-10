package it.unibo.fnafretro.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GameMapImpl implements GameMap{
    private final List<Room> rooms;
    private final Cameras cameras;
    private final HashMap<Room, List<Room>> adjacencies;

    private List<String> getRoomsValues(){
        List<String> roomsValues = new ArrayList<>();
        try {
            File roomsFile = new File("rooms.txt");
            Scanner reader = new Scanner(roomsFile);
            while (reader.hasNextLine()) {
                roomsValues.add(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("error");
        }
        return List.copyOf(roomsValues);
    }

    private void setRoomsValues(List<String> roomsValues){
        String[] room;
        List<Room> adjacentRooms = new ArrayList<>();
        for (String values : roomsValues) {
            room = values.split(" ");
            this.rooms.add(new RoomImpl(room[0], room[1]));
        }
        for (String values : roomsValues) {
            room = values.split(" ");
            for(int i = 2; i<room.length; i++){
                adjacentRooms.add(getRoom(room[i]));
            }
            this.adjacencies.put(getRoom(room[0]), List.copyOf(adjacentRooms));
            adjacentRooms.clear();
        }
    }

    public GameMapImpl(){
        this.rooms = new ArrayList<>();
        this.adjacencies = new HashMap<>();
        List<String> roomsValues = getRoomsValues();
        setRoomsValues(roomsValues);
        this.cameras = new CamerasImpl(getRoom("1A"));
    }

    public Room getRoom(String roomName){
        for (Room room : this.rooms) {
            if(room.getRoomName().equals(roomName)){
                return room;
            }
        }
        return null;
    }

    public Cameras getCameras(){
        return this.cameras;
    }

    public List<Room> getAdjacencies(Room room){
        return List.copyOf(this.adjacencies.get(room));
    }
}
