package game.player;

import game.components.Tile;
import game.components.TokenPoolInterface;
import game.utils.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {

    static final String name = "TestHuman";
    Player player;

    @BeforeEach
    public void before() {
        player = new Player(name) {
            @Override
            public Pair<TokenPoolInterface, Tile> pickTokenPoolAndColor() {
                return null;
            }

            @Override
            public int pickPatternRowForTiles(List<Tile> tiles) {
                return 0;
            }
        };
    }

    @Test
    public void getName() {
        Assertions.assertEquals(name, player.getName());
    }

    @Test
    public void holdTilesAndGetHeldTiles() {
        List<Tile> tiles = Arrays.stream((new Tile[]{Tile.RED, Tile.BLUE, Tile.ORANGE, Tile.BLUE, Tile.WHITE})).toList();
        player.holdTiles(tiles);
        List<Tile> heldTiles = player.getHeldTiles();
        Assertions.assertEquals(tiles.size(), heldTiles.size());
        for (int i = 0; i < tiles.size(); i++) {
            Assertions.assertEquals(tiles.get(i), heldTiles.get(i));
        }
    }

    @Test
    public void holdTilesWhileAlreadyHoldingTiles() {
        List<Tile> tiles = Arrays.stream((new Tile[]{Tile.RED, Tile.BLUE, Tile.ORANGE, Tile.BLUE, Tile.WHITE})).toList();
        List<Tile> tiles2 = Arrays.stream((new Tile[]{Tile.RED, Tile.BLUE, Tile.ORANGE, Tile.BLUE, Tile.WHITE})).toList();
        player.holdTiles(tiles);
        assertThrows(RuntimeException.class, () -> player.holdTiles(tiles2));
    }

    @Test
    public void getHeldTilesWhileNotHoldingTiles() {
        assertThrows(RuntimeException.class, () -> player.getHeldTiles());
    }
}
