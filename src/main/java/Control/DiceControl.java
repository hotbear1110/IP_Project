package Control;

public class DiceControl{
    private String[] menu;
    private GameControl gameControl;
    public DiceControl(GameControl gameControl){
        this.gameControl = gameControl;
        this.menu = new String[]{Translator.getString("ROLL_DICE")};

    }

    public String[] getControlMenu() {
        return menu;
    }

    public void controlAction(){
        gameControl.getGame().rollDice();
        //update ui dice
    }
}