package Model;

import Model.Squares.Property;

import java.util.ArrayList;

public class Account {
    private int balance;
    private final ArrayList<Property> properties;

    public Account(int START_AMOUNT) {
        balance = START_AMOUNT;
        properties = new ArrayList<>();
    }
    public void setBalance(int Int) {
        //Sets balance to the highest value of 0 and balance + Int
        //so that balance is never negative
        balance = Math.max(balance + Int, 0);
    }

    public int getBalance() {
        return balance;
    }

    public void addProperty(Property property){
        properties.add(property);
    }

    public boolean balanceCheck(int amount) {
        return balance >= amount;
    }
}
