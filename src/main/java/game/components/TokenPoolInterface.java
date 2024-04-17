package game.components;

import java.util.ArrayList;
import java.util.List;

public interface TokenPoolInterface {
	static boolean useTeam11 = false;

	static TokenPoolInterface construct(String name) {
		if (useTeam11) {
			return new TilePoolFaker(name);
		} else {
			return new TokenPool(name);
		}
	}

	// @ ensures \result != null;
	public ArrayList<Tile> getContents();

	// @ requires tiles != null;
	// @ ensures getContents().containsAll(tiles);
	public void addTiles(ArrayList<Tile> tiles);

	// @ requires tile != null;
	// @ ensures getContents().contains(tile);
	public void addTile(Tile tile);

	// @ requires color != null;
	// @ ensures \result != null;
	// @ ensures (\forall Tile t; \result.contains(t); t == color || t ==
	// Tile.STARTING_PLAYER_TILE);
	// @ ensures getContents().containsAll(\result);
	public List<Tile> extractTilesOfColor(Tile color);

	// @ ensures \result != null;
	// @ ensures getContents().isEmpty();
	public List<Tile> extractRemainingTiles();

	// @ ensures \result == getContents().isEmpty();
	public boolean isEmpty();

	// @ ensures \result != null;
	public String getName();
}