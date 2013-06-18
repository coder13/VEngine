package com.virtuel.gui;

public interface IClickable {
	
	public static final int STATE_DOWN = 2;
	
	public void down();
	
	public void up();
	
	public void clicked();
}
