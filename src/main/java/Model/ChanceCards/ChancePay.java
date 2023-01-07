package Model.ChanceCards;

import Model.Player;
import org.apache.velocity.tools.config.Property;

public class ChancePay {

    private String name;

    private String type;

    private String description;

    private int amount;

    private int hotelAmount;

    public ChancePay(String Name, String Type, String Description, int Amount, int HotelAmount) {
        name = Name;
        type = Type;
        description = Description;
        amount = Amount;
        hotelAmount = HotelAmount;
    }

    public void payAmount(Player player) {
        player.setPlayerBalance(-amount);
    }

    public void payPerHouse(Player player) {
        Property[] properties = player.getProperties();

        int houseCount = 0;
        int hotelCount = 0;

        for (Property property : properties) {
            int propertyHouses = property.getHouses().length;
            int propertyHotels = property.getHotels().length;
            houseCount += propertyHouses;
            hotelCount += propertyHotels;
        }

        player.setPlayerBalance((amount * houseCount) + (hotelAmount * hotelCount));
    }

}
