package game.player.aistrategies;

import game.components.PlayerBoard;
import game.components.Tile;
import game.components.TokenPoolInterface;
import game.controller.Controller;
import game.player.AI;
import game.ui.cli.CLI;
import game.utils.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RandomAIPlayerStrategyTest {
    Controller controller;
    RandomAIPlayerStrategy strategy;
    AI playerMock;
    PlayerBoard playerBoardMock;

    @BeforeEach
    void before() {
        controller = new Controller(new CLI());
        controller.initGame();
        playerMock = mock(AI.class);
        playerBoardMock = mock(PlayerBoard.class);
        when(playerMock.getBoard()).thenReturn(playerBoardMock);
        strategy = new RandomAIPlayerStrategy(controller);
        strategy.setAIPlayer(playerMock);
    }

    @Test
    void pickTokenPoolAndColor() {
        Pair<TokenPoolInterface, Tile> result = strategy.pickTokenPoolAndColor();
        Assertions.assertNotNull(result.first);
        // Make sure tile color is actually in this TokenPool
        Assertions.assertTrue(result.first.getContents().contains(result.second));
    }

    @Test
    void pickTokenPoolAndColorAllFactoriesAndCenterEmpty() {
        controller.getField().getCenter().extractRemainingTiles();
        for (TokenPoolInterface factory : controller.getField().getFactories()) {
            factory.extractRemainingTiles();
        }
        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> strategy.pickTokenPoolAndColor(),
                "RuntimeException was expected"
        );
        Assertions.assertEquals("pickTokenPoolAndColor() was called, but all factories and the center are empty", exception.getMessage());
    }

    @Test
    void pickTokenPoolAndColorOnlySecondFactoryNotEmpty() {
        controller.getField().getCenter().extractRemainingTiles();
        for (int i = 0; i < controller.getField().getFactories().size(); i++) {
            if (i == 1) {
                continue;
            }
            controller.getField().getFactories().get(i).extractRemainingTiles();
        }
        // If only second factory contains tiles, it must be the one that is selected
        Assertions.assertSame(controller.getField().getFactories().get(1), strategy.pickTokenPoolAndColor().first);
    }

    @Test
    void pickPatternRowForTileAllAvailable() {
        when(playerBoardMock.isColorAvailableInRow(Tile.ORANGE, 0)).thenReturn(true);
        when(playerBoardMock.isColorAvailableInRow(Tile.ORANGE, 1)).thenReturn(true);
        when(playerBoardMock.isColorAvailableInRow(Tile.ORANGE, 2)).thenReturn(true);
        when(playerBoardMock.isColorAvailableInRow(Tile.ORANGE, 3)).thenReturn(true);
        when(playerBoardMock.isColorAvailableInRow(Tile.ORANGE, 4)).thenReturn(true);
        List<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.ORANGE);
        int row = strategy.pickPatternRowForTiles(tiles);
        // Chosen row must be the floor or row 1
        Assertions.assertTrue(row >= -1 && row <= 4);
    }

    @Test
    void pickPatternRowForTileOneAvailable() {
        when(playerBoardMock.isColorAvailableInRow(Tile.RED, 0)).thenReturn(false);
        when(playerBoardMock.isColorAvailableInRow(Tile.RED, 1)).thenReturn(true);
        when(playerBoardMock.isColorAvailableInRow(Tile.RED, 2)).thenReturn(false);
        when(playerBoardMock.isColorAvailableInRow(Tile.RED, 3)).thenReturn(false);
        when(playerBoardMock.isColorAvailableInRow(Tile.RED, 4)).thenReturn(false);
        List<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.RED);
        int row = strategy.pickPatternRowForTiles(tiles);
        // Chosen row must be the floor or row 1
        Assertions.assertTrue(row == -1 || row == 1);
    }

    @Test
    void pickPatternRowForTileNoSpaceInRows() {
        when(playerBoardMock.isColorAvailableInRow(Tile.BLUE, 0)).thenReturn(false);
        when(playerBoardMock.isColorAvailableInRow(Tile.BLUE, 1)).thenReturn(false);
        when(playerBoardMock.isColorAvailableInRow(Tile.BLUE, 2)).thenReturn(false);
        when(playerBoardMock.isColorAvailableInRow(Tile.BLUE, 3)).thenReturn(false);
        when(playerBoardMock.isColorAvailableInRow(Tile.BLUE, 4)).thenReturn(false);
        List<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.BLUE);
        // Chosen row must be the floor
        Assertions.assertEquals(-1, strategy.pickPatternRowForTiles(tiles));
    }
}
