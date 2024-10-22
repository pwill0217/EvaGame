package com.example;

public class Angel {
    private String name;
    private double powerLevel;

    public Angel(String name, double powerLevel) {
        this.name = name;
        this.powerLevel = powerLevel;
    }

    public String getName() {
        return name;
    }

    public double getPowerLevel() {
        return powerLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPowerLevel(double powerLevel) {
        this.powerLevel = powerLevel;
    }
}