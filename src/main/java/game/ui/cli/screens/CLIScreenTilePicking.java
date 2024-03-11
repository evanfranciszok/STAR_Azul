package game.ui.cli.screens;

import game.components.Tile;
import game.components.TokenPoolInterface;
import game.ui.cli.CLI;
import game.utils.Pair;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLIScreenTilePicking extends CLIScreenPlaying {
    /**
     * The scanner to read the command line inputs
     */
    Scanner scanner;

    /**
     * The constructor
     *
     * @param ui (CLI The injected CLI
     */
    public CLIScreenTilePicking(CLI ui) {
        super(ui);
    }

    /**
     * Parse the tile color from an input string
     *
     * @param input (String)
     * @return The input as a tile
     */
    private static Tile parseTileColorFromString(String input) {
        return Tile.valueOf(input.toUpperCase());
    }

    /**
     * Select a token pool and color
     *
     * @return The selected token pool and color
     */
    public Pair<TokenPoolInterface, Tile> selectTokenPoolAndColor() {
        if (ui.getController().getField().getCenter().isEmpty() && ui.getController().getField().getNonEmptyFactories().size() == 0) {
            throw new RuntimeException("selectTokenPoolAndColor() was called, but all factories and the center are empty");
        }
        scanner = new Scanner(System.in);
        TokenPoolInterface factoryIndex = pickTokenPool();
        Tile tileColor = pickColor(factoryIndex);
        return new Pair<>(factoryIndex, tileColor);
    }

    /**
     * Pick a token pool
     *
     * @return The selected token pool
     */
    private TokenPoolInterface pickTokenPool() {
        TokenPoolInterface pool = null;
        do {
            updateScreen();
            System.out.println("Please enter the number of the factory, or the c(enter), from which you want to select a color.");
            String input = scanner.nextLine();
            try {
                pool = ui.getController().getField().getFactories().get(Integer.parseInt(input) - 1);
            } catch (Exception ignored) {
                Pattern pattern = Pattern.compile("^c(?:enter)?$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(input);
                if (matcher.find()) {
                    pool = ui.getController().getField().getCenter();
                }
            }
        } while (pool == null || pool.isEmpty());
        return pool;
    }

    /**
     * Pick a color
     *
     * @param tokenPool (TokenPool) The colors in the token pool
     * @return The picked token color
     */
    private Tile pickColor(TokenPoolInterface tokenPool) {
        Tile tileColor = null;
        do {
            updateScreen();
            System.out.printf("You have selected TokenPool %s.\n", tokenPool.getName());
            System.out.println("This factory contains the following tiles: ");
            printTokenPool(tokenPool);
            System.out.println("Please pick one of the available colors.");
            String input = scanner.nextLine();
            try {
                tileColor = parseTileColorFromString(input);
            } catch (Exception ignored) {
            }
        } while (tileColor == null || !tokenPool.getContents().contains(tileColor));
        return tileColor;
    }

}
