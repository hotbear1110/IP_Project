package Control;

public class PositionControl {
    private GameControl gameControl;
    public PositionControl(GameControl gameControl){
        this.gameControl = gameControl;
    }

    public boolean controlAction(int sum){
        gameControl.getGame().getCurrentPlayer().movePlayerPosition(sum);

        if (gameControl.getGame().getCurrentPlayer().hasPassedStart()){
            return true;
        } else {
            return false;
        }

    }
}
