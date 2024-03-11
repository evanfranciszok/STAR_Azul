package game;

import game.components.PlayerBoard;
import game.components.Tile;
import game.components.TokenPoolInterface;
import game.controller.Controller;
import game.controller.states.*;
import game.player.AI;
import game.ui.cli.CLI;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserTest {
    static CLI ui;
    static Controller controller;
    static ByteArrayOutputStream out;

    static void setup() {
        resetOut();
        ui = new CLI();
        controller = new Controller(ui);
    }

    static void resetOut() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Test
    @Order(1)
    void configure() { // I can configure the game
        setup();

        // CONFIGURING STATE:

        ByteArrayInputStream in = new ByteArrayInputStream("c1\ny".getBytes()); // 1 AI, 1 human
        System.setIn(in);

        controller.getState().act(); // Input 'c1'

        assert(out.toString().contains("Amount of <Computer> players: 1"));
        assert(out.toString().contains("It is Player 1's turn."));
    }

    @Test
    @Order(2)
    void tilePicking() { // I can pick tiles from a
        resetOut();

        // TILE PICKING STATE:

        ByteArrayInputStream in = new ByteArrayInputStream("1\nBLUE\nORANGE\nRED\nWHITE\nBLACK".getBytes()); // Tries any colour
        System.setIn(in);

        while (controller.getState().getClass() == TilePicking.class) {
            controller.getState().act();
        }

        assert(out.toString().contains("Remaining held tiles:"));
    }

    @Test
    @Order(3)
    void tilePlacing() { // I can place my tiles on a pattern row
        resetOut();

        // TILE PATTERN PLACING STATE:

        int tiles = controller.getCurrentPlayer().getHeldTiles().size();

        // Chooses the row with the exact slots available
        ByteArrayInputStream in = new ByteArrayInputStream(String.format("%d", Math.min(tiles,5)).getBytes());
        System.setIn(in);

        while (controller.getState().getClass() == TilePatternPlacing.class
                || controller.getCurrentPlayer().getClass() == AI.class) {
            controller.getState().act();
        }

        assert(out.toString().contains("0 slots available"));
    }

    @Test
    @Order(4)
    void tilePickingCenter() { // I can pick tiles from the center
        resetOut();

        // TILE PICKING STATE:

        // Do stuff until something on center
        while (controller.getField().getCenter().getContents().stream().noneMatch(Set.of(Tile.values())::contains)) {
            ByteArrayInputStream in = new ByteArrayInputStream("1\n2\n3\n4\n5\nBLUE\nORANGE\nRED\nWHITE\nBLACK".getBytes());
            System.setIn(in);

            while (controller.getState().getClass() == TilePicking.class) {
                controller.getState().act();
            }

            in = new ByteArrayInputStream("f".getBytes());
            System.setIn(in);

            while (controller.getState().getClass() == TilePatternPlacing.class
                    || controller.getCurrentPlayer().getClass() == AI.class) {
                controller.getState().act();
            }
        }

        ByteArrayInputStream in = new ByteArrayInputStream("c\nBLUE\nORANGE\nRED\nWHITE\nBLACK".getBytes());
        System.setIn(in);

        while (controller.getState().getClass() == TilePicking.class) {
            controller.getState().act();
        }

        assert(out.toString().contains("Remaining held tiles:"));
    }

    @Test
    @Order(5)
    void tilePlacingFloor() { // I can place my tiles on the floor row
        resetOut();

        int floorLevel = controller.getCurrentPlayer().getBoard().getFloor().getFillLevel();
        int tiles = controller.getCurrentPlayer().getHeldTiles().size();

        // TILE PATTERN PLACING STATE:

        ByteArrayInputStream in = new ByteArrayInputStream("f".getBytes()); // Put tiles on the floor
        System.setIn(in);

        while (controller.getState().getClass() == TilePatternPlacing.class
                || controller.getCurrentPlayer().getClass() == AI.class) {
            controller.getState().act();
        }

        assert(out.toString().contains(String.format("Floor: %d out of 7 slots filled",floorLevel + tiles)));
    }

    @Test
    @Order(6)
    void tilePickingMoreThanOne() { // if I pick tiles off a factory, I pick all tiles of that colour in that factory
        resetOut();

        int factory = -1;
        String colour = null;
        int amount = 0;

        do {
            setup();
            configure();
            List<TokenPoolInterface> facts = controller.getField().getNonEmptyFactories();
            for (TokenPoolInterface fact : facts) {
                ArrayList<Tile> contents = fact.getContents();
                int blue = 0;
                int red = 0;
                int black = 0;
                int orange = 0;
                int white = 0;
                for (Tile content : contents) {
                    switch (content) {
                        case RED -> red += 1;
                        case BLUE -> blue += 1;
                        case ORANGE -> orange += 1;
                        case BLACK -> black += 1;
                        case WHITE -> white += 1;
                    }
                }
                Set<Integer> gtOne = new HashSet<>();
                gtOne.add(2);
                gtOne.add(3);
                gtOne.add(4);
                if (Arrays.stream(new int[]{blue, red, black, orange, white}).anyMatch(gtOne::contains)) {
                    factory = Integer.parseInt(fact.getName().substring(fact.getName().length()-1));
                    if (blue > 1) {
                        colour = "BLUE";
                        amount = blue;
                    } else if (red > 1) {
                        colour = "RED";
                        amount = red;
                    } else if (black > 1) {
                        colour = "BLACK";
                        amount = black;
                    } else if (orange > 1) {
                        colour = "ORANGE";
                        amount = orange;
                    } else if (white > 1) {
                        colour = "WHITE";
                        amount = white;
                    }
                }
            }
        } while (factory == -1);

        ByteArrayInputStream in = new ByteArrayInputStream(String.format("%d\n%s",factory, colour).getBytes());
        System.setIn(in);

        while (controller.getState().getClass() == TilePicking.class) {
            controller.getState().act();
        }

        StringBuilder output = new StringBuilder("Remaining held tiles:");
        for (int i = 0; i < amount; i++) {
            output.append(" ").append(colour);
        }

        assert(out.toString().contains(output.toString()));
    }

    @Test
    @Order(7)
    void tilePlacingTooMuch() { // I can place too many tiles, dropping some to the floor
        resetOut();

        int floorLevel = controller.getCurrentPlayer().getBoard().getFloor().getFillLevel();
        int tiles = controller.getCurrentPlayer().getHeldTiles().size();

        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);

        while (controller.getState().getClass() == TilePatternPlacing.class
                || controller.getCurrentPlayer().getClass() == AI.class) {
            controller.getState().act();
        }

        assert(out.toString().contains(String.format("Floor: %d out of 7 slots filled",floorLevel+tiles-1)));
    }

    void playRound() {
        while (controller.getState().getClass() != WallTiling.class) {
            ByteArrayInputStream in = new ByteArrayInputStream("1\n2\n3\n4\n5\nc\nBLUE\nORANGE\nRED\nBLACK\nWHITE".getBytes());
            System.setIn(in);

            while (controller.getState().getClass() == TilePicking.class) {
                controller.getState().act();
            }

            PlayerBoard board = controller.getCurrentPlayer().getBoard();
            List<Integer> availableRows = new ArrayList<>();
            for (int i = 0; i < PlayerBoard.N_ROWS; i++) {
                if (board.isColorAvailableInRow(controller.getCurrentPlayer().getHeldTiles().get(0), i) && !board.getPatternRows().isRowFull(i)) {
                    availableRows.add(i);
                }
            }
            int row = availableRows.get(new Random().nextInt(availableRows.size()));

            in = new ByteArrayInputStream(String.format("1\n%d\nf",row).getBytes());
            System.setIn(in);

            while (controller.getState().getClass() == TilePatternPlacing.class
                    || (controller.getState().getClass() != GameFinished.class
                    && controller.getState().getClass() != WallTiling.class
                    && controller.getCurrentPlayer().getClass() == AI.class)) {
                controller.getState().act();
            }
        }
        while (controller.getState().getClass() == WallTiling.class) {
            controller.getState().act();
        }
        while (controller.getState().getClass() != GameFinished.class && controller.getCurrentPlayer().getClass() == AI.class) {
            controller.getState().act();
        }
    }

    @Test
    @Order(8)
    void automaticWallTiling() { // I can play a full round, and my wall will be automatically tiled
        resetOut();

        playRound();

        assert(out.toString().contains("Automatically tiled wall"));
    }

    @Test
    @Order(9)
    void restart() { // At the end of the game, I can choose to restart the game.

        while (controller.getState().getClass() != GameFinished.class) {
            playRound();
        }

        ByteArrayInputStream in = new ByteArrayInputStream("r".getBytes());
        System.setIn(in);

        resetOut();

        controller.getState().act();

        assert(out.toString().contains("Amount of <Human> players:"));
    }
}