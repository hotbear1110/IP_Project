package Control;
import Model.Squares.*;

import java.util.ArrayList;

public class ActionControl {
    private GameControl gameControl;
    public ActionControl(GameControl gameControl){
        this.gameControl = gameControl;
    }

    public String controlAction(int position){
        Square squareType = gameControl.getGame().getBoard().getSquare(position);
        if (squareType instanceof Property){
            return "Property";
        }
        if (squareType instanceof Chance){
            return "Chance";
        }
        if (squareType instanceof Tax) {
            return "Tax";
        }
        if (squareType instanceof Metro) {
            return "Metro";
        }
        if (squareType instanceof Start) {
            return "Start";
        }
        if (squareType instanceof Parking){
            return "Parking";
        }
        if (squareType instanceof GoToJail){
            return "GoToJail";
        }
        if (squareType instanceof VisitJail){
            return "VisitJail";
        }
        return null;
    }


}
