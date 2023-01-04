package ip2.gameboard;

public class Lots extends Property{
    private int rent;

    public Lots(String name, int price, int rent) {
        super(name, price);
        this.rent = rent;
    }

    public int getRent(){
        return rent;
    }
}
