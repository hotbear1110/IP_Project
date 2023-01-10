package Control;

import Global.Utility;
import Model.Board;
import Model.FixedValues;
import Model.Game;
import Model.Player;
import View.UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameControl {
    private Game game;
    private UI ui;
    private Board board;

    DiceControl diceControl;
    BankControl bankControl;
    PositionControl positionControl;
    ActionControl actionControl;
    ChanceControl chanceControl;
    DemoControl demoControl;

    boolean gameOver = false;

    public GameControl(Board board) {
        this.game = new Game();
        this.board = board;
        this.diceControl = new DiceControl(this);
        this.bankControl = new BankControl(this);
        this.positionControl = new PositionControl(this);
        this.actionControl = new ActionControl(this);
        this.chanceControl = new ChanceControl(this);
        this.demoControl = new DemoControl(this);
        this.ui = new UI(board);

        gameStart();
    }

    public Game getGame() {
        return this.game;
    }

    public Board getBoard(){
        return this.board;
    }

    private void gameStart() {
        ui.showMessage(Translator.getString("START_MESSAGE")); // Shows start-up message
        String start = ui.getUserButton(Translator.getString("START_MESSAGE"), "Start spil", "Start demo");
        if (start.equals("Start demo")) {
            setUpDemoGame();
        } else {
            setUpGame();
        }
    }
    private void runGame(){

    }

    private void setUpGame() {
        setUpPlayers(getNumberOfPlayers()); //Gets number of players, and gets the game model to create the player objects
        ui.setGUIPlayers(game.getPlayers()); //Gets the UI to create GUI-players based on the game models player array
    }

    private int getNumberOfPlayers() {
        return ui.getNumOfPlayers();
    }

    private void setUpPlayers(int number) {
        String[] players = new String[number]; //Creates a new string with an index number based on number of players
        for (int i = 0; i < players.length; i++) {
            players[i] = ui.getPlayerName(Translator.getString("PLAYER_NAMES")); //User inputs a string for each index in the array
        }
        //--------- MIGHT BE REDUNDANT --------\\
        String[] playersInOrder = decideFirstPlayer(players); //Creates a new String array based on the users choice of first player
        game.setPlayers(playersInOrder); //Returns the String array of player names in playing order to the game model, that creates the player objects.
        //-------------------------------------\\
        //game.createPlayers(players); Returns the String array of names to the game model, which then creates the Player objects - Here the user has chosen playing order by writing the first name as player one.
    }


    // ------------- MIGHT BE REDUNDANT --------- \\
    private String[] decideFirstPlayer(String[] names) {
        List<String> list = Arrays.asList(names);
        switch (names.length) {
            case 3 -> {
                String s = ui.getUserButton(Translator.getString("CHOOSE_FIRST_PLAYER"), "1. " + names[0], "2. " + names[1], "3. " + names[2]);
                int n = Utility.parseFirstInt(s) - 1;
                Utility.moveToFront(list, n);
            }
            case 4 -> {
                String s = ui.getUserButton(Translator.getString("CHOOSE_FIRST_PLAYER"), "1. " + names[0], "2. " + names[1], "3. " + names[2], "4. " + names[3]);
                int n = Utility.parseFirstInt(s) - 1;
                Utility.moveToFront(list, n);
            }
            case 5 -> {
                String s = ui.getUserButton(Translator.getString("CHOOSE_FIRST_PLAYER"), "1. " + names[0], "2. " + names[1], "3. " + names[2], "4. " + names[3], "5. " + names[4]);
                int n = Utility.parseFirstInt(s) - 1;
                Utility.moveToFront(list, n);
            }
            case 6 -> {
                String s = ui.getUserButton(Translator.getString("CHOOSE_FIRST_PLAYER"), "1. " + names[0], "2. " + names[1], "3. " + names[2], "4. " + names[3], "5. " + names[4], "6. " + names[5]);
                int n = Utility.parseFirstInt(s) - 1;
                Utility.moveToFront(list, n);
            }
        }
        return list.toArray(new String[0]);
    }
    private void takeTurn(){
        ui.updatePlayers(game.getPlayers());
        if (diceControl.getManipulationStatus()){
            int diceSum = ui.getNumber(diceControl.getControlMenu(), FixedValues.MIN_DICE_SUM, FixedValues.MAX_DICE_SUM);
            int[] dicePair = new int[]{diceSum - 1, 1};
            diceControl.controlAction(dicePair);
            updateDice(game.getDice().getSingleDice(0), game.getDice().getSingleDice(1));
            positionControl.controlAction(diceSum);
            updatePlayerInfo(game.getPlayers());
            if (positionControl.hasPassedStart()){
                bankControl.getPassedStart();
                actionControl.controlAction(diceSum);
            } else {
                actionControl.controlAction(diceSum);
            }
            endTurn();
        } else if (bankControl.getManipulationStatus()){
            diceControl.controlAction(null);
            updateDice(game.getDice().getSingleDice(0), game.getDice().getSingleDice(1));
            positionControl.controlAction(game.getDice().getSum());
            updatePlayerInfo(game.getPlayers());
            if (positionControl.hasPassedStart()){
                bankControl.getPassedStart();
                actionControl.controlAction(game.getDice().getSum());
            } else {
                actionControl.controlAction(game.getDice().getSum());
            }
            String s = ui.getUserButton(bankControl.getMenu(), game.getSpecificPlayer(0).getPlayerName(), game.getSpecificPlayer(1).getPlayerName(), "Ingen i denne omgang");
            if (s.equals(game.getSpecificPlayer(0).getPlayerName())){
                bankControl.removeMoney(game.getSpecificPlayer(0), game.getSpecificPlayer(0).getPlayerBalance());
            } else if (s.equals(game.getSpecificPlayer(1).getPlayerName())){
                bankControl.removeMoney(game.getSpecificPlayer(1), game.getSpecificPlayer(1).getPlayerBalance());
            }
            endTurn();
        } else {
            diceControl.controlAction(null);
            updateDice(game.getDice().getSingleDice(0), game.getDice().getSingleDice(1));
            positionControl.controlAction(game.getDice().getSum());
            updatePlayerInfo(game.getPlayers());
            if (positionControl.hasPassedStart()){
                bankControl.getPassedStart();
                actionControl.controlAction(game.getDice().getSum());
            } else {
                actionControl.controlAction(game.getDice().getSum());
            }
            endTurn();
        }
    }
    private void endTurn() {
        if (!game.isAnyBankrupt()){
            getGame().setCurrentPlayer();
        } else if (game.isThereAWinner()){
            endGame();
        }
    }

    private void endGame(){
        ui.showMessage(game.getWinner().getPlayerName() + " \n" + Translator.getString("WIN_GAME"));
        String s = ui.getUserButton("Spillet er slut, vil I spille igen?", "Ja", "Nej");
        if (s.equals("Nej")){
            gameOver = true;
        }
    }

    //--------- DEMO GAME --------\\

    private void setUpDemoGame() {
        setDemoPlayers(2); //MAYBE JUST CHOOSE ONE OR TWO PLAYERS
        while (!gameOver) {
            String testType = ui.getDropDown("Vælg en accepttest", demoControl.getMenu());
            demoControl.setUpTest(testType);
            runDemoGame();
        }
    }
    private void runDemoGame(){
        while(true){
            takeTurn();
            String s = ui.getUserButton("Vil du forsætte?", "Ja" , "Nej");
            if(s.equals("Nej")){
                break;
            }
        }
    }

    private void setDemoPlayers(int number){
        String[] players = new String[number];
        for (int i = 0; i < players.length; i++){
            players[i] = "Player" + i;
        }
        game.setPlayers(players);
        ui.setGUIPlayers(game.getPlayers());
    }
    //----------------------------\\
    UI getUI(){
        return ui;
    }

    private void updateDice(int x, int y) {
        ui.updateDice(x, y);
    }
    private void updatePlayerInfo(Player[] players){
        ui.updatePlayers(players);
    }

    public void forceEndGame(){
        gameOver = true;
    }
}
