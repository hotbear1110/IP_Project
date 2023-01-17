package Model.ChanceCards;

import Model.Player;
import Model.Squares.Lot;
import Model.Squares.Property;

import java.util.ArrayList;

public class ChancePay extends Cards{
    private int amount;

    private int hotelAmount;

    public ChancePay(String Name, String Type, String Description, int Amount, int HotelAmount) {
        super(Name, Type, Description);
        amount = Amount;
        hotelAmount = HotelAmount;
    }

    public void payAmount(Player player) {
        player.setPlayerBalance(-amount);
    }

    public void payPerHouse(Player player) {

        int houseCount = player.getHouseCount();
        int hotelCount = player.getHotelCount();

        player.setPlayerBalance((amount * houseCount) + (hotelAmount * hotelCount));
    }

}
