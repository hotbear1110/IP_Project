package Control;

public class PositionControl {
    private GameControl gameControl;
    public PositionControl(GameControl gameControl){
        this.gameControl = gameControl;
    }

    public void controlAction(int sum){
        gameControl.getGame().getCurrentPlayer().movePlayerPosition(sum);
    }

    public boolean hasPassedStart(){
        return gameControl.getGame().getCurrentPlayer().hasPassedStart();
    }
}
