package Control;

import Model.FixedValues;
import Model.Player;

public class BankControl {
    private GameControl gameControl;
    private String menu;
    private boolean enabledManipulateWithAccount = false;
    public BankControl(GameControl gameControl){
        this.gameControl = gameControl;
        menu = "Hvilken spiller skal gøres fallit?";
    }

    public void getPassedStart(){
        gameControl.getUI().showMessage(Translator.getString("PASSED_START"));
        gameControl.getGame().getCurrentPlayer().setPlayerBalance(FixedValues.PASSED_START);
    }

    public void removeMoney(Player player, int amount){
        player.setPlayerBalance(-amount);
    }

    public String getMenu(){
        return menu;
    }
    public void enabledManupulation(){
        enabledManipulateWithAccount = true;
    }

    public boolean getManipulationStatus(){
        return enabledManipulateWithAccount;
    }
}
