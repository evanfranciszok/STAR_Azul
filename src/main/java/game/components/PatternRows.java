package game.components;

import java.util.ArrayList;
import java.util.List;

public class PatternRows {
    /**
     * The amount of rows there are
     */
    private static final int N_ROWS = 5;

    /**
     * Array containing all pattern rows
     */
    private final List<Tile>[] rows;

    /**
     * The default constructor
     */
    // @ ensures rows != null;
    // @ ensures rows.length == N_ROWS;
    // @ ensures (\forall int i; i >= 0 && i < N_ROWS; rows[i] != null &&
    // rows[i].isEmpty());
    public PatternRows() {
        rows = new ArrayList[N_ROWS];
        for (int i = 0; i < N_ROWS; i++) {
            rows[i] = new ArrayList<>();
        }
    }

    /**
     * Can a tile of the specified color be placed in this pattern row?
     *
     * @param color (Tile) The color
     * @param row   (int) The row
     * @return true if the color can be placed in this row, else false
     */
    public boolean isColorAvailableInRow(Tile color, int row) {
        if (color == Tile.STARTING_PLAYER_TILE) {
            return false;
        }
        if (rows[row].size() == 0) {
            return true;
        }
        return rows[row].get(0) == color;
    }

    /**
     * Is a row in the pattern rows full
     *
     * @param row (int) The row that is checked whether it is full
     * @return true if the row is full, else false
     */
    public boolean isRowFull(int row) {
        // rows[0] may contain 1 tile, rows[4] may contain 5
        return rows[row].size() >= (row + 1);
    }

    /**
     * Add a tile to a row
     *
     * @param row  (int) The row the tile is being added to
     * @param tile (Tile) The tile being added
     * @throws IllegalStateException    When trying to add to a full row
     * @throws IllegalArgumentException When trying to add to a row with other color
     *                                  tiles
     */
    public void addToRow(int row, Tile tile) throws IllegalStateException, IllegalArgumentException {
        if (isRowFull(row)) {
            throw new IllegalStateException("Trying to add to a full row of a pattern: " + row);
        }
        if (!isRowEmpty(row) && rows[row].get(0) != tile) {
            throw new IllegalArgumentException("Tile cannot be added to pattern row: " + row + ", tile in pattern row: "
                    + rows[row].get(0) + " != " + tile);
        }
        rows[row].add(tile);
    }

    /**
     * Extract all tiles from a row
     * The extracted row is cleared
     *
     * @param row (int) The row the tiles are being extracted from
     * @return The extracted tiles
     * @throws IllegalStateException When trying to extract a non-empty row
     */
    public List<Tile> extractRow(int row) throws IllegalStateException {
        if (!isRowFull(row)) {
            throw new IllegalStateException("Trying to extract a not full pattern row: " + row);
        }

        var extractedTiles = new ArrayList<>(rows[row]);
        resetRow(row);
        return extractedTiles;
    }

    /**
     * Get the contents of the specified @p row
     *
     * @param row (int) The row to get the contents of
     * @return The contents of the row
     */
    public List<Tile> getContentsOfRow(int row) {
        return rows[row];
    }

    /**
     * Reset a row based on the row number
     * Replaces the current ArrayList with a new one
     *
     * @param row (int) The row number
     */
    private void resetRow(int row) {
        rows[row] = new ArrayList<>();
    }

    /**
     * Is a row based on the row number empty
     *
     * @param row (int) The row number
     * @return If the row that belongs to the row number is empty
     */
    private boolean isRowEmpty(int row) {
        return rows[row].isEmpty();
    }
}
