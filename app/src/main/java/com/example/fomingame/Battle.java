package com.example.fomingame;

public class Battle extends Situation {
    int requiredStrength;
    String monsterName;

    public Battle(String monsterName, String result, Statistics statChange, int requiredStrength) {
        super("Битва", monsterName + '(' + requiredStrength + ')', result, statChange);
        this.requiredStrength = requiredStrength;
        this.monsterName = monsterName;
    }

    @Override
    public void action(Character player) {
        if (player.stat.strength >= requiredStrength) {
            result = "Вы победили монстра " + monsterName + "!\n" + result;
        }
        else {
            result = "Монстр " + monsterName + " победил вас.\nВы умерли.";
            statChange = new Statistics();
        }
    }
}
