package Model.ChanceCards;

import Model.Player;

public class JailCard extends Cards{
    public JailCard(String Name, String Type, String Description){
        super(Name, Type, Description);
    }

    public void giveJailCard(Player player) {
        player.giveJailCard();
    }
}
