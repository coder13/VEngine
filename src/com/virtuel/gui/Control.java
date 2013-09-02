package com.virtuel.gui;

import java.util.ArrayList;
import java.util.List;

import com.virtuel.math.Rect;
import com.virtuel.math.vec.Vec2;
import com.virtuel.rendering.Font;
import com.virtuel.rendering.FontRenderer;
import com.virtuel.util.Color;

public abstract class Control {
	public static final int STATE_NONE = 0;
	public static final int STATE_HOVER = 1;
	
	protected static List<Control> Controls = new ArrayList<Control>();
	public static FontRenderer fontRender;
	
	static {
		fontRender = new FontRenderer(new Font(20, 20, Color.Black, false));
		fontRender.init();
	}
	
	protected String Name;
	protected String DisplayName;
	protected int State, lastState;
	protected Vec2.i Pos;
	protected Vec2.i Size;
	
	public Control(Vec2.i pos, Vec2.i size, String name) {
		Pos = pos;
		Size = size;
		Name = name;
		DisplayName = name;
		State = Control.STATE_NONE;
	}

	public Control(Vec2.i pos, Vec2.i size, String name, String displayName) {
		Pos = pos;
		Size = size;
		Name = name;
		DisplayName = displayName;
		State = Control.STATE_NONE;
	}
	
	public Control setPos(Vec2.i pos){
		Pos = pos;
		return this;
	}

	public Control setSize(Vec2.i size){
		Size = size;
		return this;
	}

	public Control setState(int state){
		State = state;
		return this;
	}
	
	public void hover(){
	}
	
	public void unHover(){
	}
	
	public abstract void draw();
	
	
	public String getName() {
		return Name;
	}

	public String getDisplayName() {
		return DisplayName;
	}
	
	public Vec2.i getPos(){
		return Pos;
	}
	
	public Vec2.i getSize(){
		return Size;
	}
	
	public Rect.i getBounds() {
		return new Rect.i(Pos.X, Pos.Y, Size.X, Size.Y);
	}
	
	public int getState(){
		return State;
	}

}
