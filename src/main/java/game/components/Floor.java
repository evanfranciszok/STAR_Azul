package game.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Floor {
    /**
     * The size of the floor
     */
    public static final int FLOOR_SIZE = 7;
    /**
     * The minus points that are given for each tile
     */
    private static final int[] MINUS_POINTS = {-1, -1, -2, -2, -2, -3, -3};
    /**
     * The tiles on the floor
     */
    private Tile[] contents = new Tile[FLOOR_SIZE];
    /**
     * The amount of tiles on the floor
     */
    private int currentIndex = 0;

    /**
     * Whether the floor contains the STARTING_PLAYER_TILE
     */
    private boolean containsStartingPlayerTile = false;

    /**
     * The default constructor
     */
    public Floor() {
    }

    /**
     * Place a tile onto the floor
     *
     * @param tile (Tile) The tile being placed on the floor
     * @throws IllegalStateException If the floor is full and the @p tile is not a Tile.STARTING_PLAYER_TILE
     */
    public void placeTile(Tile tile) throws IllegalStateException {
        if (tile == Tile.STARTING_PLAYER_TILE) {
            containsStartingPlayerTile = true;
            if (isFull()) {
                return;
            }
        } else if (isFull()) {
            throw new IllegalStateException("Floor is full");
        }

        contents[currentIndex] = tile;
        ++currentIndex;
    }

    /**
     * Extract all tiles from the floor and reset the floor
     *
     * @return The extracted tiles from the floor, does not include STARTING_PLAYER_TILE
     */
    public List<Tile> reset() {
        var returnedTiles = new ArrayList<>(Arrays.asList(contents).subList(0, currentIndex).stream().filter(tile -> tile != Tile.STARTING_PLAYER_TILE).toList());
        currentIndex = 0;
        contents = new Tile[FLOOR_SIZE];
        containsStartingPlayerTile = false;
        return returnedTiles;
    }

    /**
     * Is the floor full
     *
     * @return true if the floor is full, else false
     */
    public boolean isFull() {
        return currentIndex >= FLOOR_SIZE;
    }

    /**
     * Is the starting player tile on the floor
     *
     * @return true if the starting player tile is on the floor, else false
     */
    public boolean hasStartingPlayerTile() {
        return containsStartingPlayerTile;
    }

    /**
     * Get the minus score obtained through tiles on the floor
     *
     * @return The minus score (Always <= 0)
     */
    public int getMinusScore() {
        int totalMinusPoints = 0;
        for (var i = 0; i < currentIndex; ++i) {
            totalMinusPoints += MINUS_POINTS[i];
        }
        return totalMinusPoints;
    }

    /**
     * @return The amount of tiles currently placed on the floor
     */
    public int getFillLevel() {
        return currentIndex;
    }
}