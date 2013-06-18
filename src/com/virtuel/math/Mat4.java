package com.virtuel.math;

public class Mat4 {
	
	private double[] matrix;
	
	public Mat4(double[] mat) {
		matrix = mat;
	}
	
	public Mat4(double x0, double y0, double z0, double w0, 
				double x1, double y1, double z1, double w1, 
				double x2, double y2, double z2, double w2, 
				double x3, double y3, double z3, double w3) {
		matrix = new double[] { x0, y0, z0, w0, 
				   				x1, y1, z1, w1, 
				   				x2, y2, z2, w2, 
				   				x3, y3, z3, w3 };
				 
	}
	
}
