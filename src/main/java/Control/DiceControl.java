package Control;

import Global.Utility;

public class DiceControl{
    private GameControl gameControl;
    boolean doubleDice;
    int doubleDiceCounter = 0;
    boolean diceManipulation = false;
    public DiceControl(GameControl gameControl){
        this.gameControl = gameControl;

    }

    public void controlAction(){
        if (!gameControl.getGame().getDice().isDouble()){
            resetCounter();
        }
        if(!diceManipulation) {
            gameControl.getGame().getDice().rollDice();
            doubleDice = gameControl.getGame().getDice().isDouble();
            if (doubleDice) {
                doubleDiceCounter++;
            }
        } else {
            loadedDice();
        }
    }

    public void loadedDice(){
        String[] menu = new String[]{"1 & 1", "1 & 2", "2 & 2", "2 & 3", "1 & 5", "3 & 4", "2 & 6", "4 & 5", "5 & 5", "4 & 6", "5 & 6", "6 & 6"};
        String s = gameControl.getUI().getDropDown("Vælg et terningeslag", menu);
        int[] dicePair = Utility.parseTwoIntsToArray(s);
        gameControl.getGame().getDice().setDicePair(dicePair);
        doubleDice = gameControl.getGame().getDice().isDouble();
        if (doubleDice) {
            doubleDiceCounter++;
        }

    }

    public boolean getDoubleDice(){
        return gameControl.getGame().getDice().isDouble();
    }

    public int getDoubleDiceCounter(){
        return doubleDiceCounter;
    }

    public void resetCounter(){
        doubleDiceCounter = 0;
    }
    public void resetDouble(){

    }

    public void enabledDiceManipulation(){
        diceManipulation = true;
    }
}
