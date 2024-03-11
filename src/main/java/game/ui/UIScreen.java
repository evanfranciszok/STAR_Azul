package game.ui;

import game.components.Tile;
import game.components.TokenPoolInterface;
import game.controller.Configuration;
import game.utils.Pair;

import java.util.List;

public abstract class UIScreen {

    /**
     * The UI belonging to the screen
     */
    protected final UI ui;

    /**
     * The constructor
     *
     * @param ui (UI) The injected UI
     */
    public UIScreen(UI ui) {
        this.ui = ui;
    }

    /**
     * Update the current screen
     */
    public abstract void updateScreen();

    /**
     * @return The desired configuration
     * @throws UnsupportedOperationException When not implemented
     */
    public Configuration getConfiguration() {
        throw new UnsupportedOperationException("Call not valid in current state");
    }

    /**
     * @return Which color Tiles should be grabbed from which TokenPool
     * @throws UnsupportedOperationException When not implemented
     */
    public Pair<TokenPoolInterface, Tile> selectTokenPoolAndColor() {
        throw new UnsupportedOperationException("Call not valid in current state");
    }

    /**
     * @param tiles (List<Tile>) List of tiles to place on the to-be-selected PatternRow
     * @throws UnsupportedOperationException When not implemented
     */
    public int selectPatternRowForTiles(List<Tile> tiles) {
        throw new UnsupportedOperationException("Call not valid in current state");
    }

    /**
     * @return true if the game should be restarted, false if the application should exit
     * @throws UnsupportedOperationException When not implemented
     */
    public boolean selectRestartOrQuit() {
        throw new UnsupportedOperationException("Call not valid in current state");
    }
}
