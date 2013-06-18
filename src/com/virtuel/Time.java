package com.virtuel;

import org.lwjgl.Sys;

public class Time {

	private static double delta;
	private static long lastFrame;
	private static int FPS;
	
	static {
		lastFrame = getTime();
	}
	
	/** Calculates the delta and fps. 
	 *  Should be ran once every tick in the game loop. */
	public static void tick(){
		long time = getTime();
		delta = time - lastFrame;
		lastFrame = time;
		
		FPS = (int)(1000.0 / delta);
	}
	
	/** Get the accurate system time
	 *  @return The system time in milliseconds */
	public static long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/** Returns the fps. */
	public static int getFps(){
		return FPS;
	}
		
	/** Returns the delta value. */
	public static double getDelta(){
		return delta;
	}
}
