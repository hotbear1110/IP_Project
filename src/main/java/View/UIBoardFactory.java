package View;
import Global.Utility;
import Model.Squares.*;
import gui_fields.*;

import java.awt.*;

public class UIBoardFactory {

    public static GUI_Field boardFactory(Square square){
        if (square instanceof Start){
            return new GUI_Start(square.getName(), square.getSubText(), square.getDescription(), Color.red, Color.black);
        } else if (square instanceof Lot){
            return new GUI_Street(square.getName(), square.getSubText(), square.getDescription(), Utility.parseIntToString(((Lot) square).getRent()) + "kr.", ((Lot) square).getColor(), Color.black);
        } else if(square instanceof Ship){
            return new GUI_Shipping("",square.getName(), square.getSubText(), square.getDescription(),"",Color.WHITE, Color.black);
        } else if (square instanceof Brewery) {
            return new GUI_Brewery("",square.getName(), square.getSubText(), square.getDescription(),"",Color.WHITE, Color.black);
        } else if (square instanceof Chance){
            return new GUI_Chance("?", square.getSubText(), square.getDescription(), Color.BLACK, Color.GREEN);
        } else if (square instanceof Tax){
            return new GUI_Tax(square.getName(), square.getSubText(), square.getDescription(), Color.WHITE, Color.BLACK);
        } else if(square instanceof GoToJail) {
            return new GUI_Jail("", square.getName(), square.getSubText(), square.getDescription(), Color.black, Color.WHITE);
        } else if (square instanceof VisitJail) {
            return new GUI_Jail("", square.getName(), square.getSubText(), square.getDescription(), Color.black, Color.WHITE);

        }
        return new GUI_Street(square.getName(), square.getSubText(), square.getDescription(), "", Color.WHITE, Color.black);
    }
}
