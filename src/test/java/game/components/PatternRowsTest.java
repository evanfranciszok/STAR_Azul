package game.components;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PatternRowsTest {
    @Test
    void ConstructorTest() {
        var patternRows = new PatternRows();
        assertNotNull(patternRows);
    }

    @Test
    void AddToRowTest() {
        var patternRows = new PatternRows();
        patternRows.addToRow(3, Tile.BLUE);
        patternRows.addToRow(4, Tile.BLUE);
    }

    @Test
    void AddToFullRowTest() {
        var patternRows = new PatternRows();
        patternRows.addToRow(2, Tile.BLUE);
        patternRows.addToRow(2, Tile.BLUE);
        patternRows.addToRow(2, Tile.BLUE);
        assertThrows(IllegalStateException.class, () -> patternRows.addToRow(2, Tile.BLUE));
    }

    @Test
    void AddWrongTileToRowTest() {
        var patternRows = new PatternRows();
        patternRows.addToRow(2, Tile.BLUE);
        assertThrows(IllegalArgumentException.class, () -> patternRows.addToRow(2, Tile.RED));
    }

    @Test
    void RowIsNotFullTest() {
        var patternRows = new PatternRows();
        assertFalse(patternRows.isRowFull(1));
        patternRows.addToRow(1, Tile.RED);
        assertFalse(patternRows.isRowFull(1));
    }

    @Test
    void RowIsFullTest() {
        var patternRows = new PatternRows();
        assertFalse(patternRows.isRowFull(0));
        patternRows.addToRow(0, Tile.WHITE);
        assertTrue(patternRows.isRowFull(0));
    }

    @Test
    void ExtractFullRowTest() {
        var patternRows = new PatternRows();
        patternRows.addToRow(4, Tile.ORANGE);
        patternRows.addToRow(4, Tile.ORANGE);
        patternRows.addToRow(4, Tile.ORANGE);
        patternRows.addToRow(4, Tile.ORANGE);
        patternRows.addToRow(4, Tile.ORANGE);

        var tilesFromPatternRow = patternRows.extractRow(4);
        var expectedOutput = Arrays.asList(Tile.ORANGE, Tile.ORANGE, Tile.ORANGE, Tile.ORANGE, Tile.ORANGE);
        assertEquals(tilesFromPatternRow.size(), expectedOutput.size());
        for (var i = 0; i < tilesFromPatternRow.size(); ++i) {
            assertSame(tilesFromPatternRow.get(i), expectedOutput.get(i));
        }
        assertFalse(patternRows.isRowFull(4));
    }

    @Test
    void ExtractEmptyRowTest() {
        var patternRows = new PatternRows();
        patternRows.addToRow(4, Tile.ORANGE);
        patternRows.addToRow(4, Tile.ORANGE);

        assertThrows(IllegalStateException.class, () -> patternRows.extractRow(4));
    }
}
