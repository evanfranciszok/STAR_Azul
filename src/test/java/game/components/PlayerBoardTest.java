package game.components;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerBoardTest {
    @Test
    void ConstructorTest() {
        var playerboard = new PlayerBoard();
        assertNotNull(playerboard);
    }

    @Test
    void GetWallTest() {
        var playerboard = new PlayerBoard();
        assertNotNull(playerboard.getWall());
    }

    @Test
    void GetPatternRowsTest() {
        var playerboard = new PlayerBoard();
        assertNotNull(playerboard.getPatternRows());
    }

    @Test
    void GetFloorTest() {
        var playerboard = new PlayerBoard();
        assertNotNull(playerboard.getFloor());
    }
    @Test
    void SetScoreTest() {
        var playerBoard = new PlayerBoard();
        assertEquals(0,playerBoard.getScore());
        playerBoard.setScore(1);
        assertEquals(1,playerBoard.getScore());
    }
    @Test
    void PlaceTilesOnPatternLineFitting() {
        var playerBoard = new PlayerBoard();
        var tilesToBePlaced = Arrays.asList(Tile.ORANGE, Tile.ORANGE);
        var tilesForBox = playerBoard.placeTilesOnPatternRow(tilesToBePlaced, 1);

        assertEquals(playerBoard.getFloor().getMinusScore(), 0);
        assertEquals(tilesForBox.size(), 0);
    }

    @Test
    void PlaceTilesOnPatternLineNotFitting() {
        var playerBoard = new PlayerBoard();
        var tilesToBePlaced = Arrays.asList(Tile.ORANGE, Tile.ORANGE, Tile.ORANGE, Tile.ORANGE);
        var tilesForBox = playerBoard.placeTilesOnPatternRow(tilesToBePlaced, 1);

        assertEquals(playerBoard.getFloor().getMinusScore(), -2);
        assertEquals(tilesForBox.size(), 0);
    }

    @Test
    void PlaceTilesOnPatternLineNotEqualInputArrayTest() {
        var playerBoard = new PlayerBoard();
        var tilesToBePlaced = Arrays.asList(Tile.ORANGE, Tile.ORANGE, Tile.ORANGE, Tile.WHITE);

        assertThrows(IllegalArgumentException.class, () -> playerBoard.placeTilesOnPatternRow(tilesToBePlaced, 1));
    }

    @Test
    void PlaceTileOnPatternLineFullFloor() {
        var playerBoard = new PlayerBoard();
        var tilesToBePlaced = new ArrayList<>(Arrays.asList(Tile.ORANGE, Tile.ORANGE, Tile.ORANGE, Tile.ORANGE, Tile.ORANGE, Tile.ORANGE, Tile.ORANGE, Tile.ORANGE, Tile.ORANGE));
        var expectedTilesForBox = List.of(Tile.ORANGE);
        var tilesForBox = playerBoard.placeTilesOnPatternRow(tilesToBePlaced, 0);

        assertEquals(expectedTilesForBox.size(), tilesForBox.size());
        for (var i = 0; i < expectedTilesForBox.size(); ++i) {
            assertSame(expectedTilesForBox.get(i), tilesForBox.get(i));
        }
    }

    @Test
    void PerformWallTilingOneRowNoNegativePoints() {
        var playerBoard = new PlayerBoard();
        var tilesToBePlaced = new ArrayList<>(List.of(Tile.WHITE));
        playerBoard.placeTilesOnPatternRow(tilesToBePlaced, 0);
        var tilesForBox = playerBoard.performWallTiling();

        assertEquals(0, tilesForBox.size());
        assertEquals(1, playerBoard.getScore());
    }

    @Test
    void PerformWallTilingTwoRowsNoNegativePoints() {
        var playerBoard = new PlayerBoard();
        var tilesToBePlaced1 = new ArrayList<>(List.of(Tile.WHITE));
        playerBoard.placeTilesOnPatternRow(tilesToBePlaced1, 0);
        var tilesToBePlaced2 = new ArrayList<>(Arrays.asList(Tile.BLUE, Tile.BLUE));
        playerBoard.placeTilesOnPatternRow(tilesToBePlaced2, 1);

        var tilesForBox = playerBoard.performWallTiling();
        var expectedTilesForBox = List.of(Tile.BLUE);

        assertEquals(2, playerBoard.getScore());
        assertEquals(expectedTilesForBox.size(), tilesForBox.size());
        for (var i = 0; i < expectedTilesForBox.size(); ++i) {
            assertSame(expectedTilesForBox.get(i), tilesForBox.get(i));
        }
    }

    @Test
    void PerFormWallTilingOneRowNegativePoints() {
        var playerBoard = new PlayerBoard();
        var tilesToBePlaced = Arrays.asList(Tile.BLUE, Tile.BLUE);
        playerBoard.placeTilesOnPatternRow(tilesToBePlaced, 0);

        var tilesForBox = playerBoard.performWallTiling();
        var expectedTilesForBox = List.of(Tile.BLUE);

        assertEquals(0, playerBoard.getScore());
        assertEquals(expectedTilesForBox.size(), tilesForBox.size());
        for (var i = 0; i < expectedTilesForBox.size(); ++i) {
            assertSame(expectedTilesForBox.get(i), tilesForBox.get(i));
        }
    }

    @Test
    void PerformWallTilingOneRowMoreNegativePointsThanPositive() {
        var playerBoard = new PlayerBoard();
        var tilesToBePlaced = Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE);
        playerBoard.placeTilesOnPatternRow(tilesToBePlaced, 0);

        var tilesForBox = playerBoard.performWallTiling();
        var expectedTilesForBox = Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE);

        assertEquals(playerBoard.getScore(), 0);
        assertEquals(expectedTilesForBox.size(), tilesForBox.size());
        for (var i = 0; i < expectedTilesForBox.size(); ++i) {
            assertSame(expectedTilesForBox.get(i), tilesForBox.get(i));
        }
    }


    @Test
    void PlaceTilesOnPatternLineWhichIsAlreadyOnTheWall() {
        var playerBoard = new PlayerBoard();
        var tilesToBePlaced = new ArrayList<>(List.of(Tile.WHITE));
        playerBoard.placeTilesOnPatternRow(tilesToBePlaced, 0);
        playerBoard.performWallTiling();

        assertThrows(IllegalArgumentException.class, () -> playerBoard.placeTilesOnPatternRow(tilesToBePlaced, 0));
    }

    @Test
    void PlaceTilesOnTwoDifferentPatternLinesAndWallTiling() {
        var playerBoard = new PlayerBoard();
        var tilesToBePlaced = new ArrayList<>(List.of(Tile.WHITE));
        var tilesToBePlaced2 = new ArrayList<>(List.of(Tile.BLACK, Tile.BLACK));
        playerBoard.placeTilesOnPatternRow(tilesToBePlaced, 0);
        playerBoard.placeTilesOnPatternRow(tilesToBePlaced2, 1);

        playerBoard.performWallTiling();

        assertEquals(3, playerBoard.getScore());
    }
}
