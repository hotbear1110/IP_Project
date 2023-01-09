package Model.ChanceCards;

import Control.Translator;
import Model.Player;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class Deck {

    private ArrayList<Cards> cards = new ArrayList<Cards>();

    public void createCards() {
        for (int i = 0; i < 36; i++) {
            String card = "chance" + (i + 1);
            Cards newCard = null;
            switch (card) {
                case "chance1" -> {
                    newCard = new ChancePay(card, "payPerHouse", Translator.getString(card), 500, 2000);
                }
                case "chance2" -> {
                    newCard = new ChancePay(card, "payPerHouse", Translator.getString(card), 800, 2300);
                }
                case "chance3", "chance7", "chance9" -> {
                    newCard = new ChancePay(card, "payAmount", Translator.getString(card), 1000, 0);
                }
                case "chance4" -> {
                    newCard = new ChancePay(card, "payAmount", Translator.getString(card), 300, 0);
                }
                case "chance5", "chance8", "chance10" -> {
                    newCard = new ChancePay(card, "payAmount", Translator.getString(card), 200, 0);
                }
                case "chance6" -> {
                    newCard = new ChancePay(card, "payAmount", Translator.getString(card), 3000, 0);
                }
                case "chance11" -> {
                    newCard = new ChancePay(card, "payAmount", Translator.getString(card), 2000, 0);
                }
                case "chance12" -> {
                    newCard = new ChanceReceive(card, "recieve", Translator.getString(card), 500);
                }
                case "chance13", "chance15", "chance16", "chance17", "chance18" -> {
                    newCard = new ChanceReceive(card, "recieve", Translator.getString(card), 1000);
                }
                case "chance14" -> {
                    newCard = new ChanceReceive(card, "recieve", Translator.getString(card), 3000);
                }
                case "chance19" -> {
                    newCard = new ChanceReceive(card, "recieve", Translator.getString(card), 200);
                }
                case "chance20" -> {
                    newCard = new ChanceReceive(card, "rarecieve", Translator.getString(card), 0);
                }
                case "chance21" -> {
                    newCard = new ChanceReceive(card, "steal", Translator.getString(card), 200);
                }
                case "chance22", "chance23" -> {
                    newCard = new ChanceReceive(card, "steal", Translator.getString(card), 500);
                }
            }
            cards.add(newCard);
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void pullCard(Player player) {
        Cards card = cards.get(0);
        cards.remove(0);
        cards.add(card);

        String cardType = card.getType();
        int playerCount = Game.getPlayers(); //Vi skal have en metode der retunerer playercount

        switch (cardType) {
            case "payAmount" -> {
                ChancePay payAmountCard = (ChancePay) card;
                payAmountCard.payAmount(player);
            }
            case "payPerHouse" -> {
                ChancePay payPerHouseCard = (ChancePay) card;
                payPerHouseCard.payPerHouse(player);
            }
            case "recieve" -> {
                ChanceReceive recieveCard = (ChanceReceive) card;
                recieveCard.recieve(player);
            }
            case "steal" -> {
                ChanceReceive stealCard = (ChanceReceive) card;
                stealCard.steal(player, playerCount);
            }
            case "rarecieve" -> {
                ChanceReceive rarecieveCard = (ChanceReceive) card;
                rarecieveCard.rarecieve(player);
            }
        }
    }
}
