package game.ui.cli.screens;

import game.components.PlayerBoard;
import game.components.Tile;
import game.player.Player;
import game.ui.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLIScreenTilePatternPlacing extends CLIScreenPlaying {
    /**
     * The constructor
     *
     * @param ui (CLI The injected CLI
     */
    public CLIScreenTilePatternPlacing(UI ui) {
        super(ui);
    }

    /**
     * Print a held tiles of a player
     *
     * @param player (Player) The player the held tiles are printed from
     */
    protected void printHeldTiles(Player player) {
        System.out.print("Remaining held tiles:");
        if (!player.isHoldingTiles()) {
            System.out.print("NONE");
        } else {
            for (Tile tile : player.getHeldTiles()) {
                System.out.print(" " + tile.toString());
            }
        }
        System.out.println();
    }

    /**
     * {@inheritDoc} <br/>
     * Update the screen by printing the held tiles of the current player
     */
    @Override
    public void updateScreen() {
        super.updateScreen();
        printHeldTiles(ui.getController().getCurrentPlayer());
    }

    /**
     * Select the pattern rows for the tiles to be placed in
     *
     * @param tiles (List<Tile>) The tile color
     * @return The pattern row to place the tiles in
     */
    @Override
    public int selectPatternRowForTiles(List<Tile> tiles) {
        Tile tileColor = tiles.get(0);
        Scanner scanner = new Scanner(System.in);
        PlayerBoard board = ui.getController().getCurrentPlayer().getBoard();
        List<Integer> availableRows = new ArrayList<>();
        availableRows.add(-1);
        for (int i = 0; i < PlayerBoard.N_ROWS; i++) {
            if (board.isColorAvailableInRow(tileColor, i)) {
                availableRows.add(i);
            }
        }
        Integer patternRow = null;
        do {
            updateScreen();
            System.out.println("Please pick the pattern row, or the f(loor), on which you want to place the held tiles");
            String input = scanner.nextLine();
            try {
                patternRow = Integer.parseInt(input) - 1;
                if (patternRow == -1) {
                    patternRow = null;
                }
            } catch (Exception ignored) {
                Pattern pattern = Pattern.compile("^f(?:loor)?$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(input);
                if (matcher.find()) {
                    patternRow = -1;
                }
            }
        } while (patternRow == null || !availableRows.contains(patternRow));
        return patternRow;
    }

}
