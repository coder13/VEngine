package com.virtuel.gui;

import com.virtuel.math.vec.Vec2;

public class Panel extends ControlContainer{

	protected Vec2.i Pos, Size;
	protected String Name;
	
	public Panel(Vec2.i pos, Vec2.i size, String name) {
		Pos = pos;
		Size = size;
		Name = name;
	}

	public void draw() {
		
	}

}
