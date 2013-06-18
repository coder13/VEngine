package com.virtuel.gui;

import org.lwjgl.opengl.GL11;

import com.virtuel.math.vec.Vec2;
import com.virtuel.rendering.Renderer;

import com.virtuel.util.Color;

public abstract class Button extends Control implements IClickable {

	protected Color color = Color.White;

	
	public Button(Vec2.i pos, Vec2.i size, String name) {
		super(pos, size, name, name);
	}

	public Button(Vec2.i pos, Vec2.i size, String name, String displayName) {
		super(pos, size, name, displayName);
	}

	public Button setColor(Color col) {
		color = col;
		return this;
	}

	
	@Override
	public void draw() {
		Renderer renderer = Renderer.getPerferedRenderer();
		renderer.beginQuads();
		renderer.setColor(color);
			renderer.addVertex(Pos.X		 , Pos.Y		 , 0);
			renderer.addVertex(Pos.X + Size.X, Pos.Y		 , 0);
			renderer.addVertex(Pos.X + Size.X, Pos.Y + Size.Y, 0);
			renderer.addVertex(Pos.X		 , Pos.Y + Size.Y, 0);
		renderer.draw();

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		fontRender.drawText(Pos.X + Size.X / 2 - fontRender.calculateLength(DisplayName) / 2, 
							Pos.Y + Size.Y / 2 - fontRender.getFont().Size / 2, 
								DisplayName, Color.Red);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	@Override
	public void hover() {

	}

}
