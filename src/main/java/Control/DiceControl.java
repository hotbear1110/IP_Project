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
        if(!diceManipulation) {
            gameControl.getGame().getDice().rollDice();
            doubleDice = gameControl.getGame().getDice().isDouble();
            if (doubleDice) {
                doubleDiceCounter++;
            }
        } else {
            String[] menu = new String[]{"1 + 1", "1 + 2", "3 + 4", "5 + 5", "6 + 6"};
            String s = gameControl.getUI().getDropDown("Vælg et terningeslag", menu);
            int x = Utility.parseFirstIntBeforePlus(s);
            int y = Utility.parseSecondIntAfterPlus(s);
            int[] dice = new int[]{x, y};
            gameControl.getGame().getDice().setDicePair(dice);
        }
    }

    public boolean getDoubleDice(){
        return doubleDice;
    }

    public int getDoubleDiceCounter(){
        return doubleDiceCounter;
    }

    public void resetCounter(){
        doubleDiceCounter = 0;
    }
}
