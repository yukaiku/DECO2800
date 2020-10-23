package deco2800.thomas.util;

import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

import java.util.List;

public abstract class Pathfinder {

	public abstract List<Tile> pathfind(AbstractWorld world, SquareVector origin, SquareVector destination);

}
