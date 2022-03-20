package com.example.fomingame;

public class Battle extends Situation {
    int requiredStrength;
    String monsterName;
    boolean intro = true;

    public Battle(String monsterName, String result, Statistics statChange, int requiredStrength) {
        super(
                "Битва",
                monsterName + '(' + requiredStrength + ')',
                "На вас напал " + monsterName + "!",
                new Statistics()
        );
        this.options.add(new Battle(
                monsterName,
                result,
                statChange,
                requiredStrength,
                false
        ));
        this.requiredStrength = requiredStrength;
        this.monsterName = monsterName;
    }

    private Battle(String monsterName, String result, Statistics statChange, int requiredStrength, boolean isIntro) {
        super(
                "Битва: " + monsterName + '(' + requiredStrength + ')',
                "Сражаться",
                result,
                statChange
        );
        this.requiredStrength = requiredStrength;
        this.monsterName = monsterName;
        this.intro = isIntro;
    }

    @Override
    public void action(Character player) {
        super.action(player);
        if (intro) return;
        if (player.stat.strength >= requiredStrength) {
            result = "Вы победили монстра " + monsterName + "!\n" + result;
        }
        else {
            result = "Монстр " + monsterName + " победил вас.\nВы умерли.";
            statChange = new Statistics();
            player.stat.strength = -1;
        }
    }
}
