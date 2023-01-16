package Model.Squares;

import Model.Player;

public class Brewery extends Property{
    private final int[] rentTable;
    private int currentRent;

    public Brewery(String name, String subText, String description, int price, int mortgage, int[] rentTable, ColorGroup group) {
        super(name, subText, description, price, mortgage, group);
        this.rentTable = rentTable;
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
    public int getRent(int diceSum){
        return currentRent * diceSum;
    }
    @Override
    public void setCurrentRent(int index){
        this.currentRent = rentTable[index];
    }
    @Override
    public void setAsMortgaged(){
        super.setAsMortgaged();
    }
    @Override
    public void setAsNotMortgaged(){
        super.setAsNotMortgaged();
    }
    @Override
    public Player getOwner(){
        return super.getOwner();
    }
    @Override
    public void setOwner(Player player){
        super.setOwner(player);
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

    @Override
    public String toString(){
        return super.name;
    }
}
