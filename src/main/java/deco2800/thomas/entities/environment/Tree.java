package deco2800.thomas.entities.environment;

import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Tree extends StaticEntity {
	public static final String ENTITY_ID_STRING = "Tree";

	AbstractWorld world;

	public Tree() {
		this.setObjectName(ENTITY_ID_STRING);
	}

	public Tree(float col, float row, int renderOrder, List<Part> parts) {
		super(col, row, renderOrder, parts);
		Logger logger = LoggerFactory.getLogger(Tree.class);
		logger.info("Making a tree at {}, {}", col, row);
		this.setTexture("tree");
		this.setObjectName(ENTITY_ID_STRING);
	}

	public Tree(Tile t, boolean obstructed) {
		super(t, RenderConstants.TREE_RENDER, "tree", obstructed);
		this.setObjectName(ENTITY_ID_STRING);
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
		return this.getCol() == otherTree.getCol() && this.getRow() == otherTree.getRow() && this.getHeight() == otherTree.getHeight();
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Tree{");
		sb.append("world=").append(world);
		sb.append(", children=").append(children);
		sb.append(", position=").append(position);
		sb.append('}');
		sb.append("\n");
		return sb.toString();
	}
}