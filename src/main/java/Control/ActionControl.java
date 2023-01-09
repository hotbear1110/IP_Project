package Control;

import Model.Squares.Chance;
import Model.Squares.DemoSquare;
import Model.Squares.Lot;
import Model.Squares.Start;

public class ActionControl {
    private GameControl gameControl;
    public ActionControl(GameControl gameControl){
        this.gameControl = gameControl;
    }

    public void controlAction(int position){
        if (gameControl.getGame().getBoard().getSquare(position) instanceof Start){

        }
        if (gameControl.getGame().getBoard().getSquare(position) instanceof Lot){

        }
        if (gameControl.getGame().getBoard().getSquare(position) instanceof Chance) {

        }
        if (gameControl.getGame().getBoard().getSquare(position) instanceof DemoSquare) {

        }
    }
}
