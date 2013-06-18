package com.virtuel.math;

import com.virtuel.math.vec.Vec2;

public abstract class Rect<t> {

	public static class i extends Rect<Integer> {

		public i(int x, int y, int width, int height) {
			super(x, y, width, height);
		}

		@Override
		public boolean isPointInside(Vec2<Integer> point) {
			if ((point.X > X && point.X < X+Width) && (point.Y > Y && point.Y < Y+Height)) {
				return true;
			}
			return false;
		}

	}
	
	public static class d extends Rect<Double> {

		public d(double x, double y, double width, double height) {
			super(x, y, width, height);
		}

		@Override
		public boolean isPointInside(Vec2<Double> point) {
			if ((point.X > X && point.X < X+Width) && (point.Y > Y && point.Y < Y+Height)) {
				return true;
			}
			return false;
		}

	}

	public static class f extends Rect<Float> {
		
		public f(float x, float y, float width, float height) {
			super(x, y, width, height);
		}

		@Override
		public boolean isPointInside(Vec2<Float> point) {
			if ((point.X > X && point.X < X+Width) && (point.Y > Y && point.Y < Y+Height)) {
				return true;
			}
			return false;
		}

	}
	
	public t X,Y,Width,Height;
	
	public Rect(t x, t y, t width, t height) {
		X = x;
		Y = y;
		Width = width;
		Height = height;
	}
	
	public abstract boolean isPointInside(Vec2<t> point);
	
	@Override
	public String toString(){
		return "[" + X.toString() + "," + Y.toString() + " | " + Width + "/" + Height + "]";
	}
}
