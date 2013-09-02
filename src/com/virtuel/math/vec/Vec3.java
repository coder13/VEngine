package com.virtuel.math.vec;

import com.virtuel.math.VMath;

public abstract class Vec3<N> {

	public static class i extends Vec3<Integer> {

		public i() {
			super();
			X = 0;
			Y = 0;
			Z = 0;
		}
		
		public i(int x, int y, int z) {
			super(x, y, z);
			
		}

		@Override
		public Vec3.i scale(Integer a) {
			return new Vec3.i(X*a, Y*a, Z*a);
		}

		@Override
		public Vec3.i add(Vec3<Integer> vec) {
			return new Vec3.i(X-vec.X, Y-vec.Y, Z-vec.Z);
		}

		@Override
		public Vec3.i sub(Vec3<Integer> vec) {
			return new Vec3.i(X*vec.X, Y*vec.Y, Z*vec.Z);
		}

		@Override
		public Vec3.i mul(Vec3<Integer> vec) {
			return new Vec3.i(X*vec.X, Y*vec.Y, Z*vec.Z);
		}

		@Override
		public Vec3.i div(Vec3<Integer> vec) {
			return new Vec3.i(X/vec.X, Y/vec.Y, Z/vec.Z);
		}

		@Override
		public Integer dist(Vec3<Integer> vec) {
			return (int) VMath.sqrt((X-vec.X)*(X-vec.X) + (Y-vec.Y)*(Y-vec.Y) + (Z-vec.Z)*(Z-vec.Z));
		}

		@Override
		public Integer dot(Vec3<Integer> vec) {
			return X*vec.X + Y*vec.Y + Z*vec.Z;
		}

		@Override
		public Vec3.i cross(Vec3<Integer> vec) {
            return new Vec3.i(Y*vec.Z - Z*vec.Y, Z*vec.X - X*vec.Z, X*vec.Y - Y*vec.X);
		}
		
		public Vec3.i normal() {
			int mag = mag();
			return new Vec3.i(X/mag, Y/mag, Z/mag);
		}
		
		@Override
		public Integer mag() {
			return X*X + Y*Y + Z*Z;
		}
		
		@Override
		public Integer length() {
			return (int) VMath.sqrt(mag());
		}
		
		
		
		public long combine(){
			long x = (long)(X<0?(long)X&0x7fffff:X),
				 y = (long)(Y<0?(long)Y&0x7fff  :Y),
				 z = (long)(Z<0?(long)Z&0x7fffff:Z);
			return x << 48 | z << 24 | y;
		}
	
		@Override 
		public int hashCode() {
			return (int)combine();
		}

		@Override
		public Vec3.i clone() {
			return new Vec3.i(X, Y, Z);
		}
	}
	
	public static class f extends Vec3<Float> {

		public f(){
			super();
			X = 0.0f;
			Y = 0.0f;
			Z = 0.0f;
		}
		
		public f(float x, float y, float z) {
			super(x, y, z);
		}

		@Override
		public Vec3.f scale(Float a) {
			return new Vec3.f(X*a, Y*a, Z*a);
		}

		@Override
		public Vec3.f add(Vec3<Float> vec) {
			return new Vec3.f(X+vec.X, Y+vec.Y, Z+vec.Z);
		}

		@Override
		public Vec3.f sub(Vec3<Float> vec) {
			return new Vec3.f(X-vec.X, Y-vec.Y, Z-vec.Z);
		}

		@Override
		public Vec3.f mul(Vec3<Float> vec) {
			return new Vec3.f(X*vec.X, Y*vec.Y, Z*vec.Z);
		}

		@Override
		public Vec3.f div(Vec3<Float> vec) {
			return new Vec3.f(X/vec.X, Y/vec.Y, Z/vec.Z);
		}

		@Override
		public Float dist(Vec3<Float> vec) {
			return (float) VMath.sqrt((X-vec.X)*(X-vec.X) + (Y-vec.Y)*(Y-vec.Y) + (Z-vec.Z)*(Z-vec.Z));
		}

		@Override
		public Float dot(Vec3<Float> vec) {
			return X*vec.X + Y*vec.Y + Z*vec.Z;
		}

		@Override
		public Vec3.f cross(Vec3<Float> vec){
			return new Vec3.f(Y*vec.Z - Z*vec.Y, Z*vec.X - X*vec.Z, X*vec.Y - Y*vec.X);
		}
		
