package Model;

public class PricesFactory {
    public static int[][] rentTable(){
        int[][] rentTable = new int[][]{
                //Rødovrevej, leje, dobbelt leje, 1 hus, 2 hus, 3 hus, 4 hus, hotel
                {50, 100, 250, 750, 2250, 4000, 6000},
                //Hvidovrevej
                {50, 100, 250, 750, 2250, 4000, 6000},
        };
        return rentTable;
    }

    public static int[][] upgradeTable(){
        int[][] upgradeTable = new int[][]{
                //hus pris, hotel pris
                {},
                {},
        };
        return upgradeTable;
    }
}
