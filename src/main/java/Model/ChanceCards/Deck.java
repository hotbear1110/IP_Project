package Model.ChanceCards;

import java.util.Collections;
import java.util.ArrayList;

public class Deck {

    private static ArrayList<Cards> cards = new ArrayList<Cards>();

    public void createCards() {
        for (int i = 0; i < 36; i++) {
            Cards newCard = new Cards("chance" + (i + 1));
            cards.add(newCard);
        }
    }

    public static void shuffle() {
        Collections.shuffle(cards);
    }

    public static void pullCard() {
        Cards card = cards.get(0);
        cards.remove(0);
        cards.add(card);
    }
}
