package game.player;

import game.components.Tile;
import game.components.TokenPoolInterface;
import game.ui.UI;
import game.utils.Pair;

import java.util.List;

public class Human extends Player {
    /**
     * A reference to the user interface
     */
    private final UI ui;

    /**
     * The constructor
     *
     * @param ui   (UI) The injected UI
     * @param name (String) The name of the player
     */
    public Human(UI ui, String name) {
        super(name);
        this.ui = ui;
    }

    /**
     * {@inheritDoc} <br/>
     * Start the select token pool anc color process in the UI
     */
    @Override
    public Pair<TokenPoolInterface, Tile> pickTokenPoolAndColor() {
        return ui.selectTokenPoolAndColor();
    }

    /**
     * {@inheritDoc} <br/>
     * Start the select pattern row process in the UI
     */
    @Override
    public int pickPatternRowForTiles(List<Tile> tiles) {
        return ui.selectPatternRowForTile(tiles);
    }
}
