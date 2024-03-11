package game.ui.cli.screens;

import game.components.*;
import game.ui.UI;
import game.ui.UIScreen;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class CLIScreenPlaying extends UIScreen {
    /**
     * The constructor
     *
     * @param ui (UI) The injected UI
     */
    public CLIScreenPlaying(UI ui) {
        super(ui);
    }

    /**
     * {@inheritDoc} <br/>
     * Update the screen by wiping and printing the following:
     * The player scores
     * The field
     * The current players board
     */
    @Override
    public void updateScreen() {
        ui.wipeScreen();
        printAllScores();
        System.out.printf("It is %s's turn.\n", ui.getController().getCurrentPlayer().getName());
        System.out.println("Field:");
        printField(ui.getController().getField());
        printPlayerBoard(ui.getController().getCurrentPlayer().getBoard());
    }

    /**
     * Print the scores of all players
     */
    protected void printAllScores() {
        System.out.print("Scores: ");
        for (int i = 0; i < ui.getController().getPlayers().size(); i++) {
            System.out.printf("P%d: %d", i + 1, ui.getController().getPlayers().get(i).getBoard().getScore());
            if (i < ui.getController().getPlayers().size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    /**
     * Print a field
     *
     * @param field (Field) The field to be printed
     */
    protected void printField(Field field) {
        printTokenPool(field.getCenter());
        for (TokenPoolInterface factory : field.getNonEmptyFactories()) {
            printTokenPool(factory);
        }
    }

    /**
     * Print a token pool
     *
     * @param tokenPool (TokenPool) The token pool to be printed
     */
    protected void printTokenPool(TokenPoolInterface tokenPool) {
        System.out.printf("\t%-10s: ", tokenPool.getName());
        if (tokenPool.isEmpty()) {
            System.out.println("Empty");
            return;
        }
        var tilecounts = tokenPool.getContents().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (Tile color : Tile.values()) {
            if (tilecounts.get(color) != null) {
                System.out.printf("%s:%d ", color.name(), tilecounts.get(color));
            }
        }
        System.out.println();
    }

    /**
     * Print a player board
     *
     * @param board (PlayerBoard) The player board to be printed
     */
    protected void printPlayerBoard(PlayerBoard board) {
        System.out.println("Board:");
        for (int i = 0; i < PlayerBoard.N_ROWS; i++) {
            printPatternRow(board, i);
        }
        for (int i = 0; i < PlayerBoard.N_ROWS; i++) {
            printWallRow(board, i);
        }
        printFloor(board.getFloor());
    }

    /**
     * Print a pattern row
     *
     * @param board    (PlayerBoard) The player board the pattern row is part of
     * @param rowIndex (int) The pattern row to be printed
     */
    protected void printPatternRow(PlayerBoard board, int rowIndex) {
        List<Tile> row = board.getPatternRows().getContentsOfRow(rowIndex);
        String rowContents;
        if (row.isEmpty()) {
            rowContents = "EMPTY, " + (rowIndex + 1) + " slots available";
        } else {
            rowContents = row.get(0).toString() + ", " + (rowIndex + 1 - row.size()) + " slots available";
        }
        System.out.printf("\tPatternRow %d: %s\n", rowIndex + 1, rowContents);
    }

    /**
     * Print a wall row
     *
     * @param board    (PlayerBoard) The player board the wall row is part of
     * @param rowIndex (int) The wall row to be printed
     */
    protected void printWallRow(PlayerBoard board, int rowIndex) {
        Tile[] slots = Wall.layout[rowIndex];
        Boolean[] filledSlots = board.getWall().getPlacedTilesOnRow(rowIndex);
        System.out.printf("\tWall row %d:", rowIndex + 1);
        for (int i = 0; i < slots.length; i++) {
            System.out.printf("\t%8s:%d", slots[i].toString(), (filledSlots[i] ? 1 : 0));
        }
        System.out.println();
    }

    /**
     * Print a floor
     *
     * @param floor (Floor) The floor to be printed
     */
    protected void printFloor(Floor floor) {
        System.out.printf("\tFloor: %d out of %d slots filled\n", floor.getFillLevel(), Floor.FLOOR_SIZE);
    }
}
