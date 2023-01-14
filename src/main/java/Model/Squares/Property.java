package Model.Squares;

import Model.Player;

public abstract class Property extends Square{
    private final int price;
    private Player owner;
    private boolean isOwned;
    private final int mortgage;
    private boolean isMortgaged = false;

    public Property(String name, String subText, String description, int price, int mortgage){
        super(name, subText, description);
        this.price = price;
        this.mortgage = mortgage;
        this.owner = null;
        this.isOwned = false;
    }

    public int getPrice(){
        return this.price;
    }

    public int getMortgage(){
        return this.mortgage;
    }
    public void setAsMortgaged(){
        isMortgaged = true;
    }
    public void setAsNotMortgaged(){
        isMortgaged = false;
    }

    public Player getOwner(){
        return this.owner;
    }
    public void setOwner(Player player){
        this.owner = player;
        this.isOwned = true;
    }
    public void removeOwner(){
        this.owner = null;
        this.isOwned = false;
    }

    public boolean isPropertyOwned(){
        return this.isOwned;
    }

    public boolean isPropertyMortgaged(){
        return isMortgaged;
    }

    @Override
    public String toString(){
        return super.name;
    }
}
