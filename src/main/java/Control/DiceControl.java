package Control;

public class DiceControl{
    private GameControl gameControl;
    public DiceControl(GameControl gameControl){
        this.gameControl = gameControl;

    }

    public void controlAction(){
        gameControl.getGame().getDice().rollDice();
    }


}
