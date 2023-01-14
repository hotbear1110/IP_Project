package Model;

import Model.ChanceCards.Deck;
import Model.Squares.Lot;
import Model.Squares.Metro;
import Model.Squares.Property;
import Model.Squares.Square;

public class Board {
    private final Square[] squares;
    private final Deck cardDeck;

    public Board(){
        //this.squares = demoBoard();
        this.squares = makeBoard();
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

    public Property getProperty(String propertyName){
        for (Square square : getBoard()){
            if (square instanceof Lot){
                if (square.getName().equals(propertyName)){
                    return (Property) square;
                }
            }
        }
        return null;
    }
    public Lot getLot(String lotName){
        for (Square square : getBoard()){
            if (square instanceof Lot){
                if (square.getName().equals(lotName)){
                    return (Lot) square;
                }
            }
        }
        return null;
    }

    public Metro getMetro(String metroName){
        for(Square square : getBoard()){
            if(square instanceof Metro){
                if(square.getName().equals(metroName)){
                    return (Metro) square;
                }
            }
        }
        return null;
    }

    public String drawCard(Player currentPlayer, Player[] players){
        return cardDeck.pullCard(currentPlayer, players);
    }

    /*
    //------------- DEMO ------------\\
    public Square[] demoBoard(){
        return BoardFactory.makeDemoBoard();
    }
    */

}
