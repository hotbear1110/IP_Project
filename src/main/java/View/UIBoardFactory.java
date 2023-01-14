package View;
import Model.Squares.*;
import gui_fields.*;

import java.awt.*;

public class UIBoardFactory {

    public static GUI_Field boardFactory(Square square){
        if (square instanceof Start){
            return new GUI_Start(square.getName(), square.getSubText(), square.getDescription(), Color.red, Color.black);
        } else if (square instanceof Lot){
            return new GUI_Street(square.getName(), square.getSubText(), square.getDescription(), "", ((Lot) square).getColor(), Color.black);
        } else if(square instanceof Ship){
            return new GUI_Shipping("",square.getName(), square.getSubText(), square.getDescription(),"",Color.WHITE, Color.black);
        } else if (square instanceof Brewery) {
            return new GUI_Brewery("",square.getName(), square.getSubText(), square.getDescription(),"",Color.WHITE, Color.black);
        } else if (square instanceof Chance){
            return new GUI_Chance("?", square.getSubText(), square.getDescription(), Color.BLACK, Color.GREEN);
        } else if (square instanceof DemoSquare){
            return new GUI_Street(square.getName(), square.getSubText(), square.getDescription(), "", Color.WHITE, Color.BLACK);
        }
        return new GUI_Street(square.getName(), square.getSubText(), square.getDescription(), "", Color.WHITE, Color.black);
    }
}
