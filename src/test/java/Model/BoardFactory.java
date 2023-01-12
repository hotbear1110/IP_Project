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
        demoBoard[4] = new DemoSquare("4\n","","");
        demoBoard[5] = new DemoSquare("5\n","","");
        demoBoard[6] = new Lot(Translator.getString("SQUARE_6"),"","", 2000, 0, PricesFactory.rentTable()[2], Color.orange);
        demoBoard[7] = new Chance(Translator.getString("SQUARE_7"),"","");
        demoBoard[8] = new Lot(Translator.getString("SQUARE_8"),"","", 2000, 0, PricesFactory.rentTable()[3], Color.orange);
        demoBoard[9] = new Lot(Translator.getString("SQUARE_9"),"","", 2400, 0, PricesFactory.rentTable()[4], Color.orange);
        demoBoard[10] = new DemoSquare("10\n","","");
        demoBoard[11] = new Lot(Translator.getString("SQUARE_10"),"","", 2800, 0, PricesFactory.rentTable()[5], Color.green);
        demoBoard[12] = new DemoSquare("11\n","","");
        demoBoard[13] = new Lot(Translator.getString("SQUARE_13"),"","", 2800, 0, PricesFactory.rentTable()[6], Color.green);
        demoBoard[14] = new Lot(Translator.getString("SQUARE_14"),"","", 3200, 0, PricesFactory.rentTable()[7], Color.green);
        demoBoard[15] = new DemoSquare("15\n","","");
        demoBoard[16] = new Lot(Translator.getString("SQUARE_16"),"","", 3600, 0, PricesFactory.rentTable()[8], Color.gray);
        demoBoard[17] = new Chance(Translator.getString("SQUARE_17"),"","");
        demoBoard[18] = new Lot(Translator.getString("SQUARE_18"),"","", 3600, 0, PricesFactory.rentTable()[9], Color.gray);
        demoBoard[19] = new Lot(Translator.getString("SQUARE_19"),"","", 4000, 0, PricesFactory.rentTable()[10], Color.gray);
        demoBoard[20] = new DemoSquare("20\n","","");
        demoBoard[21] = new Lot(Translator.getString("SQUARE_21"),"","", 4400, 0, PricesFactory.rentTable()[11], Color.red);
        demoBoard[22] = new Chance(Translator.getString("SQUARE_18"),"","");
        demoBoard[23] = new Lot(Translator.getString("SQUARE_23"),"","", 4400, 0, PricesFactory.rentTable()[12], Color.red);
        demoBoard[24] = new Lot(Translator.getString("SQUARE_24"),"","", 4800, 0, PricesFactory.rentTable()[13], Color.red);
        demoBoard[25] = new DemoSquare("25\n","","");
        demoBoard[26] = new Lot(Translator.getString("SQUARE_26"),"","", 5200, 0, PricesFactory.rentTable()[14], Color.white);
        demoBoard[27] = new Lot(Translator.getString("SQUARE_27"),"","", 5200, 0, PricesFactory.rentTable()[15], Color.white);
        demoBoard[28] = new DemoSquare("28\n","","");
        demoBoard[29] = new Lot(Translator.getString("SQUARE_29"),"","", 5600, 0, PricesFactory.rentTable()[16], Color.white);
        demoBoard[30] = new DemoSquare("30\n","","");
        demoBoard[31] = new Lot(Translator.getString("SQUARE_31"),"","", 6000, 0, PricesFactory.rentTable()[17], Color.yellow);
        demoBoard[32] = new Lot(Translator.getString("SQUARE_32"),"","", 6000, 0, PricesFactory.rentTable()[18], Color.yellow);
        demoBoard[33] = new Chance(Translator.getString("SQUARE_33"),"","");
        demoBoard[34] = new Lot(Translator.getString("SQUARE_34"),"","", 6400, 0, PricesFactory.rentTable()[19], Color.yellow);
        demoBoard[35] = new DemoSquare("35\n","","");
        demoBoard[36] = new Chance(Translator.getString("SQUARE_35"),"","");
        demoBoard[37] = new Lot(Translator.getString("SQUARE_37"),"","", 7000, 0, PricesFactory.rentTable()[20], Color.magenta);
        demoBoard[38] = new DemoSquare("38\n","","");
        demoBoard[39] = new Lot(Translator.getString("SQUARE_39"),"","", 8000, 0, PricesFactory.rentTable()[21], Color.magenta);
        return demoBoard;
    }
    public static Square[] makeBoard(){
        return new Square[]{
        };
    }
}
