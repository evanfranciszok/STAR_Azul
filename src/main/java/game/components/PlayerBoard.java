package game.components;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {
    /**
     * The amount of rows in the wall and pattern rows
     */
    public static final int N_ROWS = 5;
    /**
     * The wall of the player board
     */

    //@ spec_public
    private final Wall wall = new Wall();
    /**
     * The floor of the player board
     */
    //@ spec_public
    private final Floor floor = new Floor();
    /**
     * The pattern rows of the player board
     */
    //@ spec_public
    private final PatternRows patternRows = new PatternRows();

    /**
     * The current score of the player
     */
    //@ spec_public
    private int score = 0;

    /**
     * The default constructor
     */
    public PlayerBoard() {
    }

    /**
     * Can a tile of the specified color be placed in this pattern row?
     *
     * @param color (Tile) The color
     * @param row   (int) The row
     * @return true if the color can be placed in this row, else false
     */
    /*@
      @ requires color != null;
      @ requires 0 <= row && row < N_ROWS;
      @ ensures \result == (wall.isColorAvailableInRow(color, row) && patternRows.isColorAvailableInRow(color, row));
      @ pure
      @*/
    public boolean isColorAvailableInRow(Tile color, int row) {
        return wall.isColorAvailableInRow(color, row) && patternRows.isColorAvailableInRow(color, row);
    }

    /**
     * Perform a wall tiling sequence
     * tile the wall for each row the pattern row is filled
     * If the score dips below 0 in a turn the score is set to 0
     *
     * @return All tiles in the pattern row that are not tiled and the tiles in the floor that need to be returned to the box
     */
    /*@
      @ ensures \result != null;
      @ ensures (\forall Tile t; \result.contains(t) <==> (patternRows.isRowFull(\result.indexOf(t)) && wall.isColorAvailableInRow(t, \result.indexOf(t))));
      @ ensures \result.isEmpty() || (\forall Tile t; \result.contains(t) ==> (t == Tile.STARTING_PLAYER_TILE || (patternRows.isRowFull(\result.indexOf(t)) && floor.isFull())));
      @ ensures score >= 0;
      @*/
    public List<Tile> performWallTiling() {
        var tilesForBox = new ArrayList<Tile>();

        for (var row = 0; row < N_ROWS; ++row) {
            if (patternRows.isRowFull(row)) {
                var extractedTiles = patternRows.extractRow(row);
                score += wall.tileRow(extractedTiles.remove(0), row);
                tilesForBox.addAll(extractedTiles);
            }
        }

        score += floor.getMinusScore();
        tilesForBox.addAll(floor.reset());

        if (score < 0) {
            score = 0;
        }

        return tilesForBox;
    }

    /**
     * Place a list of tiles on a pattern row
     * If the pattern row is full the tile is placed on the floor
     * If the floor is full the tiles return to the box
     *
     * @param tiles (List<Tile>) The Tiles being added to the pattern row
     * @param row   (int) The pattern row
     * @return All unplaced tiles due to the pattern row and floor being full
     * @throws IllegalArgumentException If not all tiles in the tiles parameter are the same color
     *                                  or if the color is not available anymore in the wall for the passed row
     */
    /*@
      @ requires tiles != null;
      @ requires !tiles.isEmpty();
      @ requires 0 <= row && row < N_ROWS;
      @ ensures \result != null;
      @ ensures (\forall Tile t; \result.contains(t) <==> (patternRows.isRowFull(row) && floor.isFull() && tiles.contains(t)));
      @ signals (IllegalArgumentException e) (\exists Tile t; tiles.contains(t) && !wall.isColorAvailableInRow(t, row));
      @*/
    public List<Tile> placeTilesOnPatternRow(List<Tile> tiles, int row) throws IllegalArgumentException {
        if (tiles.remove(Tile.STARTING_PLAYER_TILE)) {
            floor.placeTile(Tile.STARTING_PLAYER_TILE);
        }
        var colorOfTiles = tiles.get(0);
        if (!tiles.stream().allMatch(tile -> tile == colorOfTiles)) {
            throw new IllegalArgumentException("not all tiles in tiles are the same");
        }
        if (!wall.isColorAvailableInRow(colorOfTiles, row)) {
            throw new IllegalArgumentException("Color: " + colorOfTiles + " is already in row: " + row);
        }

        var tilesForBox = new ArrayList<Tile>();

        for (var tile : tiles) {
            if (patternRows.isRowFull(row)) {
                if (floor.isFull()) {
                    tilesForBox.add(tile);
                } else {
                    floor.placeTile(tile);
                }
            } else {
                patternRows.addToRow(row, tile);
            }
        }

        return tilesForBox;
    }

    /**
     * getter for the score of the player
     *
     * @return The current scores
     */
    /*@
      @ ensures \result >= 0;
      @ pure
      @*/
    public int getScore() {
        return score;
    }

    /**
     * setter for the score of the player
     *
     * @param newScore The new score to set
     */
    /*@
      @ requires newScore >= 0;
      @ ensures score == newScore;
      @*/
    public void setScore(int newScore) {
        this.score = newScore;
    }

    /**
     * getter for the wall of the player board
     *
     * @return The wall
     */
    /*@
      @ ensures \result != null;
      pure
      @*/
    public Wall getWall() {
        return wall;
    }

    /**
     * getter for the floor of the player board
     *
     * @return The floor
     */
    /*@
      @ ensures \result != null;
      pure
      @*/
    public Floor getFloor() {
        return floor;
    }

    /**
     * getter for the pattern rows of the player board
     *
     * @return The pattern rows
     */
    /*@
      @ ensures \result != null;
      @ pure
      @*/
    public PatternRows getPatternRows() {
        return patternRows;
    }

    /**
     * Apply additional points gained from having filled wall rows, columns and completed colors at the end of the game
     */
    /*@
      @ ensures score >= 0;
      @ ensures score == \old(score) + 2 * wall.getNumberOfFilledRows() + 7 * wall.getNumberOfFilledColumns() + 10 * wall.getNumberOfCompletedColors();
      @*/
    public void finalizeScore() {
        score += 2 * wall.getNumberOfFilledRows();
        score += 7 * wall.getNumberOfFilledColumns();
        score += 10 * wall.getNumberOfCompletedColors();
    }
}
