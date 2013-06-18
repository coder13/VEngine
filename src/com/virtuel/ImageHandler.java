package com.virtuel;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class ImageHandler {

	public static int loadTexture(int texture, BufferedImage image){
		return loadTexture(texture, image, false, false);
	}
	
	public static int loadTexture(int texture, BufferedImage image, boolean blur, boolean clamp){
		if (image == null) return -1;
		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		if (blur) {
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR); 
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		}
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, clamp?GL_CLAMP:GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, clamp?GL_CLAMP:GL_REPEAT);
		
		return loadTexture(texture, getContents(image), image.getWidth(), image.getHeight(), blur, clamp);
	}
	
	public static int loadTexture(int texture, int[] data, int width, int height, boolean blur, boolean clamp){
		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		if (blur){
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR); 
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		}
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, clamp?GL_CLAMP:GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, clamp?GL_CLAMP:GL_REPEAT);
		
		ByteBuffer Pixels = BufferUtils.createByteBuffer(4*width*height);
		
		for (int i = 0; i < data.length; i++){
			Pixels.put((byte)(data[i]>>16 & 0xff));
			Pixels.put((byte)(data[i]>>8  & 0xff));
			Pixels.put((byte)(data[i]     & 0xff));
			Pixels.put((byte)(data[i]>>24 & 0xff));
		}
		
		Pixels.flip();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, Pixels);
		return texture;
	}
	
	public static int[] getContents(int texture) {
		return null;
	}
	
	public static int[] getContents(BufferedImage image) {
		int width = image.getWidth(), height = image.getHeight();
		int[] rawPixels = new int[width*height];
		image.getRGB(0, 0, width, height, rawPixels, 0, width);
		return rawPixels;
	}
	
}
