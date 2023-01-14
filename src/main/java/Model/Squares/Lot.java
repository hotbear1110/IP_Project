package Model.Squares;

import Global.Utility;
import Model.Player;

import java.awt.*;

public class Lot extends Property {
    private final int[] rentTable;
    public boolean hasUpgrades;
    private int currentRent;
    private int houses;
    private boolean hotel = false;
    private boolean canBeUpgraded = true;
    private final int[] upgradePrice;
    private final Color color;

    public Lot(String name, String subText, String description, int price, int mortgage, int[] rentTable, int[] upgradePrice, Color color) {
        super(name, subText, description, price, mortgage);
        this.rentTable = rentTable;
        this.upgradePrice = upgradePrice;
        this.hasUpgrades = false;
        this.color = color;
        this.currentRent = rentTable[0];
    }
    @Override
    public int getPrice(){
        return super.getPrice();
    }
    @Override
    public int getMortgage(){
        return super.getMortgage();
    }

    @Override
    public void setAsMortgaged(){
        super.setAsMortgaged();
        canBeUpgraded = false;
    }

    @Override
    public void setAsNotMortgaged(){
        super.setAsNotMortgaged();
    }
    public void setCurrentRent(int index){
        this.currentRent = rentTable[index];
    }
    public int getRent(){
        return this.currentRent;
    }

    public int getHousePrice(){
        return this.upgradePrice[0];
    }
    public int getHotelPrice(){
        return this.upgradePrice[1];
    }
    @Override
    public void setOwner(Player player){
        super.setOwner(player);
    }

    @Override
    public Player getOwner(){
        return super.getOwner();
    }
    @Override
    public void removeOwner(){
        super.removeOwner();
    }

    @Override
    public boolean isPropertyOwned(){
        return super.isPropertyOwned();
    }
    @Override
    public boolean isPropertyMortgaged(){
        return super.isPropertyMortgaged();
    }

    public boolean canPropertyBeUpgraded(){
        return canBeUpgraded;
    }
    public void addHouse(){
        hasUpgrades = true;
        houses++;
    }
    public int getNumberOfHouses(){
        return houses;
    }

    public void removeHouse(int number){
        houses -= number;
        if (houses == 0){
            hasUpgrades = false;
        }
    }
    public void addHotel(){
        hotel = true;
        canBeUpgraded = false;
    }

    public void removeHotel(){
        hotel = false;
        canBeUpgraded = true;
    }

    public boolean getHotel(){
        return hotel;
    }

    public void resetLot(){
        removeOwner();
        hasUpgrades = false;
        canBeUpgraded = true;
        hotel = false;
        currentRent = rentTable[0];

    }
    public Color getColor(){
        return color;
    }
    @Override
    public String[] getSquareInfo(){
        return new String[]{name, subText, description};
    }

    @Override
    public String toString(){
        return super.name;
    }
}
