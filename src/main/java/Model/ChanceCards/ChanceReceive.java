package Model.ChanceCards;

import Model.Player;

public class ChanceReceive {

    private String name;

    private String description;

    private boolean regular;

    private boolean perPlayer;

    private boolean propertyBonus;

    public ChanceReceive(String Name, String Description, boolean Regular, boolean PerPlayer, boolean PropertyBonus) {
        name = Name;
        description = Description;
        regular = Regular;
        perPlayer = PerPlayer;
        propertyBonus = PropertyBonus;
    }

    public static void recieve(Player player) {
        player.setPlayerBalance(amount);
    }

    public static void steal(Player player, int amountOfPlayers) {
    player.setPlayerBalance(amount*amountOfPlayers+200);
    Player[] players = Game.getPlayers();
    for (Player player1: players){
        player1.setPlayersBalance(amount)
    }
    }

}
