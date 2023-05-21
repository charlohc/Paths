package edu.ntnu.paths.GameDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder class for creating Player objects.
 */
public class PlayerBuilder {
    public String name;
    public int health;
    public int score;
    public int gold;
    public List<String> inventory = new ArrayList<>();

    /**
     * Creates a new instance of PlayerBuilder.
     *
     * @return A new instance of PlayerBuilder.
     */
    public static PlayerBuilder newInstance() {
        return new PlayerBuilder();
    }

    private PlayerBuilder() {}

    /**
     * Sets the name of the player.
     *
     * @param name The name of the player.
     * @return The PlayerBuilder instance.
     */
    public PlayerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the health of the player.
     *
     * @param health The health of the player.
     * @return The PlayerBuilder instance.
     */
    public PlayerBuilder setHealth(int health) {
        this.health = health;
        return this;
    }

    /**
     * Sets the score of the player.
     *
     * @param score The score of the player.
     * @return The PlayerBuilder instance.
     */
    public PlayerBuilder setScore(int score) {
        this.score = score;
        return this;
    }

    /**
     * Sets the gold amount of the player.
     *
     * @param gold The gold amount of the player.
     * @return The PlayerBuilder instance.
     */
    public PlayerBuilder setGold(int gold) {
        this.gold = gold;
        return this;
    }

    /**
     * Sets the inventory of the player.
     *
     * @param inventory The inventory of the player.
     * @return The PlayerBuilder instance.
     */
    public PlayerBuilder setInventory(List<String> inventory) {
        this.inventory = inventory;
        return this;
    }

    /**
     * Builds a new Player object based on the configured builder.
     *
     * @return A new Player object.
     */
    public Player build() {
        return new Player(this);
    }
}
