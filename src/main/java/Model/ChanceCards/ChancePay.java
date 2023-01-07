package Model.ChanceCards;

import Model.Player;

public class ChancePay {

    private String name;

    private String type;

    private String description;

    private int amount;

    public ChancePay(String Name, String Type, String Description, int Amount) {
        name = Name;
        type = Type;
        description = Description;
        amount = Amount;
    }

    public void payAmount(Player player) {
        player.setPlayerBalance(-amount);
    }

    public void payPerHouse(Player player) {

    }

}
