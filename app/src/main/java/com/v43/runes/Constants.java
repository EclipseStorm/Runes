package com.v43.runes;

public class Constants {

    // Wizard status

    public static final int NO_EFFECTS = 0;
    public static final int STUNNED = 1;
    public static final int CURSED = 2;

    // Spells

    public static final int FIREBALL = 1;
    public static final int PARALYZING_RAY = 2;
    public static final int CURSE = 3;
    public static final int ARCANE_SHIELD = 4;
    public static final int HEAL = 5;

    // Game balance

    public static final int LIFE_POINTS_MAX = 100;
    public static final int SHIELD_POINTS_MAX = 30;

    public static final int FIREBALL_NORMAL_DAMAGE = 30;
    public static final int FIREBALL_HARDCORE_DAMAGE_MIN = 5;
    public static final int FIREBALL_HARDCORE_DAMAGE_MAX = 25;

    public static final int PARALYZING_RAY_NORMAL_DAMAGE = 10;
    public static final int PARALYZING_RAY_NORMAL_STUN_CHANCE = 20;
    public static final int PARALYZING_RAY_HARDCORE_DAMAGE_MIN = 0;
    public static final int PARALYZING_RAY_HARDCORE_DAMAGE_MAX = 10;
    public static final int PARALYZING_RAY_HARDCORE_STUN_CHANCE_MIN = 5;
    public static final int PARALYZING_RAY_HARDCORE_STUN_CHANCE_MAX = 20;
    public static final double PARALYZING_RAY_HARDCORE_STUN_TIME_MIN = 0.5;
    public static final double PARALYZING_RAY_HARDCORE_STUN_TIME_MAX = 2.0;

    public static final double CURSE_NORMAL_TIME = 0.5;
    public static final int CURSE_HARDCORE_DEBUFF = 50;

    public static final int ARCANE_SHIELD_NORMAL = 30;
    public static final int ARCANE_SHIELD_HARDCORE_MIN = 5;
    public static final int ARCANE_SHIELD_HARDCORE_MAX = 30;

    public static final int HEAL_NORMAL = 25;
    public static final int HEAL_HARDCORE_MIN = 5;
    public static final int HEAL_HARDCORE_MAX = 25;

}
