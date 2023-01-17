package Model.Squares;

public class Chance extends Square{

    public Chance(String name, String subText, String description){
        super(name, subText, description);
    }

    public String[] getSquareInfo(){
        return new String[]{name, subText, description};
    }
}
