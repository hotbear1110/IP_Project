package Model.ChanceCards;

import Model.Player;

public class Cards {

    private String name;
    private String type;

    public Cards(String Name, String Type) {
        name = Name;
        type = Type;
    }

    public String getType() {
        return this.type;
    }

}
