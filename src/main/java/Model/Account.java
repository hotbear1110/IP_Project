package Model;

import Model.Squares.Lot;
import Model.Squares.Property;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public void removeProperty(Property property){
        properties.remove(property);
    }
    public ArrayList<Property> getProperty(){
        return properties;
    }

    public int calculateTotalWorth(){
        int totalWorth = balance;
        for (int i = 0; i < properties.size(); i++){
            if(properties.get(i).isPropertyMortgaged()){
                i++;
            } else {
                totalWorth += properties.get(i).getMortgage();
                if (properties.get(i) instanceof Lot) {
                    int n = ((Lot) properties.get(i)).getNumberOfHouses();
                    totalWorth += (n * (((Lot) properties.get(i)).getHousePrice() / 2));
                    if (((Lot) properties.get(i)).getHotel()) {
                        totalWorth += (((Lot) properties.get(i)).getHotelPrice() / 2);
                    }
                }
            }
        }
        return totalWorth;
    }

    public Lot[] getUpgradableProperties(){
        ArrayList<Lot> upgradables = new ArrayList<>();
        ArrayList<Lot> ownedLots = new ArrayList<>();
        for (int i = 0; i < properties.size(); i++){
            if(properties.get(i) instanceof Lot){
                ownedLots.add((Lot) properties.get(i));
            }
        }
        HashMap<Color, Integer> propertyCount = new HashMap<Color, Integer>();
        for(int i = 0; i < ownedLots.size(); i++){
            Integer j = propertyCount.get(ownedLots.get(i).getColor());
            if(j == null){
                propertyCount.put(ownedLots.get(i).getColor(), 1);
            } else {
                propertyCount.put(ownedLots.get(i).getColor(), i + 1);
            }
        }
        ArrayList<Color> color = new ArrayList<>();
        for(Map.Entry<Color, Integer> entry : propertyCount.entrySet()){
            Color key = entry.getKey();
            Integer value = entry.getValue();
            if(value == 3){
                color.add(key);
            }
        }
        for (Color value : color) {
            for (Lot ownedLot : ownedLots) {
                if (ownedLot.getColor().equals(value)) {
                    upgradables.add(ownedLot);
                }
            }
        }
        Lot[] upgradableProperties = new Lot[upgradables.size()];
        for(int i = 0; i < upgradables.size(); i++){
            upgradableProperties[i] = upgradables.get(i);
        }
        return upgradableProperties;
    }
    public Lot[] getPropertiesWithUpgrades(){
        ArrayList<Property> ownedProperties = properties;
        ArrayList<Lot> propertiesWithUpgrades = new ArrayList<>();
        for (Property ownedProperty : ownedProperties) {
            if (ownedProperty instanceof Lot lot) {
                if (lot.hasUpgrades) {
                    propertiesWithUpgrades.add(lot);
                }
            }
        }
        Lot[] lots = new Lot[propertiesWithUpgrades.size()];
        for (int i = 0; i < propertiesWithUpgrades.size(); i++){
            lots[i] = propertiesWithUpgrades.get(i);
        }
        return lots;
    }

    public boolean canPropertyBeUpgraded(Lot lot) {
        Lot[] upgradableProperties = getUpgradableProperties();
        HashMap<Lot, Integer> numberOfUpgrades = new HashMap<>();
        for (int i = 0; i < upgradableProperties.length; i++) {
            if (upgradableProperties[i].equals(lot)) {
                i++;
            }
            if (upgradableProperties[i].getHotel()) {
                numberOfUpgrades.put(upgradableProperties[i], upgradableProperties[i].getNumberOfHouses() + 1);
            } else {
                numberOfUpgrades.put(upgradableProperties[i], upgradableProperties[i].getNumberOfHouses());
            }
        }
        return false;
    }
    /**
     * @return current balance
     */
    public int getBalance(){
        return balance;
    }

    /**
     * @param amount (int) the amount to check against
     * @return (boolean) - True if balance is equal or greater than the amount.
     */
    public boolean balanceCheck(int amount) {
        return balance >= amount;
    }

    public boolean checkBankrupcy(){
        int totalworth = calculateTotalWorth();
        if (totalworth == 0){
            return true;
        } else {
            return false;
        }
    }

    public void setAsBankrupt(){
        balance = 0;
    }
}
