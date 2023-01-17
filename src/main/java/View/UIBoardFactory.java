package View;
import Control.Translator;
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
            return new GUI_Shipping("", square.getName(), square.getSubText(), square.getDescription(), Utility.parseIntToString(((Ship) square).getRent()) + "kr.", Color.white, Color.black);
        } else if (square instanceof Brewery) {
            return new GUI_Brewery("", square.getName(), square.getSubText(), square.getDescription(), Translator.getString("SQUARE_BREWERY_RENT"), Color.white, Color.black);
        } else if (square instanceof Chance){
            return new GUI_Chance("?", square.getSubText(), square.getDescription(), Color.BLACK, Color.GREEN);
        } else if (square instanceof Tax){
            return new GUI_Tax(square.getName(), square.getSubText(), square.getDescription(), Color.WHITE, Color.BLACK);
        } else if(square instanceof GoToJail) {
            GUI_Field goToJail = new GUI_Jail();
            goToJail.setTitle(square.getName());
            goToJail.setSubText(square.getSubText());
            goToJail.setDescription(square.getDescription());
            return goToJail;
        } else if (square instanceof VisitJail) {
            GUI_Field visitJail = new GUI_Jail();
            visitJail.setTitle(square.getName());
            visitJail.setSubText(square.getSubText());
            visitJail.setDescription(square.getDescription());
            return visitJail;
        } else if (square instanceof Metro) {
            return new GUI_Street(square.getName(), square.getSubText(), square.getDescription(), null, Color.WHITE, Color.black);
        } else if (square instanceof Parking) {
            GUI_Field parking = new GUI_Refuge();
            parking.setTitle(square.getName());
            parking.setDescription(square.getDescription());
            parking.setSubText(square.getSubText());
            return parking;
        }
        return null;
    }
}
