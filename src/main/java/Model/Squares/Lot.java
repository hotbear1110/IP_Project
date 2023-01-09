package Model.Squares;

import java.awt.*;

public class Lot extends Property {
    private final int[] rentTable;
    private int currentRent;
    private final Color color;


    public Lot(String name, String subText, String description, int price, int mortgage, int[] rentTable, Color color) {
        super(name, subText, description, price, mortgage);
        this.rentTable = rentTable;
        this.color = color;
        currentRent = rentTable[0];
    }

    public void setCurrentRent(int index){
        currentRent = rentTable[index];
    }
    public int getCurrentRent(){
        return currentRent;
    }

    public Color getColor(){
        return color;
    }
}
