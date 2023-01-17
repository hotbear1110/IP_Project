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

        gameControl.getUI().showMessage(Translator.getString("LAND_ON_PROPERTY") + property.getName() + Translator.getString("OWNER_BY") + property.getOwner().getPlayerName() + Translator.getString("PAY_RENT") + rent);
        if (!checkPlayerBalance(player, rent)) {
                //din balance er ..... og kan derfor ikke betale hele lejen på ... Du er derfor gået fallit.\n Det du kan betale af lejen bliver betalt, dine grunde og opgraderinger gives tilbage til banken.
            playerToPlayer(property.getOwner(), player, rent);
                //reset property;
            gameControl.declarePlayerBankrupt(player);
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
                String action = gameControl.getUI().getUserButton(Translator.getString("CHOOSE_ACTION"), ControlMenus.upgradeMenu);
                switch (action) {
                        case "Køb opgraderinger":
                            ArrayList<Lot> nextUpgradableProperties = player.nextUpgrade();

                            boolean isNext = false;

                            for (Lot lot : nextUpgradableProperties) {
                                if (activeLot.equals(lot)) {
                                    isNext = true;
                                    break;
                                }
                            }

                            if (isNext) {
                                buyUpgrades(player, activeLot);
                            } else {
                                gameControl.getUI().showMessage(Translator.getString("CANT_UPGRADE"));
                                handleUpgrades(player);
                            }
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
            boolean userSelection = gameControl.getUI().getYesNoAnswer(Translator.getString("LOT_PAWN"));
            if(userSelection){
                Property property = gameControl.getBoard().getProperty(activeLot);
                payMortgaged(player, property);
            } else {
                handleUpgrades(player);
            }
        } else {
            String action = gameControl.getUI().getDropDown(Translator.getString("CHOOSE_ACTION"), ControlMenus.buyUpgradeMenu);
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
        lot.setCurrentRent(FixedValues.FOUR_HOUSE_RENT_INDEX);
        fromBankToPlayer(player, sellAmount);
    }
    private void sellHouse(Player player, Lot lot){
        int sellAmount = lot.getHousePrice();
        lot.removeHouse(1);
        switch (lot.getNumberOfHouses()) {
            case 0: lot.setCurrentRent(FixedValues.DEFAULT_RENT_INDEX);
            case 1: lot.setCurrentRent(FixedValues.ONE_HOUSE_RENT_INDEX);
            case 2: lot.setCurrentRent(FixedValues.TWO_HOUSE_RENT_INDEX);
            case 3: lot.setCurrentRent(FixedValues.THREE_HOUSE_RENT_INDEX);
        }
        fromBankToPlayer(player, sellAmount);
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
            return;
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
                property.setAsNotMortgaged();
                fromPlayerToBank(player, totaltAmount);
                if (player.hasColorSet(property)) {
                    property.setCurrentRent(FixedValues.DOUBLE_RENT_INDEX);
                }
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

            case "Nej":
                mortgagedActions(player);
        }
    }
}
