package com.virtuel;

import static org.lwjgl.opengl.GL11.*;
import static com.virtuel.math.VMath.*;

import java.nio.FloatBuffer;


import com.virtuel.math.vec.Vec2;
import com.virtuel.math.vec.Vec3;
import com.virtuel.util.GLUtil;
import com.virtuel.util.Keys;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.glu.GLU;

public abstract class Camera {
	
	/** Gui camera. Used for Guis. */
	public static class GuiCamera extends Camera {

		private float Width, Height;
		
		public GuiCamera(int width, int height) {
			Width = width;
			Height = height;
		}	

		@Override
		public void create() {
			glOrtho(0, Width, Height, 0, -2, 2);
		}

		@Override
		protected void updateInput(double delta) {
			
		}
		
	}
	
	/** Orthographic camera that extends the abstract Camera class. <br>
	 *  Establishes an Orthographic projection matrix with a given left, right, bottom, top, near and far. <br>
	 *  Takes in W, A, S, D, TAB, and LSHIFT for keys in which translate the cameras location depending on which keys were pressed. <br> 
	 *  	W 		- forward <br>
	 *  	S 		- backward <br>
	 *  	A 		- left <br>
	 *  	D 		- right <br>
	 *  	TAB 	- speed up <br>
	 *  	LSHIFT 	- slow down <br>
	 */
	public static class OrthoCamera extends Camera{

		private double Left = -1, Right = 1, Bottom = -1, Top = 1, Near = -1, Far = 1;
		
		public OrthoCamera ortho(double left, double right, double bottom, double top, double near, double far){
			Left 	= left;
			Right 	= right;
			Bottom 	= bottom;
			Top 	= top;
			Near 	= near;
			Far 	= far;
			return this;
		}
		
		@Override
		public void create() {
			glOrtho(Left, Right, Bottom, Top, Near, Far);
		}

		@Override
		protected void updateInput(double delta) {
			boolean keyUp 	 = InputHandler.isKeyDown(Keyboard.KEY_W);
			boolean keyDown  = InputHandler.isKeyDown(Keyboard.KEY_S);
			boolean keyLeft  = InputHandler.isKeyDown(Keyboard.KEY_A);
			boolean keyRight = InputHandler.isKeyDown(Keyboard.KEY_D);
			boolean keySpeed = InputHandler.isKeyDown(Keyboard.KEY_TAB);
			boolean keySlow  = InputHandler.isKeyDown(Keyboard.KEY_LSHIFT);
			
			double s = delta/16;
			
			double x=0, y=0, a=32;
			if ( keyUp    && !keyDown)  y =  .5/a;
			if (!keyUp 	  &&  keyDown)  y = -.5/a;
			if ( keyLeft  && !keyRight) x = -.5/a;
			if (!keyLeft  &&  keyRight) x =  .5/a;
			if ( keySpeed && !keySlow)  s *= 2;
			if (!keySpeed &&  keySlow)  s /= 4;
			translate(x*Speed*s, y*Speed*s);
		}
		
	}
	
	/** An orthographic camera that deals with a width and height variable and centers the camera.<br>
	 * 	Used for player based 2d games. The Player class should extends this. <br>
	 *  Takes in W, A, S, D, TAB, and LSHIFT for keys in which translate the cameras location depending on which keys were pressed. <br> 
	 *  	W 		- forward <br>
	 *  	S 		- backward <br>
	 *  	A 		- left <br>
	 *  	D 		- right <br>
	 *  	TAB 	- speed up <br>
	 *  	LSHIFT 	- slow down <br>
	 */
	public static class PlayerCamera extends Camera {	
		private double Width = 1, Height = 1;
		
		public PlayerCamera ortho(double width, double height){
			Width = width;
			Height = height;
			return this;
		}
		
		@Override
		public void create() {
			glOrtho(-Width/2, Width/2, Height/2, -Height/2, -1, 1);
		}

