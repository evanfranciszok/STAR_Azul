package game.components;

import java.util.ArrayList;
import java.util.List;

public interface TokenPoolInterface {
	static boolean useTeam11 = false;

	static TokenPoolInterface construct(String name)
	{
		if(useTeam11)
		{
			return new TilePoolFaker(name);
		}
		else
		{
			return new TokenPool(name);
		}
	}

	public ArrayList<Tile> getContents();
	public void addTiles(ArrayList<Tile> tiles);
	public void addTile(Tile tile);
	public List<Tile> extractTilesOfColor(Tile color);
	public List<Tile> extractRemainingTiles();
	public boolean isEmpty();
	public String getName();
}
