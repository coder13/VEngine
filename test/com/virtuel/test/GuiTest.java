package com.virtuel.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.virtuel.Engine;
import com.virtuel.Game;
import com.virtuel.ImageHandler;
import com.virtuel.rendering.FontRenderer;
import com.virtuel.util.Terminal;

public class GuiTest extends Game {

	public static int WIDTH = 960, HEIGHT = 640;
	
	private Boolean Clicked = false;
	
	private int backgroundTexture;
	
	public GuiTest(Engine parent) {
		super(parent, "GuiTest");
	}
	
	public void start() {
		try {
			backgroundTexture = ImageHandler.loadTexture(backgroundTexture, ImageIO.read(new FileInputStream("res/Background.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			stop();
		} catch (IOException e) {
			e.printStackTrace();
			stop();
		}
		
	}
	
	@Override
	public void stop() {
		parentEngine.setCurrentGame(null);
		parentEngine.getMenuHandler().setCurrentMenu("MainMenu");
	}

	@Override
	public void update() {
	}
	
	@Override
	public void drawGame() {
//		GL11.glBindTexture(GL11.GL_TEXTURE_2D, backgroundTexture);
	}
	
	@Override
	public void drawUI() {

	}
	
}
