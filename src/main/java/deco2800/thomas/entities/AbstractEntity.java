package deco2800.thomas.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.NetworkManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.Renderable;
import deco2800.thomas.util.BoundingBox;
import deco2800.thomas.util.SquareVector;

import java.util.Objects;

import com.google.gson.annotations.Expose;
import deco2800.thomas.util.WorldUtil;
import org.w3c.dom.Text;

import static deco2800.thomas.managers.GameManager.getManagerFromInstance;

/**
 * AbstractEntity is an item that can exist in both 3D and 2D worlds.
 * AbstractEntities are rendered by Render2D and Render3D.
 * An item that does not need to be rendered should not be a WorldEntity.
 */
public abstract class AbstractEntity implements Comparable<AbstractEntity>, Renderable {
	private static final String ENTITY_ID_STRING = "entityID";
	
	@Expose
	private String objectName = null;
		
	static int nextID = 0;

	public static void resetID() {
		nextID = 0;
	}

	static int getNextID() {
		return nextID++;
	}

	@Expose
	private String texture = "error_box";

	protected SquareVector position;

	protected BoundingBox bounds;
	
	private int height;

	private float colRenderLength;

	private float rowRenderLength;
	

	@Expose
	private int entityID = 0;

	/** Whether an entity should trigger a collision. */
	private boolean collidable = true; 
	
	private int renderOrder = 0; 
	
	/**
	 * Constructor for an abstract entity.
	 * @param col the col position on the world
	 * @param row the row position on the world
     */
	public AbstractEntity(float col, float row, int renderOrder) {
		this(col, row, renderOrder, 1f, 1f);
		
		this.setObjectName(ENTITY_ID_STRING);
		this.renderOrder = renderOrder;
	}

	public AbstractEntity() {
		this.position = new SquareVector();
		this.bounds = new BoundingBox(this.position, 0, 0);
		this.colRenderLength = 1f;
		this.rowRenderLength = 1f;
		entityID = AbstractEntity.getNextID();
		this.setObjectName(ENTITY_ID_STRING);
	}


	/**
	 * Constructor for an abstract entity.
	 * @param col the col position on the world
	 * @param row the row position on the world
	 * @param height the height position on the world
	 * @param colRenderLength the rendered length in col direction
	 * @param rowRenderLength the rendered length in the row direction
     */
	public AbstractEntity(float col, float row, int height, float colRenderLength, float rowRenderLength) {
		this.position = new SquareVector(col, row);
		this.bounds = new BoundingBox(this.position, 0, 0);
		this.height = height;
		this.colRenderLength = colRenderLength;
		this.rowRenderLength = rowRenderLength;
		this.entityID = AbstractEntity.getNextID();
	}

	/**
	 * Get the column position of this AbstractWorld Entity.
	 */
	public float getCol() {
		return position.getCol();
	}

	/**
	 * Get the row position of this AbstractWorld Entity.
	 */
	public float getRow() {
		return position.getRow();
	}

	/**
	 * Get the Z position of this AbstractWorld Entity.
	 * 
	 * @return The Z position
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets the col coordinate for the entity.
     */
	public void setCol(float col) {
		this.position.setCol(col);
	}

	/**
	 * Sets the row coordinate for the entity.
	 */
	public void setRow(float row) {
		this.position.setRow(row);
	}

	/**
	 * Sets the height coordinate for the entity.
	 */
	public void setHeight(int z) {
		this.height = z;
	}

	/**
	 * Sets the position of the entity in the world.
	 * @param col the x coordinate for the entity
	 * @param row the y coordinate for the entity
     * @param height the z coordinate for the entity
     */
	public void setPosition(float col, float row, int height) {
		setCol(col);
		setRow(row);
		setHeight(height);
	}

	public float getColRenderWidth() {
		return colRenderLength;
	}

	public float getRowRenderWidth() {
		return rowRenderLength;
	}
	
	public void setRenderOrder(int newLevel) {
		this.renderOrder = newLevel;
	}
	
	public int getRenderOrder() {
		return renderOrder;
	} 
	
	@Override 
	public int compareTo(AbstractEntity otherEntity) {
		return this.renderOrder - otherEntity.getRenderOrder();
	}

	/**
	 * Tests to see if the item collides with another entity in the world.
	 * @param entity the entity to test collision with
	 * @return true if they collide, false if they do not collide
     */
	public boolean collidesWith(AbstractEntity entity) {
		//TODO: Implement this.
		return false;
	}

	/**
	 * Gets the bounding box of this entity.
	 * @return the bounding box of this entity.
	 */
	public BoundingBox getBounds() {
		return bounds;
	}

	/**
	 * Sets the width and height of the bounding box of this entity
	 * based off of the current texture.
	 */
	public void setBounds() {
		TextureManager textureManager =
				GameManager.getManagerFromInstance(TextureManager.class);
		bounds.setWidth(textureManager.getTexture(texture).getWidth()
				* WorldUtil.SCALE_X);
		bounds.setHeight(textureManager.getTexture(texture).getHeight()
				* WorldUtil.SCALE_Y);
	}

	@Override
	public float getColRenderLength() {
		return this.colRenderLength;
	}

	@Override
	public float getRowRenderLength() {
		return this.rowRenderLength;
	}

	/**
	 * Gives the string for the texture of this entity.
	 * This does not mean the texture is currently registered.
	 * 
	 * @return texture string
	 */
	public String getTexture() {
		return texture;
	}

	/**
	 * Sets the texture string for this entity.
	 * Check the texture is registered with the TextureRegister.
	 * 
	 * @param texture String texture id
	 */
	public void setTexture(String texture) {
		this.texture = texture;
		setBounds();
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AbstractEntity that = (AbstractEntity) o;
		return height == that.height &&
				Float.compare(that.colRenderLength, colRenderLength) == 0 &&
				Float.compare(that.rowRenderLength, rowRenderLength) == 0 &&
				entityID == that.entityID &&
				collidable == that.collidable &&
				Objects.equals(texture, that.texture) &&
				Objects.equals(position, that.position);
	}

	@Override
	public int hashCode() {
		int result = position != null ? position.hashCode() : 0;
		result = 31 * result + (texture != null ? texture.hashCode() : 0);
		return result;
	}

	/**
	 * Gets the distance from an abstract entity.
	 * @param e the abstract entity
	 * @return the distance as a float
     */
	public float distance(AbstractEntity e) {
		return this.position.distance(e.position);
	}
	

	public SquareVector getPosition() {
		return position;
	}

	public abstract void onTick(long i);

	/**
	 * Set objectID (If applicable).
	 * @param name of object
	 */
	public void setObjectName(String name) {
		this.objectName = name;
	}

	/**
	 * Get objectID (If applicable).
	 * @return Name of object
	 */
	public String getObjectName() { return this.objectName; }
	
	
	public int getEntityID() {
		return entityID;
	}

	public void setEntityID(int id) {
		this.entityID = id;
	}

	public void dispose() {
		GameManager.get().getManager(NetworkManager.class).deleteEntity(this);
		GameManager.get().getWorld().getEntities().remove(this);
	}	
}
