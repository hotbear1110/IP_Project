package Model.ChanceCards;

import Model.Player;

public class ChanceReceive extends Cards {
    private int amount;

    public ChanceReceive(String Name, String Type,String Description, int Amount) {
        super(Name, Type, Description);
        amount = Amount;
    }

    public void recieve(Player player) {
        player.setPlayerBalance(amount);
    }

    public void steal(Player player, Player[] players) {
        int amountOfPlayers = players.length;
        player.setPlayerBalance(amount*amountOfPlayers+200);
        for (Player player1: players){
            player1.setPlayerBalance(amount);
        }
    }

    /**
     * Metode der checker om playeren har værdier for mere end 15000kr. Hvis ikke, så modtager de 40000kr
     */

    public void  rarecieve(Player player){
        int balance = player.getPlayerBalance();

        if (balance <= 15000) {
            player.setPlayerBalance(40000);
        }
    }

}
