package Model.ChanceCards;

import Model.Player;

public class ChancePay {

    private String name;

    private String description;

    private boolean regular;

    private boolean perHouse;

    private boolean perHouseHotel;

    public ChancePay(String Name, String Description, boolean Regular, boolean PerHouse, boolean PerHouseHotel) {
        name = Name;
        description = Description;
        regular = Regular;
        perHouse = PerHouse;
        perHouseHotel = PerHouseHotel;
    }

}
