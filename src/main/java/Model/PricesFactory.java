package Model;

public class PricesFactory {
    public static int[][] rentTable(){
        int[][] rentTable = new int[][]{
                //Rødovrevej, leje, dobbelt leje, 1 hus, 2 hus, 3 hus, 4 hus, hotel
                {50, 100, 250, 750, 2250, 4000, 6000},
                //Hvidovrevej
                {50, 100, 250, 750, 2250, 4000, 6000},
                //Roskildevej
                {100, 200, 600, 1800, 5400, 8000, 11000},
                //Valby Langgade
                {100, 200, 600, 1800, 5400, 8000, 11000},
                //Allégade
                {150, 300, 800, 2000, 6000, 9000, 12000},
                //Frederiksberg Alle
                {200, 400, 1000, 3000, 9000, 12500, 15000},
                //Bülowsvej
                {200, 400, 1000, 3000, 9000, 12500, 15000},
                //Gl. Kongevej
                {250, 500, 1250, 3750, 10000, 14000, 18000},
                //Bernstoffsvej
                {300, 600, 1400, 4000, 11000, 15000, 19000},
                //Hellerupvej
                {300, 600, 1400, 4000, 11000, 15000, 19000},
                //Strandvejen
                {350, 700, 1600, 4400, 12000, 16000, 20000},
                //Trianglen
                {350, 700, 1800, 5000, 14000, 17500, 21000},
                //Østerbrogade
                {350, 700, 1800, 5000, 14000, 17500, 21000},
                //Grønningen
                {400, 800, 2000, 6000, 15000, 18500, 22000},
                //Bredgade
                {450, 900, 2200, 6600, 16000, 19500, 23000},
                //Kgs. Nytorv
                {450, 900, 2200, 6600, 16000, 19500, 23000},
                //Østergade
                {500, 1000, 2400, 7200, 17000, 20500, 24000},
                //Amagertorv
                {550, 1100, 2600, 7800, 18000, 22000, 25000},
                //Vimmelskaftet
                {550, 1100, 2600, 7800, 18000, 22000, 25000},
                //Nygade
                {600, 1200, 3000, 9000, 20000, 24000, 28000},
                //Frederiksberggade
                {700, 1400, 3500, 10000, 22000, 26000, 30000},
                //Rådhuspladsen
                {1000, 2000, 4000, 12000, 28000, 34000, 40000}
        };
        return rentTable;
    }

    public static int[][] upgradeTable(){
        int[][] upgradeTable = new int[][]{
                //Pris hus , pris hotel
                //Rødovrevej
                {1000, 5000},
                //Hvidovrevej
                {1000, 5000},
                //Roskildevej
                {1000, 5000},
                //Valby Langgade
                {1000, 5000},
                //Allégade
                {1000, 5000},
                //Frederiksberg Alle
                {2000, 10000},
                //Bülowsvej
                {2000, 10000},
                //Gl. Kongevej
                {2000, 10000},
                //Bernstoffsvej
                {2000, 10000},
                //Hellerupvej
                {2000, 10000},
                //Strandvejen
                {2000, 10000},
                //Trianglen
                {3000, 15000},
                //Østerbrogade
                {3000, 15000},
                //Grønningen
                {3000, 15000},
                //Bredgade
                {3000, 15000},
                //Kgs. Nytorv
                {3000, 15000},
                //Østergade
                {3000, 15000},
                //Amagertorv
                {4000, 20000},
                //Vimmelskaftet
                {4000, 20000},
                //Nygade
                {4000, 20000},
                //Frederiksberggade
                {4000, 20000},
                //Rådhuspladsen
                {4000, 20000}
        };
        return upgradeTable;
    }
}
