package ip2.game;

import java.util.Collections;
import java.util.ArrayList;

public class Deck {

    private static ArrayList<Cards> cards = new ArrayList<Cards>();

    public static void shuffle() {

        Collections.shuffle(cards);

    }

}



