package Model.ChanceCards;

import Model.Player;

public class MovePlayer extends Cards {

    private String description;

    private int fields;
    public MovePlayer(String Name, String Type, String Description, int Fields){
        super(Name, Type, Description);
        fields = Fields;
    }

    public void move(Player player) {
        player.movePlayerPosition(fields);
    }

    public void specific(Player player) {
        player.movePlayerPosition(fields);
    }

    public void jail(Player player) {
        //Vi skal lige finde ud af en måde spilleren ikke får penge på
        player.movePlayerPosition(fields);
    }

    public void moveToNext(Player player) {
        //Vi skal lige finde ud af en måde at se hvilet redderi der er det næste
        player.movePlayerPosition(fields);
    }

}
