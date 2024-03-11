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
    public TokenPool(String name) {
        tiles = new ArrayList<>();
        this.name = name;
    }

    /**
     * Get the tiles in the TokenPool, without removing them.
     *
     * @return the tiles in the TokenPool
     */
    public ArrayList<Tile> getContents() {
        return tiles;
    }

    /**
     * Add the given tiles to the pool
     *
     * @param tiles (ArrayList<Tile>) The list of tiles that should be added to the pool
     */
    public void addTiles(ArrayList<Tile> tiles) {
        this.tiles.addAll(tiles);
    }

    /**
     * Add the given tile to the pool
     *
     * @param tile (Tile) The tile that should be added to the pool
     */
    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    /**
     * Extract all Tiles of a given color from the pool
     * And return the extracted tiles
     * Note: This function also returns the STARTING_PLAYER_TILE if it is in this TokenPool
     *
     * @param color (Tile) The color of the tiles to be extracted
     * @return (List < Tile >) The extracted tiles
     */
    public List<Tile> extractTilesOfColor(Tile color) {
        if (color == Tile.STARTING_PLAYER_TILE) {
            throw new InvalidParameterException("Extracting only the STARTING_PLAYER_TILE is not supported");
        }
        var tilesToExtract = new ArrayList<Tile>();
        for (int i = tiles.size() - 1; i >= 0; --i) {
            if (tiles.get(i) == color) {
                tilesToExtract.add(tiles.get(i));
                tiles.remove(tiles.get(i));
            }
        }
        if (tilesToExtract.isEmpty()) {
            throw new InvalidParameterException("There are no tiles of the requested color available in this TokenPool");
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
    public List<Tile> extractRemainingTiles() {
        var remainingTiles = tiles;
        tiles = new ArrayList<>();
        return remainingTiles;
    }

    /**
     * @return Whether the TokenPool is empty or not
     */
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    /**
     * {@inheritDoc} <br/>
     * Getter for the name of the token pool
     *
     * @return The name of the token pool
     */
    @Override
    public String getName() {
        return name;
    }
}