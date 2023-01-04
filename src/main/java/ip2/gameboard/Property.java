package ip2.gameboard;

public abstract class Property extends Squares {
    private final int price;
    private Player owner;
    private boolean isOwned = false;

    public Property(String name, int price) {
        super(name);
        this.price = price;
    }

    public int getPrice(){
        return this.price;
    }

    public void setOwner(Player player){
        this.owner = player;
        isOwned = true;
    }

    public Player getOwner(){
        return owner;
    }

    public boolean checkOwnership(){
        return isOwned;
    }

}
