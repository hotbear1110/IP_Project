package Model;

import Model.Squares.*;

import java.awt.*;

public class BoardFactory {

    public static Square[] makeDemoBoard(){
        Square[] demoBoard = new Square[FixedValues.NUM_OF_SQUARES];
        demoBoard[0] = new Start("","","");
        demoBoard[1] = new Lot("", "", "", 0, 0, PricesFactory.rentTable()[0], Color.WHITE);
        demoBoard[2] = new Chance("", "", "");
        demoBoard[3] = new Lot("", "", "", 0, 0, PricesFactory.rentTable()[1], Color.WHITE);
        for (int i = 4; i < FixedValues.NUM_OF_SQUARES; i++){
            demoBoard[i] = new DemoSquare("","",""); {
            }
        }
        return demoBoard;
    }
    public static Square[] makeBoard(){
        return new Square[]{
        };
    }
}
