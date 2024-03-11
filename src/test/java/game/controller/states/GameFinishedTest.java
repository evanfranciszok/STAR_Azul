package game.controller.states;

import game.components.PlayerBoard;
import game.controller.Controller;
import game.player.Player;
import game.ui.UI;
import game.ui.UIScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GameFinishedTest {
    GameFinished gameFinishedState;

    Controller controllerMock;
    UI uiMock;
    UIScreen uiScreenMock;

    Player player1Mock;
    PlayerBoard board1Mock;

    Player player2Mock;
    PlayerBoard board2Mock;

    @BeforeEach
    void before() {
        uiScreenMock = mock(UIScreen.class);
        uiMock = mock(UI.class);
        when(uiMock.getScreen()).thenReturn(uiScreenMock);

        board1Mock = mock(PlayerBoard.class);
        player1Mock = mock(Player.class);
        when(player1Mock.getBoard()).thenReturn(board1Mock);

        board2Mock = mock(PlayerBoard.class);
        player2Mock = mock(Player.class);
        when(player2Mock.getBoard()).thenReturn(board2Mock);


        controllerMock = mock(Controller.class);
        when(controllerMock.getUI()).thenReturn(uiMock);
        List<Player> players = new ArrayList<>();
        players.add(player1Mock);
        players.add(player2Mock);
        when(controllerMock.getPlayers()).thenReturn(players);

        gameFinishedState = new GameFinished(controllerMock);
    }

    @Test
    void entry() {
        gameFinishedState.entry();
        verify(board1Mock).finalizeScore();
        verify(board2Mock).finalizeScore();
        verify(uiMock).showGameFinishedScreen();
    }

    @Test
    void actRestart() {
        when(uiScreenMock.selectRestartOrQuit()).thenReturn(true);
        ArgumentCaptor<ControllerState> stateArgumentCaptor = ArgumentCaptor.forClass(ControllerState.class);
        gameFinishedState.act();
        verify(controllerMock).goToState(stateArgumentCaptor.capture());
        assertEquals(Configuring.class, stateArgumentCaptor.getValue().getClass());
    }

    @Test
    void actQuit() {
        when(uiScreenMock.selectRestartOrQuit()).thenReturn(false);
        gameFinishedState.act();
        verify(controllerMock).quit();
    }

    @Test
    void exit() {
        // Verify exit does not throw an exception
        gameFinishedState.exit();
    }
}
