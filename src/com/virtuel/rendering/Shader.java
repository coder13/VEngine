package com.virtuel.rendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL20;

import com.virtuel.IO.IOUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

@SuppressWarnings("rawtypes")
public class Shader {
	public static final int VertexShader = GL20.GL_VERTEX_SHADER;
	public static final int FragmentShader = GL20.GL_FRAGMENT_SHADER;
	
	private int program = -1;
	private List<Uniform> Uniforms;
	
	private Shader() {
		program = glCreateProgram();
		Uniforms = new ArrayList<Uniform>();
	}
	
	/** Creates a shader based off of the given vert and frag shaders. */
	private Shader(int vert, int frag) {
		program = glCreateProgram();
		linkVertexShader(vert);
		linkFragmentShader(frag);
		build();
		Uniforms = new ArrayList<Uniform>();
	}
	
	/** Links the given vertex shader to the program. */
	public Shader linkVertexShader(int vert) {
		glAttachShader(program, vert);
		glDeleteShader(vert);
		return this;
	}

	/** Links the given fragment shader to the program. */
	public Shader linkFragmentShader(int frag) {
		glAttachShader(program, frag);
		glDeleteShader(frag);
		return this;
	}
	
	public Shader uniform(Uniform uniform){
		Uniforms.add(uniform);
		return this;
	}
	
	/** Builds the shader. */
	public Shader build() {
		glLinkProgram(program);
		if (glGetProgram(program, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Shader program wasn't linked correctly. log:");
            System.err.println(glGetProgramInfoLog(program, 1024));
            return this;
        }
		
		glValidateProgram(program);
		if (glGetProgram(program, GL_VALIDATE_STATUS) == GL_FALSE) {
			return this;
		}
		
		for (Uniform uniform : Uniforms){
			uniform.use();
		}
		
		return this;
	}
	
	/** Enables the shader when in use. */
	public void use() {
		if (program != -1)
			glUseProgram(program);
	}

	/** Returns the shader. */
	public int get() {
		return program;
	}
	
	/** Deletes the shader. */
	public void delete() {
		if (program != -1)
			glDeleteProgram(program);
	}
	
	
	/** sets the current program to the given shader. */
	public static void use(int shader) {
		glUseProgram(shader);
	}
	
	/** Sets the current program to 0. */
	public static void clear() {
		glUseProgram(-1);
	}
	
	/** Deletes the given shader. */
	public static void delete(int shader) {
		glDeleteShader(shader);
	}
	
	/** Creates a shader based on the content in the given files. */
	public static Shader LoadFromFile(String vertPath, String fragPath) throws Exception {
		String vertSource = IOUtil.getFileAsString(vertPath);
		String fragSource = IOUtil.getFileAsString(fragPath);
		return LoadFromSource(vertSource, fragSource);
	}
	
	/** Creates a shader based on the given fragment and vertex source. */
	public static Shader LoadFromSource(String vert, String frag) {
		Shader shader = new Shader();
		try {
			int vertID = createShader(vert, GL_VERTEX_SHADER);
			int fragID = createShader(frag, GL_FRAGMENT_SHADER);
			return shader.linkVertexShader(vertID).linkFragmentShader(fragID).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shader;
	}
	
	/** Creates a Shader with the given source and type; */
	public static int createShader(String source, int shaderType) throws Exception {
		int shader = -1;
		try {
			shader = glCreateShader(shaderType);
			if (shader == -1) return -1;
			
			glShaderSource(shader, source);
			glCompileShader(shader);
			
			if (glGetShader(shader, GL_COMPILE_STATUS) == GL_FALSE) {
				System.err.println((shaderType == VertexShader ? "Vertex" : "Fragment") + " shader wasn't able to be compiled correctly. Error log:");
				System.err.println(glGetShaderInfoLog(shader, 1024));
				return -1;
			}
			
			return shader;
		} catch (Exception ex) {
			glDeleteShader(shader);
			throw ex;
		}
	}

}
