package it.unibo.fnafretro.map;

//import java.util.List;

public class RoomImpl implements Room{
    private final String roomName;
    private final String side;

    public RoomImpl(String roomName, String side){
        this.roomName = roomName;
        this.side = side;
    }

    public String getRoomName(){
        return this.roomName;
    }

    public String getSide(){
        return this.side;
    }
}
