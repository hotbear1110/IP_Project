package Model;

import Model.Squares.Lot;
import Model.Squares.Property;

import java.util.ArrayList;

public class Player {
    private String playerName; //The players name

    private Account account; //The account associated with the player

    private Token token; //The token associated with the player

    /**
     * The constructor for the player
     * @param Name (String) The player name
     */
    public Player(String Name) {
        playerName = Name;
        account = new Account(FixedValues.START_AMOUNT);
        token = new Token();
    }

//metode der retunere arraylist og propeties
    public ArrayList<Property> playerProperties(){
        return account.getProperty();
    }

    public Lot[] getPropertiesWithUpgrades(){
        return account.getPropertiesWithUpgrades();
    }

    public Lot[] getUpgradableProperties(){
        return account.getUpgradableProperties();
    }

    public void sellProperty(Property property){
        account.removeProperty(property);
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

    public boolean checkPlayerBalance(int amount){
        return account.balanceCheck(amount);
    }

    public int getTotalWorth(){
        return account.calculateTotalWorth();
    }

    /**
     * Moves the player a given amount of fields
     * @param dice (int) the die sum
     * @return (boolean) True if the player passes start
     */
    public void movePlayerPosition(int dice) {
        token.moveToken(dice);
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

    public void takeJailCard() {
        account.takeJailCard();
    }

    public void giveJailCard() {
        account.giveJailCard();
    }

    public boolean hasJailCard() {
        return account.hasJailCard();
    }

    /**
     * Checks of the player is bankrupt
     * @return (boolean) True if the player balance is 0
     */
    public boolean isBankrupt() {
        return account.checkBankrupcy();
    }

    /**
     * A toString method
     * @return (String) The player name and balance
     */
    public String toString() {
        return playerName + " " + account.getBalance();
    }

    public boolean hasPassedStart() {
        return token.hasPassedStart();
    }
}
