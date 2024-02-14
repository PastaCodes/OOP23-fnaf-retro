package it.unibo.fnafretro.map;

/**
 * implementazione dell'interfaccia Cameras.
 * @author Filipp Del Bianco
 */
public final class CamerasImpl implements Cameras {

    private String currentRoom;
    private boolean active;
    private boolean disturbed;

    CamerasImpl(final String startingRoom) {
        this.currentRoom = startingRoom;
        this.active = false;
        this.disturbed = false;
    }

    @Override
    public boolean areActive() {
        return this.active;
    }

    @Override
    public void setActive(final boolean active) {
        this.active = active;
    }

    @Override
    public boolean areDisturbed() {
        return this.disturbed;
    }

    @Override
    public void setDisturbed(final boolean disturbed) {
        this.disturbed = disturbed;
    }

    @Override
    public String getCurrentRoom() {
        return this.currentRoom;
    }

    @Override
    public void setCurrentRoom(final String newRoom) {
        this.currentRoom = newRoom;
    }

}