package game.player;

import game.components.Tile;
import game.components.TokenPoolInterface;
import game.player.aistrategies.AIPlayerStrategy;
import game.utils.Pair;

import java.util.List;

public class AI extends Player {
    /**
     * The strategy of the AI
     */
    private final AIPlayerStrategy strategy;

    /**
     * The constructor
     *
     * @param strategy (AIPlayerStrategy) The injected strategy for the AI
     * @param name     (String) The name of the AI
     */
    public AI(AIPlayerStrategy strategy, String name) {
        super(name);
        this.strategy = strategy;
        strategy.setAIPlayer(this);
    }

    /**
     * {@inheritDoc} <br/>
     * Let the AI pick a token pool and a color within the pool
     */
    @Override
    public Pair<TokenPoolInterface, Tile> pickTokenPoolAndColor() {
        return strategy.pickTokenPoolAndColor();
    }

    /**
     * {@inheritDoc} <br/>
     * Let the AI pick a pattern row for the tiles it picked
     */
    @Override
    public int pickPatternRowForTiles(List<Tile> tiles) {
        return strategy.pickPatternRowForTiles(tiles);
    }
}
