package Model.Squares;

import Model.Player;

public abstract class Property extends Square{
    private final int price;

    private final int mortgage;
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


    public boolean isMortgaged(){
        return isMortgaged;
    }
}
