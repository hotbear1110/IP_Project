package Model;

import Control.Translator;
import Model.Squares.*;

import java.awt.*;

public class BoardFactory {

    public static Square[] makeBoard(){
        Square[] board = new Square[FixedValues.NUM_OF_SQUARES];
        board[0] = new Start("START","","");
        board[1] = new Lot(Translator.getString("SQUARE_1"), "", "", 1200, 0, PricesFactory.rentTable()[0], PricesFactory.upgradeTable()[0], Color.blue);
        board[2] = new Chance(Translator.getString("SQUARE_2"), "", "");
        board[3] = new Lot(Translator.getString("SQUARE_3"), "", "", 1200, 0, PricesFactory.rentTable()[1], PricesFactory.upgradeTable()[1], Color.blue);
        board[4] = new Tax(Translator.getString("SQUARE_4"),"","");
        board[5] = new Metro(Translator.getString("SQUARE_5"),"","", 17);
        board[6] = new Lot(Translator.getString("SQUARE_6"),"","", 2000, 0, PricesFactory.rentTable()[2], PricesFactory.upgradeTable()[2], Color.orange);
        board[7] = new Chance(Translator.getString("SQUARE_7"),"","");
        board[8] = new Lot(Translator.getString("SQUARE_8"),"","", 2000, 0, PricesFactory.rentTable()[3], PricesFactory.upgradeTable()[3], Color.orange);
        board[9] = new Lot(Translator.getString("SQUARE_9"),"","", 2400, 0, PricesFactory.rentTable()[4], PricesFactory.upgradeTable()[4], Color.orange);
        board[10] = new DemoSquare(Translator.getString("SQUARE_10"),"","");
        board[11] = new Lot(Translator.getString("SQUARE_11"),"","", 2800, 0, PricesFactory.rentTable()[5], PricesFactory.upgradeTable()[5], Color.green);
        board[12] = new Brewery(Translator.getString("SQUARE_12"),"","", 3000, 0, PricesFactory.rentTable()[6]);
        board[13] = new Lot(Translator.getString("SQUARE_13"),"","", 2800, 0, PricesFactory.rentTable()[7], PricesFactory.upgradeTable()[6], Color.green);
        board[14] = new Lot(Translator.getString("SQUARE_14"),"","", 3200, 0, PricesFactory.rentTable()[8], PricesFactory.upgradeTable()[7], Color.green);
        board[15] = new Ship(Translator.getString("SQUARE_15"),"","", 4000, 0, PricesFactory.rentTable()[9]);
        board[16] = new Lot(Translator.getString("SQUARE_16"),"","", 3600, 0, PricesFactory.rentTable()[10], PricesFactory.upgradeTable()[8], Color.gray);
        board[17] = new Metro(Translator.getString("SQUARE_17"),"","", 25);
        board[18] = new Lot(Translator.getString("SQUARE_18"),"","", 3600, 0, PricesFactory.rentTable()[11], PricesFactory.upgradeTable()[9], Color.gray);
        board[19] = new Lot(Translator.getString("SQUARE_19"),"","", 4000, 0, PricesFactory.rentTable()[12], PricesFactory.upgradeTable()[10], Color.gray);
        board[20] = new Parking(Translator.getString("SQUARE_20"),"","");
        board[21] = new Lot(Translator.getString("SQUARE_21"),"","", 4400, 0, PricesFactory.rentTable()[13], PricesFactory.upgradeTable()[11], Color.red);
        board[22] = new Chance(Translator.getString("SQUARE_18"),"","");
        board[23] = new Lot(Translator.getString("SQUARE_23"),"","", 4400, 0, PricesFactory.rentTable()[14], PricesFactory.upgradeTable()[12], Color.red);
        board[24] = new Lot(Translator.getString("SQUARE_24"),"","", 4800, 0, PricesFactory.rentTable()[15], PricesFactory.upgradeTable()[13], Color.red);
        board[25] = new Metro(Translator.getString("SQUARE_25"),"","", 36);
        board[26] = new Lot(Translator.getString("SQUARE_26"),"","", 5200, 0, PricesFactory.rentTable()[16], PricesFactory.upgradeTable()[14], Color.white);
        board[27] = new Lot(Translator.getString("SQUARE_27"),"","", 5200, 0, PricesFactory.rentTable()[17], PricesFactory.upgradeTable()[15], Color.white);
        board[28] = new Brewery(Translator.getString("SQUARE_28"),"","", 3000, 0, PricesFactory.rentTable()[18]);
        board[29] = new Lot(Translator.getString("SQUARE_29"),"","", 5600, 0, PricesFactory.rentTable()[19], PricesFactory.upgradeTable()[16], Color.white);
        board[30] = new DemoSquare(Translator.getString("SQUARE_30"),"","");
        board[31] = new Lot(Translator.getString("SQUARE_31"),"","", 6000, 0, PricesFactory.rentTable()[20], PricesFactory.upgradeTable()[17], Color.yellow);
        board[32] = new Lot(Translator.getString("SQUARE_32"),"","", 6000, 0, PricesFactory.rentTable()[21], PricesFactory.upgradeTable()[18], Color.yellow);
        board[33] = new Chance(Translator.getString("SQUARE_33"),"","");
        board[34] = new Lot(Translator.getString("SQUARE_34"),"","", 6400, 0, PricesFactory.rentTable()[22], PricesFactory.upgradeTable()[19], Color.yellow);
        board[35] = new Ship(Translator.getString("SQUARE_35"),"","", 4000, 0, PricesFactory.rentTable()[23]);
        board[36] = new Metro(Translator.getString("SQUARE_36"),"","", 5);
        board[37] = new Lot(Translator.getString("SQUARE_37"),"","", 7000, 0, PricesFactory.rentTable()[24], PricesFactory.upgradeTable()[20], Color.magenta);
        board[38] = new Tax(Translator.getString("SQUARE_38"),"","");
        board[39] = new Lot(Translator.getString("SQUARE_39"),"","", 8000, 0, PricesFactory.rentTable()[25], PricesFactory.upgradeTable()[21], Color.magenta);
        return board;
    }
}
