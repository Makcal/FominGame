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

    public Story(Character player, Situation start) {
        questQueue.add(start);
        this.player = player;
    }

    public void choose(int number) {
        choose(number, false);
    }

    public void choose(int number, boolean addToTail) {
        Situation current = getCurrentSituation();
        if (number < 0 || number >= current.options.size())
            return;
        addQuest(current.options.get(number), addToTail);
        nextQuest();
    }

    public Situation getCurrentSituation() {
        return questQueue.getFirst();
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
        player.stat.add(getCurrentSituation().statChange);
    }
}
