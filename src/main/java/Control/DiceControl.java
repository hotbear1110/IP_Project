package Control;

public class DiceControl{
    private GameControl gameControl;
    boolean doubleDice;
    int doubleDiceCounter = 0;
    public DiceControl(GameControl gameControl){
        this.gameControl = gameControl;

    }

    public void controlAction(){
        gameControl.getGame().getDice().rollDice();
        doubleDice = gameControl.getGame().getDice().isDouble();
        if (doubleDice){
            doubleDiceCounter++;
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
