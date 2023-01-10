package Model;

import Model.ChanceCards.Deck;
import Model.Squares.Lot;
import Model.Squares.Property;
import Model.Squares.Square;

public class Board {
    private final Square[] squares;
    private final Deck cardDeck;

    public Board(){
        this.squares = demoBoard();
        //this.squares = makeBoard();
        this.cardDeck = new Deck();
    }
    private Square[] makeBoard(){
        return BoardFactory.makeBoard();
    }
    public Square[] getBoard(){
        return squares;
    }
    public Square getSquare(int index){
        return squares[index];
    }

    public String drawCard(Player currentPlayer, Player[] players){
        return cardDeck.pullCard(currentPlayer, players);
    }

    //------------- DEMO ------------\\
    public Square[] demoBoard(){
        return BoardFactory.makeDemoBoard();
    }

}
