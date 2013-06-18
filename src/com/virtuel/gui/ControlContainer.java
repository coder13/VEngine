package com.virtuel.gui;

import java.util.ArrayList;
import java.util.List;

public abstract class ControlContainer {
	
	protected List<Control> Children = new ArrayList<Control>();
	
	public void addControl(Control control) {
		Children.add(control);
	}
	
	
	public void drawControls(Theme theme) {
		for (Control control : Children) {
			theme.drawControl(control);
		}
	}
	
	
	public int getControlCount() {
		return Children.size();
	}
}
