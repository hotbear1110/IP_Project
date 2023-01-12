package View;
import Model.Squares.*;
import gui_fields.*;

import java.awt.*;

public class UIBoardFactory {

    public static GUI_Field boardFactory(Square square){
        if (square instanceof Start){
            return new GUI_Start(square.getName(), "", "", Color.red, Color.black);
        } else if (square instanceof Lot){
            return new GUI_Street(square.getName(), "", "", "", ((Lot) square).getColor(), Color.black);
        } else if (square instanceof Chance){
            return new GUI_Chance("?", "", "", Color.BLACK, Color.GREEN);
        }
        return null;
    }
}
