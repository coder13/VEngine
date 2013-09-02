package com.virtuel.rendering.texturing;

import static org.lwjgl.opengl.GL11.GL_CLAMP;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.virtuel.math.vec.Vec2;

public class Texture {

	protected int textureID, Width, Height;
	protected int[] texData;
	protected boolean Blur, Clamp;
	
	public Texture(int[] textureData, int width, int height, boolean blur, boolean clamp) {
		Width = width;
		Height = height;
		Blur = blur;
		Clamp = clamp;
		textureID = GL11.glGenTextures();
		texData = textureData;
		
		bind();
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		if (blur){
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR); 
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		}
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, clamp?GL_CLAMP:GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, clamp?GL_CLAMP:GL_REPEAT);
		
		ByteBuffer Pixels = BufferUtils.createByteBuffer(4*width*height);
		for (int i = 0; i < textureData.length; i++){
			Pixels.put((byte)(textureData[i]>>16 & 0xff));
			Pixels.put((byte)(textureData[i]>>8  & 0xff));
			Pixels.put((byte)(textureData[i]     & 0xff));
			Pixels.put((byte)(textureData[i]>>24 & 0xff));
		}
		
		Pixels.flip();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, Pixels);
	}
	
	
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}
	
	public int[] getTextureData(){
		return texData;
	}
	
	public int getWidth(){
		return Width;
	}

	public int getHeight(){
		return Height;
	}
	
	public Vec2.f getTexCoordsFor(int x, int y){
		return new Vec2.f(1f/(float)Width * (float)x, 1f/(float)Height * (float)y);
	}
	
	
	public static Texture load(BufferedImage image, boolean blur, boolean clamp) {
		Texture texture = new Texture(getContents(image), image.getWidth(), image.getHeight(), blur, clamp);
		return texture;
	}

	public static Texture load(BufferedImage image) {
		Texture texture = new Texture(getContents(image), image.getWidth(), image.getHeight(), false, false);
		return texture;
	}
	
	public static TextureAtlas load(Texture texture, int cellWidth, int cellHeight){
		return new TextureAtlas(texture, cellWidth, cellHeight);
	}
	
	public static int[] getContents(BufferedImage image) {
		int width = image.getWidth(), height = image.getHeight();
		int[] rawPixels = new int[width*height];
		image.getRGB(0, 0, width, height, rawPixels, 0, width);
		return rawPixels;
	}
	
}
