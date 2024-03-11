package game.ui.cli.screens;

import game.components.PlayerBoard;
import game.components.Tile;
import game.controller.Controller;
import game.ui.cli.CLI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class CLIScreenTilePatternPlacingTest {

    CLIScreenTilePatternPlacing cliScreenTilePatternPlacing;
    CLI ui;
    Controller controller;

    @BeforeEach
    public void initCLIScreenTilePicking() {
        ui = new CLI();
        controller = new Controller(ui);
        controller.initGame();
        cliScreenTilePatternPlacing = new CLIScreenTilePatternPlacing(ui);
    }

    @Test
    public void selectPatternRowForTiles() {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.RED);
        for (int rowIndex = 0; rowIndex < PlayerBoard.N_ROWS; ++rowIndex) {
            String input = (rowIndex + 1) + "\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            Assertions.assertEquals(rowIndex, cliScreenTilePatternPlacing.selectPatternRowForTiles(tiles));
        }
    }

    @Test
    public void selectPatternRowForTileFloorSingleChar() {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.RED);
        String input = "f\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Assertions.assertEquals(-1, cliScreenTilePatternPlacing.selectPatternRowForTiles(tiles));
    }

    @Test
    public void selectPatternRowForTileFloorFullWord() {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.RED);
        String input = "floor\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Assertions.assertEquals(-1, cliScreenTilePatternPlacing.selectPatternRowForTiles(tiles));
    }
}
