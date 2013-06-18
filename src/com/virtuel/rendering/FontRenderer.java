package com.virtuel.rendering;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.virtuel.math.VMath;
import com.virtuel.math.vec.Vec2;
import com.virtuel.util.Color;
import com.virtuel.util.Font;
import com.virtuel.util.IOUtil;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

/**
 * * Do not forget to call init()!
 * 
 */
public class FontRenderer {
	private static Renderer render = Renderer.getPerferedRenderer();

	private byte[] glyphWidth = new byte[65536];
	private int[] charWidth = new int[256];

	private int TextureID = -1;
	private Vec2.d pos;
	private Font font = Font.Default;
	private float fs;

	public FontRenderer() {
		pos = new Vec2.d();

		try {
			InputStream glyphWidthStream = IOUtil
					.getPathAsStream("res/glyph_sizes.bin");
			glyphWidthStream.read(glyphWidth);
		} catch (IOException var18) {
			throw new RuntimeException(var18);
		}
	}

	
	public FontRenderer(Font font) {
		this.font = font;
		pos = new Vec2.d();

		try {
			InputStream glyphWidthStream = IOUtil
					.getPathAsStream("res/glyph_sizes.bin");
			glyphWidthStream.read(glyphWidth);
		} catch (IOException var18) {
			throw new RuntimeException(var18);
		}
	}

	public FontRenderer setFont(Font font) {
		this.font = font;
		return this;
	}

	public FontRenderer setFontSize(int width, int height) {
		font.Width = width;
		font.Height = height;
		return this;
	}

	public FontRenderer setColor(Color col) {
		font.color = col;
		return this;
	}

