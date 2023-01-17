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
    }

    public boolean isJailed() {
        Player player = gameControl.getGame().getCurrentPlayer();

        int rounds =  counter.get(player) + 1;

        return (rounds == 4);

    }

    public void jailPlayer() {
        Player player = gameControl.getGame().getCurrentPlayer();

        counter.put(player, 0);

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
            if (!hasJailCard()) {
                gameControl.getUI().showMessage("Du har ikke et fængsels kort");
                return controlAction();
            }
            useJailCard();
        }

        return action;
    }

    public void leaveJail() {
        Player player = gameControl.getGame().getCurrentPlayer();

        counter.remove(player);

        player.leaveJail();
    }

    public void jailCount() {
        Player player = gameControl.getGame().getCurrentPlayer();
        int rounds = counter.get(player) + 1;
        counter.remove(player);
        counter.put(player, rounds);
    }
}
