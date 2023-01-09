package View;
import Model.Squares.*;
import gui_fields.*;

public class UIBoardFactory {

    public static GUI_Field demoBoardFactory(Square square){
        if (square instanceof Start){
            return new GUI_Start();
        } else if (square instanceof Lot){
            return new GUI_Street();
        } else if (square instanceof Chance){
            return new GUI_Chance();
        } else if (square instanceof DemoSquare){
            return new GUI_Empty();
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
