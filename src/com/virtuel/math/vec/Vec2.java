package com.virtuel.math.vec;

import com.virtuel.math.VMath;

public abstract class Vec2<N> {

	public static class i extends Vec2<Integer> {

		public i() {
			super(0, 0);
		}

		public i(int x, int y) {
			super(x, y);

		}

		public i(int xy) {
			super(xy);
		}

		@Override
		public Vec2.i scale(Integer a) {
			return new Vec2.i(X * a, Y * a);
		}

		@Override
		public Vec2.i add(Vec2<Integer> vec) {
			return new Vec2.i(X + vec.X, Y + vec.Y);
		}

		@Override
		public Vec2.i sub(Vec2<Integer> vec) {
			return new Vec2.i(X - vec.X, Y - vec.Y);
		}

		@Override
		public Vec2.i mul(Vec2<Integer> vec) {
			return new Vec2.i(X * vec.X, Y * vec.Y);
		}

		@Override
		public Vec2.i div(Vec2<Integer> vec) {
			return new Vec2.i(X / vec.X, Y / vec.Y);
		}

		@Override
		public Integer dist(Vec2<Integer> vec) {
			return (int) VMath.sqrt((X - vec.X) * (X - vec.X) + (Y - vec.Y)
					* (Y - vec.Y));
		}

		@Override
		public Integer dot(Vec2<Integer> vec) {
			return null;
		}

		public Vec2.i cross(Vec2<Integer> vec) {
			return new Vec2.i(Y * vec.X - X * vec.Y, Y * vec.X - X * vec.Y);
		}

		@Override
		public Vec2.i normal() {
			int mag = mag();
			return new Vec2.i(X / mag, Y / mag);
		}

		@Override
		public Integer mag() {
			return X * X + Y * Y;
		}

		@Override
		public Integer length() {
			return (int) VMath.sqrt(X * X + Y * Y);
		}

		public long combine() {
			long x = (long) (X < 0 ? (long) X & 0x7fffffff : X), y = (long) (Y < 0 ? (long) Y & 0x7fffffff
					: Y);
			return x | y << 32;
		}

	}

	public static class f extends Vec2<Float> {

		public f() {
			super(0.0f, 0.0f);
		}

		public f(float x, float y) {
			super(x, y);
		}

		public f(float xy) {
			super(xy);
		}

		@Override
		public Vec2.f scale(Float a) {
			return new Vec2.f(X * a, Y * a);
		}

		@Override
		public Vec2.f add(Vec2<Float> vec) {
			return new Vec2.f(X + vec.X, Y + vec.Y);
		}

		@Override
		public Vec2.f sub(Vec2<Float> vec) {
			return new Vec2.f(X - vec.X, Y - vec.Y);
		}

		@Override
		public Vec2.f mul(Vec2<Float> vec) {
			return new Vec2.f(X * vec.X, Y * vec.Y);
		}

		@Override
		public Vec2.f div(Vec2<Float> vec) {
			return new Vec2.f(X / vec.X, Y / vec.Y);
		}

		@Override
		public Float dist(Vec2<Float> vec) {
			return (float) VMath.sqrt((X - vec.X) * (X - vec.X) + (Y - vec.Y)
					* (Y - vec.Y));
		}

		@Override
		public Float dot(Vec2<Float> vec) {
			return null;
		}

		public Vec2.f cross(Vec2<Float> vec) {
			return new Vec2.f(Y * vec.X - X * vec.Y, Y * vec.X - X * vec.Y);
		}

		@Override
		public Vec2.f normal() {
			float mag = mag();
			return new Vec2.f(X / mag, Y / mag);
		}

		@Override
		public Float mag() {
			return X * X + Y * Y;
		}

		@Override
		public Float length() {
			return (float) VMath.sqrt(X * X + Y * Y);
		}

	}

	public static class d extends Vec2<Double> {

		public d() {
			super(0.0, 0.0);
		}

		public d(double x, double y) {
			super(x, y);
		}

		public d(double xy) {
			super(xy);
		}

		@Override
		public Vec2.d scale(Double a) {
			return new Vec2.d(X * a, Y * a);
		}

		@Override
		public Vec2.d add(Vec2<Double> vec) {
			return new Vec2.d(X + vec.X, Y + vec.Y);
		}

		@Override
		public Vec2.d sub(Vec2<Double> vec) {
			return new Vec2.d(X - vec.X, Y - vec.Y);
		}

		@Override
		public Vec2.d mul(Vec2<Double> vec) {
			return new Vec2.d(X * vec.X, Y * vec.Y);
		}

		@Override
		public Vec2.d div(Vec2<Double> vec) {
			return new Vec2.d(X / vec.X, Y / vec.Y);
		}

		@Override
		public Double dist(Vec2<Double> vec) {
			return (double) VMath.sqrt((X - vec.X) * (X - vec.X) + (Y - vec.Y)
					* (Y - vec.Y));
		}

		@Override
		public Double dot(Vec2<Double> vec) {
			return X * vec.X + Y * vec.Y;
		}

		public Vec2.d cross(Vec2<Double> vec) {
			return new Vec2.d(Y * vec.X - X * vec.Y, Y * vec.X - X * vec.Y);
		}

		@Override
		public Vec2.d normal() {
			double mag = mag();
			return new Vec2.d(X / mag, Y / mag);
		}

		public Vec2.d normalize() {
			return new Vec2.d(1.0 / X, 1.0 / Y);
		}

		@Override
		public Double mag() {
			return X * X + Y * Y;
		}

		@Override
		public Double length() {
			return (double) VMath.sqrt(X * X + Y * Y);
		}

	}

	public N X;
	public N Y;

	public Vec2() {
	}

	public Vec2(N x, N y) {
		X = x;
		Y = y;
	}

	public Vec2(N xy) {
		X = xy;
		Y = xy;
	}

	public void set(N x, N y) {
		X = x;
		Y = y;
	}

	/** Multiplies both X, Y by a. */
	public abstract Vec2<N> scale(N a);

	public abstract Vec2<N> add(Vec2<N> vec);

	public abstract Vec2<N> sub(Vec2<N> vec);

	public abstract Vec2<N> mul(Vec2<N> vec);

	public abstract Vec2<N> div(Vec2<N> vec);

	public abstract N dist(Vec2<N> vec);

	public abstract N dot(Vec2<N> vec);

	public abstract Vec2<N> cross(Vec2<N> vec);

	public abstract Vec2<N> normal();

	public abstract N mag();

	public abstract N length();

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		Vec2<?> vec = (obj instanceof Vec2<?> ? (Vec2<?>) obj : null);
		return (vec == null ? null : (X == vec.X && Y == vec.Y));
	}

	@Override
	public String toString() {
		return String.format("[%s, %s]", X, Y);
	}

	public static Vec2.i floor(Vec2.d vec) {
		return new Vec2.i((int) VMath.floor(vec.X), (int) VMath.floor(vec.Y));
	}

	public static Vec2.i floor(Vec3.f vec) {
		return new Vec2.i((int) VMath.floor(vec.X), (int) VMath.floor(vec.Y));
	}

}