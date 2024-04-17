package game.components;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.max;

public class Wall {
    /**
     * The layout of the wall
     */
    public static final Tile[][] layout = {
            { Tile.BLUE, Tile.ORANGE, Tile.RED, Tile.BLACK, Tile.WHITE },
            { Tile.WHITE, Tile.BLUE, Tile.ORANGE, Tile.RED, Tile.BLACK },
            { Tile.BLACK, Tile.WHITE, Tile.BLUE, Tile.ORANGE, Tile.RED },
            { Tile.RED, Tile.BLACK, Tile.WHITE, Tile.BLUE, Tile.ORANGE },
            { Tile.ORANGE, Tile.RED, Tile.BLACK, Tile.WHITE, Tile.BLUE }
    };
    /**
     * The amount of rows the wall has
     */
    private static final int ROWS = 5;
    /**
     * The amount of column the wall has
     */
    private static final int COLUMNS = 5;

    /**
     * The filled tiles in the wall
     */
    private final Boolean[][] tilesPlaced = new Boolean[5][5];

    /**
     * Default constructor
     * Initializes the tilesPlaced array to false
     */
    // @ ensures (\forall int row; row >= 0 && row < ROWS; (\forall int col; col >=
    // 0 && col < COLUMNS; !tilesPlaced[row][col]));
    public Wall() {
        // Initialize the tilesPlaced array to false
        for (var row = 0; row < ROWS; ++row) {
            Arrays.fill(tilesPlaced[row], false);
        }
    }

    /**
     * Get an array of booleans indicating whether each cell of the row contains a
     * tile
     *
     * @param row (int) The row to get the placed tiles for
     * @return an array of booleans indicating whether each cell of the row contains
     *         a tile
     */
    // @ requires row >= 0 && row < ROWS;
    // @ ensures \result != null;
    // @ ensures \result.length == COLUMNS;
    public Boolean[] getPlacedTilesOnRow(int row) {
        return tilesPlaced[row];
    }

    /**
     * Place a tile in its designated spot in a row
     *
     * @param tile (Tile) The tile that is being placed on the wall
     * @param row  (int) The row the tile is placed in
     * @return the score added from placing this tile
     * @throws IllegalArgumentException If the row parameter is outside the ranges
     *                                  or if the tile placed is already filled
     * @throws RuntimeException         Sanity check, should never happen
     */
    // @ requires row >= 0 && row < ROWS;
    // @ requires tile != null;
    // @ ensures \result >= 0;
    // @ ensures (\exists int column; column >= 0 && column < COLUMNS;
    // layout[row][column] == tile && tilesPlaced[row][column]);
    // @ ensures (\forall int column; column >= 0 && column < COLUMNS;
    // layout[row][column] != tile || !tilesPlaced[row][column]) ==> \result == -1;
    public int tileRow(Tile tile, int row) throws IllegalArgumentException, RuntimeException {
        if (row >= ROWS || row < 0) {
            throw new IllegalArgumentException("Invalid row parameter, must be between 0 and 5 row is: " + row);
        }

        // Check if the tile exists in the layout and is not already placed
        // @ loop_invariant column >= 0 && column <= COLUMNS;
        // @ loop_invariant (\forall int c; c >= 0 && c < column; layout[row][c] != tile
        // || !tilesPlaced[row][c]);
        // @ decreases COLUMNS - column;
        for (var column = 0; column < COLUMNS; ++column) {
            if (layout[row][column] == tile) {
                if (tilesPlaced[row][column]) {
                    throw new IllegalArgumentException(
                            "Trying to fill an already filled tile in the wall: " + layout[row][column]);
                }
                return tileElement(row, column);
            }
        }
        // Sanity check, should never happen
        throw new RuntimeException("Could not tile the wall, tile: " + tile + ", row: " + row);
    }

    /**
     * Is a color in a specific row already filled with a tile
     *
     * @param color (Tile) The color in the row
     * @param row   (int) The row on the wall
     * @return true if the spot is still available, else false
     * @throws IllegalArgumentException If the row parameter is outside the ranges
     * @throws RuntimeException         Sanity check, should never happen
     */
    // @ requires row >= 0 && row < ROWS;
    // @ requires color != null;
    // @ ensures \result == false ==> (\exists int column; column >= 0 && column <
    // COLUMNS; layout[row][column] == color && tilesPlaced[row][column]);
    // @ ensures \result == true ==> (\forall int column; column >= 0 && column <
    // COLUMNS; layout[row][column] != color || !tilesPlaced[row][column]);
    public boolean isColorAvailableInRow(Tile color, int row) throws IllegalArgumentException, RuntimeException {
        if (row >= ROWS || row < 0) {
            throw new IllegalArgumentException("Invalid row parameter, must be between 0 and 5 row is: " + row);
        }

        if (color == Tile.STARTING_PLAYER_TILE) {
            return false;
        }
        // Check each column to see if the color is available
        for (var column = 0; column < COLUMNS; ++column) {
            if (layout[row][column] == color) {
                return !tilesPlaced[row][column];
            }
        }
        // Sanity check, should never happen
        throw new RuntimeException("Could not determine if color is available in color: " + color + ", row: " + row);
    }

