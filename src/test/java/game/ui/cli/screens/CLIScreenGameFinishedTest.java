package game.ui.cli.screens;

import game.components.PlayerBoard;
import game.components.Wall;
import game.controller.Controller;
import game.player.Player;
import game.ui.cli.CLI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CLIScreenGameFinishedTest {

    CLIScreenGameFinished cliScreenGameFinished;
    CLI cliMock;
    Controller controllerMock;
    Player player1Mock;
    PlayerBoard board1Mock;
    Wall wall1Mock;
    Player player2Mock;
    PlayerBoard board2Mock;
    Wall wall2Mock;

    ArrayList<Player> playerMocks;


    @BeforeEach
    public void initCLIScreenTilePicking() {
        wall1Mock = mock(Wall.class);
        board1Mock = mock(PlayerBoard.class);
        when(board1Mock.getWall()).thenReturn(wall1Mock);
        player1Mock = mock(Player.class);
        when(player1Mock.getBoard()).thenReturn(board1Mock);

        wall2Mock = mock(Wall.class);
        board2Mock = mock(PlayerBoard.class);
        when(board2Mock.getWall()).thenReturn(wall2Mock);
        player2Mock = mock(Player.class);
        when(player2Mock.getBoard()).thenReturn(board2Mock);

        playerMocks = new ArrayList<>();
        playerMocks.add(player1Mock);
        playerMocks.add(player2Mock);

        cliMock = mock(CLI.class);
        controllerMock = mock(Controller.class);
        when(controllerMock.getPlayers()).thenReturn(playerMocks);

        when(cliMock.getController()).thenReturn(controllerMock);

        cliScreenGameFinished = new CLIScreenGameFinished(cliMock);
    }

    @Test
    public void updateScreenSingleWinner() {
        List<Player> winners = new ArrayList<>();
        winners.add(player1Mock);
        when(controllerMock.determineWinners()).thenReturn(winners);
        // Should not throw an exception
        cliScreenGameFinished.updateScreen();
    }

    @Test
    public void updateScreenMultipleWinnersSameScore() {
        List<Player> winners = new ArrayList<>();
        winners.add(player1Mock);
        winners.add(player2Mock);
        when(controllerMock.determineWinners()).thenReturn(winners);
        when(board1Mock.getScore()).thenReturn(25);
        when(board2Mock.getScore()).thenReturn(25);
        // Should not throw an exception
        cliScreenGameFinished.updateScreen();
    }

    @Test
    public void updateScreenMultipleWinnersDifferentScore() {
        List<Player> winners = new ArrayList<>();
        winners.add(player1Mock);
        winners.add(player2Mock);
        when(controllerMock.determineWinners()).thenReturn(winners);
        when(board1Mock.getScore()).thenReturn(20);
        when(board2Mock.getScore()).thenReturn(25);
        // Should not throw an exception
        cliScreenGameFinished.updateScreen();
    }

    @Test
    public void selectRestartFullWord() {
        String input = "restart\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Assertions.assertTrue(cliScreenGameFinished.selectRestartOrQuit());
    }

    @Test
    public void selectRestartSingleChar() {
        String input = "r\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Assertions.assertTrue(cliScreenGameFinished.selectRestartOrQuit());
    }

    @Test
    public void selectQuitFullWord() {
        String input = "quit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Assertions.assertFalse(cliScreenGameFinished.selectRestartOrQuit());
    }

    @Test
    public void selectQuitSingleChar() {
        String input = "q\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Assertions.assertFalse(cliScreenGameFinished.selectRestartOrQuit());
    }

    @Test
    public void selectReplayOrQuitInvalid() {
        // First input is invalid and should be ignored
        String input = "Awql5168@!#@\n";
        input += "r\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Assertions.assertTrue(cliScreenGameFinished.selectRestartOrQuit());
    }
}
