package com.virtuel;

import com.virtuel.rendering.FontRenderer;

public abstract class Game {

	protected Engine Parent;

	protected boolean hasStarted = false;
	
	protected Camera cam;
	protected FontRenderer fontRender;
	protected String Name;
	
	public Game(Engine parent, String name) {
		Parent = parent;
		fontRender = new FontRenderer();
		Name = name;
	}
	
	public void init() {
		fontRender.init();
	}
	
	public abstract void start();
	
	public abstract void stop();
	
	public abstract void update();
	
	public abstract void drawGame();
	
	public abstract void drawUI();
	
	
	protected void ready() {
		hasStarted = true;
	}
	
	
	public String getName(){
		return Name;
	}
	
	public boolean hasStarted() {
		return hasStarted;
	}
	
	public boolean isReady() {
		return hasStarted && cam!=null && fontRender!=null;
	}
	
	public Camera getCam() {
		return cam;
	}
	
	public FontRenderer getFontRenderer() {
		return fontRender;
	}
	
	public Engine getParent(){
		return Parent;
	}
	
}
