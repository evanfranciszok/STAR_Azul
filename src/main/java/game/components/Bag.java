package game.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bag {
    private final List<Tile> contents;

    /**
     * Default constructor
     * Initializes the contents of the bag
     */
    public Bag() {
        contents = new ArrayList<>();
        reset();
    }

    /**
     * Reset the tiles in the bag
     * Clears the bag and adds 20 of each color tile @see Tile
     */
    public void reset() {
        contents.clear();

        var NTilesPerColor = 20;
        for (var i = 0; i < NTilesPerColor; ++i) {
            contents.add(Tile.BLUE);
            contents.add(Tile.ORANGE);
            contents.add(Tile.RED);
            contents.add(Tile.BLACK);
            contents.add(Tile.WHITE);
        }
    }

    /**
     * Refill the bag using the discarded tiles from the current round
     *
     * @param tiles (List<Tile>) The discard pile, all tiles in this list will be moved to the bag, so this list will be emptied
     * @throws RuntimeException If trying to refill a non-empty bag
     */
    public void refill(List<Tile> tiles) {
        if (!contents.isEmpty()) {
            throw new RuntimeException("Called Bag::refill() but Bag is not empty!");
        }
        for (int i = tiles.size() - 1; i >= 0; --i) {
            contents.add(tiles.get(i));
            tiles.remove(tiles.get(i));
        }
    }

    /**
     * Extracts one tile from the bag
     *
     * @return (Tile) The extracted tile
     * @throws RuntimeException If trying to extract from an empty bag
     */
    public Tile extractOneTile() {
        if (contents.isEmpty()) {
            throw new RuntimeException("Trying to extract a tile from an empty bag");
        }

        var index = new Random().nextInt(contents.size());
        var tile = contents.get(index);
        contents.remove(index);
        return tile;
    }

    /**
     * Insert one Tile into the bag
     *
     * @param tile (Tile) The tile to be inserted
     */
    public void putTile(Tile tile) {
        contents.add(tile);
    }

    /**
     * Getter for the contents member variable
     *
     * @return (List < Tile >) The contents member variable
     */
    public List<Tile> getContents() {
        return contents;
    }

    /**
     * @return Whether the bag is empty
     */
    public boolean isEmpty() {
        return contents.isEmpty();
    }
}
