package com.example.fomingame;

public class Character {
    public Statistics stat;
    public String name;
    public int life = 0;

    public Character(String name, Statistics initials) {
        this.name = name;
        stat = initials;
    }
}
