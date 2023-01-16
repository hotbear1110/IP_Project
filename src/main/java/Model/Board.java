package Model;

import Model.ChanceCards.Deck;
import Model.Squares.*;

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
    public Ship getShip(String shipName){
        for (Square square : getBoard()){
            if (square instanceof Ship){
                if (square.getName().equals(shipName)){
                    return (Ship) square;
                }
            }
        }
        return null;
    }

    public Brewery getBrewery(String breweryName){
        for (Square square : getBoard()){
            if (square instanceof Brewery){
                if (square.getName().equals(breweryName)){
                    return (Brewery) square;
                }
            }
        }
        return null;
    }
    public int drawCard(Player currentPlayer, Player[] players){
        return cardDeck.pullCard(currentPlayer, players);
    }

    public String getNextCard() {
        return cardDeck.getNextCard();
    }

    public void addJailCard() {
        cardDeck.addJailCard();
    }

    /*
    //------------- DEMO ------------\\
    public Square[] demoBoard(){
        return BoardFactory.makeDemoBoard();
    }
    */

}
