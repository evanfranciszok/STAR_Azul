package game.controller.states;

import game.components.Floor;
import game.components.PlayerBoard;
import game.components.Tile;
import game.components.Wall;
import game.controller.Controller;
import game.player.Player;
import game.ui.UI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class WallTilingTest {
    WallTiling wallTilingState;
    Controller controllerMock;
    UI uiMock;

    Player player1Mock;
    PlayerBoard board1Mock;
    Wall wall1Mock;
    Floor floor1Mock;

    Player player2Mock;
    PlayerBoard board2Mock;
    Wall wall2Mock;
    Floor floor2Mock;

    List<Tile> discardPileMock;

    @BeforeEach
    void before() {
        uiMock = mock(UI.class);

        floor1Mock = mock(Floor.class);
        wall1Mock = mock(Wall.class);
        board1Mock = mock(PlayerBoard.class);
        when(board1Mock.getFloor()).thenReturn(floor1Mock);
        when(board1Mock.getWall()).thenReturn(wall1Mock);
        player1Mock = mock(Player.class);
        when(player1Mock.getBoard()).thenReturn(board1Mock);

        floor2Mock = mock(Floor.class);
        wall2Mock = mock(Wall.class);
        board2Mock = mock(PlayerBoard.class);
        when(board2Mock.getFloor()).thenReturn(floor2Mock);
        when(board2Mock.getWall()).thenReturn(wall2Mock);
        player2Mock = mock(Player.class);
        when(player2Mock.getBoard()).thenReturn(board2Mock);

        discardPileMock = mock(List.class);

        controllerMock = mock(Controller.class);
        when(controllerMock.getUI()).thenReturn(uiMock);
        List<Player> players = new ArrayList<>();
        players.add(player1Mock);
        players.add(player2Mock);
        when(controllerMock.getPlayers()).thenReturn(players);
        when(controllerMock.getDiscardPile()).thenReturn(discardPileMock);

        wallTilingState = new WallTiling(controllerMock);
    }

    @Test
    void entry() {
        wallTilingState.entry();
        verify(uiMock).showWallTilingScreen();
    }

    @Test
    void performWallTiling() {
        wallTilingState.act();
        verify(board1Mock).performWallTiling();
        verify(board2Mock).performWallTiling();
    }

    @Test
    void storeDiscardedTiles() {
        List<Tile> discardedTiles = new ArrayList<>(Arrays.stream(new Tile[]{Tile.RED, Tile.WHITE, Tile.BLUE, Tile.BLACK, Tile.ORANGE}).toList());
        when(board1Mock.performWallTiling()).thenReturn(discardedTiles);

        wallTilingState.act();

        verify(discardPileMock).addAll(discardedTiles);
    }

    @Test
    void gameFinishedState() {
        when(wall1Mock.getNumberOfFilledRows()).thenReturn(1);

        wallTilingState.act();

        ArgumentCaptor<ControllerState> stateArgumentCaptor = ArgumentCaptor.forClass(ControllerState.class);
        verify(controllerMock).goToState(stateArgumentCaptor.capture());
        Assertions.assertEquals(GameFinished.class, stateArgumentCaptor.getValue().getClass());
    }

    @Test
    void gameNotFinishedState() {
        when(wall1Mock.getNumberOfFilledRows()).thenReturn(0);
        when(wall2Mock.getNumberOfFilledRows()).thenReturn(0);

        wallTilingState.act();

        ArgumentCaptor<ControllerState> stateArgumentCaptor = ArgumentCaptor.forClass(ControllerState.class);
        verify(controllerMock).goToState(stateArgumentCaptor.capture());
        Assertions.assertEquals(TilePicking.class, stateArgumentCaptor.getValue().getClass());
    }

    @Test
    void gameNotFinishedStartingPlayer1() {
        when(wall1Mock.getNumberOfFilledRows()).thenReturn(0);
        when(wall2Mock.getNumberOfFilledRows()).thenReturn(0);
        when(floor1Mock.hasStartingPlayerTile()).thenReturn(true);
        ArgumentCaptor<Player> playerArgumentCaptor = ArgumentCaptor.forClass(Player.class);

        wallTilingState.act();

        verify(controllerMock, atLeastOnce()).setCurrentPlayer(playerArgumentCaptor.capture());
        List<Player> playerArguments = playerArgumentCaptor.getAllValues();
        Assertions.assertSame(player1Mock, playerArguments.get(playerArguments.size() - 1));
    }

    @Test
    void gameNotFinishedStartingPlayer2() {
        when(wall1Mock.getNumberOfFilledRows()).thenReturn(0);
        when(wall2Mock.getNumberOfFilledRows()).thenReturn(0);
        when(floor2Mock.hasStartingPlayerTile()).thenReturn(true);
        ArgumentCaptor<Player> playerArgumentCaptor = ArgumentCaptor.forClass(Player.class);

        wallTilingState.act();

        verify(controllerMock, atLeastOnce()).setCurrentPlayer(playerArgumentCaptor.capture());
        List<Player> playerArguments = playerArgumentCaptor.getAllValues();
        Assertions.assertSame(player2Mock, playerArguments.get(playerArguments.size() - 1));
    }

    @Test
    void gameNotFinishedRefillFactories() {
        when(wall1Mock.getNumberOfFilledRows()).thenReturn(0);
        when(wall2Mock.getNumberOfFilledRows()).thenReturn(0);

        wallTilingState.act();

        verify(controllerMock).refillFactories();
    }


    @Test
    void exit() {
        // Verify exit does not throw an exception
        wallTilingState.exit();
    }
}
