package com.example.fomingame;

public class Story {
    public Situation situation;

    public Story() {

    }

    public void choose(int number) {

    }

    public boolean isEnd() {
        return situation.options.size() == 0;
    }
}
