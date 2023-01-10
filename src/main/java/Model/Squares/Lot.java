package Model.Squares;

import Global.Utility;
import Model.Player;

import java.awt.*;

public class Lot extends Property {
    private final int[] rentTable;
    private int currentRent;
    private final Color color;

    private Player owner;
    private boolean isOwned = false;
    public Lot(String name, String subText, String description, int price, int mortgage, int[] rentTable, Color color) {
        super(name, subText, description, price, mortgage);
        this.rentTable = rentTable;
        this.color = color;
        currentRent = rentTable[0];
    }

    public void setCurrentRent(int index){
        currentRent = rentTable[index];
    }
    public int getRent(){
        return currentRent;
    }

    public Color getColor(){
        return color;
    }
    public void setOwner(Player player){
        owner = player;
        isOwned = true;
    }

    public Player getOwner(){
        return owner;
    }

    public boolean isOwned(){
        return isOwned;
    }

    public String[] getSquareInfo(){
        return new String[]{name, subText, description};
    }
}
