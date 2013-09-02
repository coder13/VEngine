package com.virtuel.test;

import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;


import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.opengl.GL11;

import com.virtuel.Engine;
import com.virtuel.Game;
import com.virtuel.InputHandler;
import com.virtuel.Time;
import com.virtuel.Window;
import com.virtuel.gui.Button;
import com.virtuel.gui.Control;
import com.virtuel.gui.Menu;
import com.virtuel.gui.MenuHandler;
import com.virtuel.gui.Theme;
import com.virtuel.interfaces.IEngine;
import com.virtuel.math.vec.Vec2;
import com.virtuel.rendering.Font;
import com.virtuel.util.Color;
import com.virtuel.util.Keys;
import com.virtuel.util.ScreenShotHandler;
import com.virtuel.util.Terminal;
import com.virtuel.util.commands.Commands;
import com.virtuel.util.commands.QuitCommand;

public class VTestEngine extends Engine implements IEngine {
	
	public static int WIDTH = 960, HEIGHT = 640;
	
	private Game guiTest;
	private Terminal terminal;
	
	public VTestEngine(Window window) {
		super(window);
		menuHandler = new MenuHandler();
		terminal = new Terminal(new Vec2.i(8, window.getHeight()-8), new Vec2.i(512, 256), "Main");
		
		guiTest = new GuiTest(this);
	}
	
	public void init() throws LWJGLException {
		super.init();
//		InputHandler.DEBUG = true;

		guiTest.init();
		
		// Menu
		
		terminal.init();
		
		Menu MainMenu = new Menu(this, "MainMenu") {
			
			@Override
			public void init() {
				
			}
			
			@Override
			public void draw() {
				
			}

		};

		MainMenu.addControl(terminal);
		
		Control options = new Button(new Vec2.i(108, 64), new Vec2.i(128, 64), "Options") {
			@Override
			public void down() {
				color.set(127, 127, 127);
			}
			
			@Override
			public void up() {
				color.set(255, 255, 255);
			}

			@Override
			public void clicked() {
				
			}
		}; 
		Control quit = new Button(new Vec2.i(window.getWidth()-64, 0), new Vec2.i(64, 32), "Quit") {
			@Override
			public void down() {
				color.set(127, 127, 127);
			}
			
			@Override
			public void up() {
				color.set(255, 255, 255);
			}

			@Override
			public void clicked() {
				Running = false;
			}
		}; 
		Control startGame = new Button(new Vec2.i(108, 128), new Vec2.i(128, 64), "Start Game") {

			@Override
			public void down() {
				color.set(127, 127, 127);
				
			}

			@Override
			public void up() {
				color.set(255, 255, 255);
				
			}

			@Override
			public void clicked() {
				guiTest.start();
				currentGame = guiTest;
				menuHandler.setCurrentMenu(null);
			}
			
		};
		
		MainMenu.addControl(options);
		MainMenu.addControl(quit);
		MainMenu.addControl(startGame);
		menuHandler.addMenu(MainMenu);
		menuHandler.setCurrentMenu(MainMenu.getName());
		

		Theme warTheme = new Theme("res/WarriorsTheme/").setFont(new Font(20, 20, 16, Color.Black, false));
		if (warTheme.init() == false) {
			System.out.println("Failed to set up theme.");
		}
		menuHandler.setTheme(warTheme);

		
		Commands.registerCommand("quit", new QuitCommand());

		Controllers.create();
		System.out.println(Controllers.isCreated());
		System.out.println(String.format("%s controllers", Controllers.getControllerCount()));
		
		// GL Calls
		
		GL11.glEnable(GL11.GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
	}
	
	public void start() {
		super.start();
	}
	
	public void stop() {
		
		super.stop();
	}
	
	@Override
	public void update() {
		terminal.update();
		if (InputHandler.isKeyPressed(Keys.KEY_F2)) {
			long time = Time.getTime();
			terminal.process("Taking screenshot... screenshots/ " + time + ".png");
			ScreenShotHandler.take("screenshots/" + time);
		} 
	}

	@Override
	public void draw() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		fontRender.drawText(0, 0, "FPS: " + Time.getFps());
		if (menuHandler.getCurrentMenu()!=null)
			fontRender.drawText(8, 0, String.format("Menus: %d    CurrentMenu: %s with %d controls", menuHandler.getMenuCount(), menuHandler.getCurrentMenuName(), menuHandler.getCurrentMenu().getControlCount()));
		fontRender.drawText(0, 1, String.format("MousePos: %d, %d", InputHandler.getMouseX(), InputHandler.getMouseY()));
		fontRender.drawText(0, 2, InputHandler.isKeyPressed(Keys.KEY_T));
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	
	public static void main(String[] args) {
		try {
			Window window = new Window(WIDTH, HEIGHT).create();
			Engine test = new VTestEngine(window);
			test.init();
			test.start();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
}
