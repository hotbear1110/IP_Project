package Control;

import Model.ChanceCards.Cards;
import Model.ChanceCards.ChancePay;
import Model.ChanceCards.ChanceReceive;
import Model.ChanceCards.Deck;
import Model.Game;
import Model.Player;
import Model.Squares.*;

import java.util.ArrayList;

public class ActionControl {
    private GameControl gameControl;
    private boolean disableBuy = false;
    private boolean disableChance = false;

    private boolean disableUpgrade = false;
    public ActionControl(GameControl gameControl){
        this.gameControl = gameControl;
    }

    public void controlAction(int position){
        if (gameControl.getGame().getBoard().getSquare(position) instanceof Start){
            //gameControl.getUI().showMessage(Translator.getString("LAND_ON_START"));
        }
        if (gameControl.getGame().getBoard().getSquare(position) instanceof Lot lot){
            if (!disableBuy){
                landedOnProperty(lot);
            }
        }
        if (gameControl.getGame().getBoard().getSquare(position) instanceof Chance) {
            if (!disableChance){
                landedOnChance();
            }
        }
        if (gameControl.getGame().getBoard().getSquare(position) instanceof DemoSquare) {
        }
    }

    public void landedOnProperty(Lot lot){
        if (!lot.isOwned()){
            buyProperty(lot);
        } else if (!lot.getOwner().equals(gameControl.getGame().getCurrentPlayer())){
            payRent(lot);
        } else if (!disableUpgrade){
            upgradeProperty(lot);
        }

    }

    public void buyProperty(Lot lot){
        String s = gameControl.getUI().getUserButton(Translator.getString("LAND_ON_UNOWNED") + "\nPrisen er " + lot.getPrice(), "Ja" , "Nej");
        if (s.equals("Ja")){
            gameControl.getGame().getCurrentPlayer().setPlayerBalance(-lot.getPrice());
            lot.setOwner(gameControl.getGame().getCurrentPlayer());
        }
    }

    public void payRent(Lot lot){
        gameControl.getUI().showMessage(Translator.getString("LAND_ON_OWNED") + lot.getRent());
        if (gameControl.getGame().getCurrentPlayer().getPlayerBalance() < lot.getRent()){
            gameControl.getGame().getCurrentPlayer().setPlayerBalance(-lot.getRent());
            int n = gameControl.getGame().getCurrentPlayer().getPlayerBalance() % lot.getRent();
            lot.getOwner().setPlayerBalance(n);
        } else {
            gameControl.getGame().getCurrentPlayer().setPlayerBalance(-lot.getRent());
            lot.getOwner().setPlayerBalance(lot.getRent());
        }
    }

    public void upgradeProperty(Lot lot){

    }

    public void landedOnChance(){
        String chanceCard = gameControl.getGame().getBoard().drawCard(gameControl.getGame().getCurrentPlayer(), gameControl.getGame().getPlayers());
        gameControl.getUI().getChanceCard(chanceCard);
    }
    public void disableBuy(){
        disableBuy = true;
    }

    public void disableChance(){
        disableChance = true;
    }
    public void disableUpgrade(){
        disableUpgrade = true;
    }
}
