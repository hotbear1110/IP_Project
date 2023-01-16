package Control;

import Model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JailControl {

    GameControl gameControl;

    Map<Player, Integer> counter = new HashMap<Player, Integer>();

    public JailControl(GameControl gameControl) {
        this.gameControl = gameControl;

        Player[] players = gameControl.getGame().getPlayers();

        for (Player player : players) {
            counter.put(player, 0);
        }

    }

    public boolean isJailed() {
        Player player = gameControl.getGame().getCurrentPlayer();

        int rounds = counter.get(player) + 1;
        counter.remove(player);
        counter.put(player, rounds);


        return (rounds >= 3);
    }

    public void jailPlayer() {
        Player player = gameControl.getGame().getCurrentPlayer();

        player.jailPlayer();
    }

    public boolean hasJailCard() {
        Player player = gameControl.getGame().getCurrentPlayer();

        boolean hasJailCard = player.hasJailCard();

        return hasJailCard;
    }

    public void useJailCard() {
        Player player = gameControl.getGame().getCurrentPlayer();

        player.takeJailCard();
        gameControl.getGame().getBoard().addJailCard();

        leaveJail();
    }

    public String controlAction() {
        String action = gameControl.getUI().getDropDown("Vælg action", ControlMenus.inJail);
        Player player = gameControl.getGame().getCurrentPlayer();

        if (action.equals("Brug fængsels kort")) {
            useJailCard();
        }

        return action;
    }

    public void leaveJail() {
        Player player = gameControl.getGame().getCurrentPlayer();

        counter.remove(player);
        counter.put(player, 0);

        player.leaveJail();
    }
}
