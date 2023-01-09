package Control;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {
    private static File file = new File("../../resources");
    private static URL[] urls;

    static {
        try {
            urls = new URL[]{file.toURI().toURL()};
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    private static ClassLoader loader = new URLClassLoader(urls);
    private static Locale bLocale = new Locale.Builder().setLanguage("da").setRegion("DK").build();
    private static ResourceBundle i18n = ResourceBundle.getBundle("MessagesBundle",  bLocale, loader);

    public static String getString(String name) {
        String response = i18n.getString(name);
        return response;
    }

}
