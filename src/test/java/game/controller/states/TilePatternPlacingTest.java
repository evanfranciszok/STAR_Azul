package game.controller.states;

import game.components.*;
import game.controller.Controller;
import game.player.Player;
import game.ui.UI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TilePatternPlacingTest {

    TilePatternPlacing tilePatternPlacingState;
    Controller controllerMock;
    UI uiMock;
    Player currentPlayerMock;

    PlayerBoard playerBoardMock;
    Floor floorMock;
    Field fieldMock;
    TokenPool centerMock;

    List<Tile> discardPileMock;

    @BeforeEach
    void before() {
        uiMock = mock(UI.class);

        floorMock = mock(Floor.class);

        playerBoardMock = mock(PlayerBoard.class);
        when(playerBoardMock.getFloor()).thenReturn(floorMock);


        currentPlayerMock = mock(Player.class);
        when(currentPlayerMock.getBoard()).thenReturn(playerBoardMock);

        centerMock = mock(TokenPool.class);

        fieldMock = mock(Field.class);
        when(fieldMock.getCenter()).thenReturn(centerMock);

        discardPileMock = mock(List.class);

        controllerMock = mock(Controller.class);
        when(controllerMock.getUI()).thenReturn(uiMock);
        when(controllerMock.getCurrentPlayer()).thenReturn(currentPlayerMock);
        when(controllerMock.getField()).thenReturn(fieldMock);
        when(controllerMock.getDiscardPile()).thenReturn(discardPileMock);

        tilePatternPlacingState = new TilePatternPlacing(controllerMock);
    }

    @Test
    void entry() {
        tilePatternPlacingState.entry();
        verify(uiMock).showTilePatternPlacingScreen();
    }

    @Test
    void actRow() {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.RED);
        tiles.add(Tile.RED);
        tiles.add(Tile.RED);
        when(currentPlayerMock.pickPatternRowForTiles(tiles)).thenReturn(2);
        when(currentPlayerMock.extractHeldTiles()).thenReturn(tiles);
        when(currentPlayerMock.getHeldTiles()).thenReturn(tiles);

        tilePatternPlacingState.act();

        verify(playerBoardMock).placeTilesOnPatternRow(tiles, 2);
    }

    @Test
    void actFloor() {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.RED);
        tiles.add(Tile.RED);
        tiles.add(Tile.RED);
        when(currentPlayerMock.pickPatternRowForTiles(tiles)).thenReturn(-1);
        when(currentPlayerMock.extractHeldTiles()).thenReturn(tiles);
        when(currentPlayerMock.getHeldTiles()).thenReturn(tiles);

        tilePatternPlacingState.act();

        verify(floorMock, times(3)).placeTile(Tile.RED);
    }

    @Test
    void actOverflowFloor() {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            tiles.add(Tile.RED);
        }
        tiles.add(Tile.BLUE);
        tiles.add(Tile.ORANGE);
        tiles.add(Tile.ORANGE);

        when(currentPlayerMock.pickPatternRowForTiles(tiles)).thenReturn(-1);
        when(currentPlayerMock.extractHeldTiles()).thenReturn(tiles);
        when(currentPlayerMock.getHeldTiles()).thenReturn(tiles);
        when(floorMock.isFull()).thenReturn(false, false, false, false, false, false, false, true);

        tilePatternPlacingState.act();

        verify(floorMock, times(6)).placeTile(Tile.RED);
        verify(floorMock, times(1)).placeTile(Tile.BLUE);
        verify(discardPileMock, times(2)).add(Tile.ORANGE);
    }

    @Test
    void actAllPoolsEmpty() {
        when(centerMock.isEmpty()).thenReturn(true);
        when(fieldMock.getNonEmptyFactories()).thenReturn(new ArrayList<>());
        tilePatternPlacingState.act();
        ArgumentCaptor<ControllerState> stateArgumentCaptor = ArgumentCaptor.forClass(ControllerState.class);
        verify(controllerMock).goToState(stateArgumentCaptor.capture());
        Assertions.assertEquals(WallTiling.class, stateArgumentCaptor.getValue().getClass());
    }

    @Test
    void actAtLeastOneNonEmptyPool() {
        when(centerMock.isEmpty()).thenReturn(false);
        List<TokenPoolInterface> pools = new ArrayList<>();
        pools.add(new TokenPool("TestPool"));
        when(fieldMock.getNonEmptyFactories()).thenReturn(pools);
        tilePatternPlacingState.act();
        ArgumentCaptor<ControllerState> stateArgumentCaptor = ArgumentCaptor.forClass(ControllerState.class);
        verify(controllerMock).goToState(stateArgumentCaptor.capture());
        Assertions.assertEquals(TilePicking.class, stateArgumentCaptor.getValue().getClass());
    }

    @Test
    void exit() {
        tilePatternPlacingState.exit();
        verify(controllerMock).incrementCurrentPlayer();
    }
}
