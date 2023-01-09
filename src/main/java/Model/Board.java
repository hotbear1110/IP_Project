package Model;

import Model.Squares.Square;

public class Board {
    private final Square[] squares;
    //private final ChanceCards[] cardDeck;

    public Board(){
        this.squares = demoBoard();
        //this.squares = makeBoard();
    }
    public Square[] demoBoard(){
        return BoardFactory.makeDemoBoard();
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
