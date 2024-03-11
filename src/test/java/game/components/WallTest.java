package game.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WallTest {
    @Test
    void CreateWall() {
        new Wall();
    }

    @Test
    void TileEmptyRow() {
        var wall = new Wall();
        wall.tileRow(Tile.BLUE, 1);
        assertTrue(wall.getTilePlacedElement(1, 1));
    }

    @Test
    void TileFullRow() {
        var wall = new Wall();
        wall.tileRow(Tile.BLUE, 1);
        assertThrows(IllegalArgumentException.class, () -> wall.tileRow(Tile.BLUE, 1));
    }

    @Test
    void TileRowInvalidRowParameter() {
        var wall = new Wall();
        assertThrows(IllegalArgumentException.class, () -> wall.tileRow(Tile.RED, 6));
        assertThrows(IllegalArgumentException.class, () -> wall.tileRow(Tile.ORANGE, -1));
    }

    @Test
    void GetTilePlacedOnWall() {
        var wall = new Wall();
        var isTilePlaced = wall.getTilePlacedElement(4, 1);
        assertFalse(isTilePlaced);
        wall.tileRow(Tile.RED, 4);
        isTilePlaced = wall.getTilePlacedElement(4, 1);
        assertTrue(isTilePlaced);
    }

    @Test
    void CalculateScore1() {
        var wall = new Wall();
        assertEquals(1, wall.tileRow(Tile.BLUE, 1));
        assertEquals(1, wall.tileRow(Tile.RED, 1));
        assertEquals(2, wall.tileRow(Tile.WHITE, 2));
    }

    @Test
    void CalculateScore2() {
        var wall = new Wall();
        assertEquals(1, wall.tileRow(Tile.BLUE, 0));
        assertEquals(2, wall.tileRow(Tile.ORANGE, 0));
        assertEquals(3, wall.tileRow(Tile.RED, 0));
        assertEquals(4, wall.tileRow(Tile.BLACK, 0));
        assertEquals(5, wall.tileRow(Tile.WHITE, 0));
    }

    @Test
    void CalculateScore3() {
        var wall = new Wall();
        assertEquals(1, wall.tileRow(Tile.BLUE, 0));
        assertEquals(2, wall.tileRow(Tile.WHITE, 1));
        assertEquals(3, wall.tileRow(Tile.BLACK, 2));
        assertEquals(4, wall.tileRow(Tile.RED, 3));
        assertEquals(5, wall.tileRow(Tile.ORANGE, 4));
    }

    @Test
    void CalculateScore4() {
        var wall = new Wall();
        assertEquals(1, wall.tileRow(Tile.BLUE, 0));
        assertEquals(2, wall.tileRow(Tile.ORANGE, 0));
        assertEquals(2, wall.tileRow(Tile.WHITE, 1));
        assertEquals(3, wall.tileRow(Tile.RED, 0));
        assertEquals(3, wall.tileRow(Tile.BLACK, 2));
        assertEquals(4, wall.tileRow(Tile.BLACK, 0));
        assertEquals(4, wall.tileRow(Tile.RED, 3));
        assertEquals(5, wall.tileRow(Tile.WHITE, 0));
        assertEquals(5, wall.tileRow(Tile.ORANGE, 4));
    }

    @Test
    void CalculateScore5() {
        var wall = new Wall();
        assertEquals(1, wall.tileRow(Tile.BLUE, 0));
        assertEquals(1, wall.tileRow(Tile.BLACK, 2));
        assertEquals(1, wall.tileRow(Tile.BLUE, 2));
        assertEquals(2, wall.tileRow(Tile.ORANGE, 1));
        assertEquals(1, wall.tileRow(Tile.BLACK, 1));
        assertEquals(3, wall.tileRow(Tile.RED, 1));
        assertEquals(2, wall.tileRow(Tile.WHITE, 0));
        assertEquals(1, wall.tileRow(Tile.RED, 4));
        assertEquals(3, wall.tileRow(Tile.WHITE, 3));
        assertEquals(4, wall.tileRow(Tile.BLACK, 3));
    }

    @Test
    void getNumberOfFilledRows0() {
        var wall = new Wall();
        assertEquals(0, wall.getNumberOfFilledRows());
    }

    @Test
    void getNumberOfFilledRows1() {
        var wall = new Wall();
        wall.tileRow(Tile.BLUE, 0);
        wall.tileRow(Tile.ORANGE, 0);
        wall.tileRow(Tile.RED, 0);
        wall.tileRow(Tile.BLACK, 0);
        wall.tileRow(Tile.WHITE, 0);

        assertEquals(1, wall.getNumberOfFilledRows());
    }

    @Test
    void getNumberOfFilledRows3() {
        var wall = new Wall();
        wall.tileRow(Tile.BLUE, 0);
        wall.tileRow(Tile.ORANGE, 0);
        wall.tileRow(Tile.RED, 0);
        wall.tileRow(Tile.BLACK, 0);
        wall.tileRow(Tile.WHITE, 0);

        wall.tileRow(Tile.BLUE, 2);
        wall.tileRow(Tile.ORANGE, 2);
        wall.tileRow(Tile.RED, 2);
        wall.tileRow(Tile.BLACK, 2);
        wall.tileRow(Tile.WHITE, 2);

        wall.tileRow(Tile.BLUE, 4);
        wall.tileRow(Tile.ORANGE, 4);
        wall.tileRow(Tile.RED, 4);
        wall.tileRow(Tile.BLACK, 4);
        wall.tileRow(Tile.WHITE, 4);

        assertEquals(3, wall.getNumberOfFilledRows());
    }

    @Test
    void getNumberOfFilledRows5() {
        var wall = new Wall();
        wall.tileRow(Tile.BLUE, 0);
        wall.tileRow(Tile.ORANGE, 0);
        wall.tileRow(Tile.RED, 0);
        wall.tileRow(Tile.BLACK, 0);
        wall.tileRow(Tile.WHITE, 0);

        wall.tileRow(Tile.BLUE, 1);
        wall.tileRow(Tile.ORANGE, 1);
        wall.tileRow(Tile.RED, 1);
        wall.tileRow(Tile.BLACK, 1);
        wall.tileRow(Tile.WHITE, 1);

        wall.tileRow(Tile.BLUE, 2);
        wall.tileRow(Tile.ORANGE, 2);
        wall.tileRow(Tile.RED, 2);
        wall.tileRow(Tile.BLACK, 2);
        wall.tileRow(Tile.WHITE, 2);

        wall.tileRow(Tile.BLUE, 3);
        wall.tileRow(Tile.ORANGE, 3);
        wall.tileRow(Tile.RED, 3);
        wall.tileRow(Tile.BLACK, 3);
        wall.tileRow(Tile.WHITE, 3);

        wall.tileRow(Tile.BLUE, 4);
        wall.tileRow(Tile.ORANGE, 4);
        wall.tileRow(Tile.RED, 4);
        wall.tileRow(Tile.BLACK, 4);
        wall.tileRow(Tile.WHITE, 4);

        assertEquals(5, wall.getNumberOfFilledRows());
    }

    @Test
    void getNumberOfFilledColumns0() {
        var wall = new Wall();
        assertEquals(0, wall.getNumberOfFilledColumns());
    }

    @Test
    void getNumberOfFilledColumns1() {
        var wall = new Wall();

        wall.tileRow(Tile.BLUE, 0);
        wall.tileRow(Tile.WHITE, 1);
        wall.tileRow(Tile.BLACK, 2);
        wall.tileRow(Tile.RED, 3);
        wall.tileRow(Tile.ORANGE, 4);

        assertEquals(1, wall.getNumberOfFilledColumns());
    }

    @Test
    void getNumberOfFilledColumns3() {
        var wall = new Wall();

        wall.tileRow(Tile.BLUE, 0);
        wall.tileRow(Tile.WHITE, 1);
        wall.tileRow(Tile.BLACK, 2);
        wall.tileRow(Tile.RED, 3);
        wall.tileRow(Tile.ORANGE, 4);

        wall.tileRow(Tile.BLUE, 3);
        wall.tileRow(Tile.WHITE, 4);
        wall.tileRow(Tile.BLACK, 0);
        wall.tileRow(Tile.RED, 1);
        wall.tileRow(Tile.ORANGE, 2);

        wall.tileRow(Tile.BLUE, 1);
        wall.tileRow(Tile.WHITE, 2);
        wall.tileRow(Tile.BLACK, 3);
        wall.tileRow(Tile.RED, 4);
        wall.tileRow(Tile.ORANGE, 0);

        assertEquals(3, wall.getNumberOfFilledColumns());
    }

    @Test
    void getNumberOfFilledColumns5() {
        var wall = new Wall();

        wall.tileRow(Tile.BLUE, 0);
        wall.tileRow(Tile.WHITE, 1);
        wall.tileRow(Tile.BLACK, 2);
        wall.tileRow(Tile.RED, 3);
        wall.tileRow(Tile.ORANGE, 4);

        wall.tileRow(Tile.BLUE, 4);
        wall.tileRow(Tile.WHITE, 0);
        wall.tileRow(Tile.BLACK, 1);
        wall.tileRow(Tile.RED, 2);
        wall.tileRow(Tile.ORANGE, 3);

        wall.tileRow(Tile.BLUE, 3);
        wall.tileRow(Tile.WHITE, 4);
        wall.tileRow(Tile.BLACK, 0);
        wall.tileRow(Tile.RED, 1);
        wall.tileRow(Tile.ORANGE, 2);

        wall.tileRow(Tile.BLUE, 2);
        wall.tileRow(Tile.WHITE, 3);
        wall.tileRow(Tile.BLACK, 4);
        wall.tileRow(Tile.RED, 0);
        wall.tileRow(Tile.ORANGE, 1);

        wall.tileRow(Tile.BLUE, 1);
        wall.tileRow(Tile.WHITE, 2);
        wall.tileRow(Tile.BLACK, 3);
        wall.tileRow(Tile.RED, 4);
        wall.tileRow(Tile.ORANGE, 0);

        assertEquals(5, wall.getNumberOfFilledColumns());
    }

    @Test
    void getNumberOfCompletedColors0() {
        var wall = new Wall();
        assertEquals(0, wall.getNumberOfCompletedColors());
    }

    @Test
    void getNumberOfCompletedColors1() {
        var wall = new Wall();

        wall.tileRow(Tile.BLUE, 0);
        wall.tileRow(Tile.BLUE, 1);
        wall.tileRow(Tile.BLUE, 2);
        wall.tileRow(Tile.BLUE, 3);
        wall.tileRow(Tile.BLUE, 4);

        assertEquals(1, wall.getNumberOfCompletedColors());
    }

    @Test
    void getNumberOfCompletedColors3() {
        var wall = new Wall();

        wall.tileRow(Tile.BLUE, 0);
        wall.tileRow(Tile.BLUE, 1);
        wall.tileRow(Tile.BLUE, 2);
        wall.tileRow(Tile.BLUE, 3);
        wall.tileRow(Tile.BLUE, 4);

        wall.tileRow(Tile.RED, 0);
        wall.tileRow(Tile.RED, 1);
        wall.tileRow(Tile.RED, 2);
        wall.tileRow(Tile.RED, 3);
        wall.tileRow(Tile.RED, 4);

        wall.tileRow(Tile.WHITE, 0);
        wall.tileRow(Tile.WHITE, 1);
        wall.tileRow(Tile.WHITE, 2);
        wall.tileRow(Tile.WHITE, 3);
        wall.tileRow(Tile.WHITE, 4);

        assertEquals(3, wall.getNumberOfCompletedColors());
    }

    @Test
    void getNumberOfCompletedColors4() {
        var wall = new Wall();

        wall.tileRow(Tile.BLUE, 0);
        wall.tileRow(Tile.BLUE, 1);
        wall.tileRow(Tile.BLUE, 2);
        wall.tileRow(Tile.BLUE, 3);
        wall.tileRow(Tile.BLUE, 4);

        wall.tileRow(Tile.ORANGE, 0);
        wall.tileRow(Tile.ORANGE, 1);
        wall.tileRow(Tile.ORANGE, 2);
        wall.tileRow(Tile.ORANGE, 3);
        wall.tileRow(Tile.ORANGE, 4);

        wall.tileRow(Tile.RED, 0);
        wall.tileRow(Tile.RED, 1);
        wall.tileRow(Tile.RED, 2);
        wall.tileRow(Tile.RED, 3);
        wall.tileRow(Tile.RED, 4);

        wall.tileRow(Tile.BLACK, 0);
        wall.tileRow(Tile.BLACK, 1);
        wall.tileRow(Tile.BLACK, 2);
        wall.tileRow(Tile.BLACK, 3);
        wall.tileRow(Tile.BLACK, 4);

        wall.tileRow(Tile.WHITE, 0);
        wall.tileRow(Tile.WHITE, 1);
        wall.tileRow(Tile.WHITE, 2);
        wall.tileRow(Tile.WHITE, 3);
        wall.tileRow(Tile.WHITE, 4);

        assertEquals(5, wall.getNumberOfCompletedColors());
    }
}
