package edu.ntnu.paths.GameDetails;

import java.util.ArrayList;
import java.util.List;

public class PlayerBuilder {
    public String name;
    public int health;
    public int score;
    public int gold;
    public List<String> inventory = new ArrayList<>();

    public static PlayerBuilder newInstance() {
        return new PlayerBuilder();
    }

    private PlayerBuilder() {}

    public PlayerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PlayerBuilder setHealth(int health) {
        this.health = health;
        return this;
    }

    public PlayerBuilder setScore(int score) {
        this.score = score;
        return this;
    }

    public PlayerBuilder setGold(int gold) {
        this.gold = gold;
        return this;
    }

    public PlayerBuilder setInventory(List<String> inventory) {
        this.inventory = inventory;
        return this;
    }

    public Player build() {
        return new Player(this);
    }
}

