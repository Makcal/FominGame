package com.example.fomingame;

public class Statistics {
    public int money, power, reputation, strength;

    public Statistics() {
        this(0, 0, 0, 0);
    }

    public Statistics(int money, int power, int reputation, int strength) {
        this.money = money;
        this.power = power;
        this.reputation = reputation;
        this.strength = strength;
    }

    public void add(Statistics other) {
        money += other.money;
        power += other.power;
        reputation += other.reputation;
        strength += other.strength;
    }
}
