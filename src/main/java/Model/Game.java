package Model;

public class Game {
    private int PASSED_START;
    private Player[] players;
    private Player currentPlayer;
    private Player winner;
    private final Dice dice;
    private final Board board;

    private boolean gameOver = false;

    public Game(){
        this.dice = new Dice();
        this.board = new Board();
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
    public int getDiceSum(){
        return dice.getSum();
    }

    public int[] getDicePair(){
        return dice.getPair();
    }

    public int getSingleDice(int index){
        return dice.getDice(index);
    }

    public boolean isDiceDouble(){
        return dice.isDouble();
    }

    public boolean isGameOver(){
        return gameOver;
    }

    //---------- ONLY FOR DEMO --------\\
    public void setDice(int[] pair){
        dice.setDice(pair);
    }
}
