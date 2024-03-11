package game.components;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BagTest {
    Bag bag;

    @BeforeEach
    void setUp() {
        bag = new Bag();
    }

    @Test
    void extractOneTile() {
        int i = 0;
        while (!bag.isEmpty()) {
            Tile tile = bag.extractOneTile();
            Assertions.assertNotNull(tile);
            i++;
        }
        assertEquals(100, i);
        // TODO: TDD style, how do we test randomness?
    }

    @Test
    void extractOneTileWhileEmpty() {
        while (!bag.isEmpty()) {
            bag.extractOneTile();
        }
        Assertions.assertThrows(RuntimeException.class, () -> bag.extractOneTile());
    }

    @Test
    void putTile() {
        bag.putTile(Tile.BLUE);
        assertEquals(21, bag.getContents().stream().filter(tile -> tile == Tile.BLUE).count());
    }
}