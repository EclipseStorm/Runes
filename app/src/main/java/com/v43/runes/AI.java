package com.v43.runes;

import com.v43.runes.model.Wizard;

public class AI {

    public static int aiSelectSpell(Wizard player1, Wizard player2) {
        int spell = 0;
        int random = (int) (Math.random() * 100) + 1; // Goes from 1 to 100

        if (player2.getShield() <= 10 && random <= 40) {
            spell = Constants.FIREBALL;
        }

        else if (player2.getHp() < 30 && random <= 80) {
            spell = Constants.HEAL;
        }

        else if (player2.getHp() < 60 && random <= 40) {
            spell = Constants.HEAL;
        }

        else if (player1.getHp() < 30 && random <= 60) {
            spell = Constants.FIREBALL;
        }

        else {
            if (random <= 45) {
                spell = Constants.FIREBALL;
            }
            else if (random > 45 && random <= 70) {
                spell = Constants.PARALYZING_RAY;
            }
            else if (random > 70 && random <= 80) {
                spell = Constants.CURSE;
            }
            else {
                spell = Constants.ARCANE_SHIELD;
            }
        }

        return spell;
    }


    public static double aiScore(int spell) {
        double score = 0;

        if (spell == 1) {
            score = Math.random() * 40 + 10;
        }
        else if (spell == 2) {
            score = Math.random() * 40 + 10;
        }
        else if (spell == 3) {
            score = Math.random() * 20 + 10;
        }
        else if (spell == 4) {
            score = Math.random() * 40 + 10;
        }
        else if (spell == 5) {
            score = Math.random() * 140 + 60;
        }

        return score;
    }

}
