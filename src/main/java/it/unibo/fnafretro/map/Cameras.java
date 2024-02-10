package it.unibo.fnafretro.map;

interface Cameras {
    public boolean getStatus();
    public void setStatus(boolean status);
    public boolean getGreyScreen();
    public void setGreyScreen(boolean greyScreen);
    public Room getCurrentRoom();  
    public void setCurrentRoom(Room newRoom);
}
