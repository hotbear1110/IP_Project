package Control;

public class PositionControl {
    private GameControl gameControl;
    public PositionControl(GameControl gameControl){
        this.gameControl = gameControl;
    }

    public void controlAction(int sum){
        for (int i = 0; i < sum; i++) {
            gameControl.getGame().getCurrentPlayer().movePlayerPosition(1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gameControl.updatePlayerInfo(gameControl.getGame().getPlayers());
        }
    }
    public boolean hasPassedStart(){
        return gameControl.getGame().getCurrentPlayer().hasPassedStart();
    }
}
