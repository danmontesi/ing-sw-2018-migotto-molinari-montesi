package it.polimi.se2018;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Describes DraftPool behavior. A die can be placed in the draftPool, removed from it, rolled or switched with
 * another one (not in de draftPool). The number of present dice can be returned as well.
 * @author Alessio Molinari, Nives Migotto
 */
public class DraftPool {
    /**
     * The arraylist is a List of Die.
     * If a Die is picked, the value has to remain NULL in order to let the Graphic to remain the same when a die is removed
     */
    private ArrayList<Die> dice;

    /**
     * Constructor: generates a draftPool by taking from the dicebag 2 dice for each player + 1
     * @param dice dice to assign
     */
    public DraftPool(ArrayList<Die> dice) {
        this.dice = dice;
    }

    /**
     * Removes a die from the draftPool
     * @param diePosition position from which the die is taken
     * @return removed die
     */
    public Die takeDie(int diePosition){
        Die temp = dice.get(diePosition);
        dice.set(diePosition, new Die(COLOR.RED, 0)); //@danmontesi TODO: PER ORA DISTINGUO UN DADO RIMOSSO COME DADO CON VALORE 0 , BISOGNA CAMBIARLO
        return temp;
    }

    /**
     * Switches a die with a random one
     * @param toBeSwitched new die in draftPool
     * @return old die from draftPool
     */
    public Die switchDie(Die toBeSwitched){
        int index = ThreadLocalRandom.current().nextInt(0,  dice.size());
        Die temp = dice.get(index);
        dice.set(index, toBeSwitched);
        return temp;
    }

    /**
     * Switches a die with a given one
     * @param diePosition position from which the die is taken
     * @param toBeSwitched new die in draftPool
     * @return old die from draftPool
     */
    public Die switchDie(int diePosition, Die toBeSwitched){
        Die temp = dice.get(diePosition);
        dice.set(diePosition, toBeSwitched);
        return temp;
    }

    /**
     * Places a die in the draftPool
     * @param toBePlaced to be placed in the draftPool die
     */
    public void placeDie(Die toBePlaced){
        dice.add(toBePlaced);
    }

    //roll all dice in the DraftPool

    /**
     * Rolls all dice in the draftPool (gives all dice a new random value)
     */
    public void rollDice(){
        for (Die aDice : dice) {
            aDice.roll();
        }
    }

    /**
     *
     * @return numbers of dice in Draft Pool
     */
    public int draftPoolSize(){
        return dice.size();
    }

    /**
     *
     * @param index draftPool index
     * @return Die at given index
     */
    public Die getDie(int index){
        return dice.get(index);
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < dice.size(); i++) {
            if (dice.get(i).getValue() == 0) {
                builder.append(i).append(":- ").append("noDie");
            } else {
                builder.append(i).append(":- ").append(dice.get(i).toString());
            }
            builder.append("\t");
        }
        builder.append("\n");
        return builder.toString();
    }
}
