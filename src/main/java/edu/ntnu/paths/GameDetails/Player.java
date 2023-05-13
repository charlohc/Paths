package edu.ntnu.paths.GameDetails;

import java.util.List;
//Mabye create builder class here
public class Player {
    private final String name;
    private int health;
    private int score;
    private int gold;
    private List<String> inventory;

    Player(PlayerBuilder playerBuilder) {
        if (playerBuilder.name.isEmpty()) {
            throw new NullPointerException("Name cannot be null");
        } else if (playerBuilder.health < 0) {
            throw new IllegalArgumentException("Health cannot be zero or negative");
        } else if (playerBuilder.score < 0) {
            throw new IllegalArgumentException("Score cannot negative");
        } else if (playerBuilder.gold < 0) {
            throw new IllegalArgumentException("Gold cannot negative");
        } else {
            this.name = playerBuilder.name.trim();
            this.health = playerBuilder.health;
            this.score = playerBuilder.score;
            this.gold = playerBuilder.gold;
            this.inventory = playerBuilder.inventory;
        }
    }

    public Player (Player copyPlayer) {
        this.name = copyPlayer.getName();
        this.health = copyPlayer.getHealth();
        this.score = copyPlayer.getScore();
        this.gold = copyPlayer.getGold();
        this.inventory = copyPlayer.getInventory();
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void changeHealth(int health) {
        if ((this.health + health) < 0) { this.health = 0;
        } else if ((this.health + health) > 100){
            this.health = 100;
        }
        else {
            this.health += health;
        }
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        if (score <= 0) throw new IllegalArgumentException("Cannot add negative or zero number to score");
        this.score += score;
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int gold) {
        if (gold <= 0) throw new IllegalArgumentException("Cannot add negative or zero number to gold");
        this.gold += gold;
    }

    public List<String> getInventory() {
        return inventory;
    }

    //can collect more items of one type? check if is in list already
    public void addToInventory(String item) {
        if (item.isEmpty()) throw new NullPointerException("Item cannot be blank");
        inventory.add(item.trim().toLowerCase());
    }
}
