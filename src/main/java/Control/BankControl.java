package Control;

import Global.Utility;
import Model.FixedValues;
import Model.Player;
import Model.Squares.Brewery;
import Model.Squares.Lot;
import Model.Squares.Property;
import Model.Squares.Ship;
import jdk.jshell.execution.Util;

import java.util.ArrayList;

public class BankControl {
    private GameControl gameControl;
    public BankControl(GameControl gameControl){
        this.gameControl = gameControl;
    }


    // ------------- PASSING START TRANSACTION ------------ \\
    public void getPassedStart(){
        gameControl.getUI().showMessage(Translator.getString("PASSED_START"));
        gameControl.getGame().getCurrentPlayer().setPlayerBalance(FixedValues.PASSED_START);
    }

    // --------- METHODS FOR BANK TRANSACTIONS ------------ \\
    public void fromPlayerToBank(Player player, int amount){
        player.setPlayerBalance(-amount);
    }

    public void fromBankToPlayer(Player player, int amount){
        player.setPlayerBalance(amount);
    }

    public void playerToPlayer(Player toPlayer, Player fromPlayer, int amount){
        toPlayer.setPlayerBalance(amount);
        fromPlayer.setPlayerBalance(-amount);
    }
    public int getPlayerBalance(Player player){
        return player.getPlayerBalance();
    }
    public int getPlayersTotalWorth(Player player){
        return player.getTotalWorth();
    }

    public boolean checkPlayerBalance(Player player, int amount){
        return player.getPlayerBalance() >= amount;
    }
    public boolean checkBankrupcy(Player player){
        return player.isBankrupt();
    }

    // ------------ METHODS FOR BUYING PROPERTY ------------ \\
    public void buyProperty(Player player, Property property){
        String action = gameControl.getUI().getUserButton("Du er landet på " + property.getName() + ".", "Køb", "Spring over");
        switch (action){
            case "Køb":
                int amount = property.getPrice();
                fromPlayerToBank(player, amount);
            case "Spring over":
                break;
        }
    }

    // ------------ METHODS FOR PAYING RENT ---------------\\
    public void payRent(Player player, Property property, int diceSum) {
        int rent = 0;
        if (property instanceof Lot) {
            Lot activeSquare = gameControl.getGame().getBoard().getLot(property.getName());
            rent += activeSquare.getRent();
        }
        if (property instanceof Ship) {
            Ship activeSquare = gameControl.getBoard().getShip(property.getName());
            rent += activeSquare.getRent();
        }
        if (property instanceof Brewery) {
            Brewery activeSquare = gameControl.getBoard().geBrewery(property.getName());
            rent += activeSquare.getRent(diceSum);
        }

        gameControl.getUI().showMessage("Du er landet på " + property.getName() + " som er ejet af " + property.getOwner().getPlayerName() + ".\n Du skal derfor betale lejen på " + rent);
        if (!checkPlayerBalance(player, rent)) {
            if (checkBankrupcy(player)) {
                //gameControl.declarePlayerBankrupt();
            } /*else {
                int totalWorth = getPlayersTotalWorth(player);
                if (totalWorth >= rent) {
                    String action = gameControl.getUI().getUserButton("Med din nuværende balance har du ikke råd til at betale lejen.", "Sælg opgraderinger", "Pantsæt grunde", "Giv op");
                    switch (action) {
                        case "Sælg opgraderinger":

                        case "Pantsæt grunde":

                        case "Giv op":
                            //gameControl.declarePlayerBankrupt();
                            break;
                    }
                } else if (totalWorth < rent) {
                    //gameControl.declarePlayerBankrupt();
                }
            } */
        }
    }

    // ------------ METHODS FOR BUYING AND SELLING ------------- \\
    public void buySellActions(Player player) {
        String action = gameControl.getUI().getDropDown("Vælg en handling fra listen", ControlMenus.buySellMenu);
        switch (action){
            case "Køb/Sælg opgraderinger":
                handleUpgrades(player);
            case "Pantsæt/Ophæv pantsætning":
                mortgagedActions(player);
            case "Tilbage":
                break;
        }
    }

    private void handleUpgrades(Player player) {
        Lot[] upgradableProperties = player.getUpgradableProperties();
        String[] properties = new String[upgradableProperties.length];
        for(int i = 0; i < upgradableProperties.length; i++){
            properties[i] = upgradableProperties[i].getName();
        }
        String[] menu = Utility.addElementToStringArray(properties, "Tilbage");
        if(menu.length == 1){
            gameControl.getUI().showMessage("Du ejer ikke tre grunde af samme farve, og kan derfor ikke købe opgraderinger");
            buySellActions(player);
        } else {
            String activeLot = gameControl.getUI().getDropDown("Hvilken grund vil du arbejde med?", menu);
            if (activeLot.equals("Tilbage")) {
                    buySellActions(player);
            } else {
                String action = gameControl.getUI().getUserButton("Vælg en handling: ", ControlMenus.upgradeMenu);
                switch (action) {
                        case "Køb opgraderinger":
                            buyUpgrades(player, activeLot);
                        case "Sælg opgraderinger":
                            sellUpgrades(player, activeLot);
                        case "Tilbage":
                            handleUpgrades(player);
                    }
                }
            }
        }

