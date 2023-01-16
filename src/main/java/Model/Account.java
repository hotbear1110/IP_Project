package Model;

import Model.Squares.ColorGroup;
import Model.Squares.Lot;
import Model.Squares.Property;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Account {
    private int balance;
    private ArrayList<Property> properties;

    private int JailCards = 0;

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
        for (Property property : properties) {
            if (property instanceof Lot) {
                ownedLots.add((Lot) property);
            }
        }
        HashMap<Lot, Integer> propertyCount = new HashMap<Lot, Integer>();
        for (Lot lot : ownedLots) {
            Integer j = propertyCount.get(lot);
            if (j == null) {
                propertyCount.put(lot, 1);
            } else if (!lot.isPropertyMortgaged()) {
                propertyCount.put(lot, j + 1);
            }
        }
        ArrayList<Color> color = new ArrayList<>();
        for(Map.Entry<Lot, Integer> entry : propertyCount.entrySet()){
            Lot currentLot = entry.getKey();
            Color key = entry.getKey().getColor();
            Integer value = entry.getValue();
            int colorCount = currentLot.getMembers().size();
            if(value == colorCount){
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

    public ArrayList<Lot> nextUpgrade() {
        Lot[] ownedProperties = getUpgradableProperties();
        HashMap<Color, ArrayList<Lot>> sortedProperties = new HashMap<Color, ArrayList<Lot>>();

        ArrayList<Lot> finalProperties = new ArrayList<>();



        for (Lot ownedProperty : ownedProperties) {
            Color color = ownedProperty.getColor();

            ArrayList<Lot> j = sortedProperties.get(color);

            if (j == null) {
                ArrayList<Lot> k = new ArrayList<>();
                k.add(ownedProperty);
                sortedProperties.put(color, k);
            } else {
                j.add(ownedProperty);
                sortedProperties.put(color, j);
            }
        }

        for(Map.Entry<Color, ArrayList<Lot>> entry : sortedProperties.entrySet()) {
            Color key = entry.getKey();
            ArrayList<Lot> propertyList = entry.getValue();

            ArrayList<Lot> upgradableProperties = new ArrayList<>();

            int maxHouses = 0;

            for (Lot property : propertyList ) {
                int houseCount = property.getNumberOfHouses();

                if (houseCount < maxHouses) {
                    maxHouses = houseCount;
                    upgradableProperties.clear();
                    upgradableProperties.add(property);
                } else if (houseCount == maxHouses) {
                    upgradableProperties.add(property);
                }
            }

            finalProperties.addAll(upgradableProperties);

        }

        return finalProperties;

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

    /*
    lav metode hasColorSet(Lot lot) der returnerer sandt hvis spilleren har alle lots i den farvegruppe
     */

    public boolean hasColorSet(Property property) {
        ArrayList<Property> ownedProperties = new ArrayList<>();
        for (int i = 0; i < properties.size(); i++){
            if(properties.get(i) instanceof Lot){
                ownedProperties.add((Lot) properties.get(i));
            }
        }
        ColorGroup lotColor = property.getGroup();
        int colorProperties = 0;

        for (Property colorProperty : ownedProperties) {
            if (colorProperty.getGroup().equals(lotColor)) {
                colorProperties++;
            }
        }

        int colorCount = property.getMembers().size();

        return (colorProperties == colorCount);
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

    public void giveJailCard() {
        this.JailCards++;
    }

    public void takeJailCard() {
        this.JailCards--;
    }

    public boolean hasJailCard() {
        return JailCards>0;
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
