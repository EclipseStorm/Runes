package com.v43.runes.model;

import com.v43.runes.Constants;

public class Wizard {

    private String name;
    private int lifePoints;
    private int shield;
    private int status;


    public Wizard(){
        //TODO: Initialize name
        lifePoints = Constants.LIFE_POINTS_MAX;
        shield = 0;
        status = Constants.NO_EFFECTS;
    }

    public void receiveDamage(int damage){
        if (shield == 0){
            lifePoints = lifePoints - damage;
        }
        else {
            shield = shield - damage;
            if (shield < 0){
                lifePoints = lifePoints + shield;
                shield = 0;
            }
        }
    }


    public void setLifePoints(int lifePoints){
        this.lifePoints = this.lifePoints + lifePoints;
        if (this.lifePoints > Constants.LIFE_POINTS_MAX){
            this.lifePoints = Constants.LIFE_POINTS_MAX;
        }
    }

    public void setShield(int shield){
        this.shield = this.shield + shield;
        if (this.shield > Constants.SHIELD_POINTS_MAX){
            this.shield = Constants.SHIELD_POINTS_MAX;
        }
    }

    public void setStatus(int status){
        this.status = status;
    }


    public String getName(){
        return name;
    }

    public int getHp(){
        return lifePoints;
    }

    public int getShield(){
        return shield;
    }

    public int getStatus() {
        return status;
    }

}
