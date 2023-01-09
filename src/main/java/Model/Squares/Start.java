package Model.Squares;

public class Start extends Square{

    public Start(String name, String subtext, String description){
        super(name, subtext, description);
    }

    public String[] getSquareInfo(){
        return new String[]{name, subText, description};
    }

}
