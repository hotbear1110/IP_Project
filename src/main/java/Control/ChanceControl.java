package Control;

import Model.Player;

public class ChanceControl {
    private GameControl gameControl;
    public ChanceControl(GameControl gameControl){
        this.gameControl = gameControl;
    }

    public int controlAction(Player player) {
        Player[] players = gameControl.getGame().getPlayers();
        String description = gameControl.getGame().getBoard().getNextCard();

        gameControl.getUI().setChanceCard(description);

        gameControl.getUI().getChanceCard(description);

        gameControl.getUI().showMessage("Du har trukket et chance kort");

        int move  = gameControl.getGame().getBoard().drawCard(player, players);

        String nextDescription = gameControl.getGame().getBoard().getNextCard();

        gameControl.getUI().setChanceCard(nextDescription);

        return move;
    }
}