    private void buyUpgrades(Player player, String activeLot) {
        Lot lot = gameControl.getBoard().getLot(activeLot);
        if(lot.isPropertyMortgaged()){
            boolean userSelection = gameControl.getUI().getYesNoAnswer("Denne grund er pantsat og kan derfor ikke opgraderes, vil du ophæve pantsætningen?");
            if(userSelection){
                Property property = gameControl.getBoard().getProperty(activeLot);
                payMortgaged(player, property);
            } else {
                handleUpgrades(player);
            }
        } else {
            String action = gameControl.getUI().getDropDown("Vælg en handling:", ControlMenus.buyUpgradeMenu);
            switch (action){
                case "Køb hus":
                    buyHouses(player, activeLot);
                case "Køb hotel":
                    buyHotel(player, activeLot);
                case "Tilbage":
                    handleUpgrades(player);
            }
        }
    }

    private void buyHotel(Player player, String activeLot) {
        Lot lot = gameControl.getBoard().getLot(activeLot);
        int buyAmount = lot.getHotelPrice();
        lot.addHotel();
        fromPlayerToBank(player, buyAmount);
    }

    private void buyHouses(Player player, String activeLot) {
        Lot lot = gameControl.getBoard().getLot(activeLot);
        int buyAmount = lot.getHousePrice();
        lot.addHouse();
        fromPlayerToBank(player, buyAmount);
    }

    private void sellUpgrades(Player player, String activeLot){
        Lot lot = gameControl.getBoard().getLot(activeLot);
        int houses = lot.getNumberOfHouses();
        if (lot.getHotel()){
            String action = gameControl.getUI().getDropDown("Vælg handling", ControlMenus.sellHotelUpgradesMenu);
            switch (action){
                case "Sælg hotel":
                    sellHotel(player, lot);
                    handleUpgrades(player);
                case "Tilbage":
                    handleUpgrades(player);
            }
        } else {
            String action = gameControl.getUI().getDropDown("Vælg en handling" , ControlMenus.sellHouseUpgradesMenu);
            switch (action){
                case "Sælg et hus":
                    sellHouse(player, lot);
                    handleUpgrades(player);
                case "Tilbage":
                    handleUpgrades(player);
            }
        }
    }
    private void sellHotel(Player player, Lot lot) {
        int sellAmount = lot.getHotelPrice();
        lot.removeHotel();
        fromBankToPlayer(player, sellAmount);
    }
    private void sellHouse(Player player, Lot lot){
        int sellAmount = lot.getHousePrice();
        lot.removeHouse(1);
        fromBankToPlayer(player, sellAmount);
    }

    public void mortgagedActions(Player player) {
        int allProperties = player.playerProperties().size();
        String[] properties = new String[allProperties];
        for(int i = 0; i < player.playerProperties().size(); i++){
            properties[i] = player.playerProperties().get(i).getName();
        }
        String[] menu = Utility.addElementToStringArray(properties, "Tilbage");
        String action= gameControl.getUI().getDropDown("Vælg den grund du gerne vil arbejde med", menu);
        if (action.equals("Tilbage")) {
            buySellActions(player);
        } else {
            Property property = gameControl.getBoard().getProperty(action);
            if (property.isPropertyMortgaged()){
                payMortgaged(player, property);
            } else if (property instanceof Lot lot && lot.hasUpgrades){
                gameControl.getUI().showMessage("Denne grund har opgraderinger som skal sælges før den kan blive pantsat");
                mortgagedActions(player);
            } else {
                mortgagedProperty(player, property);
            }

        }
    }

    public void payMortgaged(Player player, Property property){
        int propertyMortgagedPrice = property.getMortgage();
        int totaltAmount = Utility.addProcentToNumber(propertyMortgagedPrice, FixedValues.MARK_UP_MORTGAGED);
        String userSelection = gameControl.getUI().getUserButton("Denne ejendom er pantsat, det vil koste " + totaltAmount + " at ophæve pantsætningen.\n Vil du ophæve pantsætning?", "Ja", "Nej");
        switch (userSelection){
            case "Ja":
                property.setAsNotMortgaged();
                fromPlayerToBank(player, totaltAmount);
            case "Nej":
                mortgagedActions(player);
        }
    }
    public void mortgagedProperty(Player player, Property property){
        int propertyMortgagedValue = property.getMortgage();
        String userSelection = gameControl.getUI().getUserButton("Denne ejendom er ikke pantsat, og du kan få " + propertyMortgagedValue + " for at pantsætte den.\n Vil du pantsætte grunden?", "Ja", "Nej");
        switch (userSelection){
            case "Ja":
                property.setAsMortgaged();
                fromBankToPlayer(player, propertyMortgagedValue);
            case "Nej":
                mortgagedActions(player);
        }
    }
}
