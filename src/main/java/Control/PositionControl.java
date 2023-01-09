package Control;

public class PositionControl {
    private GameControl gameControl;
    public PositionControl(GameControl gameControl){
        this.gameControl = gameControl;
    }

    public int controlAction(int sum){
        gameControl.getGame().getCurrentPlayer().movePlayerPosition(sum);
        //update ui
        return gameControl.getGame().getCurrentPlayer().getPlayerPosition();
    }
}
