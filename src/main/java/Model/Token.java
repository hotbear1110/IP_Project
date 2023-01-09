package Model;
import Model.Player;
public class Token {
    private int position; //The token position

    public Token() {
        this.position = FixedValues.START_SQUARE;
    }

    public boolean moveToken(int dice) {
        //If the new position is greater than 40, the player has lapped the board and the position goes back to 0
        //Else the dice sum is just added to the current position
        if (position + dice > 40) {
            position = position + dice - 40;
            return true;
        } else {
            position += dice;
            return false;
        }
    }

    public boolean setPosition(int newPosition) {
        if (newPosition <= position){
            position = newPosition;
            return true;
        } else {
            position = newPosition;
            return false;
        }
    }

    public int getPosition() {
        return position;
    }
}
