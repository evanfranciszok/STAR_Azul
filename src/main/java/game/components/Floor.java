package game.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Floor {


    /**
     * The size of the floor
     */
    public static final int FLOOR_SIZE = 7; //@ in contentsTest;
    /**
     * The minus points that are given for each tile
     */

    private static final int[] MINUS_POINTS = {-1, -1, -2, -2, -2, -3, -3}; //@ in MINUS_POINTSTest;
    //@ public model int[] MINUS_POINTSTest;
    //@ private represents MINUS_POINTSTest = MINUS_POINTS;

    /**
     * The tiles on the floor
     */
    private Tile[] contents = new Tile[FLOOR_SIZE]; //@ in contentsTest;
    //@ public model Tile[] contentsTest;
    //@ private represents contentsTest = contents;

    /**
     * The amount of tiles on the floor
     */
    private int currentIndex = 0; //@ in currentIndexTest;
    //@ public model int currentIndexTest;
    //@ private represents currentIndexTest = currentIndex;
    /**
     * Whether the floor contains the STARTING_PLAYER_TILE
     */
    private boolean containsStartingPlayerTile = false;//@ in containsStartingPlayerTileTest;
    //@ public model boolean containsStartingPlayerTileTest;
    //@ private represents containsStartingPlayerTileTest = containsStartingPlayerTile;

    //@ public invariant currentIndexTest >= 0 && currentIndexTest <= FLOOR_SIZE;
    //@ public invariant contentsTest.length == FLOOR_SIZE;
    //@ public invariant (\forall int i; 0 <= i && i < currentIndexTest; contentsTest[i] != null);
    //@ public invariant (\forall int i; 0 <= i && i < MINUS_POINTSTest.length; MINUS_POINTSTest[i] < 0);
    /**
     * The default constructor
     */
    //@ ensures currentIndexTest == 0 && !containsStartingPlayerTileTest;
    public Floor() {
    }

    /**
     * Place a tile onto the floor
     *
     * @param tile (Tile) The tile being placed on the floor
     * @throws IllegalStateException If the floor is full and the @p tile is not a Tile.STARTING_PLAYER_TILE
     */


    //@ ensures !isFull() ==> (getFillLevel() == \old(getFillLevel()) + 1);
    //@ ensures tile == Tile.STARTING_PLAYER_TILE ==> hasStartingPlayerTile();
    //@ requires tile != null;
    //@ ensures containsStartingPlayerTileTest ==> currentIndexTest == \old(currentIndexTest);
    //@ ensures !containsStartingPlayerTileTest ==> (currentIndexTest == \old(currentIndexTest) + 1 || currentIndexTest == \old(currentIndexTest));
    //@ signals (IllegalStateException) isFull();
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
    //@ ensures getFillLevel() == 0 && !hasStartingPlayerTile() && \result.isEmpty();
    //@ ensures currentIndexTest == 0;
    //@ ensures contentsTest != null && contentsTest.length == FLOOR_SIZE;
    //@ ensures !containsStartingPlayerTileTest;
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
    //@ ensures \result ==> getFillLevel() >= FLOOR_SIZE;
    //@ ensures !\result ==> getFillLevel() < FLOOR_SIZE;
    //@ pure
    public boolean isFull() {
        return currentIndex >= FLOOR_SIZE;
    }

    /**
     * Is the starting player tile on the floor
     *
     * @return true if the starting player tile is on the floor, else false
     */

    //@ ensures \result ==> hasStartingPlayerTile();
    //@ ensures !\result ==> !hasStartingPlayerTile();
    //@ pure
    public boolean hasStartingPlayerTile() {
        return containsStartingPlayerTile;
    }

    /**
     * Get the minus score obtained through tiles on the floor
     *
     * @return The minus score (Always <= 0)
     */

    //@ ensures \result <= 0;

    //@ ensures \result == (\sum int i; 0 <= i && i < currentIndexTest; MINUS_POINTSTest[i]);
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

    //@ ensures \result == currentIndexTest;
    //@ pure
    public int getFillLevel() {
        return currentIndex;
    }
}