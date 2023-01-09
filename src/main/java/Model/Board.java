package Model;

import Model.ChanceCards.Cards;
import Model.ChanceCards.Deck;
import Model.Squares.Square;

import java.util.ArrayList;

public class Board {
    private final Square[] squares;
    private final Deck cardDeck;

    public Board(){
        this.squares = demoBoard();
        //this.squares = makeBoard();
        this.cardDeck = new Deck();
        cardDeck.demoCards();
    }
    public Square[] demoBoard(){
        return BoardFactory.makeDemoBoard();
    }

    public String drawCard(Player currentPlayer, Player[] players){
        return cardDeck.pullCard(currentPlayer, players);
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

}
