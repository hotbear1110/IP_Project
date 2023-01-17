import Control.GameControl;
import Model.Board;
import Model.Game;

public class PawnAPropertyAndRebuy {
    public static void main(String[] args ){
        Game game = new Game();
        Board board = new Board(game);
        GameControl gameControl = new GameControl("K21/K22");
    }
}
