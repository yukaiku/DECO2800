package deco2800.thomas.util;

import java.util.List;

import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

public abstract class Pathfinder {
	
	public  abstract List<Tile> pathfind(AbstractWorld world, SquareVector origin, SquareVector destination);

}
