package Model;

import Model.Squares.Property;

import java.util.ArrayList;

public class Account {
    private int balance;
    private ArrayList<Property> properties;

    // Constructor with a start balance set to any input.

    public Account(int StartMoney) {
        balance = StartMoney;
        properties = new ArrayList<>();
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
    public void addProperty(Property property){
        properties.add(property);
    }
    public ArrayList<Property> getProperty(){
        return properties;
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
