package Control;

import Model.Player;

public class ChanceControl {
    private GameControl gameControl;
    public ChanceControl(GameControl gameControl){
        this.gameControl = gameControl;
    }

    public void controlAction(Player player) {
        Player[] players = gameControl.getGame().getPlayers();
        String description = gameControl.getGame().getBoard().getNextCard();

        gameControl.getUI().setChanceCard(description);

        gameControl.getGame().getBoard().drawCard(player, players);

        gameControl.getUI().getChanceCard(description);

        String nextDescription = gameControl.getGame().getBoard().getNextCard();

        gameControl.getUI().setChanceCard(nextDescription);
    }
}
