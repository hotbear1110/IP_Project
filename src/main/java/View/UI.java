package View;

import Model.Board;
import Model.FixedValues;
import Model.Player;
import gui_fields.GUI_Car;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
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
        this.ui = createDemoBoard(board);
        //this.ui = createBoard(board);
    }

    //----------- GUI-Board methods --------\\
    public GUI createDemoBoard(Board board){
        this.guiBoard = new GUI_Field[FixedValues.NUM_OF_SQUARES];

        for (int i = 0; i < FixedValues.NUM_OF_SQUARES; i++){
            guiBoard[i] = UIBoardFactory.demoBoardFactory(board.getSquare(i));
        }
        return this.ui = new GUI(guiBoard, FixedValues.BOARD_COLOR);
    }

    public GUI createBoard(Board board){
        this.guiBoard = new GUI_Field[FixedValues.NUM_OF_SQUARES];

        for (int i = 0; i < FixedValues.NUM_OF_SQUARES; i++){
            guiBoard[i] = UIBoardFactory.boardFactory(board.getSquare(i));
        }
        return this.ui = new GUI(guiBoard, FixedValues.BOARD_COLOR);
    }

    //---------- GUI-Player methods ---------\\
    public int getNumOfPlayers(){
        return getNumber("", FixedValues.MIN_PLAYERS, FixedValues.MAX_PLAYERS);
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
        }
    }

    public GUI_Player getGUIPlayer(Player player){
        return players.get(player);
    }

    //------------- INPUT & OUTPUT METHODS ------------\\
    public void showMessage(String message){
        ui.showMessage(message);
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
}
