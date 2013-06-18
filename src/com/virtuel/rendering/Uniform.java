package com.virtuel.rendering;

import org.lwjgl.opengl.ARBShaderObjects;

import com.virtuel.math.vec.Vec2;
import com.virtuel.math.vec.Vec3;

public abstract class Uniform<T> {

	public static class f1 extends Uniform<Float> {
		public f1(int program, String name, Float data) {
			super(program, name, data);
		}

		@Override
		public void use() {
			ARBShaderObjects.glUniform1fARB(Loc, Data.floatValue());
		}
	}
	
	public static class f2 extends Uniform<Vec2.f> {
		public f2(int program, String name, Vec2.f data) {
			super(program, name, data);
		}

		@Override
		public void use() {
			ARBShaderObjects.glUniform2fARB(Loc, ((Vec2.f)Data).X, ((Vec2.f)Data).Y);
		}
	}
	
	public static class f3 extends Uniform<Vec3.f> {
		public f3(int program, String name, Vec3.f data) {
			super(program, name, data);
		}

		@Override
		public void use() {
			ARBShaderObjects.glUniform3fARB(Loc, ((Vec3.f)Data).X, ((Vec3.f)Data).Y, ((Vec3.f)Data).Z);
		}
	}
	
	public static class f4 extends Uniform<Vec3.f> {
		public f4(int program, String name, Vec3.f data) {
			super(program, name, data);
		}

		@Override
		public void use() {
			ARBShaderObjects.glUniform4fARB(Loc, ((Vec3.f)Data).X, ((Vec3.f)Data).Y, ((Vec3.f)Data).Y, 1);
		}
	}

	public static class i1 extends Uniform<Float> {
		public i1(int program, String name, Float data) {
			super(program, name, data);
		}

		@Override
		public void use() {
			ARBShaderObjects.glUniform1iARB(Loc, Data.intValue());
		}
	}
	
	public static class i2 extends Uniform<Vec2.i> {
		public i2(int program, String name, Vec2.i data) {
			super(program, name, data);
		}

		@Override
		public void use() {
			ARBShaderObjects.glUniform2iARB(Loc, ((Vec2.i)Data).X, ((Vec2.i)Data).Y);
		}
	}
	
	public static class i3 extends Uniform<Vec3.i> {
		public i3(int program, String name, Vec3.i data) {
			super(program, name, data);
		}

		@Override
		public void use() {
			ARBShaderObjects.glUniform3iARB(Loc, ((Vec3.i)Data).X, ((Vec3.i)Data).Y, ((Vec3.i)Data).Z);
		}
	}
	
	public static class i4 extends Uniform<Vec3.i> {
		public i4(int program, String name, Vec3.i data) {
			super(program, name, data);
		}

		@Override
		public void use() {
			ARBShaderObjects.glUniform4iARB(Loc, ((Vec3.i)Data).X, ((Vec3.i)Data).Y, ((Vec3.i)Data).Y, 1);
		}
	}
	
	
	protected int Program, Loc;
	protected T Data;
	
	public Uniform(int program, String name, T data) {
		Data = data;
		Program = program;
		Loc = ARBShaderObjects.glGetUniformLocationARB(program, name);
	}
	
	public void update(T data){
		Data = data;
		use();
	}
	
	public abstract void use();
	
	public int getLocation(){
		return Loc;
	}
	
}
