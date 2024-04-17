package game.components;

import game.ui.NamedElement;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class TokenPool implements NamedElement, TokenPoolInterface {
    /**
     * The name of the TokenPool
     */
    private final String name;
    /**
     * The tiles in the pool
     */
    private ArrayList<Tile> tiles;

    /**
     * Default constructor
     */
    // @ ensures getName().equals(name);
    // @ ensures getContents().isEmpty();
    public TokenPool(String name) {
        tiles = new ArrayList<>();
        this.name = name;
    }

    /**
     * Get the tiles in the TokenPool, without removing them.
     *
     * @return the tiles in the TokenPool
     */
    // @ ensures \result != null;
    // @ ensures \result.equals(tiles);
    public ArrayList<Tile> getContents() {
        return tiles;
    }

    /**
     * Add the given tiles to the pool
     *
     * @param tiles (ArrayList<Tile>) The list of tiles that should be added to the
     *              pool
     */
    // @ requires tiles != null;
    // @ ensures getContents().containsAll(tiles);
    public void addTiles(ArrayList<Tile> tiles) {
        this.tiles.addAll(tiles);
    }

    /**
     * Add the given tile to the pool
     *
     * @param tile (Tile) The tile that should be added to the pool
     */
    // @ requires tile != null;
    // @ ensures getContents().contains(tile);
    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    /**
     * Extract all Tiles of a given color from the pool
     * And return the extracted tiles
     * Note: This function also returns the STARTING_PLAYER_TILE if it is in this
     * TokenPool
     *
     * @param color (Tile) The color of the tiles to be extracted
     * @return (List < Tile >) The extracted tiles
     */
    // @ requires color != null;
    // @ ensures \result != null;
    // @ ensures (\forall Tile t; \result.contains(t); t == color || t ==
    // Tile.STARTING_PLAYER_TILE);
    // @ ensures getContents().containsAll(\result);
    public List<Tile> extractTilesOfColor(Tile color) {
        if (color == Tile.STARTING_PLAYER_TILE) {
            throw new InvalidParameterException("Extracting only the STARTING_PLAYER_TILE is not supported");
        }
        var tilesToExtract = new ArrayList<Tile>();

        // @ loop_invariant i >= 0 && i <= tiles.size();
        // @ decreases i;
        for (int i = tiles.size() - 1; i >= 0; --i) {
            if (tiles.get(i) == color) {
                tilesToExtract.add(tiles.get(i));
                tiles.remove(tiles.get(i));
            }
        }
        if (tilesToExtract.isEmpty()) {
            throw new InvalidParameterException(
                    "There are no tiles of the requested color available in this TokenPool");
        }
        if (tiles.remove(Tile.STARTING_PLAYER_TILE)) {
            tilesToExtract.add(Tile.STARTING_PLAYER_TILE);
        }
        return tilesToExtract;
    }

    /**
     * Return the remaining tiles in the pool
     *
     * @return (List < Tile >) The extracted tiles
     */
    // @ ensures \result != null;
    // @ ensures getContents().isEmpty();
    public List<Tile> extractRemainingTiles() {
        var remainingTiles = tiles;
        tiles = new ArrayList<>();
        return remainingTiles;
    }

    /**
     * @return Whether the TokenPool is empty or not
     */
    // @ ensures \result == getContents().isEmpty();
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    /**
     * {@inheritDoc} <br/>
     * Getter for the name of the token pool
     *
     * @return The name of the token pool
     */
    // @ ensures \result.equals(name);
    @Override
    public String getName() {
        return name;
    }
}