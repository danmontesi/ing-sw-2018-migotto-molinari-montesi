package it.polimi.se2018.model.public_obj_cards;

import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.model.WindowPatternCard;

import java.util.HashSet;

public class ShadeVariety extends PublicObjectiveCard{
    public ShadeVariety(String name, String description, Integer score) {
        this.name = name;
        this.description = description;
        this.score = score;
    }

    @Override
    public String getName() {
        return name;
    }
    /**
     * Sets of one of each value anywhere
     * @param w WindowPatternCard for which you want to calculate the score
     * @return shadeVariety score
     */
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        HashSet<Integer> numbers = new HashSet<>();
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                try {
                    numbers.add(w.getCell(i, j).getAssociatedDie().getValue());
                } catch (EmptyCellException e) {
                    //nothing
                }
            }

        }
        if(numbers.size() == 6){
            total += score;
        }
        return total;
    }
}
