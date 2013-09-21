package com.virtuel.test;

import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;

import com.virtuel.Engine;
import com.virtuel.InputHandler;
import com.virtuel.Time;
import com.virtuel.Window;
import com.virtuel.gui.MenuHandler;
import com.virtuel.interfaces.IEngine;
import com.virtuel.math.vec.Vec2;
import com.virtuel.util.Keys;
import com.virtuel.util.ScreenShotHandler;
import com.virtuel.util.Terminal;

public class VTestEngine extends Engine implements IEngine {
	
	public static int WIDTH = 960, HEIGHT = 640;
	
	private Terminal terminal;
	
	public VTestEngine(Window window) {
		super(window);
		menuHandler = new MenuHandler();
		terminal = new Terminal(new Vec2.i(8, window.getHeight()-8), new Vec2.i(512, 256), "Main");
		
	}
	
	public void init() throws LWJGLException {
		super.init();
		
		terminal.init();
		
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
