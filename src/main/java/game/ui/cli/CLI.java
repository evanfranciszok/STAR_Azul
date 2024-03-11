package game.ui.cli;

import game.ui.UI;
import game.ui.cli.screens.*;


public class CLI extends UI {
    /**
     * {@inheritDoc} <br/>
     * Clear the command line, Windows only
     */
    @Override
    public void wipeScreen() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (final Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    /**
     * {@inheritDoc} <br/>
     * Sets the command line configuration screen
     */
    @Override
    public void showConfigScreen() {
        setScreen(new CLIScreenConfiguring(this));
    }

    /**
     * {@inheritDoc} <br/>
     * Sets the command line tile picking screen
     */
    @Override
    public void showTilePickingScreen() {
        setScreen(new CLIScreenTilePicking(this));
    }

    /**
     * {@inheritDoc} <br/>
     * Sets the command line tile pattern row placing screen
     */
    @Override
    public void showTilePatternPlacingScreen() {
        setScreen(new CLIScreenTilePatternPlacing(this));
    }

    /**
     * {@inheritDoc} <br/>
     * Sets the command line to the wall tiling screen
     */
    @Override
    public void showWallTilingScreen() {
        setScreen(new CLIScreenWallTiling(this));
    }

    /**
     * {@inheritDoc} <br/>
     * Sets the command line to the Game Finished screen
     */
    @Override
    public void showGameFinishedScreen() {
        setScreen(new CLIScreenGameFinished(this));
    }
}
