package View;

import Control.Translator;
import Model.Board;
import Model.Dice;
import Model.FixedValues;
import Model.Player;
import Model.Squares.Lot;
import Model.Squares.Property;
import gui_fields.GUI_Car;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Street;
import gui_main.GUI;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class UI {
    private GUI ui;

    private GUI_Field[] guiBoard;
    private Map<Player, GUI_Player> players;

    public UI(Board board){
        this.players = new HashMap<>();
        this.ui = createBoard(board);
    }

    public void updateDice(int x, int y){
        ui.setDice(x, y);
    }

    public void updatePlayers(Player[] players){
        for (Player player : players) {
            getGUIPlayer(player).getCar().setPosition(ui.getFields()[player.getPlayerPosition()]);
            getGUIPlayer(player).setBalance(player.getPlayerBalance());
        }
    }

   public void addHouse(int index, int houseNumber){
        GUI_Field field = guiBoard[index];
        GUI_Street street = (GUI_Street) field;
        street.setHouses(houseNumber);
   }
    public void removeHouse(int index, int newHouseNumber){
        GUI_Field field = guiBoard[index];
        GUI_Street street = (GUI_Street) field;
        street.setHouses(newHouseNumber);
    }

    public void addHotel(int index){
        GUI_Field field = guiBoard[index];
        GUI_Street street = (GUI_Street) field;
        street.setHotel(true);
    }

    public void removeHotel(int index){
        GUI_Field field = guiBoard[index];
        GUI_Street street = (GUI_Street) field;
        street.setHotel(false);
    }
    //----------- GUI-Board methods --------\\

    public GUI createBoard(Board board){
        this.guiBoard = new GUI_Field[FixedValues.NUM_OF_SQUARES];

        for (int i = 0; i < FixedValues.NUM_OF_SQUARES; i++){
            guiBoard[i] = UIBoardFactory.boardFactory(board.getSquare(i));
        }
        return this.ui = new GUI(guiBoard, FixedValues.BOARD_COLOR);
    }

    //---------- GUI-Player methods ---------\\
    public int getNumOfPlayers(){
        return getNumber(Translator.getString("NUMBER_OF_PLAYERS"), FixedValues.MIN_PLAYERS, FixedValues.MAX_PLAYERS);
    }

    public String getPlayerName(String text){
        return getString(text);
    }

    public void setGUIPlayers(Player[] players){
        for (int i = 0; i < players.length; i++) {
            Color[] colors = {Color.red, Color.blue, Color.green, Color.yellow, Color.black, Color.white};
            GUI_Car guiCar = new GUI_Car();
            guiCar.setPrimaryColor(colors[i]);
            GUI_Player guiPlayer = new GUI_Player(players[i].getPlayerName(), players[i].getPlayerBalance(), guiCar);
            this.players.put(players[i], guiPlayer);
            ui.addPlayer(guiPlayer);
            getGUIPlayer(players[i]).getCar().setPosition(guiBoard[0]);
        }
    }

    public void resetGUIPlayers(){
        players.clear();
    }
    public GUI_Player getGUIPlayer(Player player){
        return players.get(player);
    }

    //------------- INPUT & OUTPUT METHODS ------------\\
    public void showMessage(String message){
        ui.showMessage(message);
    }

    public boolean getYesNoAnswer(String message){
        return ui.getUserLeftButtonPressed(message, Translator.getString("YES"),Translator.getString("NO"));
    }
    public int getNumber(String message, int min, int max){
        return ui.getUserInteger(message, min , max);
    }

    public String getString(String message){
        return ui.getUserString(message);
    }

    public String getUserButton(String message, String ... button){
        return ui.getUserButtonPressed(message, button);
    }

    public String getDropDown(String message, String ... choice){
        return ui.getUserSelection(message, choice);
    }

    public void setChanceCard(String description){
        ui.setChanceCard(description);
    }

    public void getChanceCard(String description){
        ui.displayChanceCard(description);
    }
}
