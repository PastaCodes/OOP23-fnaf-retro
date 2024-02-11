package it.unibo.fnafretro.map;

/**
 * implementazione dell'interfaccia Cameras.
 * @author Filipp Del Bianco
 */
public final class CamerasImpl implements Cameras {

    private Room currentRoom;
    private boolean enabled;
    private boolean greyScreen;

    CamerasImpl(final Room startingRoom) {
        this.currentRoom = startingRoom;
        this.enabled = false;
        this.greyScreen = false;
    }

    @Override
    public boolean getStatus() {
        return this.enabled;
    }

    @Override
    public void setStatus(final boolean status) {
        this.enabled = status;
    }

    @Override
    public boolean getGreyScreen() {
        return this.greyScreen;
    }

    @Override
    public void setGreyScreen(final boolean greyScreen) {
        this.greyScreen = greyScreen;
    }

    @Override
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    @Override
    public void setCurrentRoom(final Room newRoom) {
        this.currentRoom = newRoom;
    }

}
