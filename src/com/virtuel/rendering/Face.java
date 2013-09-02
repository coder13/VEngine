package com.virtuel.rendering;

import com.virtuel.math.vec.Vec3;

/** Stores an array of verticies and normals. Not i*/
public class Face {

	public Vec3.d[] Verticies;
	public Vec3.d Normal;
	
	public Face(Vec3.d[] verticies, Vec3.d normal){
		Verticies = verticies;
		Normal = normal;
	}
	
}