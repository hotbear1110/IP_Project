package Model;

public class Game {
    private int PASSED_START;
    private Player[] players;
    private Player currentPlayer;
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

    public void rollDice(){
        dice.rollDice();
    }


    public String drawChanceCard(){
        return board.drawCard(currentPlayer, players);
    }

    public Dice getDice(){
        return this.dice;
    }


    public boolean isGameOver() {
        return gameOver;
    }
}