		@Override
		protected void updateInput(double delta) {
			boolean keyUp 	 = InputHandler.isKeyDown(Keys.KEY_W);
			boolean keyDown  = InputHandler.isKeyDown(Keys.KEY_S);
			boolean keyLeft  = InputHandler.isKeyDown(Keys.KEY_A);
			boolean keyRight = InputHandler.isKeyDown(Keys.KEY_D);
			boolean keySpeed = InputHandler.isKeyDown(Keys.KEY_TAB);
			boolean keySlow  = InputHandler.isKeyDown(Keys.KEY_LSHIFT);
			
			double s = (delta/16)/128;

			Vec2.d vel = new Vec2.d();
			if ( keyUp    && !keyDown)  vel.Y =  1.0;
			if (!keyUp 	  &&  keyDown)  vel.Y = -1.0;
			if ( keyLeft  && !keyRight) vel.X = -1.0;
			if (!keyLeft  &&  keyRight) vel.X =  1.0;
			if ( keySpeed && !keySlow)  s *=  2;
			if (!keySpeed &&  keySlow)  s /=  2;
			vel.scale(Speed*s);
			translate(vel.X, vel.Y);
		}

	}

	/** Frustum camera that extends the abstract Camera class. <br>
	 *  Establishes a Frustum projection matrix with a given left, right, bottom, top, near and far. <br>
	 *  Takes in W, A, S, D, TAB, and LSHIFT for keys in which translate the cameras location depending on which keys were pressed. <br> 
	 *  	W 		- forward <br>
	 *  	S 		- backward <br>
	 *  	A 		- left <br>
	 *  	D 		- right <br>
	 *  	TAB 	- speed up <br>
	 *  	LSHIFT 	- slow down <br>
	 */
	public static  class FrustumCamera extends Camera{

		double Left = -1, Right = 1, Bottom = -1, Top = 1, Near = -1, Far = 1;
		
		public FrustumCamera frustum(double left, double right, double bottom, double top, double near, double far){
			Left 	= left;
			Right 	= right;
			Bottom 	= bottom;
			Top 	= top;
			Near 	= near;
			Far 	= far;
			return this;
		}
		
		@Override
		public void create() {
			glFrustum(Left, Right, Bottom, Top, Near, Far);
		}

		@Override
		protected void updateInput(double delta) {
			boolean keyUp 	 = InputHandler.isKeyDown(Keyboard.KEY_W);
			boolean keyDown  = InputHandler.isKeyDown(Keyboard.KEY_S);
			boolean keyLeft  = InputHandler.isKeyDown(Keyboard.KEY_A);
			boolean keyRight = InputHandler.isKeyDown(Keyboard.KEY_D);
			boolean keySpeed = InputHandler.isKeyDown(Keyboard.KEY_TAB);
			boolean keySlow  = InputHandler.isKeyDown(Keyboard.KEY_LSHIFT);
			
			double x=0, y=0, a=32, s = delta/16;;
			if ( keyUp    && !keyDown)  y =  .5/a;
			if (!keyUp 	  &&  keyDown)  y = -.5/a;
			if ( keyLeft  && !keyRight) x = -.5/a;
			if (!keyLeft  &&  keyRight) x =  .5/a;
			if ( keySpeed && !keySlow)  s *= 2;
			if (!keySpeed &&  keySlow)  s /= 4;
			translate(x*Speed*s, y*Speed*s);
		}
		
	}
	 
	/** Perspective camera that extends the abstract Camera class. <br>
	 *  Establishes an perspective projection matrix with a given fov, aspectRatio, near and far. <br>
	 */
	public static class PerspectiveCamera extends Camera{

		/** The speed of the mouse. */
		protected int mouseSpeed = 2;
		/** The max rotation value to look up. */
		protected double maxLookUp = 90;
		/** The max rotation value to look down. */
		protected double maxLookDown = -90;
		/** The Field of view. */
		protected float Fov = 70;
		/** The aspectRatio - Width/Height.*/
		protected float AspectRatio = 1;
		/** The near value. */
		protected float Near = 0.1f;
		/** the far value*/
		protected float Far = 10f;
		
		
		public PerspectiveCamera perspect(float fov, float aspectRatio, float near, float far){
			this.Fov = fov;
			this.AspectRatio = aspectRatio;
			this.Near = near;
			this.Far = far;
			return this;
		}
		
		@Override
		public void create() {
			GLU.gluPerspective(Fov, AspectRatio, Near, Far);
		}

