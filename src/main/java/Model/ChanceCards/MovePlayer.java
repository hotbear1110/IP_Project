package Model.ChanceCards;

import Model.Player;

public class MovePlayer {

    private static String name;

    private static String type;

    private static String description;

    private static int fields;
    public MovePlayer(String Name, String Type, String Description, int Fields){
        name = Name;
        type = Type;
        description = Description;
        fields = Fields;
    }

    public static void move(Player player) {
        Player.moveCurrentPlayerPosition(fields);
    }

}
