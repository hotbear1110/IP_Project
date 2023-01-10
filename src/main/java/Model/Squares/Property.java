package Model.Squares;

import Model.Player;

public abstract class Property extends Square{
    private final int price;

    private final int mortgage;
    private Player owner;
    private boolean isOwned = false;
    private boolean isMortgaged = false;

    public Property(String name, String subText, String description, int price, int mortgage){
        super(name, subText, description);
        this.price = price;
        this.mortgage = mortgage;
    }

    public int getPrice(){
        return price;
    }

    public int getMortgage(){
        return mortgage;
    }

    public abstract int getRent();

    public void setOwner(Player player){
        owner = player;
    }

    public Player getOwner(){
        return owner;
    }

    public boolean isOwned(){
        return isOwned;
    }

    public boolean isMortgaged(){
        return isMortgaged;
    }
}
