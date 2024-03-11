package game.controller.states;

import game.components.Tile;
import game.controller.Controller;

import java.util.ArrayList;

public class TilePicking extends ControllerState {
    /**
     * The constructor, calls the super constructor
     *
     * @param controller (Controller) The injected controller
     */
    public TilePicking(Controller controller) {
        super(controller);
    }

    /**
     * {@inheritDoc} <br/>
     * Shows the tile picking screen on the UI
     */
    @Override
    public void entry() {
        controller.getUI().showTilePickingScreen();
    }

    /**
     * {@inheritDoc} <br/>
     * Place the tile the player selected from the factory to its and the remaining tiles to the center
     */
    @Override
    public void act() {
        var selection = controller.getCurrentPlayer().pickTokenPoolAndColor();
        System.out.printf("%s selected: %s, color %s\n", controller.getCurrentPlayer().getName(), selection.first.getName(), selection.second.name());
        controller.getCurrentPlayer().holdTiles(selection.first.extractTilesOfColor(selection.second));
        controller.getField().getCenter().addTiles((ArrayList<Tile>) selection.first.extractRemainingTiles());

        // Make sure the performed action is shown for at leas 1 second
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        controller.goToState(new TilePatternPlacing(controller));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {

    }
}
