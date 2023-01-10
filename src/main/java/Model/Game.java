package Model;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private Player[] players;
    private Player currentPlayer;
    private int bankruptPlayers = 0;
    private Player winner;
    private final Dice dice;
    private final Board board;

    private boolean gameOver;

    public Game(){
        this.dice = new Dice();
        this.board = new Board();
        this.gameOver = false;
    }

    public void setPlayers(String[] names){
        Player[] players = new Player[names.length];
        for(int i = 0; i < names.length; i++){
            players[i] = new Player(names[i]);
        }
        this.players = players;
        currentPlayer = players[0];
    }

    public Player[] getPlayers(){
        return players;
    }
    public Player getSpecificPlayer(int i){
        return players[i];
    }

    public Board getBoard(){
        return this.board;
    }

    //----------- SHOULD PROBABLY GO IN RULES?? -------- \\
    public void setCurrentPlayer(){
        int index = 0;
        for(int i = 0 ; i < players.length ;  i ++){
            if (players[i].equals(currentPlayer)){
                index = (i == players.length - 1) ? 0 : i + 1;
            }
        } currentPlayer = players[index];
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public Player getWinner(){
        return winner;
    }

    public Dice getDice(){
        return this.dice;
    }

    public void removePlayer(Player player){
        Player[] temp = new Player[players.length];
        for (int i = 0; i < temp.length; i++){
            if(players[i] == player){
                i++;
            }
            temp[i] = players[i];
        }
        players = temp;
        bankruptPlayers++;
    }
    public boolean isThereAWinner() {
        if(bankruptPlayers == players.length - 1){
            winner = players[0];
            return true;
        }
       return false;
    }
}
