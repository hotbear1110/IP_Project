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
        this.menu = new String[]{"Accepttest 1 & 2", "Accepttest 3", "Accepttest 4 & 5", "Accepttest 6", "Afslut demo"};
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
            case "Afslut demo" -> {
                gameControl.endGame(){

                }
            }
        }
    }

    public void runTestOneAndTwo(){
        //Acceptest one & two : Move around board clockwise with dice, recieve start money when passing start - MISSING START MONEY!!!!!!
        while(true){
            gameControl.getGame().getDice().rollDice();
            gameControl.getGame().getCurrentPlayer().movePlayerPosition(gameControl.getGame().getDice().getSum());
            gameControl.updateUI(gameControl.getGame().getPlayers(), gameControl.getGame().getDice().getSingleDice(0), gameControl.getGame().getDice().getSingleDice(1));
            if(gameControl.getGame().getCurrentPlayer().hasPassedStart()){
                String s = gameControl.getUI().getUserButton("Vil du forsætte?", "Ja", "Nej");
                if(s.equals("Nej")){
                    break;
                }
            }
        }
    }

    public void runTestThree(){
        //Acceptest three : Land on chance and receive an effect : manipulate with dice.
        while (true) {
            int n = gameControl.getUI().getNumber("Indtast terningesum", 2, 12);
            int[] pair = new int[]{n - 1, 1};
            gameControl.getGame().getDice().setDicePair(pair);
            gameControl.getGame().getCurrentPlayer().movePlayerPosition(gameControl.getGame().getDice().getSum());
            gameControl.updateUI(gameControl.getGame().getPlayers(), gameControl.getGame().getDice().getSingleDice(0), gameControl.getGame().getDice().getSingleDice(1));
            if (gameControl.getGame().getBoard().getSquare(gameControl.getGame().getCurrentPlayer().getPlayerPosition()) instanceof
                    Chance) {
                String chanceCard = gameControl.getGame().getBoard().drawCard(gameControl.getGame().getCurrentPlayer(), gameControl.getGame().getPlayers());
                gameControl.getUI().getChanceCard(chanceCard);
                gameControl.getUI().updateUI(gameControl.getGame().getPlayers(), n - 1, 1);
            }
            String s = gameControl.getUI().getUserButton("Vil du forsætte?", "Ja", "Nej");
            if (s.equals("Nej")) {
                break;
            }
        }
    }

    public void runTestFourAndFive(){
        //Acceptest four & five : Buy property and get rent : manipulate with dice.
        while (true){
            int n = gameControl.getUI().getNumber("Indtast terningesum", 2, 12);
            int[] pair = new int[]{n - 1, 1};
            gameControl.getGame().getDice().setDicePair(pair);
            gameControl.getGame().getCurrentPlayer().movePlayerPosition(gameControl.getGame().getDice().getSum());
            gameControl.updateUI(gameControl.getGame().getPlayers(), gameControl.getGame().getDice().getSingleDice(0), gameControl.getGame().getDice().getSingleDice(1));
            if (gameControl.getGame().getBoard().getSquare(gameControl.getGame().getCurrentPlayer().getPlayerPosition()) instanceof
                    Lot) {
                int position = gameControl.getGame().getCurrentPlayer().getPlayerPosition();
                gameControl.actionControl.controlAction(position);
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
