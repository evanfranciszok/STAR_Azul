package game.components;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class FloorTest {
    @Test
    void ConstructorTest() {
        var floor = new Floor();
        assertNotNull(floor);
    }

    @Test
    void PlaceTileTestNotFull() {
        var floor = new Floor();
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.WHITE);
    }

    @Test
    void PlaceTileTestNotFullSTARTING_PLAYER_TILE() {
        var floor = new Floor();
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.WHITE);
        assertFalse(floor.hasStartingPlayerTile());
        floor.placeTile(Tile.STARTING_PLAYER_TILE);
        assertTrue(floor.hasStartingPlayerTile());
    }

    @Test
    void PlaceTileTestFull() {
        var floor = new Floor();
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.BLACK);
        floor.placeTile(Tile.BLACK);
        floor.placeTile(Tile.ORANGE);
        floor.placeTile(Tile.ORANGE);
        assertThrows(IllegalStateException.class, () -> floor.placeTile(Tile.BLUE));
    }

    @Test
    void PlaceTileTestFullSTARTING_PLAYER_TILE() {
        var floor = new Floor();
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.BLACK);
        floor.placeTile(Tile.BLACK);
        floor.placeTile(Tile.ORANGE);
        floor.placeTile(Tile.ORANGE);
        assertFalse(floor.hasStartingPlayerTile());
        floor.placeTile(Tile.STARTING_PLAYER_TILE); // Should not throw even though Floor is full
        assertTrue(floor.hasStartingPlayerTile());
    }

    @Test
    void IsNotFull() {
        var floor = new Floor();
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.WHITE);

        assertFalse(floor.isFull());
    }

    @Test
    void IsFull() {
        var floor = new Floor();
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.BLACK);
        floor.placeTile(Tile.BLACK);
        floor.placeTile(Tile.ORANGE);
        floor.placeTile(Tile.ORANGE);

        assertTrue(floor.isFull());
    }

    @Test
    void ResetEmptyFloor() {
        var floor = new Floor();

        var tilesFromFloor = floor.reset();
        assertEquals(tilesFromFloor.size(), 0);
    }

    @Test
    void ResetNotFullFloor() {
        var floor = new Floor();
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.WHITE);

        var tilesFromFloor = floor.reset();
        var expectedOutput = Arrays.asList(Tile.RED, Tile.RED, Tile.RED, Tile.WHITE);
        assertEquals(tilesFromFloor.size(), expectedOutput.size());
        for (int i = 0; i < tilesFromFloor.size(); ++i) {
            assertSame(tilesFromFloor.get(i), expectedOutput.get(i));
        }
    }

    @Test
    void ResetFullFloor() {
        var floor = new Floor();
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.BLACK);
        floor.placeTile(Tile.BLACK);
        floor.placeTile(Tile.ORANGE);
        floor.placeTile(Tile.ORANGE);

        var tilesFromFloor = floor.reset();
        var expectedOutput = Arrays.asList(Tile.RED, Tile.RED, Tile.RED, Tile.BLACK, Tile.BLACK, Tile.ORANGE, Tile.ORANGE);
        assertEquals(tilesFromFloor.size(), expectedOutput.size());
        for (int i = 0; i < tilesFromFloor.size(); ++i) {
            assertSame(tilesFromFloor.get(i), expectedOutput.get(i));
        }
    }

    @Test
    void ResetFullFloorWithSTARTING_PLAYER_TILE() {
        var floor = new Floor();
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.STARTING_PLAYER_TILE);
        floor.placeTile(Tile.BLACK);
        floor.placeTile(Tile.ORANGE);
        floor.placeTile(Tile.ORANGE);

        var tilesFromFloor = floor.reset();
        var expectedOutput = Arrays.asList(Tile.RED, Tile.RED, Tile.RED, Tile.BLACK, Tile.ORANGE, Tile.ORANGE);
        assertEquals(tilesFromFloor.size(), expectedOutput.size());
        for (int i = 0; i < tilesFromFloor.size(); ++i) {
            assertSame(tilesFromFloor.get(i), expectedOutput.get(i));
        }
    }

    @Test
    void HasStartingPlayerTileTest() {
        var floor = new Floor();
        floor.placeTile(Tile.BLACK);
        floor.placeTile(Tile.ORANGE);
        floor.placeTile(Tile.WHITE);
        floor.placeTile(Tile.STARTING_PLAYER_TILE);

        assertTrue(floor.hasStartingPlayerTile());
    }

    @Test
    void DoesNotHaveStartingPlayerTile() {
        var floor = new Floor();
        floor.placeTile(Tile.BLACK);
        floor.placeTile(Tile.ORANGE);
        floor.placeTile(Tile.WHITE);

        assertFalse(floor.hasStartingPlayerTile());
    }

    @Test
    void CalculateMinusPoints1() {
        var floor = new Floor();
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.ORANGE);
        floor.placeTile(Tile.ORANGE);

        assertEquals(floor.getMinusScore(), -6);
    }

    @Test
    void CalculateMinusPoints2() {
        var floor = new Floor();
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.RED);
        floor.placeTile(Tile.ORANGE);
        floor.placeTile(Tile.ORANGE);
        floor.placeTile(Tile.WHITE);
        floor.placeTile(Tile.WHITE);
        floor.placeTile(Tile.STARTING_PLAYER_TILE);

        assertEquals(floor.getMinusScore(), -14);
    }

    @Test
    void CalculateMinusPoints3() {
        var floor = new Floor();

        assertEquals(floor.getMinusScore(), 0);
    }
}