		@Override
		protected void updateInput(double delta) {
			if (InputHandler.isMouseButtonPressed(0)) Mouse.setGrabbed(true);
			if (InputHandler.isMouseButtonPressed(1)) Mouse.setGrabbed(false);
			if (Mouse.isGrabbed()) {
				double pitch = getPitch(), yaw = getYaw();
				double mouseDX = Mouse.getDX() * mouseSpeed * 0.125;
	            double mouseDY = Mouse.getDY() * mouseSpeed * 0.125;
	            
	            if (getPitch() - mouseDY >= maxLookDown && getPitch() - mouseDY <= maxLookUp)
	                pitch += -mouseDY;
	            else if (getPitch() - mouseDY < maxLookDown)
	                pitch = maxLookDown;
	            else if (getPitch() - mouseDY > maxLookUp)
	                pitch = maxLookUp;

	            if (getYaw() + mouseDX >= 360)
	                yaw += mouseDX - 360;
	            else if (getYaw() + mouseDX < 0)
	                yaw = 360 - Rotation.Y + mouseDX;
	            else  
	            	yaw += mouseDX;
	            
	            
				setRotation(pitch, yaw, 0);
	        }
			
			
		 	boolean keyUp      = InputHandler.isKeyDown(Keyboard.KEY_W),
	       			keyDown    = InputHandler.isKeyDown(Keyboard.KEY_S),
	       			keyLeft    = InputHandler.isKeyDown(Keyboard.KEY_A),
	       			keyRight   = InputHandler.isKeyDown(Keyboard.KEY_D),
	       			flyUp      = InputHandler.isKeyDown(Keyboard.KEY_Q),
	       			flyDown    = InputHandler.isKeyDown(Keyboard.KEY_Z),
	       			moveFaster = InputHandler.isKeyDown(Keyboard.KEY_TAB),
	       			moveSlower = InputHandler.isKeyDown(Keyboard.KEY_LSHIFT);
	        
	        double sds = (delta) * Speed;
	        if (moveFaster && !moveSlower) sds *= 2;
	        else if (!moveFaster && moveSlower) sds /= 4;
	
	        Vec3.d vel = new Vec3.d();
	        double angle = getYaw();
	          if (keyUp && keyRight && !keyLeft && !keyDown) {
	            vel.Z -= cos(angle + 45);
	            vel.X += sin(angle + 45);
	        } if (keyUp && keyLeft && !keyRight && !keyDown) {
	            vel.Z -= cos(angle - 45);
	            vel.X += sin(angle - 45);
	        } if (keyUp && !keyLeft && !keyRight && !keyDown) {
	            vel.Z -= cos(angle);
	            vel.X += sin(angle);
	        } if (keyDown && keyLeft && !keyRight && !keyUp) {
	            vel.Z -= cos(angle - 135);
	            vel.X += sin(angle - 135);
	        } if (keyDown && keyRight && !keyLeft && !keyUp) {
	            vel.Z -= cos(angle + 135);
	            vel.X += sin(angle + 135);
	        } if (keyDown && !keyUp && !keyLeft && !keyRight) {
	            vel.Z -= -cos(angle);
	            vel.X += -sin(angle);
	        } if (keyLeft && !keyRight && !keyUp && !keyDown) {
	            vel.Z -= cos(angle - 90);
	            vel.X += sin(angle - 90);
	        } if (keyRight && !keyLeft && !keyUp && !keyDown) {
	            vel.Z -= cos(angle + 90);
	            vel.X += sin(angle + 90);
	        } if (flyUp && !flyDown) {
	            vel.Y++;
	        } else if (flyDown && !flyUp) {
	            vel.Y--;
	        }
	        translate(vel.X * sds, vel.Y*sds, vel.Z * sds);
		}
		
	}
	
	
	/** The position of the camera. */
	protected Vec3.d Position;
	/** The rotation of the camera. */
	protected Vec3.d Rotation;
	/** The speed of the camera. Can be used to scale the velocity. */
	protected double Speed = 1;
	
