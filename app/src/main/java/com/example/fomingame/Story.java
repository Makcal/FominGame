package com.example.fomingame;

import java.util.LinkedList;

public class Story {
    LinkedList<Situation> questQueue = new LinkedList<>();
    public Character player;

    final Situation
        outOfMoney = new Situation("Кончились деньги", "",
            "Вы обеднели и умерли с голоду.", new Statistics()),
        badReputation = new Situation("Вас ненавидят", "", "Вы сделали столько плохих дел, что " +
                "люди решили убить вас.", new Statistics());

    public Story(Character player) {
        this.player = player;
    }

    public Situation choose(int number) {
        return choose(number, false);
    }

    public Situation choose(int number, boolean addToTail) {
        if (isEnd()) return null;
        Situation current = getCurrentSituation();
        if (number < 0 || number >= current.options.size())
            return null;
        addQuest(current.options.get(number), addToTail);
        return nextQuest();
    }

    public Situation getCurrentSituation() {
        return isEnd() ? null : questQueue.getFirst();
    }

    public void addQuest(Situation situation) {
        addQuest(situation, false);
    }

    public void addQuest(Situation situation, boolean toTail) {
        if (toTail)
            questQueue.add(situation);
        else
            questQueue.add(1, situation);
    }

    public Situation nextQuest() {
        questQueue.poll();

        if (player.stat.strength <= -1) {
            questQueue.clear();
        }

        if (player.stat.money <= 0) {
            questQueue.clear();
            addQuest(outOfMoney);
        }

        if (!isEnd())
            return getCurrentSituation();
        return null;
    }

    public boolean isEnd() {
        return questQueue.size() == 0;
    }

    public void doQuest() {
        getCurrentSituation().action(player);
        if (!isEnd())
            player.stat.add(getCurrentSituation().statChange);
    }
}
