package com.example.fomingame;

import java.util.ArrayList;
import java.util.List;

public class Situation {
    public List<Situation> options;
    public String title, description, result;
    public Statistics statChange;
    public boolean continueImmediately = true;

    public Situation(String title, String description, String result, Statistics statChange, List<Situation> options) {
        this.title = title;
        this.description = description;
        this.statChange = statChange;
        this.options = options;
        this.result = result;
    }

    public Situation(String title, String description, String result, Statistics statChange) {
        this(title, description, result, statChange, new ArrayList<>());
    }

    public void action(Character player) {
        // do nothing
    }
}
