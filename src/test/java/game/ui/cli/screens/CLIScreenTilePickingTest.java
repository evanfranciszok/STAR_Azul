package game.ui.cli.screens;

import game.components.Tile;
import game.components.TokenPoolInterface;
import game.controller.Controller;
import game.ui.cli.CLI;
import game.utils.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Random;

public class CLIScreenTilePickingTest {

    CLIScreenTilePicking cliScreenTilePicking;
    CLI ui;
    Controller controller;

    @BeforeEach
    public void initCLIScreenTilePicking() {
        ui = new CLI();
        controller = new Controller(ui);
        controller.initGame();
        cliScreenTilePicking = new CLIScreenTilePicking(ui);
    }

    @Test
    public void selectTokenPoolAndColorCorrect() {
        for (int factoryIndex = 0; factoryIndex < controller.getField().getFactories().size(); ++factoryIndex) {
            TokenPoolInterface factory = controller.getField().getFactories().get(factoryIndex);
            Tile tileColor = factory.getContents().get(new Random().nextInt(factory.getContents().size()));
            String input = (factoryIndex + 1) + "\n";
            input += tileColor.name() + "\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            Pair<TokenPoolInterface, Tile> result = cliScreenTilePicking.selectTokenPoolAndColor();
            Assertions.assertEquals(factory, result.first);
            Assertions.assertEquals(tileColor, result.second);
        }
    }

    @Test
    public void selectTokenPoolAndColorCenterSingleChar() {
        TokenPoolInterface center = controller.getField().getCenter();
        center.getContents().add(Tile.BLUE);
        center.getContents().add(Tile.RED);
        center.getContents().add(Tile.ORANGE);
        center.getContents().add(Tile.WHITE);
        center.getContents().add(Tile.BLACK);
        Tile tileColor = center.getContents().get(new Random().nextInt(center.getContents().size()));
        String input = "c\n";
        input += tileColor.name() + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Pair<TokenPoolInterface, Tile> result = cliScreenTilePicking.selectTokenPoolAndColor();
        Assertions.assertEquals(center, result.first);
        Assertions.assertEquals(tileColor, result.second);
    }

    @Test
    public void selectTokenPoolAndColorCenterFullWord() {
        TokenPoolInterface center = controller.getField().getCenter();
        center.getContents().add(Tile.BLUE);
        center.getContents().add(Tile.RED);
        center.getContents().add(Tile.ORANGE);
        center.getContents().add(Tile.WHITE);
        center.getContents().add(Tile.BLACK);
        Tile tileColor = center.getContents().get(new Random().nextInt(center.getContents().size()));
        String input = "center\n";
        input += tileColor.name() + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Pair<TokenPoolInterface, Tile> result = cliScreenTilePicking.selectTokenPoolAndColor();
        Assertions.assertEquals(center, result.first);
        Assertions.assertEquals(tileColor, result.second);
    }

    @Test
    public void selectTokenPoolAndColorIncorrect() {
        String input = "-1\n";
        input += "0\n";
        input += (controller.getField().getFactories().size() + 1) + "\n";
        input += "500\n";
        input += "ABCD\n";

        // Valid factory to continue to Color picking
        int validFactoryIndex = 0;
        TokenPoolInterface validFactory = controller.getField().getFactories().get(validFactoryIndex);
        Tile validTileColor = validFactory.getContents().get(new Random().nextInt(validFactory.getContents().size()));
        input += (validFactoryIndex + 1) + "\n";

        input += "2\n"; // Input factory index while picking Color
        input += "ABCDEFG\n";
        input += "!@#$%^&*()\n";
        input += "\n";
        input += validTileColor.name() + "\n";

        System.out.println(input);
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // All invalid inputs should have been ignored, and only the valid inputs accepted.
        Pair<TokenPoolInterface, Tile> result = cliScreenTilePicking.selectTokenPoolAndColor();
        Assertions.assertEquals(validFactory, result.first);
        Assertions.assertEquals(validTileColor, result.second);
    }

    @Test
    void selectTokenPoolAndColorAllFactoriesEmpty() {
        controller.getField().getCenter().extractRemainingTiles();
        for (TokenPoolInterface factory : controller.getField().getFactories()) {
            factory.extractRemainingTiles();
        }
        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> cliScreenTilePicking.selectTokenPoolAndColor(),
                "RuntimeException was expected"
        );
        Assertions.assertEquals("selectTokenPoolAndColor() was called, but all factories and the center are empty", exception.getMessage());
    }
}
