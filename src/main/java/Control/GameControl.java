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
    PositionControl positionControl;
    ActionControl actionControl;

    ChanceControl chanceControl;

    public GameControl(Board board){
        this.board = board;
        this.diceControl = new DiceControl(this);
        this.positionControl = new PositionControl(this);
        this.actionControl = new ActionControl(this);
        this.chanceControl = new ChanceControl(this);
        this.ui = new UI(board);

        runDemoGame();
    }

    public Game getGame(){
        return this.game;
    }
    //--------- DEMO GAME --------\\
    private void runDemoGame(){
        setUpDemoGame();
        while(!game.isGameOver()){
            playDemoTurn(game.getCurrentPlayer());
            endTurn();
        }
    }
    private void endTurn(){
        getGame().setCurrentPlayer();
    }
    private void setUpDemoGame(){
        ui.showMessage(""); // Shows start-up message

        setUpPlayers(getNumberOfPlayers()); //Gets number of players, and gets the game model to create the player objects

        ui.setGUIPlayers(game.getPlayers()); //Gets the UI to create GUI-players based on the game models player array

        playDemoTurn(game.getCurrentPlayer());
    }

    public void playDemoTurn(Player player){
            ui.getUserButton(game.getCurrentPlayer().getPlayerName() + "", diceControl.getControlMenu());

            diceControl.controlAction(); //Makes the game model roll dice and updates the UI.

            positionControl.controlAction(getGame().getDiceSum()); //Changes the models player objects position and updates the UI.

            actionControl.controlAction(getGame().getCurrentPlayer().getPlayerPosition()); //Handles appropriate action based on the player objects position and the models board layout.

    }
    //----------------------------\\
    public int getNumberOfPlayers(){
        return ui.getNumOfPlayers();
    }
    public void setUpPlayers (int number){
        String[] players = new String[number]; //Creates a new string with an index number based on number of players
        for (int i = 0 ; i < players.length ; i++){
            players[i] = ui.getPlayerName(""); //User inputs a string for each index in the array
        }
        //--------- MIGHT BE REDUNDANT --------\\
        String[] playersInOrder = decideFirstPlayer(players); //Creates a new String array based on the users choice of first player
        game.setPlayers(playersInOrder); //Returns the String array of player names in playing order to the game model, that creates the player objects.
        //-------------------------------------\\
        //game.createPlayers(players); Returns the String array of names to the game model, which then creates the Player objects - Here the user has chosen playing order by writing the first name as player one.
    }


    // ------------- MIGHT BE REDUNDANT --------- \\
    public String[] decideFirstPlayer(String[] names){
        List<String> list = Arrays.asList(names);
            switch (names.length){
                case 3 -> {
                    String s = ui.getUserButton("", "1. " + names[0], "2. " + names[1], "3. " + names[2]);
                    int n = Utility.parseFirstInt(s) - 1;
                    Utility.moveToFront(list, n);
                }
                case 4 -> {
                   String s = ui.getUserButton("", "1. " + names[0], "2. " + names[1], "3. " + names[2], "4. " + names [3]);
                    int n = Utility.parseFirstInt(s) - 1;
                    Utility.moveToFront(list, n);
                }
                case 5 -> {
                    String s = ui.getUserButton("", "1. " + names[0], "2. " + names[1], "3. " + names[2], "4. " + names [3], "5. " + names [4]);
                    int n = Utility.parseFirstInt(s) - 1;
                    Utility.moveToFront(list, n);
                }
                case 6 -> {
                    String s = ui.getUserButton("", "1. " + names[0], "2. " + names[1], "3. " + names[2], "4. " + names [3], "5. " + names [4], "6. " + names [5]);
                    int n = Utility.parseFirstInt(s) - 1;
                    Utility.moveToFront(list, n);
                }
            }
        return list.toArray(new String[0]);
    }
}
