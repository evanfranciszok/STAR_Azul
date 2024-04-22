package game.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bag {
    public final List<Tile> contents;

    //@ public invariant contents != null;

    /**
     * Default constructor
     * Initializes the contents of the bag
     */
    //@ ensures contents != null;
    //@ ensures contents.size() == 100;
    public Bag() {
        contents = new ArrayList<>();
        reset();
    }

    /**
     * Reset the tiles in the bag
     * Clears the bag and adds 20 of each color tile @see Tile
     */
    //@ requires contents != null;
    //@ ensures contents != null;
    //@ ensures contents.size() == 100;
    public void reset() {
        contents.clear();
        //@ assert contents.size() == 0;

        var NTilesPerColor = 20;
        //@ decreases NTilesPerColor - i;
        //@ loop_invariant contents.size() % 5 == 0;
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
    //@ requires tiles != null;
    //@ requires contents.size() == 0;
    //@ ensures contents != null;
    //@ ensures contents.size() == \old(tiles.size());
    //@ ensures (\forall Tile t; tiles.contains(t); contents.contains(t));
    //@ ensures tiles.isEmpty();
    public void refill(List<Tile> tiles) {
        if (!contents.isEmpty()) {
            throw new RuntimeException("Called Bag::refill() but Bag is not empty!");
        }
        //@ ghost int total_length = tiles.size();

        //@ loop_invariant contents.size() + tiles.size() == total_length;
        //@ loop_invariant i == tiles.size()-1;
        //@ decreases i;
        for (int i = tiles.size() - 1; i >= 0; --i) {
            //@ assert contents.size() + tiles.size() == total_length;
            contents.add(tiles.get(i));
            tiles.remove(tiles.get(i));
            //@ assert contents.size() + tiles.size() == total_length;
        }
    }

    /**
     * Extracts one tile from the bag
     *
     * @return (Tile) The extracted tile
     * @throws RuntimeException If trying to extract from an empty bag
     */
    //@ ensures \result != null;
    //@ ensures contents.size() == \old(contents.size() - 1);
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
    //@ ensures contents != null && contents.contains(tile);
    public void putTile(Tile tile) {
        contents.add(tile);
    }

    /**
     * Getter for the contents member variable
     *
     * @return (List < Tile >) The contents member variable
     */
    //@ ensures \result == contents;
    public List<Tile> getContents() {
        return contents;
    }

    /**
     * @return Whether the bag is empty
     */
    //@ ensures \result == contents.isEmpty();
    public boolean isEmpty() {
        return contents.isEmpty();
    }
}
