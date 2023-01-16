package Model;

import Model.ChanceCards.Deck;
import Model.Squares.*;

public class Board {
    private final Square[] squares;
    private final Deck cardDeck;

    private final ColorGroup blue = new ColorGroup("Blue");
    private final ColorGroup orange = new ColorGroup("Orange");
    private final ColorGroup green = new ColorGroup("Green");
    private final ColorGroup grey = new ColorGroup("Grey");
    private final ColorGroup red = new ColorGroup("Red");
    private final ColorGroup white = new ColorGroup("White");
    private final ColorGroup yellow = new ColorGroup("Yellow");
    private final ColorGroup purple = new ColorGroup("Purple");
    private final ColorGroup black = new ColorGroup("Black");
    private final ColorGroup brown = new ColorGroup("Brown");

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

    public Brewery geBrewery(String breweryName){
        for (Square square : getBoard()){
            if (square instanceof Brewery){
                if (square.getName().equals(breweryName)){
                    return (Brewery) square;
                }
            }
        }
        return null;
    }


    public void drawCard(Player currentPlayer, Player[] players){
        cardDeck.pullCard(currentPlayer, players);
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
