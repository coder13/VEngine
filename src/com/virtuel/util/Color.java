package com.virtuel.util;

public class Color {

	public static final Color Red 		= new Color(255, 0  , 0  , 255); 
	public static final Color Green 	= new Color(0  , 255, 0  , 255);
	public static final Color Blue		= new Color(0  , 0  , 255, 255);
	public static final Color White		= new Color(255, 255, 255, 255);
	public static final Color Black		= new Color(0  , 0  , 0  , 255);
	public static final Color Grey		= new Color(128, 128, 128, 255);
	
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
	
	/** 
	 * Sets the R, G, B values to shade to create a grey color. 
	 * Alpha gets set to 255.
	 */
	public Color(int shade) {
		R = shade;
		G = shade;
		B = shade;
		A = 255;
	}

	/** 
	 * Sets the R, G, B values to shade to create a grey color. 
	 */
	public Color(int shade, int alpha) {
		R = shade;
		G = shade;
		B = shade;
		A = alpha;
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
	public float[] forGL() {
		return new float[] { (float)R/255f, (float)G/255f, (float)B/255f, (float)A/255f };
	}

	/** returns a new Color by adding the R,G,B values of this color to the R,G,B values of the b Color. */
	public Color add(Color b) {
		return new Color(R+b.R, G+b.G, B+b.B);
	}

	public Color clone() {
		return new Color(R, G, B, A);
	}

}
