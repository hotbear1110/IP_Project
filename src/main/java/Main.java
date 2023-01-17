import Control.GameControl;
import Model.Board;

public class Main {
    public static void main(String [] args){
        Board board = new Board();
        GameControl gameControl = new GameControl(board,"Matador");
    }
}
