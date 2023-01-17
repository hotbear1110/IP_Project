package Model;

import java.util.Random;

public class Dice {
    private int[] dice;
    boolean doubleDice;

    public Dice() {
        dice = new int[FixedValues.NUM_OF_DICE];
    }

    public void rollDice() {
        //Makes a new Random object, so we can get random numbers.
        Random randomObj = new Random();
        //A loop that "throws" the dice and sets each value in DiceArray
        for (int i = 0; i < FixedValues.NUM_OF_DICE; i++) {
            //Random number between 1 and 6 (including).
            dice[i] = randomObj.nextInt(6) + 1;
        }
        isDouble();
    }
    public void setDicePair(int[] pair){
        dice = pair;
        isDouble();
    }
    public int getSingleDice(int index) {
        return dice[index];
    }
    public int getSum() {
        return dice[0]+dice[1];
    }
    public int[] getPair(){
        return dice;
    }
    public boolean isDouble() {
        if (dice[0] == dice[1]){
                doubleDice = true;
        }
        return doubleDice;
    }

    public void resetDouble(){
        doubleDice = false;
    }
}