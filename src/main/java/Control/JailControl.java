package Control;

import Model.Player;

import java.util.ArrayList;

public class JailControl {

    GameControl gameControl;

    ArrayList<Player> jailedPlayers;

    public JailControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    public void jailPlayer() {
        Player player = gameControl.getGame().getCurrentPlayer();

        jailedPlayers.add(player);
    }

    public boolean hasJailCard() {
        Player player = gameControl.getGame().getCurrentPlayer();

        boolean hasJailCard = player.hasJailCard();

        return hasJailCard;
    }

    public void useJailCard() {
        Player player = gameControl.getGame().getCurrentPlayer();

        player.takeJailCard();

        jailedPlayers.remove(player);
    }

    public void leaveJail() {
        Player player = gameControl.getGame().getCurrentPlayer();

        jailedPlayers.remove(player);
    }
}
