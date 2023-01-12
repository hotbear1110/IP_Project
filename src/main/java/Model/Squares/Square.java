package Model.Squares;

public class Square {
    final String name;
    final String subText;
    final String description;

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

    public String[] getSquareInfo(){
        return new String[]{name, subText, description};
    }
}
