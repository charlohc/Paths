package edu.ntnu.paths.GameDetails;

import java.util.List;
//Mabye create builder class here
public class Player {
    private final String name;
    private int health;
    private int score;
    private int gold;
    private List<String> inventory;

    public Player(String name, int health, int score, int gold) {
        this.name = name;
        this.health = health;
        this.score = score;
        this.gold = gold;
    }

    public Player(Player playerCopy) {
        this(playerCopy.getName(), playerCopy.getHealth(),playerCopy.getScore(), playerCopy.getGold());
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void addHealth(int health) {
        if (health <= 0) throw new IllegalArgumentException("Cannot add negative or zero number to health");
        this.health += health;
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

    public void addToInventory(String item) {
        if (item.isEmpty()) throw new IllegalArgumentException("Item cannot be blank");
        if (inventory.contains(item)) throw new IllegalArgumentException("Item is already in inventory");
        inventory.add(item);
    }
}
