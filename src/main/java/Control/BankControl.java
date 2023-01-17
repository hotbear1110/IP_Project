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
                property.setOwner(player);
                player.buyProperty(property);
                if (property instanceof Lot) {
                    Lot activeLot = gameControl.getBoard().getLot(property.getName());
                    if (player.hasColorSet(activeLot)) {
                        activeLot.setCurrentRent(FixedValues.DOUBLE_RENT_INDEX);
                    }
                }
                gameControl.getUI().showMessage("Tillykke! Du er nu ejeren af " + property.getName());
            case "Spring over":
                break;
        }
    }

    // ------------ METHODS FOR PAYING RENT ---------------\\
    public void payRent(Player player, Property property, int diceSum, boolean Double) {
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
        if (Double) {
            rent = rent * 2;
        }
        if (property.isPropertyMortgaged()){
            gameControl.getUI().showMessage("Du er landet på " + property.getName() + " som er ejet af " + property.getOwner().getPlayerName() + ".\n Denne grund er pantsat og du skal derfor ikke betale leje!");
        } else if (player.isJailed()) {
            gameControl.getUI().showMessage("Du er landet på " + property.getName() + " som er ejet af " + property.getOwner().getPlayerName() + ".\n Denne spiller er i fængsel og du skal derfor ikke betale leje!");
        } else {
            gameControl.getUI().showMessage("Du er landet på " + property.getName() + " som er ejet af " + property.getOwner().getPlayerName() + ".\n Du skal derfor betale lejen på " + rent);
            if (!checkPlayerBalance(player, rent)) {
                //din balance er ..... og kan derfor ikke betale hele lejen på ... Du er derfor gået fallit.\n Det du kan betale af lejen bliver betalt, dine grunde og opgraderinger gives tilbage til banken.
            playerToPlayer(property.getOwner(), player, rent);
                //reset property;
            gameControl.declarePlayerBankrupt(player);
            }
        }/*else {
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
            String action = gameControl.getUI().getDropDown("Betal indkomst-skat 10%, eller betal 4000kr.", ControlMenus.taxOrCash);

            switch (action) {
                case "Betal 10% i skat" -> {
                    int playerCash = player.getPlayerBalance();

                    int payment = playerCash - Utility.removeProcentFromNumber(playerCash, FixedValues.TAX_PERCENTAGE);

                    int roundedPayment = Utility.roundUpToHundred(payment);

                    gameControl.getUI().showMessage("Du skal betale " + roundedPayment + "kr.");

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
        String action = gameControl.getUI().getDropDown("Vælg en handling fra listen", ControlMenus.buySellMenu);
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

        String[] menu = Utility.addElementToStringArray(properties, "Tilbage");
        if(menu.length == 1){
            gameControl.getUI().showMessage("Du ejer ikke tre grunde af samme farve, og kan derfor ikke købe opgraderinger");
            buySellActions(player);
            return;
        } else {
            String activeLot = gameControl.getUI().getDropDown("Hvilken grund vil du arbejde med?", menu);
            if (activeLot.equals("Tilbage")) {
                    buySellActions(player);
            } else {
                String action = gameControl.getUI().getUserButton("Vælg en handling: ", ControlMenus.upgradeMenu);
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
                                gameControl.getUI().showMessage("Du kan ikke opgradere denne grund, vælg end grund med færrest huse i farvegruppen");
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
            gameControl.getUI().showMessage("Du kan ikke købe flere end et hotel pr. grund");
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
            gameControl.getUI().showMessage("Du kan ikke købe hotel, da du ikke har nok huse på alle grundene");
            buyUpgrades(player, activeLot);
            return;
        }
        int buyAmount = lot.getHotelPrice();
        lot.addHotel();
        fromPlayerToBank(player, buyAmount);
    }

    private void buyHouses(Player player, String activeLot) {
        Lot lot = gameControl.getBoard().getLot(activeLot);

        if (lot.getNumberOfHouses() == 4) {
            gameControl.getUI().showMessage("Du kan ikke købe flere huse på denne grund");
            buyUpgrades(player, activeLot);
            return;
        }
        int buyAmount = lot.getHousePrice();
        lot.addHouse();
        fromPlayerToBank(player, buyAmount);
    }

    private void sellUpgrades(Player player, String activeLot){
        Lot lot = gameControl.getBoard().getLot(activeLot);
        int houses = lot.getNumberOfHouses();
        if (houses == 0) {
            gameControl.getUI().showMessage("Du har ikke nogle huse på denne grund");
            buyUpgrades(player, activeLot);
            return;
        }


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
                        gameControl.getUI().showMessage("Du skal sælge et hus/hotel fra en af dine andre grunde med samme farvegruppe først");
                        buyUpgrades(player, activeLot);
                        return;
                    }
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
            gameControl.getUI().showMessage("Du ejer ingen grunde, og kan derfor ikke pantsætte noget");
            buySellActions(player);
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
                if (player.hasColorSet(property)) {
                    property.setCurrentRent(FixedValues.DOUBLE_RENT_INDEX);
                }
            case "Nej":
                mortgagedActions(player);
        }
    }
    public void mortgagedProperty(Player player, Property property){
        int propertyMortgagedValue = property.getMortgage();
        String userSelection = gameControl.getUI().getUserButton("Denne ejendom er ikke pantsat, og du kan få " + propertyMortgagedValue + " for at pantsætte den.\n Vil du pantsætte grunden?", "Ja", "Nej");
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
