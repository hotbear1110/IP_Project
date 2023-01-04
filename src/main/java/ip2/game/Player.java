package ip2.game;

public class Player {
    private String playerName; //The players name

    private Account account; //The account associated with the player

    private Token token; //The token associated with the player

    private boolean hasJailCard; //If the player has a jail card

    private boolean isJailed; //If the player is in jail

    /**
     * The constructor for the player
     * @param Name (String) The player name
     * @param START_AMOUNT (int) The player start balance
     */
    public Player(String Name, int START_AMOUNT) {
        playerName = Name;
        account = new Account(START_AMOUNT);
        token = new Token(this);
        hasJailCard = false;
        isJailed = false;
    }

    /**
     * Sets the player name
     * @param name (String) The player name
     */
    public void setPlayerName(String name) {
        playerName = name;
    }

    /**
     * @return (String) The player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Adds balance to the player balance
     * @param value (int) the amount to be added to the player balance
     */
    public void setPlayerBalance(int value) {
        account.setBalance(value);
    }

    /**
     * @return (int) The player balance
     */
    public int getPlayerBalance() {
        return account.getBalance();
    }

    /**
     * Moves the player a given amount of fields
     * @param dice (int) the die sum
     * @return (boolean) True if the player passes start
     */
    public boolean movePlayerPosition(int dice) {
        return token.moveToken(dice);
    }

    /**
     * Moves the player to a specific spot on the board
     * @param value (int) the spot the player is moved to
     * @return (boolean) True if the player passes start
     */
    public boolean setPlayerPosition(int value) {
        return token.setPosition(value);
    }

    /**
     * @return (int) The position of the player
     */
    public int getPlayerPosition() {
        return token.getPosition();
    }

    /**
     * Checks if the player has a jail card
     * @return (boolean) True if the player has a jail card
     */
    public boolean hasJailCard() {
        return hasJailCard;
    }

    /**
     * Gives a jail card to the player
     */
    public void giveJailCard() {
        hasJailCard = true;
    }

    /**
     * Removes a jail card from the player
     */
    public void removeJailCard() {
        hasJailCard = false;
    }

    /**
     * Check if the player is in jail
     * @return (boolean) True if the player is in jail
     */
    public boolean isJailed() {
        return isJailed;
    }

    /**
     * Sets the player in jail
     */
    public void moveToJail() {
        isJailed = true;
    }

    /**
     * Moves the player out of jail
     */
    public void moveOutOfJail() {
        isJailed = false;
    }

    /**
     * Checks of the player is bankrupt
     * @return (boolean) True if the player balance is 0
     */
    public boolean isBankrupt() {
        return account.getBalance() == 0;
    }

    /**
     * A toString method
     * @return (String) The player name and balance
     */
    public String toString() {
        return playerName + " " + account.getBalance();
    }
}
