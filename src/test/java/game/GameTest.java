package game;

import game.components.PlayerBoard;
import game.controller.Controller;
import game.controller.states.*;
import game.player.AI;
import game.player.Player;
import game.ui.cli.CLI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameTest {

    Controller game;

    @BeforeEach
    void setup() {
        game = new Controller(new CLI());
    }

    @Test
    void AIGame() {
        ByteArrayInputStream in = new ByteArrayInputStream("c4\ny".getBytes());
        System.setIn(in);

        while (game.getState().getClass() != GameFinished.class) {
            game.getState().act();
        }

        in = new ByteArrayInputStream("q".getBytes());
        System.setIn(in);

        game.run();
    }

    @Test
    void oneHumanGame() {
        ByteArrayInputStream in = new ByteArrayInputStream("h1\nc1\ny".getBytes());
        System.setIn(in);

        while (game.getState().getClass() == Configuring.class) {
            game.getState().act();
        }

        Player player = game.getCurrentPlayer();

        while (game.getState().getClass() != GameFinished.class) {
            while (game.getState().getClass() != WallTiling.class) {
                in = new ByteArrayInputStream("1\n2\n3\n4\n5\nc\nBLUE\nORANGE\nRED\nBLACK\nWHITE".getBytes());
                System.setIn(in);

                while (game.getState().getClass() == TilePicking.class) {
                    game.getState().act();
                }

                PlayerBoard board = game.getCurrentPlayer().getBoard();
                List<Integer> availableRows = new ArrayList<>();
                for (int i = 0; i < PlayerBoard.N_ROWS; i++) {
                    if (board.isColorAvailableInRow(game.getCurrentPlayer().getHeldTiles().get(0), i) && !board.getPatternRows().isRowFull(i)) {
                        availableRows.add(i);
                    }
                }
                int row = availableRows.get(new Random().nextInt(availableRows.size()));

                in = new ByteArrayInputStream(String.format("1\n%d\nf",row).getBytes());
                System.setIn(in);

                while (game.getState().getClass() == TilePatternPlacing.class
                        || (game.getState().getClass() != GameFinished.class
                        && game.getState().getClass() != WallTiling.class
                        && game.getCurrentPlayer().getClass() == AI.class)) {
                    game.getState().act();
                }
            }
            while (game.getState().getClass() == WallTiling.class) {
                game.getState().act();
            }
            while (game.getState().getClass() != GameFinished.class && game.getCurrentPlayer().getClass() == AI.class) {
                game.getState().act();
            }
        }

        in = new ByteArrayInputStream("q".getBytes());
        System.setIn(in);

        game.run();
    }

}
