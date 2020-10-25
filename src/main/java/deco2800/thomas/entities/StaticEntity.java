package deco2800.thomas.entities;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.annotations.Expose;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StaticEntity extends AbstractEntity {
	private final transient Logger log = LoggerFactory.getLogger(StaticEntity.class);

	private static final String ENTITY_ID_STRING = "staticEntityID";

	//pos, texture MUST BE A VALID POSITION ELSE IT WILL NULL!
	@Expose
	public Map<SquareVector, String> children;

	public StaticEntity() {
		super();
	}

	public StaticEntity(Tile tile, int renderOrder, String texture, boolean obstructed) {
		super(tile.getCol(), tile.getRow(), renderOrder);
		this.setObjectName(ENTITY_ID_STRING);

		children = new HashMap<>();
		children.put(tile.getCoordinates(), texture);
		if (!WorldUtil.validColRow(tile.getCoordinates())) {
			log.debug(tile.getCoordinates() + " is Invalid:");
			return;
		}
		tile.setParent(this);
		tile.setObstructed(obstructed);
		this.texture = texture;
	}

	public StaticEntity(float col, float row, int renderOrder, List<Part> entityParts) {
		super(col, row, renderOrder);
		this.setObjectName(ENTITY_ID_STRING);

		Tile centre = GameManager.get().getWorld().getTile(this.getPosition());
		if (centre == null) {
			log.debug("Centre is null");
			return;
		}

		if (!WorldUtil.validColRow(centre.getCoordinates())) {
			log.debug(centre.getCoordinates() + " Is Invalid:");
			return;
		}

		children = new HashMap<>();

		for (Part part : entityParts) {
			Tile tile = textureToTile(part.getPosition(), this.getPosition());
			if (tile != null) {
				children.put(tile.getCoordinates(), part.textureString);
				tile.setObstructed(part.isObstructed());
			}
		}
	}


	public void setup() {
		if (children != null) {
			for (SquareVector childPosition : children.keySet()) {
				Tile child = GameManager.get().getWorld().getTile(childPosition);
				if (child != null) {
					child.setParent(this);
				}
			}
		}
	}


	@Override
	public void onTick(long i) {
		// Do the AI for the entity in here.
	}

	private Tile textureToTile(SquareVector offset, SquareVector centre) {
		if (!WorldUtil.validColRow(offset)) {
			log.debug(offset + " Is Invaid:");
			return null;
		}
		SquareVector targetTile = centre.add(offset);
		return GameManager.get().getWorld().getTile(targetTile);
	}

	public Set<SquareVector> getChildrenPositions() {
		return children.keySet();
	}

	public Texture getTexture(SquareVector childPosition) {
		String texture = children.get(childPosition);
		return GameManager.get().getManager(TextureManager.class).getTexture(texture);
	}

	public void setChildren(Map<SquareVector, String> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("StaticEntity{");
		sb.append("children=").append(children);
		sb.append(", position=").append(position);
		sb.append('}');
		sb.append("\n");
		return sb.toString();
	}
}
