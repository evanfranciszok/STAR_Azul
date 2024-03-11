package game.ui.cli.screens;

import game.ui.UI;

public class CLIScreenWallTiling extends CLIScreenPlaying {
    /**
     * The constructor
     *
     * @param ui (CLI The injected CLI
     */
    public CLIScreenWallTiling(UI ui) {
        super(ui);
    }

    /**
     * {@inheritDoc} <br/>
     */
    @Override
    public void updateScreen() {
        super.updateScreen();
        System.out.println("Automatically tiled wall");
    }

}
