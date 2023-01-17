package Global;

import java.util.ArrayList;
import java.util.List;

public class Utility {
    public static int parseFirstInt(String s){
        return Integer.parseInt(s.split("\\.")[0]);
    }

    public static int parseFirstIntBeforePlus(String s){
        return Integer.parseInt(s.split("\\+")[1]);
    }
    public static int parseSecondIntAfterPlus(String s){
        return Integer.parseInt(s.split("\\+")[1]);
    }

    public static String parseIntToString(int n){
        return Integer.toString(n);
    }

    public static String[] parseIntToStringArray(int n){
        String[] array = new String[n];
        for (int i = 0; i <= n; i++){
            array[i] = i + ".";
        }
        return array;
    }

    public static String[] parseListToArray(ArrayList<String> list){
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
    }

    public static String[] addElementToStringArray(String[] array, String element){
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++){
            list.add(i, array[i]);
        }
        int n = list.size();
        list.add(n, element);
        return parseListToArray(list);
    }

    public static <E> void moveToFront(List<E> list, int index){
        E temp = list.get(0);
        list.set(0, list.get(index));
        list.set(index, temp);
    }

    public static int addProcentToNumber(int baseNumber, int markUp){
        int finalResult = baseNumber + (markUp * baseNumber / 100);
        return finalResult;
    }

    public static int removeProcentFromNumber(int baseNumber, int markUp){
        int finalResult = baseNumber - (markUp * baseNumber / 100);
        return finalResult;
    }

    public static int roundUpToHundred(int number){
        int rounded = ((number + 99) / 100) * 100;
        return rounded;
    }
}
