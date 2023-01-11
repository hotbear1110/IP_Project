package Model;

import Control.Translator;
import Model.Squares.*;

import java.awt.*;

public class BoardFactory {

    public static Square[] makeDemoBoard(){
        Square[] demoBoard = new Square[FixedValues.NUM_OF_SQUARES];
        demoBoard[0] = new Start("START","","");
        demoBoard[1] = new Lot(Translator.getString("SQUARE_1"), "", "", 1200, 0, PricesFactory.rentTable()[0], Color.blue);
        demoBoard[2] = new Chance(Translator.getString("SQUARE_2"), "", "");
        demoBoard[3] = new Lot(Translator.getString("SQUARE_3"), "", "", 1200, 0, PricesFactory.rentTable()[1], Color.blue);
        demoBoard[4] = new Metro(Translator.getString("LAND_ON_METRO"),"","");
        demoBoard[5] = new DemoSquare("4\n","","");
        demoBoard[6] = new DemoSquare("5\n","","");
        demoBoard[7] = new Lot(Translator.getString("SQUARE_6"),"","", 2000, 0, PricesFactory.rentTable()[2], Color.orange);
        demoBoard[8] = new Chance(Translator.getString("SQUARE_7"),"","");
        demoBoard[9] = new Lot(Translator.getString("SQUARE_8"),"","", 2000, 0, PricesFactory.rentTable()[3], Color.orange);
        demoBoard[10] = new Lot(Translator.getString("SQUARE_9"),"","", 2400, 0, PricesFactory.rentTable()[4], Color.orange);
        demoBoard[11] = new DemoSquare("10\n","","");
        demoBoard[12] = new Lot(Translator.getString("SQUARE_10"),"","", 2800, 0, PricesFactory.rentTable()[5], Color.green);
        demoBoard[13] = new DemoSquare("11\n","","");
        demoBoard[14] = new Lot(Translator.getString("SQUARE_13"),"","", 2800, 0, PricesFactory.rentTable()[6], Color.green);
        demoBoard[15] = new Lot(Translator.getString("SQUARE_14"),"","", 3200, 0, PricesFactory.rentTable()[7], Color.green);
        demoBoard[16] = new Metro(Translator.getString("LAND_ON_METRO"),"","");
        demoBoard[17] = new DemoSquare("15\n","","");
        demoBoard[18] = new Lot(Translator.getString("SQUARE_16"),"","", 3600, 0, PricesFactory.rentTable()[8], Color.gray);
        demoBoard[19] = new Chance(Translator.getString("SQUARE_17"),"","");
        demoBoard[20] = new Lot(Translator.getString("SQUARE_18"),"","", 3600, 0, PricesFactory.rentTable()[9], Color.gray);
        demoBoard[21] = new Lot(Translator.getString("SQUARE_19"),"","", 4000, 0, PricesFactory.rentTable()[10], Color.gray);
        demoBoard[22] = new DemoSquare("20\n","","");
        demoBoard[23] = new Lot(Translator.getString("SQUARE_21"),"","", 4400, 0, PricesFactory.rentTable()[11], Color.red);
        demoBoard[24] = new Chance(Translator.getString("SQUARE_18"),"","");
        demoBoard[25] = new Lot(Translator.getString("SQUARE_23"),"","", 4400, 0, PricesFactory.rentTable()[12], Color.red);
        demoBoard[26] = new Lot(Translator.getString("SQUARE_24"),"","", 4800, 0, PricesFactory.rentTable()[13], Color.red);
        demoBoard[27] = new DemoSquare("25\n","","");
        demoBoard[28] = new Metro(Translator.getString("LAND_ON_METRO"),"","");
        demoBoard[29] = new Lot(Translator.getString("SQUARE_26"),"","", 5200, 0, PricesFactory.rentTable()[14], Color.white);
        demoBoard[30] = new Lot(Translator.getString("SQUARE_27"),"","", 5200, 0, PricesFactory.rentTable()[15], Color.white);
        demoBoard[31] = new DemoSquare("28\n","","");
        demoBoard[32] = new Lot(Translator.getString("SQUARE_29"),"","", 5600, 0, PricesFactory.rentTable()[16], Color.white);
        demoBoard[33] = new DemoSquare("30\n","","");
        demoBoard[34] = new Lot(Translator.getString("SQUARE_31"),"","", 6000, 0, PricesFactory.rentTable()[17], Color.yellow);
        demoBoard[35] = new Lot(Translator.getString("SQUARE_32"),"","", 6000, 0, PricesFactory.rentTable()[18], Color.yellow);
        demoBoard[36] = new Chance(Translator.getString("SQUARE_33"),"","");
        demoBoard[37] = new Lot(Translator.getString("SQUARE_34"),"","", 6400, 0, PricesFactory.rentTable()[19], Color.yellow);
        demoBoard[38] = new DemoSquare("35\n","","");
        demoBoard[39] = new Chance(Translator.getString("SQUARE_35"),"","");
        demoBoard[40] = new Metro(Translator.getString("LAND_ON_METRO"),"","");
        demoBoard[41] = new Lot(Translator.getString("SQUARE_37"),"","", 7000, 0, PricesFactory.rentTable()[20], Color.magenta);
        demoBoard[42] = new DemoSquare("38\n","","");
        demoBoard[43] = new Lot(Translator.getString("SQUARE_39"),"","", 8000, 0, PricesFactory.rentTable()[21], Color.magenta);
        return demoBoard;
    }
    public static Square[] makeBoard(){
        return new Square[]{
        };
    }
}
