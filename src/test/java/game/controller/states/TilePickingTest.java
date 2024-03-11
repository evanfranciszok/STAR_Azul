package game.controller.states;

import game.components.Field;
import game.components.Tile;
import game.components.TokenPool;
import game.controller.Controller;
import game.player.Player;
import game.ui.UI;
import game.utils.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class TilePickingTest {
    TilePicking tilePickingState;
    Controller controllerMock;
    UI uiMock;
    Player currentPlayerMock;
    Field fieldMock;
    TokenPool centerMock;
    TokenPool tokenPoolMock;

    @BeforeEach
    void before() {
        uiMock = mock(UI.class);

        centerMock = mock(TokenPool.class);

        tokenPoolMock = mock(TokenPool.class);
        when(tokenPoolMock.extractTilesOfColor(Tile.BLUE)).thenReturn(new ArrayList<>(Arrays.asList(Tile.BLUE, Tile.BLUE)));
        when(tokenPoolMock.extractRemainingTiles()).thenReturn(new ArrayList<>(Arrays.asList(Tile.RED, Tile.WHITE)));

        fieldMock = mock(Field.class);
        when(fieldMock.getCenter()).thenReturn(centerMock);

        currentPlayerMock = mock(Player.class);
        when(currentPlayerMock.pickTokenPoolAndColor()).thenReturn(new Pair<>(tokenPoolMock, Tile.BLUE));

        controllerMock = mock(Controller.class);
        when(controllerMock.getUI()).thenReturn(uiMock);
        when(controllerMock.getField()).thenReturn(fieldMock);
        when(controllerMock.getCurrentPlayer()).thenReturn(currentPlayerMock);

        tilePickingState = new TilePicking(controllerMock);
    }

    @Test
    void entry() {
        tilePickingState.entry();
        verify(uiMock).showTilePickingScreen();
    }

    @Test
    void act() {
        tilePickingState.act();
        verify(currentPlayerMock).pickTokenPoolAndColor();
        verify(tokenPoolMock).extractTilesOfColor(Tile.BLUE);
        ArgumentCaptor<ArrayList<Tile>> holdTilesArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        verify(currentPlayerMock).holdTiles(holdTilesArgumentCaptor.capture());
        ArrayList<Tile> tilesPassedToHoldTiles = holdTilesArgumentCaptor.getValue();
        Assertions.assertEquals(2, tilesPassedToHoldTiles.size());
        Assertions.assertEquals(Tile.BLUE, tilesPassedToHoldTiles.get(0));
        Assertions.assertEquals(Tile.BLUE, tilesPassedToHoldTiles.get(1));
        verify(tokenPoolMock).extractRemainingTiles();
        ArgumentCaptor<ArrayList<Tile>> addTilesArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        verify(centerMock).addTiles(addTilesArgumentCaptor.capture());
        ArrayList<Tile> tilesAddedToCenter = addTilesArgumentCaptor.getValue();
        Assertions.assertEquals(2, tilesAddedToCenter.size());
        Assertions.assertEquals(Tile.RED, tilesAddedToCenter.get(0));
        Assertions.assertEquals(Tile.WHITE, tilesAddedToCenter.get(1));
    }

    @Test
    void exit() {
        tilePickingState.exit();
    }
}
