package com.virtuel.util;

public class Color {

	public static final Color Red 		= new Color(255, 0  , 0  , 255); 
	public static final Color Green 	= new Color(0  , 255, 0  , 255);
	public static final Color Blue		= new Color(0  , 0  , 255, 255);
	public static final Color White		= new Color(255, 255, 255, 255);
	public static final Color Black		= new Color(0  , 0  , 0  , 255);
	public static final Color Grey		= new Color(128, 128, 128, 255);
	
	public Color clone() {
		return new Color(R, G, B, A);
	}
	
	public int R, G, B, A;
	
	public Color(int r, int g, int b) {
		R = r;
		G = g;
		B = b;
		A = 255;
	}
	
	public Color(int r, int g, int b, int a) {
		R = r;
		G = g;
		B = b;
		A = a;
	}
	
	/** s is for shade value. Sets r,g, and b to the s value to create grey shades. */
	public Color(int s) {
		R = s;
		G = s;
		B = s;
		A = 255;
	}

	/** s is for shade value. Sets r,g, and b to the s value to create grey shades. */
	public Color(int s, int a) {
		R = s;
		G = s;
		B = s;
		A = a;
	}

	public void set(int r, int g, int b) {
		set(r, g, b, A);
	}

	public void set(int r, int g, int b, int a) {
		R = r;
		G = g;
		B = b;
		A = a;
	}
	
	/** Returns the R,G,B,A values to be used in openGL. */
	public double[] forGL() {
		return new double[] {(double)R/255, (double)G/255, (double)B/255, (double)A/255};
	}
	
	/** returns a new Color by adding the R,G,B values of this color to the R,G,B values of the b Color. */
	public Color add(Color b) {
		return new Color(R+b.R, G+b.G, B+b.B);
	}
	
	
	
}
