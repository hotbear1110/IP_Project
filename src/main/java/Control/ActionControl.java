package Control;

import Model.ChanceCards.Cards;
import Model.ChanceCards.ChancePay;
import Model.ChanceCards.ChanceReceive;
import Model.ChanceCards.Deck;
import Model.Game;
import Model.Player;
import Model.Squares.Chance;
import Model.Squares.DemoSquare;
import Model.Squares.Lot;
import Model.Squares.Start;

import java.util.ArrayList;

public class ActionControl {
    private GameControl gameControl;
    public ActionControl(GameControl gameControl){
        this.gameControl = gameControl;
    }

    public void controlAction(int position){
        if (gameControl.getGame().getBoard().getSquare(position) instanceof Start){

        }
        if (gameControl.getGame().getBoard().getSquare(position) instanceof Lot){

        }
        if (gameControl.getGame().getBoard().getSquare(position) instanceof Chance) {
                Player player = gameControl.getGame().getCurrentPlayer();

                ArrayList<Cards> cards = Deck.getCards();

                Cards card = cards.get(0);
                cards.remove(0);
                cards.add(card);

                String cardType = card.getType();
                int playerCount = gameControl.getGame().getPlayers().length; //Vi skal have en metode der retunerer playercount

                switch (cardType) {
                    case "payAmount" -> {
                        ChancePay payAmountCard = (ChancePay) card;
                        payAmountCard.payAmount(player);
                    }
                    case "payPerHouse" -> {
                        ChancePay payPerHouseCard = (ChancePay) card;
                        payPerHouseCard.payPerHouse(player);
                    }
                    case "recieve" -> {
                        ChanceReceive recieveCard = (ChanceReceive) card;
                        recieveCard.recieve(player);
                    }
                    case "steal" -> {
                        ChanceReceive stealCard = (ChanceReceive) card;
                        stealCard.steal(player, playerCount);
                    }
                    case "rarecieve" -> {
                        ChanceReceive rarecieveCard = (ChanceReceive) card;
                        rarecieveCard.rarecieve(player);
                    }
                }
        }
        if (gameControl.getGame().getBoard().getSquare(position) instanceof DemoSquare) {

        }
    }
}
