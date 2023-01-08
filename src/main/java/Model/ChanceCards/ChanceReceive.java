package Model.ChanceCards;

import Model.Player;

public class ChanceReceive extends Cards {
    private String description;

    private int amount;

    public ChanceReceive(String Name, String Type,String Description, int Amount) {
        super(Name, Type);
        description = Description;
        amount = Amount;
    }

    public void recieve(Player player) {
        player.setPlayerBalance(amount);
    }

    public void steal(Player player, int amountOfPlayers) {
        player.setPlayerBalance(amount*amountOfPlayers+200);
        Player[] players = Game.getPlayers();
        for (Player player1: players){
            player1.setPlayersBalance(amount);
        }
    }

    /**
     * Metode der checker om playeren har værdier for mere end 15000kr. Hvis ikke, så modtager de 40000kr
     */

    public void  rarecieve(Player player){
        int balance = Player.getPlayerBalance();

        if (balance > 15000) {
            return;
        }
        else {
            player.setPlayerBalance(40000);
        }
    }

}
