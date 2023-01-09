package Model.ChanceCards;

import Control.Translator;
import Model.Player;

import java.util.*;

public class Deck {
    private final ArrayList<Cards> cards;
    public Deck(){
        this.cards = new ArrayList<Cards>();
        createCards();
        shuffle();
    }

    public void createCards() {
        for (int i = 0; i < 36; i++) {
            String card = "CHANCE" + (i + 1);
            Cards newCard = null;
            switch (card) {
                case "CHANCE1" -> {
                    newCard = new ChancePay(card, "payPerHouse", Translator.getString(card), 500, 2000);
                }
                case "CHANCE2" -> {
                    newCard = new ChancePay(card, "payPerHouse", Translator.getString(card), 800, 2300);
                }
                case "CHANCE3", "CHANCE7", "CHANCE9" -> {
                    newCard = new ChancePay(card, "payAmount", Translator.getString(card), 1000, 0);
                }
                case "CHANCE4" -> {
                    newCard = new ChancePay(card, "payAmount", Translator.getString(card), 300, 0);
                }
                case "CHANCE5", "CHANCE8", "CHANCE10" -> {
                    newCard = new ChancePay(card, "payAmount", Translator.getString(card), 200, 0);
                }
                case "CHANCE6" -> {
                    newCard = new ChancePay(card, "payAmount", Translator.getString(card), 3000, 0);
                }
                case "CHANCE11" -> {
                    newCard = new ChancePay(card, "payAmount", Translator.getString(card), 2000, 0);
                }
                case "CHANCE12" -> {
                    newCard = new ChanceReceive(card, "recieve", Translator.getString(card), 500);
                }
                case "CHANCE13", "CHANCE15", "CHANCE16", "CHANCE17", "CHANCE18" -> {
                    newCard = new ChanceReceive(card, "recieve", Translator.getString(card), 1000);
                }
                case "CHANCE14" -> {
                    newCard = new ChanceReceive(card, "recieve", Translator.getString(card), 3000);
                }
                case "CHANCE19" -> {
                    newCard = new ChanceReceive(card, "recieve", Translator.getString(card), 200);
                }
                case "CHANCE20" -> {
                    newCard = new ChanceReceive(card, "rarecieve", Translator.getString(card), 0);
                }
                case "CHANCE21" -> {
                    newCard = new ChanceReceive(card, "steal", Translator.getString(card), 200);
                }
                case "CHANCE22", "CHANCE23" -> {
                    newCard = new ChanceReceive(card, "steal", Translator.getString(card), 500);
                }
            }
            cards.add(newCard);
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public ArrayList<Cards> getCards() {
        return cards;
    }

    public String pullCard(Player player, Player[] players) {

        ArrayList<Cards> cards = getCards();

        Cards card = cards.get(0);
        cards.remove(0);
        cards.add(card);

        String cardType = card.getType();
        String cardDescription = card.getDescription();

        switch (cardType) {
            case "payAmount" -> {
                ChancePay payAmountCard = (ChancePay) card;
                payAmountCard.payAmount(player);
            }
            /*case "payPerHouse" -> {
                ChancePay payPerHouseCard = (ChancePay) card;
                payPerHouseCard.payPerHouse(player);
            }*/
            case "recieve" -> {
                ChanceReceive recieveCard = (ChanceReceive) card;
                recieveCard.recieve(player);
            }
            case "steal" -> {
                ChanceReceive stealCard = (ChanceReceive) card;
                stealCard.steal(player, players);
            }
            case "rarecieve" -> {
                ChanceReceive rarecieveCard = (ChanceReceive) card;
                rarecieveCard.rarecieve(player);
            }
        }
        return cardDescription;
    }

    public void demoCards() {
        Cards newCard = new ChanceReceive("CHANCE12", "recieve", Translator.getString("CHANCE12"), 500);
        cards.add(newCard);
    }
}

/**
 * To do:
 * Make demo cards, just one card that we can show works. Made actioncontroll call demoCards() or something like that. Something easy like move or getmoney.
 * Everywhere with houses remake it so it matches with the account method.
 * Account should have a method that returns an arraylist of properties
 * i then have to iterate thru all the properties that are lots.
 * on each lot there will be a method for: boolean has house, boolean has hotel, and int of houses
 * Make default language danish and change the capitalization in deck where chance cards are generated.
 * Look at token, if the new position is passed start, then return some true boolean. Remake the current one to not return, but chance a private boolean.
 * The method that returns that boolean should be available both in token and in player. Call it hasPassedStart.
 */