package Control;

public class DiceControl{
    private String[] menu;
    private int activeMenu;
    private GameControl gameControl;
    private boolean enableDiceManipulation = false;

    public DiceControl(GameControl gameControl){
        this.gameControl = gameControl;
        this.menu = new String[]{Translator.getString("ROLL_DICE"), "Indtast ønsket terninge sum"};
        this.activeMenu = 0;

    }

    public String getControlMenu() {
        return menu[activeMenu];
    }

    public void controlAction(int[] pair){
        if (enableDiceManipulation){
            assert pair != null;
            gameControl.getGame().getDice().setDicePair(pair);
        } else {
            gameControl.getGame().getDice().rollDice();
        }
    }

    public void enableDiceManipulation(){
        enableDiceManipulation = true;
        this.activeMenu = 1;
    }

    public boolean getManipulationStatus(){
        return enableDiceManipulation;
    }

}
