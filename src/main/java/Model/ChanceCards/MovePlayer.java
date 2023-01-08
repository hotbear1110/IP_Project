package Model.ChanceCards;

import Model.Player;

public class MovePlayer extends Cards {

    private String description;

    private int fields;
    public MovePlayer(String Name, String Type, String Description, int Fields){
        super(Name, Type);
        description = Description;
        fields = Fields;
    }

    public void move(Player player) {
        Player.moveCurrentPlayerPosition(fields);
    }

    public void specific(Player player) {
        Player.setCurrentPlayerPosition(fields);
    }

    public void jail(Player player) {
        //Vi skal lige finde ud af en måde spilleren ikke får penge på
        Player.setCurrentPlayerPosition(fields);
    }

}
