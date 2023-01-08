package Model.ChanceCards;

import Model.Player;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Deck {

    private static ArrayList<Cards> cards = new ArrayList<Cards>();

    File file = new File("../../../resources");
    URL[] urls;

    {
        try {
            urls = new URL[]{file.toURI().toURL()};
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    ClassLoader loader = new URLClassLoader(urls);
    Locale bLocale = new Locale.Builder().setLanguage("da").setRegion("DK").build();
    ResourceBundle i18n = ResourceBundle.getBundle("MessageBundle",  bLocale, loader);

    /**
     * Vi skal nok have flyttet alt om i18n til en hjælper klasse på et tidspunkt
     */
    public void createCards() {
        for (int i = 0; i < 36; i++) {
            Cards newCard = new Cards("chance" + (i + 1));
            cards.add(newCard);
        }
    }

    public static void shuffle() {
        Collections.shuffle(cards);
    }

    public static void pullCard(Player player) {
        Cards card = cards.get(0);
        cards.remove(0);
        cards.add(card);

        String cardType = card.getType();

        switch (cardType) {
            case "payAmount": {
                card.payAmount(player);
            }
            case "payPerHouse": {
                card.payPerHouse(player);
            }
            case "recieve": {
                card.recieve(player);
            }
            case "steal": {
                card.steal(player);
            }
            case "rarecieve": {
                card.rarecieve(player);
            }
        }
    }
}
