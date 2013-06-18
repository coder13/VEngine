package com.virtuel.rendering;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

import com.virtuel.math.vec.Vec3;
import com.virtuel.util.GLUtil;
import com.virtuel.util.Color;

public class Tessellator {
	/** The static instance of the Tessellator. Size: 0x10000. */
	public static Tessellator Instance = new Tessellator(0x10000);

	/** The vertex Count */
	private int VertexCount = 0;
	
	/** raw byte Buffer. */
	private ByteBuffer byteBuffer;
	/** raw int Buffer. */
	private IntBuffer intBuffer;
	/** raw foat Buffer. */
	private FloatBuffer floatBuffer;
	
	/** The raw buffer that stores all drawing data. */
	private int[] buffer;
	/** The raw buffer size. */
	private int bufferSize;
	/** The raw buffer index. The current position of the buffer. */
	private int bufferIndex = 0;
	
	/** Off set value of all vertices along the x, y, and z axis. */
	private double offSetX, offSetY, offSetZ;
	
	/** The current drawMode; */
	private int DrawMode;
	/** States whether or not the tessellator is currently drawing. */
	public boolean isDrawing = false;
	
	/** Determines if the current draw object has color. */
	private boolean hasColor = false;
	/** The color to be used for the current vertex. */
	private int Color;
	/** Determines if the current draw object has texture coordinates. */
	private boolean hasTexture = false;
	/** The first or second coordinate being used for the texture. */
	private float textureU, textureV;
	/** Determines if the current draw object has normals. */
	private boolean hasNormals = false;
	/** The normal to be used on the face being drawn. */
	private int Normal;

	public Tessellator(int size) {
		Instance 	= this;
		bufferSize  = size;
		byteBuffer  = GLUtil.createByteBuffer(size*4);
		intBuffer   = byteBuffer.asIntBuffer();
		floatBuffer = byteBuffer.asFloatBuffer();
		buffer 		= new int[size];
	}
	
	//drawing
	
	public int draw() {
		if (!isDrawing)
			throw new IllegalStateException("Not drawing!");
		
		isDrawing = false;
		
		if (VertexCount <= 0 )
			return 0;
		
		intBuffer.clear();
		intBuffer.put(buffer, 0, bufferIndex);
		byteBuffer.position(0);
		byteBuffer.limit(bufferIndex * 4);

		
        floatBuffer.position(0 * 1);
        glVertexPointer(3, 32, this.floatBuffer);
		
		if (hasTexture) {
			floatBuffer.position(3 * 1);
			glTexCoordPointer(2, 32, floatBuffer);
			
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		}
		
		if (hasColor) {
			byteBuffer.position(5 * 4);
			glColorPointer(4, true, 32, byteBuffer);
			
			glEnableClientState(GL_COLOR_ARRAY);
		}
		
		if (hasNormals) {
			byteBuffer.position(0);
			glNormalPointer(32, byteBuffer);
			
			glEnable(GL_NORMAL_ARRAY);
		}

		
		glEnableClientState(GL_VERTEX_ARRAY);
		 
		
		glDrawArrays(DrawMode, 0, VertexCount);
		
		
		glDisableClientState(GL_VERTEX_ARRAY);
		if (hasTexture) {
			glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		}
		
		if (hasColor) {
			glDisableClientState(GL_COLOR_ARRAY);
		}
		
		if (hasNormals) {
			glDisableClientState(GL_NORMAL_ARRAY);
		}
		
		int vertcount = VertexCount;
		reset();
		return vertcount;
	}
	
	public void reset() {
		VertexCount = 0;
		bufferIndex = 0;
		hasColor = false;
		hasNormals = false;
		hasTexture = false;
		offSetX = offSetY = offSetZ = 0;
		byteBuffer.clear();
	}

	
	//begin drawing
	public void beginDrawingQuads() {
		beginDrawing(GL_QUADS);
	}
	
	public void beginDrawingTriangles() {
		beginDrawing(GL_TRIANGLES);
	}
	
	public void beginDrawing() {
		beginDrawing(GL_POLYGON);
	}
	
	public void beginDrawing(int drawMode) {
		if (isDrawing) {
            throw new IllegalStateException("Already drawing!");
		} else {
			isDrawing = true;
			DrawMode = drawMode;
		}
	}
	
	//Translating
	public void setTranslation(double x, double y, double z) {
		offSetX = x;
		offSetY = y;
		offSetZ = z;
	}
	
	public void translate(double x, double y, double z) {
		offSetX+=x;
		offSetY+=y;
		offSetZ+=z;
	}
	
	
	//Vertices
	public void addVertexWithUV(double x, double y, double z, float u, float v) {
		setTextureUV(u, v);
		addVertex(x, y, z);
	}
	
	public void addVertex(double x, double y, double z) {
		if (!isDrawing) {
			System.out.println("Shouldn't add vertex! Not drawing!");
		}
		VertexCount++;
		
		if (hasTexture) {
			buffer[bufferIndex + 3] = Float.floatToRawIntBits(textureU);
			buffer[bufferIndex + 4] = Float.floatToRawIntBits(textureV);
		}
		
		if (hasColor) {
			buffer[bufferIndex + 5] = Color;
		} 
		
		if (hasNormals) {
			buffer[bufferIndex + 6] = Normal;
		}
		
		buffer[bufferIndex + 0] = Float.floatToIntBits((float)(x + offSetX));
		buffer[bufferIndex + 1] = Float.floatToIntBits((float)(y + offSetY));
		buffer[bufferIndex + 2] = Float.floatToIntBits((float)(z + offSetZ));
		bufferIndex += 8;
		
		if (VertexCount % 4 == 0 && bufferIndex >= bufferSize-32) {
			draw();
			isDrawing = true;
		}
	}
	
	public void addVertexPair(double x0, double y0, double z0, double x1, double y1, double z1) {
		addVertex(x0, y0, z0);
		addVertex(x1, y1, z1);
	}
	
	public void addVertexPair(Vec3.d v0, Vec3.d v1) {
		addVertexPair(v0.X, v0.Y, v0.Z, v1.X, v1.Y, v1.Z);
	}
	
	
	//Texture & Normals
	public void setNormal(double x, double y, double z) {
		hasNormals = true;
		byte x1 = (byte)((int)x * 127);
		byte y1 = (byte)((int)y * 127);
		byte z1 = (byte)((int)z * 127);
		Normal = x1 & 255 | (y1 & 255) >> 8 | (z1 & 255) << 16;
	}
	
	public void setTextureUV(float u, float v) {
		hasTexture = true;
		textureU = u;
		textureV = v;
	}

	
	//Color
	public void setColor(Color col) {
		setColor(col.R, col.G, col.B, col.A);
	}
	
	public void setColor(double r, double g, double b) {
		setColor(r, g, b, 1);
	}

	public void setColor(int r, int g, int b) {
		setColor(r, g, b, 255);
	}
	
	public void setColor(double r, double g, double b, double a) {
		setColor((int)(r*255), (int)(g*255), (int)(b*255), (int)(a*255));
	}
	
	public void setColor(int r, int g, int b, int a) {
		hasColor = true;
		r = (r>255?255:(r<0?0:r));
		g = (g>255?255:(g<0?0:g));
		b = (b>255?255:(b<0?0:b));
		a = (a>255?255:(a<0?0:a));
		
		if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
			Color = a << 24 | b << 16 | g << 8 | r;
		else
			Color = r << 24 | g << 16 | b << 8 | a;
	}
	
	public void setColor(int col) {
		hasColor = true;
		Color = col;
	}

}
