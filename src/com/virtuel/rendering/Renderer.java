package com.virtuel.rendering;

import java.nio.ByteOrder;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import com.virtuel.math.vec.Vec2;
import com.virtuel.math.vec.Vec3;

import com.virtuel.util.Color;

/** Abstract class for a renderer used to renderer a list of vertices. */
public abstract class Renderer {
	
	private static boolean useVBOs = false;
	
	public static VAORenderer VaoInstance = new VAORenderer(0x100000);
	public static VBORenderer VboInstance;
	
	/** Returns the VboInstance if vbos are enabled. If not, returns VaoInstance. */
	public static Renderer getPerferedRenderer() {
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object && useVBOs)
			return VboInstance;
		return VaoInstance;
	}
	

	/** Translation values of all vertices along the x, y, and z axis. */
		protected float TransX, TransY, TransZ;
	
	/** Size of the buffer. */
	protected int Size;

	/** The color to be used for the current vertex. */
	protected int Color = 0xFFFFFFFF;

	/** Determines if the current draw object has texture coordinates. */
	protected boolean isTextured = false;
	/** The first or second coordinate being used for the texture. */
	protected float textureU, textureV;
	
	/** Determines if the current draw object has normals. */
	protected boolean hasNormals = false;
	/** The normal to be used on the face being drawn. */
	protected int Normal;
	
	/** The current DrawMode; */
	protected int DrawMode;
	/** States whether or not the tessellator is currently drawing. */
	protected boolean isDrawing = false;
	
	public Renderer(int size) {
		size = Size;
	}
	
	/** Sets the texture coordinates for the current vertex. Enables Texturing. */
	public void setTexCoords(float u, float v){
		textureU = u;
		textureV = v;
		isTextured = true;
	}
	
	/** Sets the normal for the current Vertex. Enables normals. */
	public void setNormal(double x, double y, double z) {
		hasNormals = true;
		byte x1 = (byte)((int)x * 127);
		byte y1 = (byte)((int)y * 127);
		byte z1 = (byte)((int)z * 127);
		Normal = x1 & 255 | (y1 & 255) >> 8 | (z1 & 255) << 16;
	}
	
	
	public void setColor(int color){
		Color = color;
	}
	
	/** Sets the color with the given int values ranging 0-255. */
	public  void setColor(int r, int g, int b, int a) {
		r = (r>255?255:(r<0?0:r));
		g = (g>255?255:(g<0?0:g));
		b = (b>255?255:(b<0?0:b));
		a = (a>255?255:(a<0?0:a));
		
		if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
			Color = a << 24 | b << 16 | g << 8 | r;
		else
			Color = r << 24 | g << 16 | b << 8 | a;
	}

	/** Sets the color with the given int values ranging 0-255. */
	public void setColor(int r, int g, int b) {
		setColor(r, g, b, 255);
	}

	/** Sets the color with the given float values ranging 0-1. */
	public void setColor(float r, float g, float b, float a) {
		setColor((int)(r*255), (int)(g*255), (int)(b*255), (int)(a*255));
	}

	/** Sets the color with the given float values ranging 0-1. */
	public void setColor(float r, float g, float b) {
		setColor(r, g, b, 1);
	}
	
	public void setColor(Color color) {
		setColor(color.R, color.G, color.B, color.A);
	}
	
	

	/** Adds a vertex with the given x, y, and 1 for z. */
	public void addVertex(float x, float y) {
		addVertex(x, y, 0);
	}

	/** Adds a vertex with the given vector. */
	public void addVertex(Vec2.f vector) {
		addVertex(vector.X, vector.Y, 0);
	}

	/** Adds a vertex with the given x, y, and z. */
	public abstract void addVertex(float x, float y, float z);

	/** Adds a vertex with the given vector. */
	public void addVertex(Vec3.f vector) {
		addVertex(vector.X, vector.Y, vector.Z);
	}
	
	/** Adds a vertex with the given x, y, z and u, v pair. */
	public void addVertexWithUV(float x, float y, float z, float u, float v) {
		setTexCoords(u, v);
		addVertex(x, y, z);
	}
	
	/** Adds a vertex with the given x, y, z and u, v pair. */
	public void addVertexWithUV(double x, double y, double z, double u, double v) {
		setTexCoords((float)u, (float)v);
		addVertex((float)x, (float)y, (float)z);
	}

	/** Adds a vertex with the given vector and u, v pair. */
	/** Adds a vertex with the given vector and u, v pair. */
	public void addVertexWithUV(Vec3.f vector, float u, float v) {
		addVertexWithUV(vector.X, vector.Y, vector.Z, u, v);
	}
	
	/** Adds a series of vertices. */
	public void addVertices(Vec3.f... vertices) {
		for (Vec3.f vertex : vertices)
			addVertex(vertex);
	}
	
	
	/** Translates each vertex with the given x, y and z. */
	public void translate(float x, float y, float z) {
		TransX += x;
		TransY += y;
		TransZ += z;
	}

	/** Sets the translation for each vertex with the given x, y and z. */
	/** Sets the translation for each vertex with the given x, y and z. */
	public void setTranslation(float x, float y, float z) {
		TransX = x;
		TransY = y;
		TransZ = z;
	}

	
	/** Starts drawing and sets the drawMode to GL_QUADS. Throws an Exception if we are already drawing. */
	public void beginQuads() {
		begin(GL11.GL_QUADS);
	}

	/** Starts drawing and sets the drawMode to GL_TRIANGLES. Throws an Exception if we are already drawing. */
	public void beginTriangles() {
		begin(GL11.GL_TRIANGLES);
	}

	/** Starts drawing and sets the DrawMode to the given. Throws an Exception if we are already drawing. */
	public void begin(int drawMode) {
		if (isDrawing) {
            throw new IllegalStateException("Already drawing!");
		} else {
			isDrawing = true;
			DrawMode = drawMode;
		}
	}

	/** Starts drawing and sets the drawMode to GL_POLYGON. Throws an Exception if we are already drawing. */
	public void begin() {
		begin(GL11.GL_POLYGON);
	}

	
	/** Resets the Renderer. */
	public abstract void reset();
	
	/** Draws the verticies and returns the number of verticies drawn. */
	public abstract int draw();
	
}
