package game.components;

import game.components.borrowed.TilePile;
import game.components.borrowed.Color;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class TilePoolFaker extends TilePile implements TokenPoolInterface {
	public TilePoolFaker(String name)
	{
        this.name = name;
	}

	private Tile ColorToTile(Color color)
	{
		switch (color) {
			case BLUE:
				return Tile.BLUE;
			case WHITE:
				return Tile.WHITE;
			case BLACK:
				return Tile.BLACK;
			case YELLOW:
				return Tile.ORANGE;
			case RED:
				return Tile.RED;
			default:
				return Tile.BLUE;
		}
	}

	private Color TileToColor(Tile color)
	{
		switch (color) {
			case BLUE:
				return Color.BLUE;
			case WHITE:
				return Color.WHITE;
			case BLACK:
				return Color.BLACK;
			case ORANGE:
				return Color.YELLOW;
			case RED:
				return Color.RED;
			default:
				return Color.BLUE;
		}
	}

    private final String name;
	private boolean hasStarter = false;

	@Override
	public ArrayList<Tile> getContents() {
		var content = this.getCounters();
		ArrayList<Tile> output = new ArrayList<>();
		content.forEach((key, value) -> {
			for(int i = 0; i < value; i++)
			{
				output.add(ColorToTile(key));
			}
		});
		if(hasStarter) {
			output.add(Tile.STARTING_PLAYER_TILE);
		}
		return output;
	}

	@Override
	public void addTiles(ArrayList<Tile> tiles) {
		for (Tile tile : tiles) {
			if(tile == Tile.STARTING_PLAYER_TILE)
			{
				hasStarter = true;
				continue;
			}
			super.add(TileToColor(tile), 1);
		}
	}

	@Override
	public void addTile(Tile tile) {
		if(tile == Tile.STARTING_PLAYER_TILE)
		{
			hasStarter = true;
			return;
		}
		super.add(TileToColor(tile), 1);
	}

	@Override
	public List<Tile> extractTilesOfColor(Tile color) {
		if(color == Tile.STARTING_PLAYER_TILE)
		{
            throw new InvalidParameterException("Extracting only the STARTING_PLAYER_TILE is not supported");
		}
		var count = super.getCounter(TileToColor(color));
		List<Tile> output = new ArrayList<>();
		for(int i = 0; i < count; i++)
		{
			output.add(color);
		}
		if(output.isEmpty())
		{
            throw new InvalidParameterException("There are no tiles of the requested color available in this TokenPool");
		}
		if(hasStarter) {
			output.add(Tile.STARTING_PLAYER_TILE);
			hasStarter = false;
		}
		super.remove(TileToColor(color));
		return output;
	}

	@Override
	public List<Tile> extractRemainingTiles() {
		var output = this.getContents();
		super.resetCounters();
		if(hasStarter) {
			output.add(Tile.STARTING_PLAYER_TILE);
			hasStarter = false;
		}
		return output;
	}

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}

	@Override
	public String getName() {
		return this.name;
	}
}
