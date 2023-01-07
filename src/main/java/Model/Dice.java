package Model;

import java.util.Random;

public class Dice {
    private int[] Dice;
    private final int NUM_OF_DICE = 2; // Number of dice in the game

    //A constructor that makes an Array of int and sets the die count.
    public Dice() {
        Dice = new int[NUM_OF_DICE];
    }

    /**
     * Throws the dice
     */
    public void rollDice() {
        //Makes a new Random object, so we can get random numbers.
        Random randomObj = new Random();
        //A loop that "throws" the dice and sets each value in DiceArray
        for (int i = 0; i < NUM_OF_DICE; i++) {
            //Random number between 1 and 6 (including).
            Dice[i] = randomObj.nextInt(6) + 1;
        }
    }

    /**
     * @return (int) Total sum of the dice values
     */
    public int getTotal() {
        return Dice[0]+Dice[1];
    }

    /**
     * @param index (int) the index of the die you want to get
     * @return (int) A specific Die value
     */
    public int getDice(int index) {
        return Dice[index];
    }

    /**
     * @return (boolean) Returns true, if The 2 Die values are the same.
     */
    public boolean getDouble() {
        return Dice[0] == Dice[1];
    }
}