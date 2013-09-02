package com.virtuel.rendering;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.virtuel.math.vec.Vec3;
import com.virtuel.math.vec.Vec3.f;
import com.virtuel.rendering.texturing.Texture;

public class RenderEngine {

	private static Map<String, Texture> Textures = new HashMap<String, Texture>();
	
	private static Map<String, Integer> displayLists = new HashMap<String, Integer>();
	
	
	public static int newDisplayList(String name) {
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
	
	public static Collection<Integer> getAllDisplayLists() {
		return displayLists.values();
	}
	
	
	
	public static void addTexture(String name, Texture texture) {
		Textures.put(name, texture);
	}
	
	public static Texture getTexture(String name) {
		return Textures.get(name);
	}
	
	public static void bind(String name){
		Textures.get(name).bind();
	}
	
	
	public static void drawMesh(Vec3.f... verticies) {
		Renderer render = Renderer.getPerferedRenderer();
		render.beginTriangles();
		for (f vertex : verticies) {
			render.addVertex(vertex.X, vertex.Y, vertex.Z);
		}
		render.draw();
	}
	
}
