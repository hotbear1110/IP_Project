package Model.ChanceCards;

public class Cards {

    private String name;
    private String type;

    private String description;

    public Cards(String Name, String Type, String Description) {
        name = Name;
        type = Type;
        description = Description;
    }

    public String getType() {
        return this.type;
    }

    public String getDescription() {
        return description;
    }
}
