package ip2.gameboard;

public class Board {
    private final int NUM_OF_SQUARES = 40;
    private final Squares[] gameBoard = new Squares[NUM_OF_SQUARES];;

    public Board(){
    gameBoard [0] = new Start( "Start");
    gameBoard [1] = new Lots("Rødovrevej");
    gameBoard [2] = new Chance( "Prøv Lykken");
    gameBoard [3] = new Lots("Hvidovervej");
    gameBoard [4] = new Tax("Indkomstskat eller 4.000kr.");
    gameBoard [5] = new Ships("Scandlines");
    gameBoard [6] = new Lots("Roskildevej");
    gameBoard [7] = new Chance("Prøv Lykken");
    gameBoard [8] = new Lots("Valby Langgade");
    gameBoard [9] = new Lots("Allégade");
    gameBoard [10] = new Jail("Fængsel");
    gameBoard [11] = new Lots("Frederiksberg Allé");
    gameBoard [12] = new Brewery("Tuborg Squash");
    gameBoard [13] = new Lots("Bülowsvej");
    gameBoard [14] = new Lots("Gl. Kongevej");
    gameBoard [15] = new Ships("Mols linien");
    gameBoard [16] = new Lots("Bernstoffsvej");
    gameBoard [17] = new Chance("Prøv Lykken");
    gameBoard [18] = new Lots("Hellerupvej");
    gameBoard [19] = new Lots("Strandvejen");
    gameBoard [20] = new Parking("Parkering");
    gameBoard [21] = new Lots("Trianglen");
    gameBoard [22] = new Chance("Prøv Lykken");
    gameBoard [23] = new Lots("Østerbrogade");
    gameBoard [24] = new Lots("Grønningen");
    gameBoard [25] = new Ships("Scandlines");
    gameBoard [26] = new Lots("Bredgade");
    gameBoard [27] = new Lots("Kgs. Nytorv");
    gameBoard [28] = new Brewery("Coca Cola");
    gameBoard [29] = new Lots("Østergade");
    gameBoard [30] = new Jail("Gå i Fængsel");
    gameBoard [31] = new Lots("Amagertorv");
    gameBoard [32] = new Lots("Vimmelskaftet");
    gameBoard [33] = new Chance("Prøv Lykken");
    gameBoard [34] = new Lots("Nygade");
    gameBoard [35] = new Ships("Scandlines");
    gameBoard [36] = new Chance("Prøv Lykken");
    gameBoard [37] = new Lots("Frederiksberggade");
    gameBoard [38] = new Tax("Statsskat");
    gameBoard [39] = new Lots("Rådhuspladsen");


    }
}
