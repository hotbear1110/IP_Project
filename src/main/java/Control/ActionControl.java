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

        }
        if (gameControl.getGame().getBoard().getSquare(position) instanceof Property property){
            landedOnProperty(property);
        }
        if (gameControl.getGame().getBoard().getSquare(position) instanceof Chance) {

        }
        if (gameControl.getGame().getBoard().getSquare(position) instanceof DemoSquare) {

        }
    }

    public void landedOnProperty(Property property){
        if (!property.isOwned()){
            buyProperty(property);
        } else if (!property.getOwner().equals(gameControl.getGame().getCurrentPlayer())){
            payRent(property);
        } else if (!disableUpgrade){
            upgradeProperty(property);
        }

    }

    public void buyProperty(Property property){
        String s = gameControl.getUI().getUserButton(Translator.getString("LAND_ON_UNOWNED") + "\nPrisen er " + property.getPrice(), "Ja" , "Nej");
        if (s.equals("Ja")){
            gameControl.getGame().getCurrentPlayer().setPlayerBalance(-property.getPrice());
            property.setOwner(gameControl.getGame().getCurrentPlayer());
        }
    }

    public void payRent(Property property){
        gameControl.getUI().showMessage(Translator.getString("LAND_ON_OWNED") + property.getRent());
        gameControl.getGame().getCurrentPlayer().setPlayerBalance(-property.getRent());
        if (gameControl.getGame().getCurrentPlayer().isBankrupt()){
            //set player as bankrupt
        } else {
            property.getOwner().setPlayerBalance(property.getRent());
        }
    }

    public void upgradeProperty(Property property){

    }

    public void landedOnChance(){
        String chanceCard = gameControl.getGame().getBoard().drawCard(gameControl.getGame().getCurrentPlayer(), gameControl.getGame().getPlayers());
        gameControl.getUI().getChanceCard(chanceCard);
        gameControl.getUI().updateUI(gameControl.getGame().getPlayers(), gameControl.getGame().getDice().getSingleDice(0), gameControl.getGame().getDice().getSingleDice(1));
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
