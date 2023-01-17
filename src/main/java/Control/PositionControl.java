package Control;

import Model.FixedValues;
import Model.Squares.Metro;

public class PositionControl {
    private GameControl gameControl;
    public PositionControl(GameControl gameControl){
        this.gameControl = gameControl;
    }

    public boolean controlAction(int sum){
        boolean hasPassedStart = false;
        int startPos = gameControl.getGame().getCurrentPlayer().getPlayerPosition();
        for (int i = 0; i < sum; i++) {
            gameControl.getGame().getCurrentPlayer().movePlayerPosition(1);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gameControl.updatePlayerInfo(gameControl.getGame().getPlayers());
            
        }
        int endPos = gameControl.getGame().getCurrentPlayer().getPlayerPosition();

        if (endPos < startPos) {
            hasPassedStart = true;
        }
        return hasPassedStart;
    }

    public boolean landsOnMetro(int position){
        String metroName = gameControl.getGame().getBoard().getSquare(position).getName();
        Metro specificMetro = gameControl.getBoard().getMetro(metroName);
        int nearestMetro = specificMetro.getNearestMetroSquare();
        gameControl.getGame().getCurrentPlayer().setPlayerPosition(nearestMetro);
        int newPosition = gameControl.getGame().getCurrentPlayer().getPlayerPosition();
        if(newPosition < position){
            return true;
        }
        return false;
    }

    public void goToJail(){
        gameControl.getGame().getCurrentPlayer().setPlayerPosition(FixedValues.VISIT_JAIL);
        gameControl.getGame().getDice().resetDouble();
    }
}
