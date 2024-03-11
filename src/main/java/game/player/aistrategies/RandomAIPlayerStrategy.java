package game.player.aistrategies;

import game.components.PlayerBoard;
import game.components.Tile;
import game.components.TokenPoolInterface;
import game.controller.Controller;
import game.player.AI;
import game.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAIPlayerStrategy implements AIPlayerStrategy {
    /**
     * The game controller
     */
    private final Controller controller;
    /**
     * The player the strategy belongs to
     */
    private AI player;

    /**
     * The Constructor
     *
     * @param controller (Controller) The injected controller
     */
    public RandomAIPlayerStrategy(Controller controller) {
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAIPlayer(AI player) {
        this.player = player;
    }

    /**
     * {@inheritDoc} <br/>
     * Select a random token pool and color from that pool
     *
     * @throws RuntimeException When all factories and the center are empty
     */
    @Override
    public Pair<TokenPoolInterface, Tile> pickTokenPoolAndColor() {
        List<TokenPoolInterface> availablePools = controller.getField().getNonEmptyFactories();

        if (!controller.getField().getCenter().isEmpty()) {
            availablePools.add(controller.getField().getCenter());
        }

        if (availablePools.size() == 0) {
            throw new RuntimeException("pickTokenPoolAndColor() was called, but all factories and the center are empty");
        }

        TokenPoolInterface pool;
        Tile tileColor;
        do {
            pool = availablePools.get(new Random().nextInt(availablePools.size()));
            List<Tile> availableColors = pool.getContents().stream().distinct().toList();
            tileColor = availableColors.get(new Random().nextInt(availableColors.size()));
        } while (tileColor == Tile.STARTING_PLAYER_TILE);
        return new Pair<>(pool, tileColor);
    }

    /**
     * {@inheritDoc}
     * Pick a random row the tiles could be placed in
     */
    @Override
    public int pickPatternRowForTiles(List<Tile> tiles) {
        PlayerBoard board = player.getBoard();
        List<Integer> availableRows = new ArrayList<>();
        availableRows.add(-1);
        for (int i = 0; i < PlayerBoard.N_ROWS; i++) {
            if (board.isColorAvailableInRow(tiles.get(0), i)) {
                availableRows.add(i);
            }
        }
        return availableRows.get(new Random().nextInt(availableRows.size()));
    }
}
