package com.virtuel.math;

import com.virtuel.math.vec.Vec2;
import com.virtuel.math.vec.Vec3;

public class VMath {
	/* --Manifest--
	*  Variables: 
	*    PI, ToDegrees, toRadians
	*    
	*  Vector Math: 
	*    dist, norm
	*    
	*  Trigonometry:
	*    sin, cos, tan, atan, atan2
	*    
	*  Fast functions: 
	*    abs, round, ceil, floor
	*    
	*  Other: 
	*    sqrt, move, min, max
	* */
	
	// Variables

	/** Not implemented yet. */
	public static boolean useDegrees = true;
	
	/** The number of times the length of the diameter of a circle can go around the circle. */
	public static final double PI = 3.141592653589;

    public static final double ToDegrees =  (180.0   / Math.PI);
    public static final double ToRadians =  (Math.PI / 180.0  );
	
	// Vector Math

	/** Returns the distance between vec2 a and vec2 b. 
	 *  @category Vector Math
	 *  @return sqrt((a.X-b.X)^2 - (a.Y-b.Y)^2) */
    public static float dist(Vec2.d a, Vec2.d b){
		return dist(a.X, a.Y, 0,  
					b.X, b.Y, 0);
	}
	
	/** Returns the distance between the first set of coords and the second set. 
	 *  @category Vector Math
	 *  @return sqrt((x0-x1)^2 - (y0-y1)^2)
	 *  */
	public static float dist(double x0, double y0, double x1, double y1){
		return dist(x0, y0, 0, 
					x1, y1, 0);
	}
	
	/** Returns the distance between vec3 a and vec3 b. 
	 *  @category Vector Math
	 *  @return sqrt((a.X-b.X)^2 - (a.Y-b.Y)^2 - (a.Z-b.Z)^2) */
	public static float dist(Vec3.d a, Vec3.d b){
		return dist(a.X, a.Y, a.Z,  
					b.X, b.Y, b.Z);	
	}

	/** Returns the distance between the first set of coords and the second set. 
	 *  @category Vector Math
	 *  @return sqrt((x0-x1)^2 - (y0-y1)^2 - (z0-z1)^2)
	 *  */	
	public static float dist(double x0, double y0, double z0, double x1, double y1, double z1){
		return sqrt((x0-x1)*(x0-x1) + 
					(y0-y1)*(y0-y1) + 
					(z0-z1)*(z0-z1));
	}
	
	
	/** Returns the vector normalized. 
	 *  @category Vector math */
	public static Vec2.d norm(Vec2.d vec){
		return norm(vec.X, vec.Y);
	}

	/** Returns the vector normalized. 
	 *  @category Vector math */
	public static Vec2.d norm(double x, double y){
		double l = sqrt(x*x+y*y);
		return new Vec2.d(x/l, y/l);
	}

	/** Returns the vector normalized. 
	 *  @category Vector math */
	public static Vec3.d norm(Vec3.d vec){
		return norm(vec.X, vec.Y, vec.Z);
	}

	/** Returns the vector normalized. 
	 *  @category Vector math */
	public static Vec3.d norm(double x, double y, double z){
		double l = sqrt(x*x+y*y+z*z);
		return new Vec3.d(x/l, y/l, z/l);
	}
		
	
	// Trigonometry
	
	/** Returns the sin for a. 
	 *  @category Trigonometry
	 *  @param a - the angle in degrees. */
	public static float sin(double a){
		return (float)Math.sin(a*ToRadians);
	}

	/** Returns the cos for a.  
	 *  @category Trigonometry
	 *  @param a - the angle in degrees. */
	public static float cos(double a){
		return (float)Math.cos(a*ToRadians);
	}

	/** Returns the sin for a.  
	 *  @category Trigonometry
	 *  @param a - the angle in degrees. */
	public static float tan(double a){
		return (float)Math.tan(a*ToRadians);
	}

	/** Returns the atan for a.  
	 *  @category Trigonometry
	 *  @param a - the angle in degrees. */
	public static float atan(double a){
		return (float)Math.atan(a*ToRadians);
	}

	/** Returns the atan2 for a and b. <br>
	 * 	This can be used to get the angle in degrees for a and b. A and b can be used for as delta x and delta y to get the angle 
	 *  between the two values. 
	 *  @category Trigonometry
	 *  @param a - the angle in degrees. */
	public static float atan2(double a, double b){
		return (float)Math.toDegrees(Math.atan2(a*ToRadians, b*ToRadians));
	}
	
	// Fast functions

	/** Returns the absolute value for a. 
	 * 	@category Fast functions */
	public static float abs(double a){
		return (float)(a<0?a*-1:a);
	}
	
	/** Returns a rounded to the nearest integer. 
	 *  @category Fast functions */
	public static int round(double a){
		return ((int)a);
	}

	/** Returns a rounded down to the nearest integer. 
	 *  @category Fast functions */
	public static int floor(double a){
		return (int)(a < 0 ? a-1 : a );
	}

	/** Returns a rounded up to the nearest integer. 
	 *  @category Fast functions */
	public static int ceil(double a){
		return (int)(a < 0 ? a+1 : a);
	}
	
	// Other

	/** Returns the square root of a. 
	 *  @category Other */
	public static float sqrt(double a){
		return (float)Math.sqrt(a);
	}

	public static Vec3.d move(Vec3.d Position, Vec3.d Rotation, double dx, double dy, double dz) {
		Position.Z += dx * cos(Rotation.Y - 90) + dz * cos(Rotation.Y);
		Position.X -= dx * sin(Rotation.Y - 90) + dz * sin(Rotation.Y);
		Position.Y += dy * sin(Rotation.X - 90) + dz * sin(Rotation.X);
		return Position;
	}
	
	public static float min(double... values){
		float rtrn = (float)values[0];
		for (int i = 1; i < values.length; i++)
			rtrn = (float)Math.min(rtrn, values[i]);
		return rtrn;
	}

	public static float max(double... values){
		float rtrn = (float)values[0];
		for (int i = 1; i < values.length; i++)
			rtrn = (float)Math.max(rtrn, values[i]);
		return rtrn;
	}
	
	public static float interpolate(double a, double b, double x){
		return (float)(a*(1-x) + b*x);
	}
	
	
}
