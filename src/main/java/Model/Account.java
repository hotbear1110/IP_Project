package Model;

import Model.Squares.ColorGroup;
import Model.Squares.Lot;
import Model.Squares.Jail;
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
        HashMap<Color, Integer> colorCount = new HashMap<Color, Integer>();
        for (Lot lot : ownedLots) {
            Integer j = colorCount.get(lot.getColor());
            if (j == null) {
                colorCount.put(lot.getColor(), 1);
            } else if (!lot.isPropertyMortgaged()) {
                colorCount.put(lot.getColor(), j + 1);
            }
        }
        HashMap<Color, Integer> maxLots = new HashMap<Color, Integer>();
        for (Lot lot : ownedLots) {
            Integer j = maxLots.get(lot.getColor());
            maxLots.put(lot.getColor(), lot.getMembers().size());

        }

        ArrayList<Color> color = new ArrayList<>();
        for(Map.Entry<Color, Integer> entry : colorCount.entrySet()){
            Color key = entry.getKey();
            Integer value = entry.getValue();
            int maxLotsCount = maxLots.get(key);
            if(value == maxLotsCount){
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

    public Lot[] nextUpgrade() {
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
                if (houseCount > maxHouses) {
                    maxHouses = houseCount;
                }
            }

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
        Lot[] upgradableProperties = new Lot[finalProperties.size()];
        for(int i = 0; i < finalProperties.size(); i++){
            upgradableProperties[i] = finalProperties.get(i);
        }
        return upgradableProperties;

    }

    public Lot[] nextDowngrade() {
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
                if (houseCount > maxHouses) {
                    maxHouses = houseCount;
                }
            }

            for (Lot property : propertyList ) {
                int houseCount = property.getNumberOfHouses();

                if (houseCount > maxHouses) {
                    maxHouses = houseCount;
                    upgradableProperties.clear();
                    upgradableProperties.add(property);
                } else if (houseCount == maxHouses) {
                    upgradableProperties.add(property);
                }
            }

            finalProperties.addAll(upgradableProperties);

        }
        Lot[] upgradableProperties = new Lot[finalProperties.size()];
        for(int i = 0; i < finalProperties.size(); i++){
            upgradableProperties[i] = finalProperties.get(i);
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

    public int getHouseCount() {
        ArrayList<Lot> ownedLots = new ArrayList<>();

        for (int i = 0; i < properties.size(); i++) {
            if(properties.get(i) instanceof Lot){
                ownedLots.add((Lot) properties.get(i));
            }
        }

        int houseCount = 0;

        for (Lot lot : ownedLots) {
            houseCount += lot.getNumberOfHouses();
        }

        return houseCount;
    }

    public int getHotelCount() {
        ArrayList<Lot> ownedLots = new ArrayList<>();

        for (int i = 0; i < properties.size(); i++) {
            if(properties.get(i) instanceof Lot){
                ownedLots.add((Lot) properties.get(i));
            }
        }

        int hotelCount = 0;

        for (Lot lot : ownedLots) {
            hotelCount += (lot.getHotel()) ? 1 : 0;
        }

        return hotelCount;
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

    /*public boolean checkBankrupcy(){
        int totalworth = calculateTotalWorth();
        if (totalworth == 0){
            return true;
        } else {
            return false;
        }
    }*/

    public boolean checkBankrupcy(){
        if (balance == 0){
            return true;
        } else {
            return false;
        }
    }

    public void setAsBankrupt(){
        balance = 0;
    }
}
