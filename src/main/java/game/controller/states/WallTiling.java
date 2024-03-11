package game.controller.states;

import game.components.Tile;
import game.controller.Controller;
import game.player.Player;

import java.util.List;

public class WallTiling extends ControllerState {
    /**
     * The constructor, calls the super constructor
     *
     * @param controller (Controller) The injected controller
     */
    public WallTiling(Controller controller) {
        super(controller);
    }

    /**
     * {@inheritDoc} <br/>
     * Show the wall tiling screen in the UI
     */
    @Override
    public void entry() {
        controller.getUI().showWallTilingScreen();
    }

    /**
     * {@inheritDoc} <br/>
     * Tile the wall for every player and sleep for 1 second so it can be seen <br/>
     * Afterwards determine the starting player based on the starting player tile and refill the factories,
     * Go to the Tile picking state
     */
    @Override
    public void act() {
        boolean gameFinished = false;
        Player startingPlayer = null;
        for (Player player : controller.getPlayers()) {
            controller.setCurrentPlayer(player);
            if (player.getBoard().getFloor().hasStartingPlayerTile()) {
                startingPlayer = player;
            }
            List<Tile> leftoverTiles = player.getBoard().performWallTiling();
            controller.getDiscardPile().addAll(leftoverTiles);
            if (player.getBoard().getWall().getNumberOfFilledRows() > 0) {
                gameFinished = true;
            }
            controller.getUI().updateScreen();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if (gameFinished) {
            controller.goToState(new GameFinished(controller));
        } else {
            controller.setCurrentPlayer(startingPlayer);
            controller.refillFactories();
            controller.goToState(new TilePicking(controller));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
    }
}
