package Control;
import Model.Squares.*;

import java.util.ArrayList;

public class ActionControl {
    private GameControl gameControl;
    public ActionControl(GameControl gameControl){
        this.gameControl = gameControl;
    }

    public void controlAction(String action){
        switch (action){
            case "Køb/Sælg" -> {
                String choice = gameControl.getUI().getUserButton("Vælg en handling:", ControlMenus.buySellMenu);
                if(choice.equals("Køb/Sælg opgradering")){
                    controlUpgrades();
                } else if (choice.equals("Sælg grunde")) {
                    controlProperties();
                } else {
                    controlAction(action);
                }
            }
            case "Pantsæt/Ophæv pantsætning" -> {
                controlMortgagedProperties();
            }
            case "Start Tur" ->{
            }
        }
    }

    private void controlMortgagedProperties() {
    }

    private void controlProperties() {
    }

    private void controlUpgrades() {
    }
}
