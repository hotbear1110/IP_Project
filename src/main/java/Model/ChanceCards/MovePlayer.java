package Model.ChanceCards;

import Model.Player;

import java.util.ArrayList;

public class MovePlayer extends Cards {

    private String description;

    private int fields;
    public MovePlayer(String Name, String Type, String Description, int Fields){
        super(Name, Type, Description);
        fields = Fields;
    }

    public String[] move(Player player) {
        player.movePlayerPosition(fields);

        return new String[]{"move", Integer.toString(fields)};
    }

    public String[] specific(Player player) {
        int position = player.getPlayerPosition();

        int newfields = (fields - position < 0) ? 40 + (fields - position) : fields - position;
        player.movePlayerPosition(newfields);

        return new String[]{"move", Integer.toString(newfields)};
    }

    public void jail(Player player) {
        player.setPlayerPosition(fields);
    }

    public String[] moveToNext(Player player) {

        int newfields = (player.getPlayerPosition() > 15) ? 35 : 15;

        int move = newfields - player.getPlayerPosition();

        player.movePlayerPosition(move);
        if (fields == 0) {
            return  new String[]{"double", Integer.toString(move)};
        }
        return  new String[]{"move", Integer.toString(move)};
    }

}

