package Control;

import Model.FixedValues;

public class BankControl {
    private GameControl gameControl;
    public BankControl(GameControl gameControl){
        this.gameControl = gameControl;
    }

    public void getPassedStart(){
        gameControl.getUI().showMessage(Translator.getString("PASSED_START"));
        gameControl.getGame().getCurrentPlayer().setPlayerBalance(FixedValues.PASSED_START);
    }
}
