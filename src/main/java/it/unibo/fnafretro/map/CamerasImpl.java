package it.unibo.fnafretro.map;

public class CamerasImpl implements Cameras{

    private Room currentRoom;
    private boolean enabled;
    private boolean greyScreen;

    public CamerasImpl(Room startingRoom){
        this.currentRoom = startingRoom;
        this.enabled = false;
        this.greyScreen = false;
    }

    public boolean getStatus(){
        return this.enabled;
    }

    public void setStatus(boolean status){
        this.enabled = status;
    }

    public boolean getGreyScreen(){
        return this.greyScreen;
    }

    public void setGreyScreen(boolean greyScreen){
        this.greyScreen = greyScreen;
    }
    
    public Room getCurrentRoom(){
        return this.currentRoom;
    }
    
    public void setCurrentRoom(Room newRoom){
        this.currentRoom = newRoom;
    }

}
