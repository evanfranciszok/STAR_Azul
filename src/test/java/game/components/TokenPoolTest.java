package game.components;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TokenPoolTest {
    TokenPool pool;

    @BeforeEach
    void before() {
        pool = new TokenPool("TestPool");
    }

    @Test
    void addTilesAndGetContents() {
        var inputTiles = new ArrayList<Tile>();
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.BLACK);
        inputTiles.add(Tile.RED);
        inputTiles.add(Tile.ORANGE);
        pool.addTiles(inputTiles);
        Assertions.assertEquals(inputTiles.size(), pool.getContents().size());
        Assertions.assertTrue(inputTiles.containsAll(pool.getContents()));
        Assertions.assertTrue(pool.getContents().containsAll(inputTiles));
    }

    @Test
    void addTileAndGetContents() {
        var inputTiles = new ArrayList<Tile>();
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.BLACK);
        inputTiles.add(Tile.RED);
        inputTiles.add(Tile.ORANGE);
        for (Tile tile : inputTiles) {
            pool.addTile(tile);
        }
        Assertions.assertEquals(inputTiles.size(), pool.getContents().size());
        Assertions.assertTrue(inputTiles.containsAll(pool.getContents()));
        Assertions.assertTrue(pool.getContents().containsAll(inputTiles));
    }

    @Test
    void testExtractTilesOfColor() {
        var inputTiles = new ArrayList<Tile>();
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.BLACK);
        inputTiles.add(Tile.RED);
        inputTiles.add(Tile.ORANGE);
        pool.addTiles(inputTiles);

        var expectedOutput = Arrays.asList(Tile.BLUE, Tile.BLUE);

        var extractedTiles = pool.extractTilesOfColor(Tile.BLUE);

        assertEquals(extractedTiles.size(), expectedOutput.size());
        Assertions.assertTrue(extractedTiles.containsAll(expectedOutput));
        Assertions.assertTrue(expectedOutput.containsAll(extractedTiles));
    }

    @Test
    void testExtractTilesOfColorNotPresent() {
        var inputTiles = new ArrayList<Tile>();
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.BLACK);
        inputTiles.add(Tile.RED);
        inputTiles.add(Tile.ORANGE);
        pool.addTiles(inputTiles);
        assertThrows(IllegalArgumentException.class, () -> pool.extractTilesOfColor(Tile.WHITE));
    }

    @Test
    void testExtractTilesOfColorSpecificallySTARTING_PLAYER_TILE() {
        var inputTiles = new ArrayList<Tile>();
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.STARTING_PLAYER_TILE);
        inputTiles.add(Tile.BLACK);
        inputTiles.add(Tile.RED);
        inputTiles.add(Tile.ORANGE);
        pool.addTiles(inputTiles);
        assertThrows(IllegalArgumentException.class, () -> pool.extractTilesOfColor(Tile.STARTING_PLAYER_TILE));
    }

    @Test
    void testExtractTilesOfColorIncludingSTARTING_PLAYER_TILE() {
        var inputTiles = new ArrayList<Tile>();
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.STARTING_PLAYER_TILE);
        inputTiles.add(Tile.BLACK);
        inputTiles.add(Tile.RED);
        inputTiles.add(Tile.ORANGE);
        pool.addTiles(inputTiles);


        var extractedTiles = pool.extractTilesOfColor(Tile.BLUE);

        assertEquals(3, extractedTiles.size());
        assertEquals(2, extractedTiles.stream().filter(tile -> tile == Tile.BLUE).count());
        assertEquals(1, extractedTiles.stream().filter(tile -> tile == Tile.STARTING_PLAYER_TILE).count());
    }

    @Test
    void testExtractRemainingTiles() {
        var inputTiles = new ArrayList<Tile>();
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.BLUE);
        inputTiles.add(Tile.BLACK);
        inputTiles.add(Tile.RED);
        inputTiles.add(Tile.ORANGE);
        pool.addTiles(inputTiles);

        var expectedOutput = Arrays.asList(Tile.BLACK, Tile.RED, Tile.ORANGE);

        pool.extractTilesOfColor(Tile.BLUE);

        var extractedTiles = pool.extractRemainingTiles();

        assertEquals(extractedTiles.size(), expectedOutput.size());
        Assertions.assertTrue(extractedTiles.containsAll(expectedOutput));
        Assertions.assertTrue(expectedOutput.containsAll(extractedTiles));
    }

    @Test
    void isEmpty() {
        pool.addTile(Tile.RED);
        Assertions.assertFalse(pool.isEmpty());
        pool.extractRemainingTiles();
        Assertions.assertTrue(pool.isEmpty());
    }

    @Test
    void getName() {
        Assertions.assertEquals("TestPool", pool.getName());
    }
}