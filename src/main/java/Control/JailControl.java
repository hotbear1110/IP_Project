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

        int rounds = counter.get(player) + 1;
        counter.remove(player);
        counter.put(player, rounds);

        return (rounds >= 3);
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
        String action = gameControl.getUI().getDropDown(Translator.getString("CHOOSE_ACTION"), ControlMenus.inJail);
        Player player = gameControl.getGame().getCurrentPlayer();

        if (action.equals(Translator.getString("USE_PRISONCARD"))) {
            if (!hasJailCard()) {
                gameControl.getUI().showMessage(Translator.getString("NO_JAIL_CARD"));
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
}
