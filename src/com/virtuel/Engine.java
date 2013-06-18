package com.virtuel;

import static org.lwjgl.opengl.GL11.glGetError;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.virtuel.gui.MenuHandler;
import com.virtuel.interfaces.IEngine;
import com.virtuel.rendering.FontRenderer;
import com.virtuel.util.Keys;

/**
 * Base engine class everything runs from. Need to construct it with a Window already 'setup' and call init after which you can call start
 * which starts the game loop.
 * 
 * @author caleb
 * 
 *
 */
public abstract class Engine implements IEngine {
	
	public static Engine Instance;
	
	/** The current game running. */
	protected Game currentGame;
	protected Window window;
	
	/** The Menu handler for the whole engine. */
	protected MenuHandler menuHandler;
	
	/** The camera for the gui. */
	protected Camera guiCam;
	protected FontRenderer fontRender;
	
	protected boolean Running = true;
	protected boolean Paused = false;
	
	protected long time;
	protected int targetFps;
	
	/** Primary constructor. 
	 * @param window - Window class already 'setup' */
	public Engine(Window window) {
		this.window = window;
		menuHandler = new MenuHandler();
		fontRender = new FontRenderer();
		Instance = this;
		time = 0;
		targetFps = 0;
	}

	/** @param game - Game Instance constructed and 'started' */
	public Engine setCurrentGame(Game game) {
		currentGame = game;
		menuHandler.setCurrentMenu(null);
		return this;
	}
	
	
	@Override
	public void init() throws LWJGLException {
		InputHandler.init();
		fontRender.init();
		
		guiCam = new Camera.GuiCamera(window.getWidth(), window.getHeight()).setup();
	}
	
	/** Syncs the FPS of the game to the target value */
	public Engine sync(int targetfps){
		targetFps = targetfps;
		return this;
	}
	

	@Override
	public void start() {
		boolean error = false;
		while (Running) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			Time.tick();
//			if (Time.getFps()!=0)
				time += Time.getDelta();
			time %= Long.MAX_VALUE;
			
			// Polls the mouse and keyboard input
			InputHandler.pollInput();
			
			// Handles closing and pausing
			if (Display.isCloseRequested()) {
				Running = false;
			} if (InputHandler.isKeyPressed(Keys.KEY_ESCAPE)) {
				Paused = !Paused;
			} 
			
			// Doesn't work 100% 
			if (Display.wasResized()) {
				window.updateSize();
				guiCam = new Camera.GuiCamera(window.getWidth(), window.getHeight()).setup();
			}
			
			// Draws the game as long as there is a game to draw and its ready 
			if (currentGame!=null && currentGame.ready()) {
				if (!Paused)
					currentGame.update();
				currentGame.getCam().use();
				currentGame.drawGame();
				
				error |= checkForGLErrors("Game Draw");
			}

			// Updates the mouse input for the menu
			menuHandler.updateControls();
			
			update();
			
			// Tells OpenGL to use this camera
			guiCam.use();
			
			// Draws the menu background
			if (currentGame == null){
				menuHandler.drawBackground();
			}
			error |= checkForGLErrors("Menu Background Draw");
			
			draw();
			error |= checkForGLErrors("Engine Draw");
			
			// Draws the current menu;
			menuHandler.draw();
			error |= checkForGLErrors("Menu Draw");

			// Rendering the GameUI. Topmost layer.
			if (currentGame!=null && currentGame.ready()){
				currentGame.drawUI();
			}
			error |= checkForGLErrors("GameUI Draw");
			if (error)
				System.out.println("----------------");
			error = false;
			
			// Fps handling
			Display.update();
			if (targetFps!=0){
				Display.sync(targetFps);
			}
		}
		
		// Clean up
		Keyboard.destroy();
		Mouse.destroy();
		
	}

	@Override
	public void stop() {
		Running = false;
	}

	@Override
	public void stopGame() {
		if (currentGame != null){
			currentGame.stop();
			setCurrentGame(null);
		}
	}
	
	@Override
	public abstract void update();

	/** Used to draw gui objects. */
	@Override
	public abstract void draw();

	
	public Window getWindow() {
		return window;
	}

	public Camera getGuiCam() {
		return guiCam;
	}
	
	public MenuHandler getMenuHandler() {
		return menuHandler;
	}
	
	/** Checks for opengl errors. Converts error gotten from glGetError and prints out a string using GLU.gluErrorString */ 
	public static boolean checkForGLErrors(String tag) {
		int error = glGetError();
		if (error != 0)
			System.out.println("[" + tag + "] openGL Error! id: " + error + "\t" + GLU.gluErrorString(error));
		return error != 0;
	}

	
}
