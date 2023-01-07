package Model.ChanceCards;

import Model.Player;

public class ChancePay {

    private String name;

    private String description;

    private boolean regular;

    private boolean perPlayer;

    private boolean propertyBonus;

    public ChancePay(String Name, String Description, boolean Regular, boolean PerPlayer, boolean PropertyBonus) {
        name = Name;
        description = Description;
        regular = Regular;
        perPlayer = PerPlayer;
        propertyBonus = PropertyBonus;
    }

public static void recieve(Player player, int amount) {
player.setPlayerBalance(amount);
    }

}
