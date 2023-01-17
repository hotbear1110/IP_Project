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
    boolean manipulationPlayerAccount = false;
    boolean gameOver = false;

    public GameControl(Board board, String version) {
        this.game = new Game(board);
        this.board = board;
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
            case "K8" -> {
                k8();
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
            case "K7/K17" -> {
                k7_k17();
                }
            case "K3/K4/K5/K6/K9/K16/K24" -> {
                k3_k4_k5_k6_k9_k16_k24();
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
            ui.showMessage(Translator.getString("3_ROUND_PRISON"));
            bankControl.fromPlayerToBank(currentPlayer, FixedValues.JAIL_FEE);
            jailControl. leaveJail();
        } else if (game.isPlayerInJail(currentPlayer) && !jailControl.isJailed()){
            jailControl.jailCount();
            ui.showMessage(Translator.getString("YOU_IN_JAIL"));
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
                            ui.showMessage(Translator.getString("NO_DOUBLE"));
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
                            ui.showMessage(Translator.getString("NO_JAILCARD"));
                        }
                    }
                }
            }
        }
        while(!turnTaken){
            if(manipulationPlayerAccount){
                manipulatePlayerAccount();
                updatePlayerInfo(game.getPlayers());
                endTurn(currentPlayer);
                turnTaken = true;
                break;
            }
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
            doubleOutOfJail = false;
            if (doubleDice && diceControl.doubleDiceCounter == 3){
                ui.showMessage(Translator.getString("THREE_DOUBLE"));
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
                        ui.showMessage(Translator.getString("START_NEXT_TURN"));
                        chance = false;
                    }
                    case "Property" -> {
                        boolean hasOwner;
                        String propertyName = game.getBoard().getSquare(playerPosition).getName();
                        Property activeSquare = game.getBoard().getProperty(propertyName);
                        hasOwner = activeSquare.isPropertyOwned();
                        if (hasOwner && !activeSquare.getOwner().equals(currentPlayer)) {
                            bankControl.payRent(currentPlayer, activeSquare, diceSum);
                        } else if (!hasOwner){
                            bankControl.buyProperty(currentPlayer, activeSquare);
                        } else {
                            ui.showMessage(Translator.getString("LAND_ON") + propertyName + Translator.getString("YOU_OWN"));
                        }
                        chance = false;
                    }
                    case "Chance" -> {
                        String[] n = chanceControl.controlAction(currentPlayer);

                        if (Objects.equals(n[0], "jail")) {
                            getGame().getDice().resetDouble();
                            chance = false;
                        }

                        if (n[0].length() > 0) {
                            passedStart = positionControl.controlAction(Integer.parseInt(n[1]));

                            playerPosition = getGame().getCurrentPlayer().getPlayerPosition();
                            action = actionControl.controlAction(playerPosition);
                        } else {
                            chance = false;
                        }
                    }
                    case "Metro" -> {
                        ui.showMessage(Translator.getString("PETRO"));
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
                        ui.showMessage(Translator.getString("LAND_ON_PARKING"));
                        chance = false;
                    }
                    case "GoToJail" -> {
                        ui.showMessage(Translator.getString("GO_TO_JAIL"));
                        positionControl.goToJail();
                        jailControl.jailPlayer();
                        diceControl.resetDouble();
                        chance = false;
                    }
                    case "VisitJail" -> {
                        ui.showMessage(Translator.getString("VISIT_JAIL"));
                        chance = false;
                    }
                }
            }
            if (passedStart){
                bankControl.getPassedStart();
                passedStart = false;
            }
            updatePlayerInfo(game.getPlayers());
            doubleDice = diceControl.getDoubleDice();
            if (doubleDice && diceControl.getDoubleDiceCounter() != 3) {
                ui.showMessage(Translator.getString("DOUBLE_DICE"));
            } else {
                endTurn(currentPlayer);
                break;
            }
        }
    }

    public void endTurn(Player player){
        if (game.isAnyBankrupt()){
            gameOver = game.isThereAWinner();
            if(gameOver){
                declareWinner();
            }
        } else {
            game.setCurrentPlayer();
        }
    }

    public void declareWinner(){
        Player winner = game.getWinner();
        ui.showMessage(Translator.getString("CONGRATS") + winner.getPlayerName() + Translator.getString("REAL_MATADOR"));
        playAgain();
    }

    public void playAgain(){
        String action = ui.getUserButton(Translator.getString("PLAY_AGAIN"), Translator.getString("YES"), Translator.getString("NO"));
        switch (action){
            case "Ja":
                for (int i = 0; i < game.getBoard().getBoard().length; i++){
                    if (game.getBoard().getBoard()[i] instanceof Property){
                        ((Property) game.getBoard().getBoard()[i]).resetProperty();
                    }
                    if (game.getBoard().getBoard()[i] instanceof Lot){
                        String lot = game.getBoard().getSquare(i).getName();
                        ui.removeHouse(i, game.getBoard().getLot(lot).getNumberOfHouses());
                        ui.removeHotel(i);
                    }
                }
                ui.resetGUIPlayers();
                gameStart();
            case "Nej":
                break;
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

    public void forceEndGame(){
        gameOver = true;
    }

    private void manipulatePlayerAccount(){
        String s = ui.getUserButton(Translator.getString("PLAYER_BROKE"), Translator.getString("YES"), Translator.getString("NO"));
        switch (s){
            case "Ja":
                Player player1 = game.getPlayers()[0];
                player1.setAsBankrupt();
                break;
            case "Nej":
                break;
        }
    }
    private void k8() {
        setUpPlayers(2);
        ui.setGUIPlayers(game.getPlayers());
        manipulationPlayerAccount = true;
        runGame();
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
        diceControl.enabledDiceManipulation();
        Player player1 = game.getSpecificPlayer(0);
        String a = game.getBoard().getSquare(11).getName();
        Lot greenOne = game.getBoard().getLot(a);
        greenOne.setOwner(player1);
        player1.buyProperty(greenOne);
        String b = game.getBoard().getSquare(13).getName();
        Lot greenTwo = game.getBoard().getLot(b);
        greenTwo.setOwner(player1);
        player1.buyProperty(greenTwo);
        String c = game.getBoard().getSquare(14).getName();
        Lot greenThree = game.getBoard().getLot(c);
        greenThree.setOwner(player1);
        player1.buyProperty(greenThree);

        Player player2 = game.getSpecificPlayer(1);
        String d = game.getBoard().getSquare(21).getName();
        Lot redOne = game.getBoard().getLot(d);
        redOne.setOwner(player2);
        player2.buyProperty(redOne);
        String e = game.getBoard().getSquare(23).getName();
        Lot redTwo = game.getBoard().getLot(e);
        redTwo.setOwner(player2);
        player2.buyProperty(redTwo);
        String f = game.getBoard().getSquare(24).getName();
        Lot redThree = game.getBoard().getLot(f);
        redThree.setOwner(player2);
        player2.buyProperty(redThree);
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
    
    private void k7_k17(){
        setUpPlayers(2);
        ui.setGUIPlayers(game.getPlayers());
        diceControl.enabledDiceManipulation();
        Player player1 = game.getSpecificPlayer(0);
        Player player2 = game.getSpecificPlayer(1);
        String a = game.getBoard().getSquare(1).getName();
        Lot blueOne = game.getBoard().getLot(a);
        blueOne.setOwner(player2);
        String b = game.getBoard().getSquare(3).getName();
        Lot blueTwo = game.getBoard().getLot(b);
        blueTwo.setOwner(player2);
        String c = game.getBoard().getSquare(6).getName();
        Lot orangeOne = game.getBoard().getLot(c);
        orangeOne.setOwner(player2);
        String d = game.getBoard().getSquare(8).getName();
        Lot orangeTwo = game.getBoard().getLot(d);
        orangeTwo.setOwner(player2);
        String e = game.getBoard().getSquare(9).getName();
        Lot orangeThree = game.getBoard().getLot(e);
        orangeThree.setOwner(player2);
        runGame();
        }

    private void k3_k4_k5_k6_k9_k16_k24(){
        setUpPlayers(1);
        ui.setGUIPlayers(game.getPlayers());
        diceControl.enabledDiceManipulation();
        runGame();
    }
}
