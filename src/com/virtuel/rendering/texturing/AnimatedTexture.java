package com.virtuel.rendering.texturing;

import java.awt.image.BufferedImage;

import com.virtuel.Time;
import com.virtuel.math.Rect;

public class AnimatedTexture {

	private TextureMap textureMap;
	private int Width, Height, width, height, Frames, Index, Delta;
	private long time;
	
	public AnimatedTexture(BufferedImage image, int cellWidth, int cellHeight, int frames, int fps) {
		Width = image.getWidth();
		Height = image.getHeight();
		width = cellWidth;
		height = cellHeight;
		Frames = frames;
		Delta = (int)(1000.0/(double)fps);
		textureMap = TextureMap.load(image, cellWidth, cellHeight);
		
		Index = 0;
	}
	
	public AnimatedTexture setIndex(int index){
		Index = index;
		return this;
	}
	
	
	public Rect.f getCoords() {
		int x = Index % (Width/width), y = Index / (Width/width);
		return textureMap.getForGL(String.format("%s,%s", x,y));
	}

	public Rect.f getCoords(int index) {
		int x = index % (Width/width), y = index / (Width/width);
		return textureMap.getForGL(String.format("%s,%s", x,y));
	}
	
	
	public void update() {
		time += Time.getDelta();
		Index += time/Delta;
		time %= Delta;
		Index %= Frames;
	}
	
	public void bind() {
		textureMap.bind();
	}
	
	
	public int getCellWidth() {
		return Width;
	}
	
	public int getCellHeight() {
		return Height;
	}
	
	public int getFrames() {
		return Frames;
	}
	
	public int getIndex() {
		return Index;
	}
	
}
