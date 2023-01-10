package Global;

import java.util.ArrayList;
import java.util.List;

public class Utility {
    public static int parseFirstInt(String s){
        return Integer.parseInt(s.split("\\.")[0]);
    }

    public static String parseIntToString(int n){
        return Integer.toString(n);
    }

    public static <E> void moveToFront(List<E> list, int index){
        E temp = list.get(0);
        list.set(0, list.get(index));
        list.set(index, temp);
    }
}
