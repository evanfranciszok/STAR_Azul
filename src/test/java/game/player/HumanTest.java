package game.player;

import game.components.Tile;
import game.ui.UI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HumanTest {
    static final String name = "TestHuman";
    Human human;
    UI ui;

    @BeforeEach
    public void before() {
        ui = mock(UI.class);
        human = new Human(ui, name);
    }

    @Test
    public void pickTokenPoolAndColor() {
        human.pickTokenPoolAndColor();
        verify(ui).selectTokenPoolAndColor();
    }

    @Test
    public void pickPatternRowForTile() {
        List<Tile> tiles = new ArrayList<>();
        human.pickPatternRowForTiles(tiles);
        verify(ui).selectPatternRowForTile(tiles);
    }

    @Test
    public void getName() {
        Assertions.assertEquals(name, human.getName());
    }
}
