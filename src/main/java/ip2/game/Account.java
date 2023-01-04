package ip2.game;

public class Account {private int balance;

    // Constructor with a start balance set to any input.

    /**
     * @param START_AMOUNT (int) The balance the players will start with.
     */
    public Account(int START_AMOUNT) {
        balance = START_AMOUNT;
    }

    /**
     * Adds an integer to the balance
     * @param Int (int) added to the current balance
     */
    public void setBalance(int Int) {
        //Sets balance to the highest value of 0 and balance + Int
        //so that balance is never negative
        balance = Math.max(balance + Int, 0);
    }

    /**
     * @return current balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * @param amount (int) the amount to check against
     * @return (boolean) - True if balance is equal or greater than the amount.
     */
    public boolean balanceCheck(int amount) {
        return balance >= amount;
    }
}
