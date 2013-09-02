package com.virtuel.rendering.texturing;

public class TextureAtlas extends Texture {

	private int cellWidth, cellHeight;
	
	public TextureAtlas(Texture texture, int cellWidth, int cellHeight) {
		super(texture.texData, texture.Width, texture.Height, texture.Blur, texture.Clamp);
	}

	
	
}
