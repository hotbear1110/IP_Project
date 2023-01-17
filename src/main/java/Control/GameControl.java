package Control;

import Global.Utility;
import Model.Board;
import Model.FixedValues;
import Model.Game;
import Model.Player;
import Model.Squares.Lot;
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
            case "K18/K19/K20" -> {
                k18_k19_k20();
            }
            case "K21/K22"-> {
                k21_k22();
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
            jailControl. leaveJail();
        } else if (game.isPlayerInJail(currentPlayer) && !jailControl.isJailed()){
            jailControl.jailCount();
            ui.showMessage("Du er i fængsel!");
            boolean jail = true;
            while(jail) {
                String action = jailControl.controlAction();
                switch (action) {
                    case "Rul med terningerne" -> {
                        diceControl.controlAction();
                        updateDice();
                        if (diceControl.getDoubleDice()) {
                            diceControl.resetCounter();
                            diceControl.resetDouble();
                            takeTurn(currentPlayer, true);
                            turnTaken = true;
                        } else {
                            ui.showMessage("Du slog ikke dobbelt og bliver i fængsel, forsøg igen næste tur");
                            endTurn(currentPlayer);
                            turnTaken = true;
                        }
                        jail = false;
                    }
                    case "Betal 1000kr" -> {
                        bankControl.fromPlayerToBank(currentPlayer, FixedValues.JAIL_FEE);
                        takeTurn(currentPlayer, false);
                        turnTaken = true;
                        jail = false;
                    }
                    case "Brug fængselskort" -> {
                        if (jailControl.hasJailCard()) {
                            jailControl.useJailCard();
                            takeTurn(currentPlayer, false);
                            turnTaken = true;
                            jail = false;
                        } else {
                            ui.showMessage("Du har ikke et fængselskort");
                        }
                    }
                }
            }
        }
        while(!turnTaken){
            String action = ui.getUserButton(currentPlayer.getPlayerName() + " " + Translator.getString("START_TURN"), ControlMenus.startMenu);
            switch (action){
                case "Start tur" -> {
                    takeTurn(currentPlayer, false);
                    turnTaken = true;
                }
                case "Administrer ejede grunde" -> {
                    bankControl.buySellActions(currentPlayer);
                }
            }
        }
    }
    private void takeTurn(Player currentPlayer, boolean doubleOutOfJail){
        while (true){
            boolean doubleDice = false;
            boolean passedStart = false;
            int diceSum = game.getDice().getSum();
            int playerPosition;
            if (!doubleOutOfJail){
                diceControl.controlAction();
                diceSum = game.getDice().getSum();
                doubleDice = diceControl.getDoubleDice();
                updateDice();
            }
            if (doubleDice && diceControl.doubleDiceCounter == 3){
                ui.showMessage("Du har slået dobbelt 3 gange i træk og rykker derfor direkte i fængsel! Du vil IKKE modtage penge hvis du passere start!");
                jailControl.jailPlayer();
                diceControl.resetCounter();
                updatePlayerInfo(game.getPlayers());
                endTurn(currentPlayer);
                break;
            }
            passedStart = positionControl.controlAction(diceSum);
            if (passedStart){
                bankControl.getPassedStart();
                passedStart = false;
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
                            passedStart = positionControl.controlAction(Integer.parseInt(n[1]));

                            playerPosition = getGame().getCurrentPlayer().getPlayerPosition();
                            action = actionControl.controlAction(playerPosition);
                        } else {
                            chance = false;
                        }
                    }
                    case "Metro" -> {
                        ui.showMessage("Du er landet på en metro og tager den til næste stop");
                        if(positionControl.landsOnMetro(playerPosition)){
                            bankControl.getPassedStart();
                            passedStart = false;
                        }
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
            if (passedStart){
                bankControl.getPassedStart();
                passedStart = false;
            }

            doubleDice = diceControl.getDoubleDice();

            updatePlayerInfo(game.getPlayers());
            updateBoard(board);

            if (doubleDice && diceControl.getDoubleDiceCounter() != 3){
                ui.showMessage("Fordi du har slået dobbelt er det din tur igen");
            } else {
                endTurn(currentPlayer);
                break;
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
    private void k18_k19_k20(){
        setUpPlayers(2);
        ui.setGUIPlayers(game.getPlayers());
        Player player1 = game.getSpecificPlayer(0);
        Player player2 = game.getSpecificPlayer(1);
        String s = game.getBoard().getSquare(11).getName();
        Lot f = game.getBoard().getLot(s);
        f.setOwner(player1);
        player1.buyProperty((Property) f);
        String g = game.getBoard().getSquare(13).getName();
        Lot b = game.getBoard().getLot(g);
        b.setOwner(player1);
        player1.buyProperty((Property) b);
        String h = game.getBoard().getSquare(14).getName();
        Lot t = game.getBoard().getLot(h);
        t.setOwner(player1);
        player1.buyProperty((Property) t);
        String j = game.getBoard().getSquare(21).getName();
        Lot z = game.getBoard().getLot(j);
        z.setOwner(player2);
        player2.buyProperty((Property) z);
        String q = game.getBoard().getSquare(23).getName();
        Lot w = game.getBoard().getLot(q);
        w.setOwner(player2);
        player2.buyProperty((Property) w);
        String x = game.getBoard().getSquare(24).getName();
        Lot e = game.getBoard().getLot(x);
        e.setOwner(player2);
        player2.buyProperty((Property) e);
        runGame();
    }
    private void k21_k22(){
        setUpPlayers(2);
        ui.setGUIPlayers(game.getPlayers());
        diceControl.enabledDiceManipulation();
        Player player1 = game.getSpecificPlayer(0);
        Player player2 = game.getSpecificPlayer(1);
        String h = game.getBoard().getSquare(6).getName();
        Lot w = game.getBoard().getLot(h);
        w.setOwner(player2);
        player2.buyProperty((Property) w);
        String t = game.getBoard().getSquare(3).getName();
        Lot q = game.getBoard().getLot(t);
        q.setOwner(player1);
        player1.buyProperty((Property) q);
        runGame();
    }
}
