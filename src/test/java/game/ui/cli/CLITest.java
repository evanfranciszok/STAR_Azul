package game.ui.cli;

import game.components.*;
import game.controller.Configuration;
import game.controller.Controller;
import game.player.Player;
import game.ui.UI;
import game.ui.cli.screens.CLIScreenConfiguring;
import game.ui.cli.screens.CLIScreenTilePicking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CLITest {
    CLI cli;
    Controller controllerMock;
    UI uiMock;
    Player currentPlayerMock;
    Field fieldMock;
    TokenPool centerMock;
    PlayerBoard playerBoardMock;
    PatternRows patternRowsMock;
    Boolean[] wallRowMock;
    Wall wallMock;
    Floor floorMock;

    @BeforeEach
    public void initCLITest() {
        uiMock = mock(UI.class);

        patternRowsMock = mock(PatternRows.class);

        wallRowMock = new Boolean[5];
        Arrays.fill(wallRowMock, false);

        wallMock = mock(Wall.class);
        when(wallMock.getPlacedTilesOnRow(anyInt())).thenReturn(wallRowMock);
        floorMock = mock(Floor.class);

        playerBoardMock = mock(PlayerBoard.class);
        when(playerBoardMock.getPatternRows()).thenReturn(patternRowsMock);
        when(playerBoardMock.getWall()).thenReturn(wallMock);
        when(playerBoardMock.getFloor()).thenReturn(floorMock);

        currentPlayerMock = mock(Player.class);
        when(currentPlayerMock.getBoard()).thenReturn(playerBoardMock);

        centerMock = mock(TokenPool.class);

        fieldMock = mock(Field.class);
        when(fieldMock.getCenter()).thenReturn(centerMock);

        controllerMock = mock(Controller.class);
        when(controllerMock.getUI()).thenReturn(uiMock);
        when(controllerMock.getCurrentPlayer()).thenReturn(currentPlayerMock);
        when(controllerMock.getField()).thenReturn(fieldMock);

        cli = new CLI();
        cli.setController(controllerMock);
    }

    @Test
    public void wipeScreen() {
        // Should not throw an exception
        cli.wipeScreen();
    }

    @Test
    public void showConfigScreen() {
        when(controllerMock.getConfig()).thenReturn(new Configuration(1, 3));
        cli.showConfigScreen();
        Assertions.assertEquals(CLIScreenConfiguring.class, cli.getScreen().getClass());
    }

    @Test
    public void showTilePickingScreen() {
        cli.showTilePickingScreen();
        Assertions.assertEquals(CLIScreenTilePicking.class, cli.getScreen().getClass());
    }
}
