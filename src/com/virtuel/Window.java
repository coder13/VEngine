package com.virtuel;

import java.awt.Canvas;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class Window {

	private static int WIDTH, HEIGHT;
	
	private int Width, Height;
	private boolean FullScreen, VSync, Resizable;
	private Canvas Parent;
	private String Title;
	
	public Window() {
		Width = 640;
		Height = 480;
		WIDTH = Width;
		HEIGHT = Height;
		FullScreen = false;
		VSync = false;
		Resizable = true;
	}
	
	public Window(int width, int height) {
		Width = width;
		Height = height;
		WIDTH = Width;
		HEIGHT = Height;
		FullScreen = false;
		VSync = false;
		Resizable = true;
	}
	
	
	public Window setTitle(String title) {
		Title = title;
		return this;
	}
	
	public Window setSize(int width, int height) throws LWJGLException {
		Width = width;
		Height = height;
		WIDTH = width;
		HEIGHT = height;
		return this;
	}
	
	public Window setFullScreen(boolean fullScreen) {
		FullScreen = fullScreen;
		return this;
	}
	
	public Window setVSync(boolean vSync) {
		VSync = vSync;
		return this;
	}
	
	public Window setResizable(boolean resizable) {
		Resizable = resizable;
		return this;
	}
	
	public Window setCanvas(Canvas canvas) {
		canvas.setSize(Width, Height);
		Parent = canvas;
		return this;
	}
	
	
	public void updateSize() {
		Width  = Display.getWidth();
		Height = Display.getHeight();
		GL11.glViewport(0, 0, Width, Height);
	}
	
	public void updateSize(int width, int height) {
		Width  = width;
		Height = height;
		GL11.glViewport(0, 0, width, height);
	}
	
	public void updateFullscreen(boolean fullscreen) throws LWJGLException {
		FullScreen = fullscreen;
		if (FullScreen) {
			Display.setFullscreen(true);
			updateSize();
		} else {
			Display.setFullscreen(false);
			updateSize(WIDTH, HEIGHT);
		}
		
		Display.update();
	}
	
	public void toggleFullscreen() throws LWJGLException {
		updateFullscreen(!FullScreen);
	}
	
	
	public Window create() throws LWJGLException {
		if (FullScreen) {
			Display.setFullscreen(true);
			Display.setDisplayMode(Display.getDesktopDisplayMode());
		} else {
			Display.setDisplayMode(new DisplayMode(Width, Height));
		}
		Display.setTitle(Title);
		Display.setVSyncEnabled(VSync);
		Display.setResizable(Resizable);
		if (Parent != null) 
			Display.setParent(Parent);
		Display.create(new PixelFormat());
		
		updateSize();
		
		return this;
	}
	
	
	public int getWidth() {
		return Width;
	}
	
	public int getHeight() {
		return Height;
	}
}
