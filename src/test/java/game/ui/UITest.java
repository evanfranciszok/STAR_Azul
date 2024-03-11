package game.ui;

import game.components.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UITest {
    UI ui;
    UIScreen mockScreen;

    @BeforeEach
    public void initUI() {
        ui = new UI() {
            @Override
            public void wipeScreen() {

            }

            @Override
            public void showConfigScreen() {

            }

            @Override
            public void showTilePickingScreen() {

            }

            @Override
            public void showTilePatternPlacingScreen() {

            }

            @Override
            public void showWallTilingScreen() {

            }

            @Override
            public void showGameFinishedScreen() {

            }
        };
        mockScreen = mock(UIScreen.class, Answers.RETURNS_SMART_NULLS);
        ui.screen = mockScreen;
    }

    @Test
    public void construction() {
        Assertions.assertEquals(mockScreen, ui.screen);
    }


    @Test
    public void getConfiguration() {
        ui.getConfiguration();
        verify(mockScreen).getConfiguration();
    }

    @Test
    public void selectTokenPoolAndColor() {
        ui.selectTokenPoolAndColor();
        verify(mockScreen).selectTokenPoolAndColor();
    }

    @Test
    public void selectPatternRowForTile() {
        List<Tile> tiles = new ArrayList<>();
        ui.selectPatternRowForTile(tiles);
        verify(mockScreen).selectPatternRowForTiles(tiles);
    }

    @Test
    public void updateScreen() {
        ui.updateScreen();
        verify(mockScreen).updateScreen();
    }

    @Test
    public void setScreen() {
        ui.setScreen(mockScreen);
        verify(mockScreen).updateScreen();
        Assertions.assertEquals(mockScreen, ui.screen);
    }

    @Test
    public void setController() {
        ui.setController(null);
        Assertions.assertNull(ui.controller);
    }
}
