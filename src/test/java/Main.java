import Control.GameControl;
import Model.Board;
import Model.Squares.Square;
import View.UI;

public class Main {

    public static void main(String [] args){
        Board board = new Board();
        GameControl GameControl = new GameControl(board);
    }
}
