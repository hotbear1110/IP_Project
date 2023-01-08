package Model.ChanceCards;

public class JailCard extends Cards{
    private static String description;
    public JailCard(String Name, String Type, String Description){
        super(Name, Type);
        description = Description;
    }
}
