package Model;

public class Player {
    private String name; //The players name

    private Account account; //The account associated with the player

    private Token token; //The token associated with the player

    public Player(String name) {
        this.name = name;
        account = new Account(FixedValues.START_MONEY);
        token = new Token();
    }

    public void setPlayerName(String name) {
        this.name = name;
    }

    public String getPlayerName() {
        return name;
    }

    public void setPlayerBalance(int value) {
        account.setBalance(value);
    }

    public int getPlayerBalance() {
        return account.getBalance();
    }

    public boolean movePlayerPosition(int dieSum) {
        return token.moveToken(dieSum);
    }
    public boolean setPlayerPosition(int value) {
        return token.setPosition(value);
    }

    public int getPlayerPosition() {
        return token.getPosition();
    }

    public boolean isBankrupt() {
        return account.getBalance() == 0;
    }
}
