package Model;

import java.util.Random;

public class Dice {
    private int[] dice;

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
    }
    public void setDice(int[] pair){
        dice = pair;
    }
    public int getDice(int index) {
        return dice[index];
    }
    public int getSum() {
        return dice[0]+dice[1];
    }
    public int[] getPair(){
        return dice;
    }
    public boolean isDouble() {
        return dice[0] == dice[1];
    }
}