	public FontRenderer setAutoAlign(boolean autoAlign) {
		font.AutoAlign = autoAlign;
		return this;
	}

	
	public void init() {
		if (TextureID == -1)
			TextureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, TextureID);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);

		int width = font.getBitmap().getWidth(), height = font.getBitmap() .getHeight();
		int[] rawPixels = new int[width * height];
		font.getBitmap().getRGB(0, 0, width, height, rawPixels, 0, width);
		ByteBuffer Pixels = BufferUtils.createByteBuffer(4 * width * height);

		for (int i = 0; i < rawPixels.length; i++) {
			Pixels.put((byte) (rawPixels[i] >> 16 & 0xff));
			Pixels.put((byte) (rawPixels[i] >> 8 & 0xff));
			Pixels.put((byte) (rawPixels[i] & 0xff));
			Pixels.put((byte) (rawPixels[i] >> 24 & 0xff));
		}

		Pixels.flip();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, Pixels);
		fs = .5f / (float) width;

		int i = 0, charX, charY, w, x, g, h;

		while (i < 256) {
			charX = i % 16;
			charY = i / 16;
			w = 7;

			while (true) {
				if (w >= 0) {
					x = charX * 8 + w;
					boolean f = true;
					for (g = 0; g < 8 && f; ++g) {
						h = (charY * 8 + g) * width;
						int p = rawPixels[x + h] & 0xff;
						if (p > 0) {
							f = false;
						}
					}
					if (f) {
						--w;
						continue;
					}
				}
				charWidth[i] = (i == 32 ? w = 2 : w) + 2;
				i++;
				break;
			}
		}
	}

	
	public void drawChar(double x, double y, char c) {
		boolean draw;
		int ic = (int) c;
		float cWidth = getCharLength(c);
		float cx = (ic & 15) * 16f, cy = (ic >> 4) * 16f, cw = cWidth;

		x *= font.Width;
		y *= font.Height;

		if (draw = !render.isDrawing) {
			render.beginQuads();
			glBindTexture(GL_TEXTURE_2D, TextureID);
		}
		render.setColor(font.color);
		render.addVertexWithUV(x,		   y, 				2.0, (cx) * fs, 	 (cy) * fs);
		render.addVertexWithUV(x + cWidth, y, 				2.0, (cx + cw) * fs, (cy) * fs);
		render.addVertexWithUV(x + cWidth, y + font.Width,  2.0, (cx + cw) * fs, (cy + font.Size) * fs);
		render.addVertexWithUV(x, 		   y + font.Height, 2.0, (cx) * fs, 	 (cy + font.Size) * fs);
		if (draw)
			render.draw();
	}

	private int drawChar(char c) {
		boolean draw;
		int ic = (int) c;
		float cWidth = getCharLength(c);
		float cx = (ic & 15) * 16f, cy = (ic >> 4) * 16f, cw = cWidth;

		if (draw = !render.isDrawing) {
			render.beginQuads();
			glBindTexture(GL_TEXTURE_2D, TextureID);
		}
		render.setColor(font.color);
		render.addVertexWithUV(pos.X, 		   pos.Y, 				2.0, (cx) * fs, 	 (cy) * fs);
		render.addVertexWithUV(pos.X + cWidth, pos.Y, 				2.0, (cx + cw) * fs, (cy) * fs);
		render.addVertexWithUV(pos.X + cWidth, pos.Y + font.Width,  2.0, (cx + cw) * fs, (cy + font.Size) * fs);
		render.addVertexWithUV(pos.X, 		   pos.Y + font.Height, 2.0, (cx) * fs, 	 (cy + font.Size) * fs);
		if (draw)
			render.draw();

		return (int) cWidth;
	}

	public void drawText(double x, double y, String text) {
		drawText(x, y, text, this.font);
	}

	public void drawText(double x, double y, String text, Font cfont) {
		if (text == null || font == null)
			return;
		if (text.isEmpty())
			return;

		Font f = this.font;
		this.font = cfont;

		if (font.AutoAlign) {
			x *= font.Width;
			y *= font.Height;
		}
		pos = new Vec2.d(x, y);

		render.beginQuads();
		glBindTexture(GL_TEXTURE_2D, TextureID);
		for (char c : text.toCharArray()) {
			switch (c) {
			case '\n':
				pos.Y += font.Height;
				pos.X = x;
				break;
			case '\t':
				pos.X = VMath.round((pos.X) / font.tabLength + 1)
						* font.tabLength;
				break;
			case ' ':
				pos.X += font.Size;
				break;
			default:
				pos.X += drawChar(c);
				break;
			}
		}
		render.draw();
		this.font = f;
	}

	public void drawText(double x, double y, String text, Color color) {
		if (text == null || color == null)
			return;
		if (text.isEmpty())
			return;

		if (font.AutoAlign) {
			x *= font.Width;
			y *= font.Height;
		}
		pos = new Vec2.d(x, y);

		render.beginQuads();
		render.setColor(color);
		glBindTexture(GL_TEXTURE_2D, TextureID);
		for (char c : text.toCharArray()) {
			switch (c) {
			case '\n':
				pos.Y += font.Height;
				pos.X = x;
				break;
			case '\t':
				pos.X = VMath.round((pos.X) / font.tabLength + 1)
						* font.tabLength;
				break;
			case ' ':
				pos.X += font.Size;
				break;
			default:
				pos.X += drawChar(c);
				break;
			}
		}
		render.draw();
	}

	public void drawText(double x, double y, Object obj) {
		if (obj == null) {
			drawText(x, y, "NULL");
		}
		drawText(x, y, obj.toString());
	}

	public void drawText(int x, int y, String text, boolean background) {
		if (background) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			int bx, by;
			if (font.AutoAlign) {
				bx = x * font.Width;
				by = y * font.Height;
			} else {
				bx = x;
				by = y;
			}
			pos = new Vec2.d(x, y);
			int width = calculateLength(text);
			render.beginQuads();
			render.setColor(32, 32, 32, 128);
				render.addVertex(bx,	   by, 1.0f);
				render.addVertex(bx+width, by, 1.0f);
				render.addVertex(bx+width, by+font.Height, 1.0f);
				render.addVertex(bx,	   by+font.Height, 1.0f);
			render.draw();
		}
		
		drawText(x, y, text);
		
	}

	
	public int calculateLength(String text) {
		int length = 0;
		for (char c : text.toCharArray()) {
			length += getCharLength(c);
		}
		return length;
	}

	public int getCharLength(char c) {
		switch (c) {
		case ' ':
			return font.Width;
		case '\t':
			return font.Width * font.tabLength;
		default:
			return charWidth[(int) c] * (int) (font.Width / 8);
		}
	}

	public Font getFont() {
		return font;
	}


}