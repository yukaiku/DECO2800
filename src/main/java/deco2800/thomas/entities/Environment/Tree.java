package deco2800.thomas.entities.Environment;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.entities.RenderConstants;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tree extends StaticEntity implements Tickable {
	private final Logger LOG = LoggerFactory.getLogger(Tree.class);

	AbstractWorld world;
	

	public Tree(float col, float row, int renderOrder, List<Part> parts) {
		super(col, row, renderOrder, parts);
		LOG.info("Making a tree at {}, {}", col, row);
		this.setTexture("tree");
	}

	public Tree(Tile t, boolean obstructed) {
        super(t, RenderConstants.TREE_RENDER, "tree", obstructed);
	}


	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!(other instanceof Tree)) {
			return false;
		}
		Tree otherTree = (Tree) other;
		if (this.getCol() != otherTree.getCol() || this.getRow() != otherTree.getRow() || this.getHeight() != otherTree.getHeight()) {
			return false;
		}
		return true;
	}


	/**
	 * Gets the hashCode of the tree.
	 *
	 * @return the hashCode of the tree
	 */
	@Override
	public int hashCode() {
		final float prime = 31;
		float result = 1;
		result = (result + super.getCol()) * prime;
		result = (result + super.getRow()) * prime;
		result = (result + super.getHeight()) * prime;
		return (int) result;
	}


	/**
	 * Animates the tree on every game tick.

	 * @param tick current game tick
	 */
	@Override
	public void onTick(long tick) {
	}
}