	protected FloatBuffer matrix;
	
	
	/** Main constructor. */
	public Camera(){
		Position = new Vec3.d();
		Rotation = new Vec3.d();
	}
	
	
	/** Sets the position of the camera and returns itself. */
	public Camera setPos(Vec3.d pos){
		this.Position = pos;
		return this;
	}

	/** Sets the position of the camera and returns itself. */
	public Camera setPos(double x, double y){
		return this.setPos(x, y, getZ());
	}
	
	/** Sets the position of the camera and returns itself. */
	public Camera setPos(double x, double y, double z){
		Position.set(x, y, z);
		return this;	
	}

	public Camera setX(double x){
		Position.X = x;
		return this;
	}
	
	public Camera setY(double y){
		Position.Y = y;
		return this;
	}
	
	public Camera setZ(double z){
		Position.Z = z;
		return this;
	}
	
	/** Sets the rotation of the camera and returns itself. */
	public Camera setRot(Vec3.d rot){
		this.Rotation = rot;
		return this;
	}

	/** Sets the rotation of the camera and returns itself. */
	public Camera setRot(double pitch, double Yaw, double roll){
		return this.setRot(new Vec3.d(pitch, Yaw, roll));
	}

	/** Sets the speed of the camera and returns itself. */
	public Camera setSpeed(double speed){
		this.Speed = speed;
		return this;
	}

	/** Creates the projection matrix. Must be called. */
	public Camera setup() {
		matrix = GLUtil.createFloatBuffer(16);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		create();
		glMatrixMode(GL_MODELVIEW);
		glGetFloat(GL_PROJECTION_MATRIX, matrix);
		return this;
	}

	
	/** Defines the projection matrix for the camera. */
	protected abstract void create();

	
	/** Loads the projection matrix and switches back into the modelView matrix. */
	public void use() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glLoadMatrix(matrix);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	/** Updates the camera. Calls the public method updateInput. 
	 * @param delta - the delta value. */
	public void update(double delta){
		updateInput(delta);
	}
	
	/** Updates the input and may translate the position or rotation by input. */
	protected abstract void updateInput(double delta);
	
	
	/** Translates the camera with the given x, y and z coords.
	 * @param x - x value.
	 * @param y - y value.
	 * @return the distance between the current position and the new position. */		
	public double translate(double x, double y){
		return translate(x, y, 0);
	}
	
	/** Translates the camera with the given x, y and z coords.
	 * @param x - x value.
	 * @param y - y value.
	 * @param z - z value.
	 * @return the distance between the current position and the new position. */
	public double translate(double x, double y, double z){
		Vec3.d dist = new Vec3.d(x,y,z);
		Position = Position.add(dist);
		return dist.length();
	}

	/** Sets the rotation of the camera. */
	public double setRotation(double pitch, double yaw, double roll){
		Vec3.d rot = new Vec3.d(pitch, yaw, roll);
		Rotation = rot;
		return rot.length();
	}
	
	/** Applies the model view Matrix. Should be called after update. */
	public void applyModelViewMatrix(){
		glRotated(getPitch(), 1, 0, 0);
		glRotated(getYaw(),   0, 1, 0);
		glRotated(getRoll(),  0, 0, 1);
		glTranslated(-getX(), -getY(), -getZ());
	}
	
	
	/** @return the position of the camera. */
	public Vec3.d getPosition(){
		return new Vec3.d(getX(), getY(), getZ());
	}
	
	/** @return the X value. */
	public double getX(){
		return Position.X;
	}

	/** @return the Y value. */
	public double getY(){
		return Position.Y;
	}

	/** @return the Z value. */	
	public double getZ(){
		return Position.Z;
	}
	
	/** @return the rotation of the camera. */
	public Vec3.d getRotation(){
		return Rotation;
	}

	/** @return the Pitch. */
	public double getPitch(){
		return Rotation.X;
	}

	/** @return the Yaw. */
	public double getYaw() {
		return Rotation.Y;
	}

	/** @return the Roll. */
	public double getRoll() {
		return Rotation.Z;
	}

	/** */
	public FloatBuffer getMatrix() {
		return matrix;
	}
	
	/** Returns the postition and rotation. */
	@Override
	public String toString() {
		return String.format("[%s, %s]", Position, Rotation);
	}

}
