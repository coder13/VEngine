package com.virtuel.math;

import com.virtuel.math.VMath;

public class NoiseGen {
	
	public static double noise(int x, int y){
		int n = x+y*57;
		n = (n<<13) ^ n;
		return (1.0 - ((n * (n*n* 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0);
	}

	public static double smoothNoise(int x, int y){
		double corners = (noise(x-1,y-1) + noise(x+1,y-1) + noise(x-1,y+1) + noise (x+1,y+1))/16.0;
		double sides   = (noise(x-1,y  ) + noise(x+1,y  ) + noise(x  ,y-1) + noise (x  ,y+1))/8.0;
		double center  = noise(x,y) / 4.0;
		return corners + sides + center;
	}

	public static double interpolatedNoise(double x, double y){
		int ix = (int)x;
		double fracX = x-ix;
		int iy = (int)y;
		double fracY = y-iy;
		
		double v1 = smoothNoise(ix  , iy  ), 
				v2 = smoothNoise(ix+1, iy  ), 
				v3 = smoothNoise(ix  , iy+1),
				v4 = smoothNoise(ix+1, iy+1);
		
		double i1 = VMath.interpolate(v1, v2, fracX), 
				i2 = VMath.interpolate(v3, v4, fracX);
		
		return VMath.interpolate(i1, i2, fracY);
	}

	public static double perlinNoise(double x, double y, double persistence, double octaves){
		double total = 0;
		for (int i = 0; i < octaves; i++){
			double frequency = 2*i, amplitude = persistence * i;
			total = total + interpolatedNoise(x*frequency, y*frequency) * amplitude;
		}
		return total;
	}

}