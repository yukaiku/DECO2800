package deco2800.thomas.entities;

import com.google.gson.annotations.Expose;
import deco2800.thomas.util.SquareVector;

public class Part {
	@Expose
	Boolean obstructed;
	@Expose
	String textureString;
	SquareVector position;

	/**
	 * @param position      relative position to a centre position defined in StaticEntity
	 * @param textureString id String for the texture
	 * @param obstructed    whether the underlying tile for the StaticEntity part is obstructed or not
	 */
	public Part(SquareVector position, String textureString, Boolean obstructed) {
		this.position = position;
		this.textureString = textureString;
		this.obstructed = obstructed;
	}

	public Part(String textureString, Boolean obstructed) {
		this(new SquareVector(0, 0), textureString, obstructed);
	}

	public Boolean isObstructed() {
		return obstructed;
	}

	public String getTextureString() {
		return textureString;
	}

	public SquareVector getPosition() {
		return position;
	}
}