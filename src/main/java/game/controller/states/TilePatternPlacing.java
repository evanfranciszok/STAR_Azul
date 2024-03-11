package game.controller.states;

import game.components.Tile;
import game.controller.Controller;
import game.player.Player;

public class TilePatternPlacing extends ControllerState {
    /**
     * The constructor, calls the super constructor
     *
     * @param controller (Controller) The injected controller
     */
    public TilePatternPlacing(Controller controller) {
        super(controller);
    }

    /**
     * {@inheritDoc} <br/>
     * Shows the tile pattern placing screen on the UI
     */
    @Override
    public void entry() {
        controller.getUI().showTilePatternPlacingScreen();
    }

    /**
     * {@inheritDoc} <br/>
     * Let the current player take tiles from the factory,
     * if the field is empty to to WallTiling else go to TilePicking
     */
    @Override
    public void act() {
        final int FLOOR_INDEX = -1;

        Player player = controller.getCurrentPlayer();

        int row = player.pickPatternRowForTiles(player.getHeldTiles());

        if (row == FLOOR_INDEX) {
            for (Tile tile : player.extractHeldTiles()) {
                if (!player.getBoard().getFloor().isFull()) {
                    player.getBoard().getFloor().placeTile(tile);
                } else {
                    controller.getDiscardPile().add(tile);
                }
            }
        } else {
            player.getBoard().placeTilesOnPatternRow(player.extractHeldTiles(), row);
        }
        if (controller.getField().getNonEmptyFactories().isEmpty() && controller.getField().getCenter().isEmpty()) {
            controller.goToState(new WallTiling(controller));
        } else {
            controller.goToState(new TilePicking(controller));
        }
    }

    /**
     * {@inheritDoc} <br/>
     * The next players turn starts
     */
    @Override
    public void exit() {
        controller.incrementCurrentPlayer();
    }
}
