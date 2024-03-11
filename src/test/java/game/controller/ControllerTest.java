package game.controller;

import game.components.PlayerBoard;
import game.components.Wall;
import game.controller.states.Configuring;
import game.controller.states.ControllerState;
import game.player.Player;
import game.ui.cli.CLI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ControllerTest {
    Controller controller;

    @BeforeEach
    void before() {
        controller = new Controller(new CLI());
    }

    @Test
    public void init() {
        controller.initGame();
        Assertions.assertNotNull(controller.getBag());
        Assertions.assertNotNull(controller.getConfig());
        Assertions.assertNotNull(controller.getField());
        Assertions.assertNotNull(controller.getPlayers());
        Assertions.assertNotNull(controller.getUI());
        assertEquals(Configuring.class, controller.getState().getClass());
        assertEquals(controller.getConfig().humans() + controller.getConfig().computers(), controller.getPlayers().size());
        assertEquals(controller.getPlayers().size() * 2 + 1, controller.getField().getFactories().size());
        assertEquals(0, controller.getCurrentPlayerIndex());
    }

    @Test
    public void currentPlayer() {
        controller.initGame();
        for (int i = 0; i < controller.getPlayers().size(); i++) {
            assertEquals(i, controller.getCurrentPlayerIndex());
            assertEquals(controller.getPlayers().get(controller.getCurrentPlayerIndex()), controller.getCurrentPlayer());
            controller.incrementCurrentPlayer();
        }
        assertEquals(0, controller.getCurrentPlayerIndex());
    }

    @Test
    public void setCurrentPlayer() {
        controller.initGame();
        Player player3 = controller.getPlayers().get(2);
        controller.setCurrentPlayer(player3);
        Assertions.assertSame(player3, controller.getCurrentPlayer());
    }

    @Test
    public void goToState() {
        ControllerState state1Mock = mock(ControllerState.class);
        ControllerState state2Mock = mock(ControllerState.class);
        InOrder sequence = inOrder(state1Mock, state2Mock);

        controller.goToState(state1Mock);
        sequence.verify(state1Mock).entry();
        assertEquals(state1Mock, controller.getState());

        controller.goToState(state2Mock);
        sequence.verify(state1Mock).exit();
        sequence.verify(state2Mock).entry();
        assertEquals(state2Mock, controller.getState());
    }

    @Test
    public void run() {
        ControllerState stateMock = mock(ControllerState.class);
        // Controller.run() should infinitely call state.act(), so throw an exception during the second call to break from this
        doNothing().doThrow(new RuntimeException("Force exit in second call for test")).when(stateMock).act();
        controller.goToState(stateMock);
        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> controller.run(),
                "RuntimeException was expected"
        );
        assertEquals("Force exit in second call for test", exception.getMessage());
        verify(stateMock, times(2)).act();
    }

    @Test
    public void runQuit() {
        ControllerState stateMock = mock(ControllerState.class);
        // Controller.run() should infinitely call state.act(), so call controller.quit() during the second call to break from this
        doNothing().doAnswer((ignored) -> {
            controller.quit();
            return null;
        }).when(stateMock).act();
        controller.goToState(stateMock);
        controller.run();
        verify(stateMock, times(2)).act();
    }

    @Test
    public void determineWinnersByScore() {
        /* ** Setup ** */
        Wall wall1Mock = mock(Wall.class);
        PlayerBoard board1Mock = mock(PlayerBoard.class);
        when(board1Mock.getWall()).thenReturn(wall1Mock);
        Player player1Mock = mock(Player.class);
        when(player1Mock.getBoard()).thenReturn(board1Mock);

        Wall wall2Mock = mock(Wall.class);
        PlayerBoard board2Mock = mock(PlayerBoard.class);
        when(board2Mock.getWall()).thenReturn(wall2Mock);
        Player player2Mock = mock(Player.class);
        when(player2Mock.getBoard()).thenReturn(board2Mock);

        Wall wall3Mock = mock(Wall.class);
        PlayerBoard board3Mock = mock(PlayerBoard.class);
        when(board3Mock.getWall()).thenReturn(wall3Mock);
        Player player3Mock = mock(Player.class);
        when(player3Mock.getBoard()).thenReturn(board3Mock);

        controller.initGame();
        controller.getPlayers().removeIf(player -> true);
        controller.getPlayers().add(player1Mock);
        controller.getPlayers().add(player2Mock);
        controller.getPlayers().add(player3Mock);

        when(board1Mock.getScore()).thenReturn(3);
        when(board2Mock.getScore()).thenReturn(5);
        when(board3Mock.getScore()).thenReturn(1);

        /* ** Test ** */
        List<Player> winners = controller.determineWinners();
        assertEquals(1, winners.size());
        assertEquals(player2Mock, winners.get(0));
    }

    @Test
    public void determineWinnersByNumberOfFilledRows() {
        /* ** Setup ** */
        Wall wall1Mock = mock(Wall.class);
        PlayerBoard board1Mock = mock(PlayerBoard.class);
        when(board1Mock.getWall()).thenReturn(wall1Mock);
        Player player1Mock = mock(Player.class);
        when(player1Mock.getBoard()).thenReturn(board1Mock);

        Wall wall2Mock = mock(Wall.class);
        PlayerBoard board2Mock = mock(PlayerBoard.class);
        when(board2Mock.getWall()).thenReturn(wall2Mock);
        Player player2Mock = mock(Player.class);
        when(player2Mock.getBoard()).thenReturn(board2Mock);

        Wall wall3Mock = mock(Wall.class);
        PlayerBoard board3Mock = mock(PlayerBoard.class);
        when(board3Mock.getWall()).thenReturn(wall3Mock);
        Player player3Mock = mock(Player.class);
        when(player3Mock.getBoard()).thenReturn(board3Mock);

        controller.initGame();
        controller.getPlayers().removeIf(player -> true);
        controller.getPlayers().add(player1Mock);
        controller.getPlayers().add(player2Mock);
        controller.getPlayers().add(player3Mock);

        when(board1Mock.getScore()).thenReturn(3);
        when(board2Mock.getScore()).thenReturn(5);
        when(board3Mock.getScore()).thenReturn(5);

        when(wall2Mock.getNumberOfFilledRows()).thenReturn(1);
        when(wall3Mock.getNumberOfFilledRows()).thenReturn(2);

        /* ** Test ** */
        List<Player> winners = controller.determineWinners();
        assertEquals(1, winners.size());
        assertEquals(player3Mock, winners.get(0));
    }

    @Test
    public void determineWinnersTie() {
        /* ** Setup ** */
        Wall wall1Mock = mock(Wall.class);
        PlayerBoard board1Mock = mock(PlayerBoard.class);
        when(board1Mock.getWall()).thenReturn(wall1Mock);
        Player player1Mock = mock(Player.class);
        when(player1Mock.getBoard()).thenReturn(board1Mock);

        Wall wall2Mock = mock(Wall.class);
        PlayerBoard board2Mock = mock(PlayerBoard.class);
        when(board2Mock.getWall()).thenReturn(wall2Mock);
        Player player2Mock = mock(Player.class);
        when(player2Mock.getBoard()).thenReturn(board2Mock);

        Wall wall3Mock = mock(Wall.class);
        PlayerBoard board3Mock = mock(PlayerBoard.class);
        when(board3Mock.getWall()).thenReturn(wall3Mock);
        Player player3Mock = mock(Player.class);
        when(player3Mock.getBoard()).thenReturn(board3Mock);

        controller.initGame();
        controller.getPlayers().removeIf(player -> true);
        controller.getPlayers().add(player1Mock);
        controller.getPlayers().add(player2Mock);
        controller.getPlayers().add(player3Mock);

        when(board1Mock.getScore()).thenReturn(3);
        when(board2Mock.getScore()).thenReturn(5);
        when(board3Mock.getScore()).thenReturn(5);

        when(wall2Mock.getNumberOfFilledRows()).thenReturn(1);
        when(wall3Mock.getNumberOfFilledRows()).thenReturn(1);

        /* ** Test ** */
        List<Player> winners = controller.determineWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(player2Mock));
        assertTrue(winners.contains(player2Mock));
    }
}
