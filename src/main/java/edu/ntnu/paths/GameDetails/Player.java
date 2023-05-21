package edu.ntnu.paths.GameDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 */
public class Player {
    private final String name;
    private int health;
    private int score;
    private int gold;
    private List<String> inventory;

    /**
     * Constructs a new Player object using the provided builder.
     *
     * @param playerBuilder The builder object containing player configurations.
     */
    Player(PlayerBuilder playerBuilder) {
        if (playerBuilder.name.isEmpty()) {
            throw new NullPointerException("Name cannot be null");
        } else if (playerBuilder.health < 0) {
            throw new IllegalArgumentException("Health cannot be zero or negative");
        } else if (playerBuilder.score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        } else if (playerBuilder.gold < 0) {
            throw new IllegalArgumentException("Gold cannot be negative");
        } else {
            this.name = playerBuilder.name.trim();
            this.health = playerBuilder.health;
            this.score = playerBuilder.score;
            this.gold = playerBuilder.gold;
            this.inventory = playerBuilder.inventory;
        }
    }

    /**
     * Constructs a new Player object by copying an existing Player.
     *
     * @param copyPlayer The Player object to copy.
     */
    public Player(Player copyPlayer) {
        this.name = copyPlayer.getName();
        this.health = copyPlayer.getHealth();
        this.score = copyPlayer.getScore();
        this.gold = copyPlayer.getGold();
        this.inventory = new ArrayList<>(copyPlayer.getInventory());
    }

    /**
     * Returns the name of the player.
     *
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the health of the player.
     *
     * @return The player's health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Changes the health of the player by the specified amount.
     *
     * @param health The amount to change the player's health by.
     */
    public void changeHealth(int health) {
        if ((this.health + health) < 0) {
            this.health = 0;
        } else if ((this.health + health) > 100) {
            this.health = 100;
        } else {
            this.health += health;
        }
    }

    /**
     * Returns the score of the player.
     *
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds the specified score to the player's current score.
     *
     * @param score The score to add.
     * @throws IllegalArgumentException if the score is zero or negative.
     */
    public void addScore(int score) {
        if (score <= 0) {
            throw new IllegalArgumentException("Cannot add negative or zero number to score");
        }
        this.score += score;
    }

    /**
     * Returns the amount of gold the player has.
     *
     * @return The player's gold amount.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Adds the specified amount of gold to the player's current gold.
     *
     * @param gold The amount of gold to add.
     * @throws IllegalArgumentException if the gold amount is zero or negative.
     */
    public void addGold(int gold) {
        if (gold <= 0) {
            throw new IllegalArgumentException("Cannot add negative or zero number to gold");
        }
        this.gold += gold;
    }

    /**
     * Returns the inventory of the player.
     *
     * @return The player's inventory.
     */
    public List<String> getInventory() {
        return inventory;
    }

    /**
     * Adds the specified item to the player's inventory.
     *
     * @param item The item to add to the inventory.
     * @throws NullPointerException if the item is blank.
     */
    public void addToInventory(String item) {
        if (item.isEmpty()) {
            throw new NullPointerException("Item cannot be blank");
        }
        inventory.add(item.trim().toLowerCase());
    }

    /**
     * Sets the inventory of the player.
     *
     * @param inventory The inventory list to set.
     */
    public void setInventory(List<String> inventory) {
        this.inventory = inventory;
    }
}
