package com.virtuel.util;

/** Contains a 2D array of elements of type T. */
public class DataMap<T> {
	
	private T[] data;
	private int Width, Height;
	
	public DataMap(T[] map, int width, int height) {
		data = map;
		Width = width;
		Height = height;
	}
	
	public T get(int x, int y) {
		if (x >= 0 && y >= 0 && x < Width && y < Height) {
			return data[y * Width + x];
		} else 
			return null;
	}

	public boolean set(int x, int y, T v) {
		if (x >= 0 && y >= 0 && x < Width && y < Height) {
			data[y * Width + x] = v;
			return true;
		} else 
			return false;
	}
	
	
	public T[] getdata() {
		return data;
	}

	public int getWidth() {
		return Width;
	}

	public int getHeight() {
		return Height;
	}
	
}