package game.player;

import game.components.PlayerBoard;
import game.components.Tile;
import game.components.TokenPoolInterface;
import game.ui.NamedElement;
import game.utils.Pair;

import java.util.List;

public abstract class Player implements NamedElement {
    /**
     * The board of the player
     */
    protected final PlayerBoard board;
    /**
     * The name of the player
     */
    private final String name;
    /**
     * The tiles the player is currently holding which need to be placed on the board
     */
    protected List<Tile> heldTiles;

    /**
     * The constructor
     * Initializes all game components
     *
     * @param name (String) The name of the player
     */
    public Player(String name) {
        this.name = name;
        this.board = new PlayerBoard();
        heldTiles = null;
    }

    /**
     * Getter for the name of the player
     *
     * @return The name of the player
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Add new tiles to the player to hold
     *
     * @param tiles (List<Tile>) The new tiles for the player to hold
     * @throws RuntimeException When the player already holds tiles
     */
    public void holdTiles(List<Tile> tiles) {
        if (heldTiles != null) {
            throw new RuntimeException("Player.holdTiles() called while Player was already holding tiles!");
        }
        heldTiles = tiles;
    }

    /**
     * Getter for the tiles the player is holding
     *
     * @return The tiles the player is holding
     * @throws RuntimeException When the player is not holding any tiles
     */
    public List<Tile> getHeldTiles() {
        if (heldTiles == null) {
            throw new RuntimeException("Player.getHeldTiles() called while Player was not holding any tiles!");
        }
        return heldTiles;
    }

    /**
     * Extract the tiles the player is holding
     *
     * @return The extracted tiles
     */
    public List<Tile> extractHeldTiles() {
        var remainingTiles = heldTiles;
        heldTiles = null;
        return remainingTiles;
    }

    /**
     * Is the player holding any tiles
     *
     * @return True if the player is holding any tiles, else false
     */
    public boolean isHoldingTiles() {
        return heldTiles != null;
    }

    /**
     * Getter for the board of the player
     *
     * @return The board
     */
    public PlayerBoard getBoard() {
        return board;
    }

    /**
     * Pick a token pool and a color from that pool
     *
     * @return The token pool and the color that is being picked from the field
     */
    public abstract Pair<TokenPoolInterface, Tile> pickTokenPoolAndColor();

    /**
     * Choose a pattern row to place the passed @p tile in
     *
     * @param tiles the color of the tile to pick a pattern row for
     * @return The row number to place the passed @p tile in. -1 indicates the tile should be placed on the floor
     */
    public abstract int pickPatternRowForTiles(List<Tile> tiles);
}
