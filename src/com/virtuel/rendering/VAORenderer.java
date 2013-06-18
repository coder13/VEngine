package com.virtuel.rendering;

import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;

import com.virtuel.util.GLUtil;

public class VAORenderer extends Renderer {

	/** The vertex Count */
	private int VertexCount = 0;
	
	/** raw byte Buffer. */
	private ByteBuffer byteBuffer;
	/** raw int Buffer. */
	private IntBuffer intBuffer;
	/** raw float Buffer. */
	private FloatBuffer floatBuffer;
	
	/** The raw buffer that stores all drawing data. */
	private int[] buffer;
	/** The raw buffer size. */
	private int bufferSize;
	/** The raw buffer index. The current position of the buffer. */
	private int bufferIndex = 0;
	


	/* Raw Buffer 8 elements per
	 *  0: X		(float)
	 *  1: Y		(float)
	 *  2: Z		(float)
	 *  3: Color	(int)
	 * ^4: U		(float)
	 * ^5: V		(float)
	 * ^6: Normal   (Vec3->int)
	 *  
	 */
	
	
	public VAORenderer(int size) {
		super(size);
		bufferSize   = size;
		byteBuffer   = GLUtil.createByteBuffer(size*4);
		intBuffer 	 = byteBuffer.asIntBuffer();
		floatBuffer  = byteBuffer.asFloatBuffer();
		buffer 		 = new int[size];
	}
	
	@Override
	public void addVertex(float x, float y, float z) {
		if (!isDrawing) {
			System.out.println("Shouldn't add vertex! Not drawing!");
			return;
		}
		VertexCount++;
		
		buffer[bufferIndex + 0] = Float.floatToRawIntBits(x + TransX);
		buffer[bufferIndex + 1] = Float.floatToRawIntBits(y + TransY);
		buffer[bufferIndex + 2] = Float.floatToRawIntBits(z + TransZ);
		
		buffer[bufferIndex + 3] = Color;
		
		if (isTextured) {
			buffer[bufferIndex + 4] = Float.floatToRawIntBits(textureU);
			buffer[bufferIndex + 5] = Float.floatToRawIntBits(textureV);
		}

		
		if (hasNormals) {
			buffer[bufferIndex + 6] = Normal;
		}
		
		bufferIndex += 8;
		
		if (DrawMode == GL11.GL_QUADS && VertexCount % 4 == 0 && bufferIndex >= bufferSize-32) {
			draw();
			isDrawing = true;
		}
	}

	@Override
	public void reset() {
		VertexCount = 0;
		bufferIndex = 0;
		Color = 0xFFFFFFFF;
		hasNormals = false;
		isTextured = false;
		TransX = TransY = TransZ = 0;
		intBuffer.clear();
		byteBuffer.clear();
	} 
	
	@Override
	public int draw() {
		if (!isDrawing)
			throw new IllegalStateException("Not drawing!");
		
		isDrawing = false;
		
		if (VertexCount <= 0 )
			return 0;
		
		// Prepares the buffers.
		intBuffer.clear();
		intBuffer.put(buffer, 0, bufferIndex);
		byteBuffer.position(0);
		byteBuffer.limit(bufferIndex * 4);
		
		// Vertex Data
		floatBuffer.position(0 * 1);
		glVertexPointer(3, 32, floatBuffer);
		glEnableClientState(GL_VERTEX_ARRAY);

		// Color Data 
		byteBuffer.position(3*4);
		glColorPointer(4, true, 32, byteBuffer);
		glEnableClientState(GL_COLOR_ARRAY);

		// Texture data
		if (isTextured) {
			floatBuffer.position(4 * 1);
			glTexCoordPointer(2, 32, floatBuffer);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		}
		
		// Normal data
		if (hasNormals) {
			byteBuffer.position(6 * 4);
			glNormalPointer(32, byteBuffer);
			glEnableClientState(GL_NORMAL_ARRAY);
		}
		
		// Draw the elements
		glDrawArrays(DrawMode, 0, VertexCount);
		
		// Disable
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
		if (isTextured)
			glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		if (hasNormals)
			glDisableClientState(GL_NORMAL_ARRAY);
		
		
		// Return vertices drawn and reset. 
		int vertcount = VertexCount;
		reset();
		return vertcount;
	}

}
