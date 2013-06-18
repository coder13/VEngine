package com.virtuel.rendering;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.virtuel.ImageHandler;
import com.virtuel.math.vec.Vec3;
import com.virtuel.util.IOUtil;

public class RenderEngine {

	private static Map<String, Integer> Textures = new HashMap<String, Integer>();
	private static Map<Integer, int[]>  TextureContents = new HashMap<Integer, int[]>();
	
	private static Map<String, Integer> displayLists = new HashMap<String, Integer>();
	
	
	public static int createDisplayList(String name) {
		int displayList = GL11.glGenLists(1);
		displayLists.put(name, displayList);
		return displayList;
	}
	
	public static int getDisplayList(String name) {
		return displayLists.get(name);
	}
	
	public static void delteDisplayList(String name) {
		GL11.glDeleteLists(displayLists.get(name), 1);
	}
	
	
	public static int createTexture(String name, boolean blur, boolean clamp) throws IOException {
		int texture=0;
		BufferedImage image = IOUtil.getImageFromStream(IOUtil.getPathAsStream(name));
		
		ImageHandler.loadTexture(texture, image);
		Textures.put(name, texture);
		
		int[] data = ImageHandler.getContents(image);
		TextureContents.put(texture, data);
		
		return texture;
	}
	
	public static int createTexture(String name) throws IOException {
		return createTexture(name, false, false);
	}
	
	public static int getTexture(String name) {
		return Textures.get(name);
	}
	
	public static int[] getTextureContents(String name) {
		return TextureContents.get(Textures.get(name));
	}
	
	
	public static void drawMesh(Vec3.d... verticies) {
		Tessellator tess = Tessellator.Instance;
		tess.beginDrawingTriangles();
		for (Vec3.d vertex : verticies) {
			tess.addVertex(vertex.X, vertex.Y, vertex.Z);
		}
		tess.draw();
	}
	
}
