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

    /*public void payPerHouse(Player player) {
        ArrayList<Model.Squares.Property> properties = player.playerProperties();

        int houseCount = 0;
        int hotelCount = 0;

        for (Property property : properties) {
            if (gameControl.getGame().getBoard().getSquare(position) instanceof Lot)
            int propertyHouses = property.getHouses().length;
            int propertyHotels = property.getHotels().length;
            houseCount += propertyHouses;
            hotelCount += propertyHotels;
        }

        player.setPlayerBalance((amount * houseCount) + (hotelAmount * hotelCount));
    }*/

}
