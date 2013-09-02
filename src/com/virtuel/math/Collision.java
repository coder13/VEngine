package com.virtuel.math;

import static com.virtuel.math.VMath.*;

import com.virtuel.math.vec.Vec2;
import com.virtuel.util.Pair;

public class Collision {

	public static boolean intersects(Pair<Vec2.d, Double> circle, Rect.d rect) {
		Vec2.d circleDistance = new Vec2.d(abs(circle.A.X - rect.X), abs(circle.A.X - rect.Y));

	    if (circleDistance.X > (rect.Width/2 + circle.B) || circleDistance.Y > (rect.Height/2 + circle.B))  
	    	return false; 
	    
	    if (circleDistance.X <= (rect.Width/2) || circleDistance.Y <= (rect.Height/2))  
	    	return true; 

	    double cornerDistance_sq = (circleDistance.X - rect.Width/2)*(circleDistance.X - rect.Width/2) +
	                         		(circleDistance.Y - rect.Height/2)*(circleDistance.Y - rect.Height/2);

	    return (cornerDistance_sq <= (circle.B*circle.B));
	}
	
	public static boolean intersects(Pair<Vec2.d, Double> circle1, Pair<Vec2.d, Double> circle2) {
		System.out.println(dist(circle1.A, circle2.A));
		return dist(circle1.A, circle2.A)<=circle1.B+circle2.B;
	}
	
	public static boolean intersects(Rect.f A, Rect.f B){
		return (B.X < A.X + A.Width) && (B.Y < A.Y+A.Height);
	}
	
}
