import Control.GameControl;
import Model.Board;

public class MoveAroundTheBoard {
    public static void main(String[] args ){
        Board board = new Board();
        GameControl gameControl = new GameControl(board, "K3/K4/K5/K6/K9/K16/K24");
    }
}
