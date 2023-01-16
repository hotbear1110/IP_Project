package Control;

import Model.FixedValues;
import Model.Squares.Metro;

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

    public void landsOnMetro(int position){
        String metroName = gameControl.getGame().getBoard().getSquare(position).getName();
        Metro specificMetro = gameControl.getBoard().getMetro(metroName);
        int nearestMetro = specificMetro.getNearestMetroSquare();
        gameControl.getGame().getCurrentPlayer().setPlayerPosition(nearestMetro);
    }

    public void goToJail(){
        gameControl.getGame().getCurrentPlayer().setPlayerPosition(FixedValues.VISIT_JAIL);
    }
}
