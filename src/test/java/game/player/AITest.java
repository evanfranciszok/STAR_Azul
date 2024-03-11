package game.player;

import game.components.Tile;
import game.player.aistrategies.AIPlayerStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AITest {
    static final String name = "TestAI";
    AI ai;
    AIPlayerStrategy strategy;

    @BeforeEach
    public void before() {
        strategy = mock(AIPlayerStrategy.class);
        ai = new AI(strategy, name);
    }

    @Test
    public void pickTokenPoolAndColor() {
        ai.pickTokenPoolAndColor();
        verify(strategy).pickTokenPoolAndColor();
    }

    @Test
    public void pickPatternRowForTile() {
        List<Tile> tiles = new ArrayList<>();
        ai.pickPatternRowForTiles(tiles);
        verify(strategy).pickPatternRowForTiles(tiles);
    }

    @Test
    public void getName() {
        Assertions.assertEquals(name, ai.getName());
    }
}
