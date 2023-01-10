package Control;

import Model.Player;
import Model.Squares.Chance;
import Model.Squares.Lot;

public class DemoControl {
    private final GameControl gameControl;
    private final String[] menu;
    boolean enableChance;
    boolean enableBuy;
    boolean enableDiceManipulation;

    public DemoControl(GameControl gameControl){
        this.gameControl = gameControl;
        this.menu = new String[]{"Accepttest 1 & 2", "Accepttest 3", "Accepttest 4 & 5", "Accepttest 6", "Afslut"};
    }

    public void setUpTest(String test){

        switch(test){
            case "Accepttest 1 & 2" -> {
                //Accepttest one & two : Move around board clockwise with dice, recieve start money when passing start
                gameControl.actionControl.disableBuy();
                gameControl.actionControl.disableChance();
            }
            case "Accepttest 3" -> {
                //Accepttest three : Land on chance and receive an effect : manipulate with dice.
                gameControl.actionControl.disableBuy();
                gameControl.diceControl.enableDiceManipulation();

            }
            case "Accepttest 4 & 5" -> {
                //Accepttest four & five : Buy property and get rent : manipulate with dice.
                gameControl.actionControl.disableChance();
                gameControl.diceControl.enableDiceManipulation();
            }
            case "Accepttest 6" -> {
                //Accepttest six : Get a player bankrupt and declare a winner : manipulate with player account
            }
            case "Afslut" -> {
                gameControl.forceEndGame();{
                }
            }
        }
    }
    public String[] getMenu(){
        return menu;
    }

    public boolean getEnableBuy(){
        return enableBuy;
    }

    public boolean getEnableChance(){
        return enableChance;
    }

    public boolean getEnableDiceManipulation(){
        return enableDiceManipulation;
    }
}
