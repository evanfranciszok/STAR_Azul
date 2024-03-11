package game.ui;

import game.components.Tile;
import game.components.TokenPoolInterface;
import game.controller.Configuration;
import game.controller.Controller;
import game.utils.Pair;

import java.util.List;

public abstract class UI {
    /**
     * A reference to the screen
     */
    protected UIScreen screen;
    /**
     * A reference to the controller
     */
    protected Controller controller;

    /**
     * The constructor
     */
    public UI() {
        screen = null;
    }

    /**
     * Getter for the screen
     *
     * @return (UIScreen) the current screen
     */
    public UIScreen getScreen() {
        return screen;
    }

    /**
     * Setter for the screen
     *
     * @param newScreen (UIScreen) The new screen
     */
    protected void setScreen(UIScreen newScreen) {
        screen = newScreen;
        screen.updateScreen();
    }

    /**
     * Getter for the controller
     *
     * @return (Controller) the controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Setter for the controller
     *
     * @param controller (Controller) The new controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Clear the current screen
     */
    public abstract void wipeScreen();

    /**
     * Show the configuration screen
     */
    public abstract void showConfigScreen();

    /**
     * Show the tile picking screen
     */
    public abstract void showTilePickingScreen();

    /**
     * Show the tile pattern placing screen
     */
    public abstract void showTilePatternPlacingScreen();

    /**
     * Show the wall tiling screen
     */
    public abstract void showWallTilingScreen();

    /**
     * Show the game finished screen
     */
    public abstract void showGameFinishedScreen();

    /**
     * Get the configuration
     *
     * @return The game configuration
     */
    public Configuration getConfiguration() {
        return screen.getConfiguration();
    }

    /**
     * Select the token pool and color of the tile
     *
     * @return The selected token pool and color
     */
    public Pair<TokenPoolInterface, Tile> selectTokenPoolAndColor() {
        return screen.selectTokenPoolAndColor();
    }

    /**
     * Select a pattern row to place the held tiles of a player on
     *
     * @param tiles (List<Tile>) The tiles being placed on the pattern row
     * @return The selected pattern row
     */
    public int selectPatternRowForTile(List<Tile> tiles) {
        return screen.selectPatternRowForTiles(tiles);
    }

    /**
     * Update the screen
     */
    public void updateScreen() {
        screen.updateScreen();
    }
}
