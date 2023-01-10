package Model.Squares;

import Global.Utility;
import Model.Player;

import java.awt.*;

public class Lot extends Property {
    private final int[] rentTable;
    private int currentRent;
    private final Color color;

    public Lot(String name, String subText, String description, int price, int mortgage, int[] rentTable, Color color) {
        super(name, subText, description, price, mortgage);
        this.rentTable = rentTable;
        this.color = color;
        this.currentRent = rentTable[0];
    }

    public void setCurrentRent(int index){
        this.currentRent = rentTable[index];
    }
    public int getRent(){
        return this.currentRent;
    }

    public Color getColor(){
        return color;
    }

    public String[] getSquareInfo(){
        return new String[]{name, subText, description};
    }
}
