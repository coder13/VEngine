package com.virtuel.rendering.texturing;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.virtuel.math.Rect;

public class TextureMap extends Texture {

	private Map<String, Rect.i> textures;
	
	public TextureMap(int[] texureData, int width, int height, boolean blur, boolean clamp) {
		super(texureData, width, height, blur, clamp);
		textures = new HashMap<String, Rect.i>();
	}
	
	/** Maps the texture to the list of subTextures. */
	public TextureMap map(String name, int x, int y, int width, int height){
		textures.put(name, new Rect.i(x, y, width, height));
		return this;
	}

	/** Returns the texture for the given name. */
	public Rect.i get(String name){
		return textures.get(name);
	}
	
	/** Returns the texture for the given name with the textures ready to be used in GL. (0-1) */
	public Rect.f getForGL(String name) {
		Rect.i iRect = get(name);
		if (iRect != null) {
			float tx = 1f/(float)(Width), ty = 1f/(float)(Height);
			return new Rect.f((float)iRect.X*tx, (float)iRect.Y*ty, (float)iRect.Width*tx, (float)iRect.Height*ty);
		}
		return null;
	}
	
	public Rect.f getForGL(int index) {
		Rect.i iRect = textures.get(index);
		if (iRect != null) {
			float tx = 1f/(float)(Width), ty = 1f/(float)(Height);
			return new Rect.f((float)iRect.X*tx, (float)iRect.Y*ty, (float)iRect.Width*tx, (float)iRect.Height*ty);
		} else {
			throw new IllegalArgumentException("rect is invalid for given index");
		}
	}
	
	
	public static TextureMap load(BufferedImage image, int cellWidth, int cellHeight) {
		TextureMap textureMap = new TextureMap(getContents(image), image.getWidth(), image.getHeight(), false, false);
		for (int x = 0; x < image.getWidth()/cellWidth; x++){
			for (int y = 0; y < image.getHeight()/cellHeight; y++){
				textureMap.map(x + "," + y, x*cellWidth, y*cellWidth, cellWidth, cellHeight);
			}
		}
		return textureMap;
	}

	public static TextureMap load(Texture texture) {
		TextureMap textureMap = new TextureMap(texture.getTextureData(), texture.getWidth(), texture.getHeight(), false, false);
		return textureMap;
	}
	
}
