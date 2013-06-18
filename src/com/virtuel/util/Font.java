package com.virtuel.util;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Font {
	public static BufferedImage FontImage;
	
	static {
		try {
			FontImage = ImageIO.read(new FileInputStream("res/default.png"));
		} catch (FileNotFoundException e) {
			System.err.println("[net.va.util.Font] default font png found in res/default.png could not be found.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static final Font Default = new Font(20, 20, 16, Color.White, true);
	
	private BufferedImage bitmap = FontImage;
	public int Size=16, Width, Height;
	public Color color = Color.White;
	public boolean AutoAlign = true;
	public int tabLength = 4;
	
	public Font(int width, int height){
		Width = width;
		Height = height;
	}

	public Font(int width, int height, Color col){
		Width = width;
		Height = height;
		color = col;
	}
	
	public Font(int width, int height, Color col, boolean autoAlign) {
		Width = width;
		Height = height;
		color = col;
		AutoAlign = autoAlign;
	}
	
	public Font(int width, int height, int size, Color col, boolean autoAlign) {
		Width = width;
		Height = height;
		Size = size;
		color = col;
		AutoAlign = autoAlign;
	}
	
	public Font(int width, int height, int size, Color col, boolean autoAlign, BufferedImage image) {
		Width = width;
		Height = height;
		Size = size;
		color = col;
		AutoAlign = autoAlign;
		bitmap = image;
	}
	
	
	public BufferedImage getBitmap() {
		return bitmap;
	}
	
	
}
