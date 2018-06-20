package it.polimi.se2018.model.public_obj_cards;

import it.polimi.se2018.model.COLOR;
import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.model.WindowPatternCard;

import java.util.HashSet;

public class ColorVariety extends PublicObjectiveCard {
    public ColorVariety(String name, String description, Integer score) {
        this.name = name;
        this.description = description;
        this.score = score;
    }

    @Override
    public String getName() {
        return name;
    }
    /**
     * Sets of one of each color anywhere
     * @param w WindowPatternCard for which you want to calculate the score
     * @return colorVariety score
     */
    public int calculateScore(WindowPatternCard w) {
        int total = 0;
        HashSet<COLOR> colors = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                    try {
                        colors.add(w.getCell(i, j).getAssociatedDie().getColor());
                    } catch (EmptyCellException e) {
                        //nothing
                    }
            }
            if (colors.size() == 5) {
                total += score;
            }
        }
        return total;
    }
}


