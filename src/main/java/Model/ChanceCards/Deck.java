package Model.ChanceCards;

import Control.Translator;
import Model.Game;
import Model.Player;

import java.util.*;

public class Deck {
    private ArrayList<Cards> cards;
    private Game game;
    public Deck(Game game){
        this.cards = new ArrayList<Cards>();

        this.game = game;
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
                case "CHANCE24" -> {
                    newCard = new MovePlayer(card, "specific", Translator.getString(card), 0);
                }
                case "CHANCE25" -> {
                    newCard = new MovePlayer(card, "move", Translator.getString(card), 3);
                }
                case "CHANCE26" -> {
                    newCard = new MovePlayer(card, "move", Translator.getString(card), -3);
                }
                case "CHANCE27" -> {
                    newCard = new MovePlayer(card, "specific", Translator.getString(card), 11);
                }
                case "CHANCE28" -> {
                    //Ikke færdigt
                    newCard = new MovePlayer(card, "moveToNext", Translator.getString(card), 0);
                }
                case "CHANCE29" -> {
                    newCard = new MovePlayer(card, "specific", Translator.getString(card), 15);
                }
                case "CHANCE30" -> {
                    newCard = new MovePlayer(card, "specific", Translator.getString(card), 24);
                }
                case "CHANCE31" -> {
                    newCard = new MovePlayer(card, "specific", Translator.getString(card), 32);
                }
                case "CHANCE32" -> {
                    newCard = new MovePlayer(card, "moveToNext", Translator.getString(card), 3);
                }
                case "CHANCE33" -> {
                    newCard = new MovePlayer(card, "specific", Translator.getString(card), 19);
                }
                case "CHANCE34" -> {
                    newCard = new MovePlayer(card, "specific", Translator.getString(card), 39);
                }
                case "CHANCE35" -> {
                    newCard = new JailCard(card, "giveJailCard", Translator.getString(card));
                }
                case "CHANCE36" -> {
                    newCard = new MovePlayer(card, "jail", Translator.getString(card), 10);
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

    public String getNextCard() {
        Cards card = cards.get(0);

        return card.getDescription();
    }

    public String[] pullCard(Player player, Player[] players) {

        Cards card = cards.get(0);
        ArrayList<Cards> tempCards = new ArrayList<Cards>();


        for (int i = 1; i < cards.size(); i++) {
            tempCards.add(cards.get(i));
        }

        String cardType = card.getType();

        String[] move = {"", "0"};
        boolean addCard = true;
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
                stealCard.steal(player, players);
            }
            case "rarecieve" -> {
                ChanceReceive rarecieveCard = (ChanceReceive) card;
                rarecieveCard.rarecieve(player);
            }
            case "specific" -> {
                MovePlayer specificCard = (MovePlayer) card;
                move = specificCard.specific(player);
            }
            case "move" -> {
                MovePlayer moveCard = (MovePlayer) card;
                move = moveCard.move(player);
            }
            case "moveToNext" -> {
                MovePlayer moveToNextCard = (MovePlayer) card;
                 move = moveToNextCard.moveToNext(player);
            }
            case "jail" -> {
                MovePlayer jailCard = (MovePlayer) card;
                jailCard.jail(player);

            }
            case "giveJailCard" -> {
                JailCard giveJailCard = (JailCard) card;
                giveJailCard.giveJailCard(player);
                addCard = false;
            }
        }
        if (addCard) {
            tempCards.add(card);
        }

        cards = tempCards;

        return move;
    }

    public void addJailCard() {
        Cards jailCard = new JailCard("CHANCE35", "giveJailCard", Translator.getString("CHANCE35"));
        cards.add(jailCard);
    }

    public void demoCards() {
        Cards newCard = new ChanceReceive("CHANCE12", "recieve", Translator.getString("CHANCE12"), 500);
        Cards newCard2 = new ChancePay("CHANCE11", "payAmount", Translator.getString("CHANCE11"), 2000, 0);
        Cards newCard3 = new ChanceReceive("CHANCE14", "recieve", Translator.getString("CHANCE14"), 3000);

        cards.add(newCard);
        cards.add(newCard2);
        cards.add(newCard3);
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