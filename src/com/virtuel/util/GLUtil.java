package com.virtuel.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Collection;

import org.lwjgl.BufferUtils;

public class GLUtil {

	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}
	
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	public static DoubleBuffer createDoubleBuffer(int size) {
		return BufferUtils.createDoubleBuffer(size);
	}

	
	public static ByteBuffer createDirectByteBuffer(int size){
		return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());	
	}


	public static ByteBuffer asBuffer(byte... bytes ){
		ByteBuffer buffer = createByteBuffer(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		return buffer;
	}
	
	public static ShortBuffer asBuffer(short... shorts ){
		ShortBuffer buffer = createByteBuffer(shorts.length).asShortBuffer();
		buffer.put(shorts);
		buffer.flip();
		return buffer;
	}

	public static IntBuffer asBuffer(int... ints ){
		IntBuffer buffer = createByteBuffer(ints.length).asIntBuffer();
		buffer.put(ints);
		buffer.flip();
		return buffer;
	}
	
	public static IntBuffer asBuffer(Collection<Integer> ints) {
		IntBuffer buffer = createIntBuffer(ints.size());
		for (int i : ints) {
			buffer.put(i);
		}
//		buffer.flip();
		return buffer;
	}
	
	public static FloatBuffer asBuffer(float... floats ){
		FloatBuffer buffer = createByteBuffer(floats.length*4).asFloatBuffer();
		buffer.put(floats);
		buffer.flip();
		return buffer;
	}

	public static DoubleBuffer asBuffer(double... doubles ){
		DoubleBuffer buffer = createByteBuffer(doubles.length*8).asDoubleBuffer();
		buffer.put(doubles);
		buffer.flip();
		return buffer;
	}

}
