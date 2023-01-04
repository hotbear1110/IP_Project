package ip2.gameboard;

public abstract class Squares {
    private final String name;

    public Squares(String name) {
        this.name = name;
    }

    public String getSquareName(){
        return name;
    }
}
