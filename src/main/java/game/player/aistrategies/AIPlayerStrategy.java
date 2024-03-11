package game.player.aistrategies;

import game.components.Tile;
import game.components.TokenPoolInterface;
import game.player.AI;
import game.utils.Pair;

import java.util.List;

public interface AIPlayerStrategy {
    /**
     * Pick a token pool and the color of one of the tiles in the pool to extract from
     *
     * @return The selected token pool and color
     */
    Pair<TokenPoolInterface, Tile> pickTokenPoolAndColor();

    /**
     * Choose a pattern row to place the passed @p tile in
     *
     * @param tiles (List<Tile>) The color of the tile to pick a pattern row for
     * @return The row number to place the passed @p tile in. -1 indicates the tile should be placed on the floor
     */
    int pickPatternRowForTiles(List<Tile> tiles);

    /**
     * Setter for player
     *
     * @param player (AI) The player
     */
    void setAIPlayer(AI player);
}
