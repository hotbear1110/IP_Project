package Model.Squares;

public abstract class Square {
    private final String name;
    private final String subText;
    private final String description;

    public Square(String name, String subText, String description){
        this.name = name;
        this.subText = subText;
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public String getSubText(){
        return subText;
    }

    public String getDescription(){
        return description;
    }
}
