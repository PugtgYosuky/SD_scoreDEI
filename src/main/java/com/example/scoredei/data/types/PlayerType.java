package com.example.scoredei.data.types;

public enum PlayerType {

    GOALKEEPER("Goalkeeper"),
    DEFENDER("Defender"),
    MIDFIELDER("Midfielder"),
    FORWARD("Forward");

    private String name;

    PlayerType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

}