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

		@Override
		public Vec2.i dist(Vec2<Integer> point) {
			return new Vec2.i((X+Width)-point.X, (Y+Height)-point.Y);
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

		@Override
		public Vec2.d dist(Vec2<Double> point) {
			return new Vec2.d((X+Width)-point.X, (Y+Height)-point.Y);
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

		@Override
		public Vec2.f dist(Vec2<Float> point) {
			return new Vec2.f((X+Width)-point.X, (Y+Height)-point.Y);
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
	
	public abstract Vec2<t> dist(Vec2<t> point);
	
	
	@Override
	public String toString(){
		return "[" + X.toString() + "," + Y.toString() + " | " + Width + "/" + Height + "]";
	}

	@Override
	public boolean equals(Object obj) {
		Rect<?> rect = (obj instanceof Rect<?> ? (Rect<?>) obj : null);
		return (rect == null ? null : (X == rect.X && Y == rect.Y && Width == rect.Width && Height == rect.Height));
	}
}
