package Control;

import Global.Utility;
import Model.Board;
import Model.FixedValues;
import Model.Game;
import Model.Player;
import Model.Squares.Property;
import View.UI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GameControl {
    private Game game;
    private UI ui;
    private Board board;

    DiceControl diceControl;
    BankControl bankControl;
    PositionControl positionControl;
    ActionControl actionControl;
    ChanceControl chanceControl;
    JailControl jailControl;
    boolean onePlayer = false;
    boolean diceManipulation = false;

    boolean gameOver = false;

    public GameControl(String version) {
        this.game = new Game();
        this.board = new Board(game);
        this.diceControl = new DiceControl(this);
        this.bankControl = new BankControl(this);
        this.positionControl = new PositionControl(this);
        this.actionControl = new ActionControl(this);
        this.chanceControl = new ChanceControl(this);
        this.jailControl = new JailControl(this);
        this.ui = new UI(board);

        switch (version){
            case "Matador" -> {
                gameStart();
            }
            case "K12/K13/K15" -> {
                k12_k13_k15();
            }
        }

    }

    public Game getGame() {
        return this.game;
    }
    public GameControl getControl(){
        return this;
    }
    public Board getBoard(){
        return this.board;
    }

    public void gameStart() {
        String language = ui.getUserButton("Choose language: ", ControlMenus.languages);

        switch (language) {
            case "English" -> {
                Translator.initLanguage("en", "US");
            }
            case "Dansk" -> {
                Translator.initLanguage("da", "DK");
            }
        }

        ui.showMessage(Translator.getString("START_MESSAGE")); // Shows start-up message
        /*
        String start = ui.getUserButton(Translator.getString("START_MESSAGE"), "Start spil", "Start demo");
        if (start.equals("Start demo")) {
            setUpDemoGame();
        } else {
            setUpGame();
        }
        */
        setUpGame();
        runGame();
    }
    private void runGame(){
        while (!gameOver){
            startTurn();
        }
    }

    private void startTurn(){
        Player currentPlayer = game.getCurrentPlayer();
        boolean turnTaken = false;
        if (game.isPlayerInJail(currentPlayer) && jailControl.isJailed()){
            ui.showMessage("Du har været i fængels i tre runder og skal nu betale 1000kr for at komme ud");
            bankControl.fromPlayerToBank(currentPlayer, FixedValues.JAIL_FEE);
        } else if (game.isPlayerInJail(currentPlayer) && !jailControl.isJailed()){
            ui.showMessage("Du er i fængsel!");
            String action = jailControl.controlAction();
            switch(action){
                case "Rul med terningerne":
                    takeTurn(currentPlayer);
                    turnTaken = true;
                case "Betal 1000kr":
                    bankControl.fromPlayerToBank(currentPlayer,FixedValues.JAIL_FEE);
                    takeTurn(currentPlayer);
                    turnTaken = true;
                case "Brug fængselskort":
                    takeTurn(currentPlayer);
                    turnTaken = true;
            }
        }
        while(!turnTaken){
            String action = ui.getUserButton(currentPlayer.getPlayerName() + " " + Translator.getString("START_TURN"), ControlMenus.startMenu);
            switch (action){
                case "Start tur" -> {
                    takeTurn(currentPlayer);
                    turnTaken = true;
                }
                case "Administrer ejede grunde" -> {
                    bankControl.buySellActions(currentPlayer);
                }
            }
        }
    }
    private void takeTurn(Player currentPlayer){
        while (true){
            boolean doubleDice;
            boolean passedStart;
            int diceSum;
            int playerPosition;
            diceControl.controlAction();
            diceSum = game.getDice().getSum();
            doubleDice = diceControl.getDoubleDice();
            updateDice();

            if (doubleDice && diceControl.doubleDiceCounter == 3){
                ui.showMessage("Du har slået dobbelt 3 gange i træk og rykker derfor direkte i fængsel! Du vil IKKE modtage penge hvis du passere start!");
                jailControl.jailPlayer();
                diceControl.resetCounter();
                updatePlayerInfo(game.getPlayers());
                endTurn(currentPlayer);
                break;
            }
            positionControl.controlAction(diceSum);
            passedStart = positionControl.hasPassedStart();
            if (passedStart){
                bankControl.getPassedStart();
            }
            playerPosition = game.getCurrentPlayer().getPlayerPosition();
            String action = actionControl.controlAction(playerPosition);
            boolean chance = true;
            while (chance) {
                switch (action) {
                    case "Start" -> {
                        ui.showMessage("Du er landet på START og vil modtage start-penge i din næste tur");
                        chance = false;
                    }
                    case "Property" -> {
                        boolean hasOwner;
                        String propertyName = game.getBoard().getSquare(playerPosition).getName();
                        Property activeSquare = game.getBoard().getProperty(propertyName);
                        hasOwner = activeSquare.isPropertyOwned();
                        if (hasOwner) {
                            bankControl.payRent(currentPlayer, activeSquare, diceSum, false);
                        } else {
                            bankControl.buyProperty(currentPlayer, activeSquare);
                        }
                        chance = false;
                    }
                    case "Chance" -> {
                        String[] n = chanceControl.controlAction(currentPlayer);

                        if (n[0].length() > 0) {
                            positionControl.controlAction(Integer.parseInt(n[1]));

                            playerPosition = getGame().getCurrentPlayer().getPlayerPosition();
                            action = actionControl.controlAction(playerPosition);
                        } else {
                            chance = false;
                        }
                    }
                    case "Metro" -> {
                        ui.showMessage("Du er landet på en metro og tager den til næste stop");
                        positionControl.landsOnMetro(playerPosition);
                        chance = false;
                    }
                    case "Tax" -> {
                        bankControl.payTax(playerPosition);
                        chance = false;
                    }
                    case "Parking" -> {
                        ui.showMessage("Du er landet på parkeringsfeltet, nyd en lille pause");
                        chance = false;
                    }
                    case "GoToJail" -> {
                        ui.showMessage("Du er skal straks rykke i fængsel!\nDu modtager IKKE penge hvis du passerer start!");
                        positionControl.goToJail();
                        jailControl.jailPlayer();
                        chance = false;
                    }
                    case "VisitJail" -> {
                        ui.showMessage("Nyd dit besøg i fængslet");
                        chance = false;
                    }
                }
            }

            updatePlayerInfo(game.getPlayers());
            updateBoard(board);

            if (doubleDice && diceControl.getDoubleDiceCounter() != 3){
                ui.showMessage("Fordi du har slået dobbelt er det din tur igen");
            } else {
                endTurn(currentPlayer);
            }
        }
    }

    public void endTurn(Player player){
        if (game.isAnyBankrupt()){
            gameOver = game.isThereAWinner();
        } else {
            game.setCurrentPlayer();
        }
    }

    public void declarePlayerBankrupt(Player player){
        player.setAsBankrupt();
    }


    private void setUpGame() {
        setUpPlayers(getNumberOfPlayers()); //Gets number of players, and gets the game model to create the player objects
        ui.setGUIPlayers(game.getPlayers()); //Gets the UI to create GUI-players based on the game models player array
    }

    private int getNumberOfPlayers() {
        return ui.getNumber(Translator.getString("NUMBER_OF_PLAYERS"), FixedValues.MIN_PLAYERS, FixedValues.MAX_PLAYERS);
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
    UI getUI(){
        return ui;
    }

    private void updateDice() {
        int x = game.getDice().getSingleDice(0);
        int y = game.getDice().getSingleDice(1);
        ui.updateDice(x, y);
    }
    void updatePlayerInfo(Player[] players){
        ui.updatePlayers(players);
    }

    void updateBoard(Board board){
        ui.updateBuildings(board);
    }

    public void forceEndGame(){
        gameOver = true;
    }

    private void k12_k13_k15() {
        setUpPlayers(2);
        ui.setGUIPlayers(game.getPlayers());
        diceControl.enabledDiceManipulation();
        runGame();
    }
}
