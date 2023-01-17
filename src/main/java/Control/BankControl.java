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
        String action = gameControl.getUI().getUserButton(Translator.getString("LAND_ON_PROPERTY") + property.getName() + ".", Translator.getString("BUY"), Translator.getString("SKIP_TURN"));
        switch (action){
            case "Køb":
                int amount = property.getPrice();
                fromPlayerToBank(player, amount);
                property.setOwner(player);
                player.buyProperty(property);
                if (property instanceof Lot) {
                    Lot activeLot = gameControl.getBoard().getLot(property.getName());
                    if (player.hasColorSet(activeLot)) {
                        activeLot.setCurrentRent(FixedValues.DOUBLE_RENT_INDEX);
                    }
                }
                gameControl.getUI().showMessage(Translator.getString("OWNER_OF") + property.getName());
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
            Brewery activeSquare = gameControl.getBoard().getBrewery(property.getName());
            rent += activeSquare.getRent(diceSum);
        }

        if (property.isPropertyMortgaged()){
            gameControl.getUI().showMessage(Translator.getString("LAND_ON") + property.getName() + Translator.getString("WHO_OWNS") + property.getOwner().getPlayerName() + Translator.getString("NO_RENT"));
        } else if (player.isJailed()) {
            gameControl.getUI().showMessage(Translator.getString("LAND_ON") + property.getName() + Translator.getString("WHO_OWNS") + property.getOwner().getPlayerName() + Translator.getString("JAIL_NO_RENT"));
        } else {
            gameControl.getUI().showMessage(Translator.getString("LAND_ON") + property.getName() + Translator.getString("WHO_OWNS") + property.getOwner().getPlayerName() + Translator.getString("PAY_RENT_ON")+ rent);
            if (!checkPlayerBalance(player, rent)) {
                gameControl.getUI().showMessage(Translator.getString("YOUR_BALANCE") + player.getPlayerBalance() + Translator.getString("PAY_KR") + rent + Translator.getString("BROKE"));
                playerToPlayer(property.getOwner(), player, rent);
                if(player.playerProperties().size() != 0){
                    for(int i = 0; i < player.playerProperties().size(); i++){
                        player.playerProperties().get(i).resetProperty();
                    }
                }
                gameControl.declarePlayerBankrupt(player);
            } else {
                playerToPlayer(property.getOwner(), player, rent);
            }
        }
    }

    public void payTax(int position) {

        Player player = gameControl.getGame().getCurrentPlayer();

        if (position == 4) {
            String action = gameControl.getUI().getDropDown(Translator.getString("PAY_TAX"), ControlMenus.taxOrCash);

            switch (action) {
                case "Betal 10% i skat" -> {
                    int playerCash = player.getPlayerBalance();

                    int payment = playerCash - Utility.removeProcentFromNumber(playerCash, FixedValues.TAX_PERCENTAGE);

                    int roundedPayment = Utility.roundUpToHundred(payment);

                    gameControl.getUI().showMessage(Translator.getString("PAY") + roundedPayment + Translator.getString("KR"));

                    fromPlayerToBank(player, roundedPayment);

                }
                case "Betal 4000kr." -> {
                    fromPlayerToBank(player, FixedValues.INCOME_TAX);
                }
            }
        } else if (position == 38) {
            fromPlayerToBank(player, FixedValues.EXTRA_TAX);
        }


    }

    // ------------ METHODS FOR BUYING AND SELLING ------------- \\
    public void buySellActions(Player player) {
        String action = gameControl.getUI().getDropDown(Translator.getString("CHOOSE_FROM_LIST"), ControlMenus.buySellMenu);
        switch (action){
            case "Køb/Sælg opgraderinger":
                handleUpgrades(player);
                return;
            case "Pantsæt/Ophæv pantsætning":
                mortgagedActions(player);
                return;
            case "Tilbage":
                return;
        }
    }

    private void handleUpgrades(Player player) {
        Lot[] upgradableProperties = player.getUpgradableProperties();
        String[] properties = new String[upgradableProperties.length];
        for(int i = 0; i < upgradableProperties.length; i++){
            properties[i] = upgradableProperties[i].getName();
        }

        String[] menu = Utility.addElementToStringArray(properties, Translator.getString("BACK"));
        if(menu.length == 1){
            gameControl.getUI().showMessage(Translator.getString("CANT_AFFORD"));
            buySellActions(player);
            return;
        } else {
            String activeLot = gameControl.getUI().getDropDown(Translator.getString("WHICH_LOT"), menu);
            if (activeLot.equals(Translator.getString("BACK"))) {
                    buySellActions(player);
            } else {
                String action = gameControl.getUI().getUserButton(Translator.getString("CHOOSE"), ControlMenus.upgradeMenu);
                switch (action) {
                        case "Køb opgraderinger":
                            Lot[] nextUpgradableProperties = player.nextUpgrade();

                            boolean isNext = false;

                            for (Lot lot : nextUpgradableProperties) {
                                if (activeLot.equals(lot.getName())) {
                                    isNext = true;
                                    break;
                                }
                            }

                            if (isNext) {
                                buyUpgrades(player, activeLot);
                            } else {
                                gameControl.getUI().showMessage(Translator.getString("CANT_UPGRADE"));
                                handleUpgrades(player);
                                break;
                            }
                            buySellActions(player);
                            break;
                        case "Sælg opgraderinger":
                            sellUpgrades(player, activeLot);
                            break;
                        case "Tilbage":
                            handleUpgrades(player);
                            break;
                    }
                }
            }
        }

    private void buyUpgrades(Player player, String activeLot) {
        Lot lot = gameControl.getBoard().getLot(activeLot);
        if(lot.isPropertyMortgaged()){
            boolean userSelection = gameControl.getUI().getYesNoAnswer(Translator.getString("LOT_PAWN"));
            if(userSelection){
                Property property = gameControl.getBoard().getProperty(activeLot);
                payMortgaged(player, property);
            } else {
                handleUpgrades(player);
            }
        } else {

            gameControl.getUI().showMessage(Translator.getString("PRIZE_HOUSE") + lot.getHousePrice() + Translator.getString("PRIZE_HOTEL") + lot.getHotelPrice() + Translator.getString("KR"));
            String action = gameControl.getUI().getDropDown(Translator.getString("CHOOSE_ACTION"), ControlMenus.buyUpgradeMenu);
            switch (action){
                case "Køb hus":
                    buyHouses(player, activeLot);
                    break;
                case "Køb hotel":
                    buyHotel(player, activeLot);
                    break;
                case "Tilbage":
                    handleUpgrades(player);
                    break;
            }
        }
    }

    private void buyHotel(Player player, String activeLot) {
        Lot[] nextUpgradableProperties = player.nextUpgrade();
        Lot lot = gameControl.getBoard().getLot(activeLot);

        if (lot.getHotel()) {
            gameControl.getUI().showMessage(Translator.getString("ONE_HOTEL"));
            buyUpgrades(player, activeLot);
            return;
        }
        boolean canBuyHotel = true;
        for (Lot thislot : nextUpgradableProperties) {
            if (thislot.getColor() == lot.getColor()) {
                if (thislot.getNumberOfHouses() != 4) {
                    canBuyHotel = false;
                    break;
                }
            }
        }
        if (!canBuyHotel) {
            gameControl.getUI().showMessage(Translator.getString("NOT_ENOUGH_HOUSE"));
            buyUpgrades(player, activeLot);
            return;
        }
        int buyAmount = lot.getHotelPrice();
        if (player.getPlayerBalance() - buyAmount <= 0) {
            gameControl.getUI().showMessage(Translator.getString("NO_HOTEL"));
            buyUpgrades(player, activeLot);
            return;
        }
        lot.addHotel();
        lot.setCurrentRent(FixedValues.HOTEL_HOUSE_RENT_INDEX);
        gameControl.getUI().addHotel(gameControl.getGame().getBoard().getIndex(activeLot));
        fromPlayerToBank(player, buyAmount);
        gameControl.getUI().updatePlayers(gameControl.getGame().getPlayers());
    }

    private void buyHouses(Player player, String activeLot) {
        Lot lot = gameControl.getBoard().getLot(activeLot);

        if (lot.getNumberOfHouses() == 4) {
            gameControl.getUI().showMessage(Translator.getString("NO_MORE_HOUSE"));
            buyUpgrades(player, activeLot);
            return;
        }
        int buyAmount = lot.getHousePrice();
        if (player.getPlayerBalance() - buyAmount <= 0) {
            gameControl.getUI().showMessage(Translator.getString("CANT_AFFORD_HOUSE"));
            buyUpgrades(player, activeLot);
            return;
        }
        lot.addHouse();
        int n = lot.getNumberOfHouses();
        lot.setCurrentRent(n+1);
        gameControl.getUI().addHouse(gameControl.getGame().getBoard().getIndex(activeLot), gameControl.getGame().getBoard().getLot(activeLot).getNumberOfHouses());
        fromPlayerToBank(player, buyAmount);
        gameControl.getUI().updatePlayers(gameControl.getGame().getPlayers());
    }

    private void sellUpgrades(Player player, String activeLot){
        Lot lot = gameControl.getBoard().getLot(activeLot);
        int houses = lot.getNumberOfHouses();
        if (houses == 0) {
            gameControl.getUI().showMessage(Translator.getString("NO_HOUSE"));
            buyUpgrades(player, activeLot);
            return;
        }


        if (lot.getHotel()){
            String action = gameControl.getUI().getDropDown(Translator.getString("CHOOSE"), ControlMenus.sellHotelUpgradesMenu);
            switch (action){
                case "Sælg hotel":
                    sellHotel(player, lot);
                    handleUpgrades(player);
                case "Tilbage":
                    handleUpgrades(player);
            }
        } else {
            String action = gameControl.getUI().getDropDown(Translator.getString("CHOOSE") , ControlMenus.sellHouseUpgradesMenu);
            switch (action){
                case "Sælg et hus":
                    Lot[] upgradableProperties = player.nextDowngrade();

                    boolean isEven = false;
                    for (Lot thislot : upgradableProperties) {
                        if (activeLot == thislot.getName()) {
                            isEven = true;
                            break;
                        }
                    }

                    for (Lot thislot : player.getUpgradableProperties()) {
                        if (thislot.getColor() == lot.getColor()) {
                            if (thislot.getHotel()) {
                                isEven = false;
                                break;
                            }
                        }
                    }
                    if (!isEven) {
                        gameControl.getUI().showMessage(Translator.getString("MUST_SELL"));
                        buyUpgrades(player, activeLot);
                        return;
                    }
                    sellHouse(player, lot);
                    handleUpgrades(player);
                    break;
                case "Tilbage":
                    handleUpgrades(player);
                    break;
            }
        }
    }
    private void sellHotel(Player player, Lot lot) {
        int sellAmount = lot.getHotelPrice();
        String activeLot = lot.getName();
        lot.removeHotel();
        lot.setCurrentRent(FixedValues.FOUR_HOUSE_RENT_INDEX);
        fromBankToPlayer(player, sellAmount);
        
        gameControl.getUI().removeHotel(gameControl.getGame().getBoard().getIndex(activeLot));
        gameControl.getUI().updatePlayers(gameControl.getGame().getPlayers());
    }
    private void sellHouse(Player player, Lot lot){
        int sellAmount = lot.getHousePrice();
        String activeLot = lot.getName();
        lot.removeHouse(1);
        switch (lot.getNumberOfHouses()) {
            case 0: lot.setCurrentRent(FixedValues.DEFAULT_RENT_INDEX);
                gameControl.getUI().removeHouse(gameControl.getGame().getBoard().getIndex(activeLot), gameControl.getGame().getBoard().getLot(activeLot).getNumberOfHouses());
                break;
            case 1: lot.setCurrentRent(FixedValues.ONE_HOUSE_RENT_INDEX);
                gameControl.getUI().removeHouse(gameControl.getGame().getBoard().getIndex(activeLot), gameControl.getGame().getBoard().getLot(activeLot).getNumberOfHouses());
                break;
            case 2: lot.setCurrentRent(FixedValues.TWO_HOUSE_RENT_INDEX);
                gameControl.getUI().removeHouse(gameControl.getGame().getBoard().getIndex(activeLot), gameControl.getGame().getBoard().getLot(activeLot).getNumberOfHouses());
                break;
            case 3: lot.setCurrentRent(FixedValues.THREE_HOUSE_RENT_INDEX);
                gameControl.getUI().removeHouse(gameControl.getGame().getBoard().getIndex(activeLot), gameControl.getGame().getBoard().getLot(activeLot).getNumberOfHouses());
                break;
        }
        fromBankToPlayer(player, sellAmount);
        gameControl.getUI().updatePlayers(gameControl.getGame().getPlayers());
    }

    public void mortgagedActions(Player player) {
        int allProperties = player.playerProperties().size();
        String[] properties = new String[allProperties];
        for(int i = 0; i < player.playerProperties().size(); i++){
            properties[i] = player.playerProperties().get(i).getName();
        }

        if (properties.length == 0) {
            gameControl.getUI().showMessage(Translator.getString("NO_LOT"));

            buySellActions(player);
        }

        String[] menu = Utility.addElementToStringArray(properties, Translator.getString("BACK"));
        String action= gameControl.getUI().getDropDown(Translator.getString("CHOOSE_WORK"), menu);
        if (action.equals(Translator.getString("BACK"))) {
            buySellActions(player);
        } else {
            Property property = gameControl.getBoard().getProperty(action);
            if (property.isPropertyMortgaged()){
                payMortgaged(player, property);
            } else if (property instanceof Lot lot && lot.hasUpgrades){
                gameControl.getUI().showMessage(Translator.getString("LOT_HAS_UPGRADE"));
                mortgagedActions(player);
            } else {
                mortgagedProperty(player, property);
            }

        }
    }

    public void payMortgaged(Player player, Property property){
        int propertyMortgagedPrice = property.getMortgage();
        int totaltAmount = Utility.addProcentToNumber(propertyMortgagedPrice, FixedValues.MARK_UP_MORTGAGED);
        String userSelection = gameControl.getUI().getUserButton(Translator.getString("PAWN_COST") + totaltAmount + Translator.getString("WANT_NO_PAWN"), Translator.getString("YES"), Translator.getString("NO"));
        switch (userSelection){
            case "Ja":
                if (player.getPlayerBalance() - totaltAmount <= 0) {
                    gameControl.getUI().showMessage(Translator.getString("CANT_AFFORD_PROPERTY"));
                    mortgagedActions(player);
                    break;
                }
                property.setAsNotMortgaged();
                fromPlayerToBank(player, totaltAmount);
                gameControl.getUI().updatePlayers(gameControl.getGame().getPlayers());
                if (player.hasColorSet(property)) {
                    property.setCurrentRent(FixedValues.DOUBLE_RENT_INDEX);
                }
                mortgagedActions(player);
                break;
            case "Nej":
                mortgagedActions(player);
        }
    }
    public void mortgagedProperty(Player player, Property property){
        int propertyMortgagedValue = property.getMortgage();
        String userSelection = gameControl.getUI().getUserButton(Translator.getString("NO_PAWN") + propertyMortgagedValue + Translator.getString("WANT_PAWN"), Translator.getString("YES"), Translator.getString("NO"));
        switch (userSelection){
            case "Ja":
                if (player.hasColorSet(property)) {
                    property.setCurrentRent(FixedValues.DEFAULT_RENT_INDEX);
                }
                property.setAsMortgaged();
                fromBankToPlayer(player, propertyMortgagedValue);
                gameControl.getUI().updatePlayers(gameControl.getGame().getPlayers());
            case "Nej":
                mortgagedActions(player);
        }
    }
}
