package Model.ChanceCards;

import Model.Player;

public class ChanceReceive {

    private String name;

    private String type;

    private String description;

    private static int amount;

    public ChanceReceive(String Name, String Type,String Description, int Amount) {
        name = Name;
        type = Type;
        description = Description;
        amount = Amount;
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
