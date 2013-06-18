package com.virtuel.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class ScreenShotHandler {

	private static String Path;
	
	private static Thread screenShotThread = new Thread() { 
		@Override 
		public void run() {
		File file = new File(Path + ".png"); // The file to save to.
		String format = "PNG"; // Example: "PNG" or "JPG"
	
		int width = Display.getDisplayMode().getWidth();
		int height= Display.getDisplayMode().getHeight();
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		GL11.glReadBuffer(GL11.GL_FRONT);
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer );
		
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++) {
				int i = (x + (width * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		  
		try {
			if (!file.exists())
				file.mkdirs();
			ImageIO.write(image, format, file);
		} catch (IOException e) { e.printStackTrace(); }
	}
};
	
	public static void take(String path) {
		Path = path;
		screenShotThread.run();
	}
}
