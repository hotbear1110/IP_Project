package Control;

import Model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JailControl {

    GameControl gameControl;

    ArrayList<Player> jailedPlayers;

    Map<Player, Integer> counter = new HashMap<Player, Integer>();

    public JailControl(GameControl gameControl) {
        this.gameControl = gameControl;

        Player[] players = gameControl.getGame().getPlayers();

        for (Player player : players) {
            counter.put(player, 0);
        }

    }

    public int isJailed() {
        Player player = gameControl.getGame().getCurrentPlayer();

        int rounds = counter.get(player) + 1;
        counter.remove(player);
        counter.put(player, rounds);

        return rounds;
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

        leaveJail();
    }

    public void leaveJail() {
        Player player = gameControl.getGame().getCurrentPlayer();

        jailedPlayers.remove(player);

        counter.remove(player);
        counter.put(player, 0);
    }
}
