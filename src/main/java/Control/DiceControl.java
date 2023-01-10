package Control;

public class DiceControl{
    private String[] menu;
    private GameControl gameControl;
    private boolean enableDiceManipulation = false;

    public DiceControl(GameControl gameControl){
        this.gameControl = gameControl;
        this.menu = new String[]{Translator.getString("ROLL_DICE")};

    }

    public String[] getControlMenu() {
        return menu;
    }

    public void controlAction(int[] pair){
        if (enableDiceManipulation){
            gameControl.getGame().getDice().setDicePair(pair);
        } else {
            gameControl.getGame().getDice().rollDice();
        }
    }

    public void enableDiceManipulation(){
        enableDiceManipulation = true;
    }
}
