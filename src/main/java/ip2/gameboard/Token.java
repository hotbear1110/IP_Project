package ip2.gameboard;
import ip2.game.Player;
public class Token {private final Player player; //The player assigned to the token
    private int position; //The token position

    /**
     * The constructor for token
     * @param Player (Player) The player assigned to the token
     */
    public Token(Player Player) {
        this.player = Player;
        this.position = 0;
    }

    /**
     * Moves the position of the token.
     * @param dice (int) The total dice sum.
     * @return (boolean) True if the player passes start
     */
    public boolean moveToken(int dice) {
        //If the new position is greater than 40, the player has lapped the board and the position goes back to 0
        //Else the dice sum is just added to the current position
        if (position + dice >= 40) {
            position = position + dice - 40;
            return true;
        } else {
            position += dice;
            return false;
        }
    }

    /**
     * Sets the token position to a given value
     * @param newPosition (int) The position the token should be at
     * @return (boolean) True if the player passes start
     */
    public boolean setPosition(int newPosition) {
        if (newPosition <= position){
            position = newPosition;
            return true;
        } else {
            position = newPosition;
            return false;
        }
    }

    /**
     * @return (int) Current position of the token.
     */
    public int getPosition() {
        return position;
    }
}