    /**
     * Is a tile in a specific column or row already filled
     *
     * @param row    (int) The specific row
     * @param column (int) The specific column
     * @return true if already filled, else false
     * @throws IllegalArgumentException If row or column are outside their ranges
     */
    // @ requires row >= 0 && row < ROWS;
    // @ requires column >= 0 && column < COLUMNS;
    // @ ensures \result == tilesPlaced[row][column];
    public boolean getTilePlacedElement(int row, int column) throws IllegalArgumentException {
        if (row >= ROWS || row < 0) {
            throw new IllegalArgumentException("Invalid row parameter, must be between 0 and 5 row is: " + row);
        }
        if (column >= COLUMNS || column < 0) {
            throw new IllegalArgumentException("Invalid row parameter, must be between 0 and 5 row is: " + column);
        }

        return tilesPlaced[row][column];
    }

    /**
     * Tile an element of row and column
     *
     * @param row    (int) The row of the tiled element
     * @param column (int) The column of the tiled element
     * @return The obtained score for tiling the element
     */
    // @ requires row >= 0 && row < ROWS;
    // @ requires column >= 0 && column < COLUMNS;
    // @ ensures tilesPlaced[row][column];
    // @ ensures \result == calculateScoreForNewlyPlacedTile(row, column);
    private int tileElement(int row, int column) {
        tilesPlaced[row][column] = true;
        return calculateScoreForNewlyPlacedTile(row, column);
    }

    /**
     * Calculate the score of a tile being tiled on the wall
     * All tiles horizontally and vertically connected to the tiled tile are counted
     * The tiled tile is worth 2 points and each horizontally or vertically
     * connected tile 1 point
     *
     * @param row    (int) The row of the tiled element
     * @param column (int) The column of the tiled element
     * @return The obtained score for tiling the element
     */
    // @ requires row >= 0 && row < ROWS;
    // @ requires column >= 0 && column < COLUMNS;
    // @ ensures \result >= 1;
    private int calculateScoreForNewlyPlacedTile(final int row, final int column) {
        int horizontalScore = 0;

        var index = row;
        while (index >= 0 && tilesPlaced[index][column]) {
            ++horizontalScore;
            --index;
        }

        index = row + 1;
        while (index < ROWS && tilesPlaced[index][column]) {
            ++horizontalScore;
            ++index;
        }
        if (horizontalScore == 1) {
            horizontalScore = 0;
        }

        int verticalScore = 0;
        index = column;
        while (index >= 0 && tilesPlaced[row][index]) {
            ++verticalScore;
            --index;
        }

        index = column + 1;
        while (index < COLUMNS && tilesPlaced[row][index]) {
            ++verticalScore;
            ++index;
        }
        if (verticalScore == 1) {
            verticalScore = 0;
        }

        return max(1, verticalScore + horizontalScore);
    }

    /**
     * Get the amount of rows filled on the wall
     *
     * @return The amount of rows filled
     */
    // @ ensures \result >= 0 && \result <= ROWS;
    public int getNumberOfFilledRows() {
        return (int) Arrays.stream(tilesPlaced).filter(
                row -> Arrays.stream(row).filter(value -> value).count() == 5).count();
    }

    /**
     * Get the amount of columns filled on the wall
     *
     * @return The amount of columns filled
     */
    // @ ensures \result >= 0 && \result <= COLUMNS;
    public int getNumberOfFilledColumns() {
        int nFilledColumns = 0;
        // @ maintaining 0 <= nFilledColumns && nFilledColumns <= COLUMNS;
        // @ decreasing COLUMNS - i;
        for (int i = 0; i < COLUMNS; i++) {
            boolean columnFilled = true;
            // @ maintaining columnFilled == true;
            // @ decreasing ROWS - j;
            for (int j = 0; j < ROWS; j++) {
                columnFilled &= tilesPlaced[j][i];
            }
            if (columnFilled) {
                ++nFilledColumns;
            }
        }
        return nFilledColumns;
    }

    /**
     * Get the amount of fully completed colors on the wall
     *
     * @return The amount of colors that are fully completed
     */
    // @ ensures \result >= 0 && \result <= layout[0].length;
    // @ ensures \result == colorsCompleted.stream().filter(value -> value).count();
    public long getNumberOfCompletedColors() {
        List<Boolean> colorsCompleted = new java.util.ArrayList<>(Collections.nCopies(5, true));
        // @ loop_invariant i >= 0 && i <= ROWS;
        // @ loop_invariant \forall int k; k >= 0 && k < i;
        // colorsCompleted.get(Arrays.asList(layout[0]).indexOf(layout[k][j])) == true;
        for (int i = 0; i < ROWS; i++) {
            // @ loop_invariant j >= 0 && j <= COLUMNS;
            // @ loop_invariant \forall int k; k >= 0 && k < j;
            // colorsCompleted.get(Arrays.asList(layout[0]).indexOf(layout[i][k])) == true;
            for (int j = 0; j < COLUMNS; j++) {
                // Use the first row of the wall to determine a unique index for each color
                int indexOfColor = Arrays.asList(layout[0]).indexOf(layout[i][j]);
                colorsCompleted.set(indexOfColor, colorsCompleted.get(indexOfColor) && tilesPlaced[i][j]);
            }
        }
        return colorsCompleted.stream().filter(value -> value).count();
    }

}