		@Override
		public Vec3.f normal() {
			float mag = mag();
			return new Vec3.f(X/mag, Y/mag, Z/mag);
		}
				
		@Override
		public Float mag() {
			return X*X + Y*Y + Z*Z;
		}
		
		@Override
		public Float length() {
			return (float) VMath.sqrt(mag());
		}

		@Override
		public Vec3.f clone() {
			return new Vec3.f(X, Y, Z);
		}
		
	}

	public static class d extends Vec3<Double> {

		public d(){
			super();
			X = 0.0;
			Y = 0.0;
			Z = 0.0;
		}
		
		public d(double x, double y, double z) {
			super(x, y, z);
		}

		@Override
		public Vec3.d scale(Double a) {
			return new Vec3.d(X*a, Y*a, Z*a);
		}

		@Override
		public Vec3.d add(Vec3<Double> vec) {
			return new Vec3.d(X+vec.X, Y+vec.Y, Z+vec.Z);
		}

		@Override
		public Vec3.d sub(Vec3<Double> vec) {
			return new Vec3.d(X-vec.X, Y-vec.Y, Z-vec.Z);
		}

		@Override
		public Vec3.d mul(Vec3<Double> vec) {
			return new Vec3.d(X*vec.X, Y*vec.Y, Z*vec.Z);
		}

		@Override
		public Vec3.d div(Vec3<Double> vec) {
			return new Vec3.d(X/vec.X, Y/vec.Y, Z/vec.Z);
		}

		@Override
		public Double dist(Vec3<Double> vec) {
			return (double) VMath.sqrt((X-vec.X)*(X-vec.X) + (Y-vec.Y)*(Y-vec.Y) + (Z-vec.Z)*(Z-vec.Z));
		}

		@Override
		public Double dot(Vec3<Double> vec) {
			return X*vec.X + Y*vec.Y + Z*vec.Z;
		}

		@Override
		public Vec3.d cross(Vec3<Double> vec){
			return new Vec3.d(Y*vec.Z - Z*vec.Y, Z*vec.X - X*vec.Z, X*vec.Y - Y*vec.X);
		}

		@Override
		public Vec3.d normal() {
			double mag = mag();
			return new Vec3.d(X/mag, Y/mag, Z/mag);
		}
				
		@Override
		public Double mag() {
			return X*X+Y*Y+Z*Z;
		}
		
		@Override
		public Double length() {
			return (double)VMath.sqrt(mag());
		}

		@Override
		public Vec3.d clone() {
			return new Vec3.d(X, Y, Z);
		}
		
	}
	
	
	public N X;
	public N Y;
	public N Z;
	
	public Vec3() {
	}
	
	public Vec3(N x, N y, N z){
		X = x;
		Y = y;
		Z = z;
	}
	
	public void set(N x, N y, N z){
		X = x;
		Y = y;
		Z = z;
	}
	
	public abstract Vec3<N> scale(N a);

	public abstract Vec3<N> add(Vec3<N> vec);

	public abstract Vec3<N> sub(Vec3<N> vec);
	
	public abstract Vec3<N> mul(Vec3<N> vec);
	
	public abstract Vec3<N> div(Vec3<N> vec);
	
	public abstract N dist(Vec3<N> vec);
	
	public abstract N dot(Vec3<N> vec);
	
	public abstract Vec3<N> cross(Vec3<N> vec);
	
	public abstract Vec3<N> normal();
	
	public abstract N mag();
		
	public abstract N length();

	public abstract Vec3<N> clone();
	
	
	@Override
	public int hashCode(){
		return 0;
	}

	@Override
	public String toString(){
		return String.format("[%s, %s, %s]", X, Y, Z);
	}
	
	@Override
	public boolean equals(Object obj){
		Vec3<?> vec = (obj instanceof Vec3<?> ? (Vec3<?>)obj : null);
		return (vec == null ? null : (X == vec.X && Y == vec.Y && Z == vec.Z));
	}

	
	public static Vec3.i floor(Vec3.d vec){
		return new Vec3.i((int)VMath.floor(vec.X), (int)VMath.floor(vec.Y), (int)VMath.floor(vec.Z));
	}
	
	public static Vec3.i floor(Vec3.f vec){
		return new Vec3.i((int)VMath.floor(vec.X), (int)VMath.floor(vec.Y), (int)VMath.floor(vec.Z));
	}
	
}