package game.ui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class UIScreenTest {

    UIScreen screen;

    @BeforeEach
    public void initUIScreen() {
        screen = new UIScreen(null) {
            @Override
            public void updateScreen() {

            }
        };
    }

    @Test
    public void construction() {
        Assertions.assertNull(screen.ui);
    }

    @Test
    public void getConfiguration() {
        UnsupportedOperationException exception = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> screen.getConfiguration(),
                "UnsupportedOperationException was expected"
        );
        Assertions.assertEquals("Call not valid in current state", exception.getMessage());
    }

    @Test
    public void selectTokenPoolAndColor() {
        UnsupportedOperationException exc = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> screen.selectTokenPoolAndColor(),
                "UnsupportedOperationException was expected"
        );
        Assertions.assertEquals("Call not valid in current state", exc.getMessage());
    }

    @Test
    public void selectPatternRowForTile() {
        UnsupportedOperationException exc = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> screen.selectPatternRowForTiles(new ArrayList<>()),
                "UnsupportedOperationException was expected"
        );
        Assertions.assertEquals("Call not valid in current state", exc.getMessage());
    }

    @Test
    public void selectRestartOrQuit() {
        UnsupportedOperationException exc = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> screen.selectRestartOrQuit(),
                "UnsupportedOperationException was expected"
        );
        Assertions.assertEquals("Call not valid in current state", exc.getMessage());
    }
}
