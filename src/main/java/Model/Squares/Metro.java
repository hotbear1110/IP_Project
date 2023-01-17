package Model.Squares;

public class Metro extends Square {
    private final int nearestMetroSquare;
    public Metro(String name, String subText, String description, int nearestMetroSquare){
        super(name,subText, description);
        this.nearestMetroSquare = nearestMetroSquare;
    }

    public int getNearestMetroSquare(){
        return nearestMetroSquare;
    }

    @Override
    public String toString(){
        return super.name;
    }
}
