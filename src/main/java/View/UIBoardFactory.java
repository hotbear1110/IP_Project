package View;
import Model.Squares.*;
import gui_fields.*;

import java.awt.*;

public class UIBoardFactory {

    public static GUI_Field demoBoardFactory(Square square){
        if (square instanceof Start){
            return new GUI_Start("Start", "", "hej", Color.red, Color.black);
        } else if (square instanceof Lot){
            return new GUI_Street("Rødorvevej", "", "hej", "", Color.BLUE, Color.black);
        } else if (square instanceof Chance){
            return new GUI_Chance("Chance", "", "hej", Color.BLACK, Color.GREEN);
        } else if (square instanceof DemoSquare){
            return new GUI_Street("", "", "" , "", Color.WHITE, Color.BLACK);
        }
        return null;
    }

    public static GUI_Field boardFactory(Square square){
        if (square instanceof Start){

        } else if (square instanceof Lot){

        } else if (square instanceof Chance){

        } else if (square instanceof DemoSquare){

        }
        return null;
    }
}
