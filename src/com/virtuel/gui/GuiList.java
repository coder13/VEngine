package com.virtuel.gui;

import java.util.ArrayList;
import java.util.List;

import com.virtuel.math.vec.Vec2.i;

public class GuiList extends Control {
	
	private List items;
	
	public GuiList(i pos, i size, String name) {
		super(pos, size, name);
		items = new ArrayList<String>();
	}

	@Override
	public void draw() {
		
	}

}
