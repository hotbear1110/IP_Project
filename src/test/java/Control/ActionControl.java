package Control;
import Model.Squares.*;
import Model.Squares.DemoSquare;

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
        if (gameControl.getGame().getBoard().getSquare(position) instanceof Property){
            if (!disableBuy){

                landedOnProperty((Property) gameControl.getGame().getBoard().getSquare(position));
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

    public void landedOnProperty(Property property){
        if (!property.isOwned()){
            buyProperty(property);
        } else if (!property.getOwner().equals(gameControl.getGame().getCurrentPlayer())){
            payRent(property);
        } else if (!disableUpgrade){
            upgradeProperty((Lot) property);
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
        if (gameControl.getGame().getCurrentPlayer().getPlayerBalance() < property.getRent()){
            gameControl.getGame().getCurrentPlayer().setPlayerBalance(-property.getRent());
            int n = gameControl.getGame().getCurrentPlayer().getPlayerBalance() % property.getRent();
            property.getOwner().setPlayerBalance(n);
        } else {
            gameControl.getGame().getCurrentPlayer().setPlayerBalance(-property.getRent());
            property.getOwner().setPlayerBalance(property.getRent());
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
