package Model.ChanceCards;

import Model.Player;

public class MovePlayer extends Cards {

    private String description;

    private int fields;
    public MovePlayer(String Name, String Type, String Description, int Fields){
        super(Name, Type, Description);
        fields = Fields;
    }

    public int move(Player player) {
        player.movePlayerPosition(fields);

        return fields;
    }

    public int specific(Player player) {
        int position = player.getPlayerPosition();

        int newfields = (fields - position < 0) ? 39 + (fields - position) : fields - position;
        player.movePlayerPosition(newfields);

        return newfields;
    }

    public void jail(Player player) {
        player.setPlayerPosition(fields);
    }

    public int moveToNext(Player player) {
        int newfields = (player.getPlayerPosition() > 15) ? 35 : 15;

        int move = newfields - player.getPlayerPosition();

        player.movePlayerPosition(move);
        return move;
    }

}

