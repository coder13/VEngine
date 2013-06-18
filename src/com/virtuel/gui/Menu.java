package com.virtuel.gui;

import com.virtuel.Engine;
import com.virtuel.rendering.FontRenderer;

public abstract class Menu extends ControlContainer {

	protected static FontRenderer fontRender;
	
	static {
		fontRender = new FontRenderer();
		fontRender.init();
	}
	
	protected Engine parent;
	
	protected String Name;
	protected boolean hidden;
	
	public Menu(Engine parent, String name) {
		this.parent = parent;
		Name = name;
		hidden = false;
	}
	
	public void show(){
		hidden = false;
	}
	
	public void hide(){
		hidden = true;
	}
	
	public abstract void init();
	
	public abstract void draw();
	
	
	public String getName() {
		return Name;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
}
