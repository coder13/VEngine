package com.virtuel.interfaces;

import org.lwjgl.LWJGLException;

public interface IEngine {

	/** Initializes the Engine. Any GL calls should be called here. 
	 * @throws LWJGLException */
	public abstract void init() throws LWJGLException;
	
	/** Starts the Engine. This contains the main loop. */
	public abstract void start();
	
	/** This is called after the main loop has ended. */
	public abstract void stop();
	
	/** This manually called to stop a game. Calls game.stop() and sets the current game to null. */
	public abstract void stopGame();
	
	/** This is called every game loop to update things. */
	public abstract void update();
	
	/** This is called every game loop to draw thins. */ 
	public abstract void draw();
	
}
