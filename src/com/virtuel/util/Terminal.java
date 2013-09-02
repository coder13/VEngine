package com.virtuel.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.virtuel.InputHandler;
import com.virtuel.Time;
import com.virtuel.IO.IOUtil;
import com.virtuel.gui.Control;
import com.virtuel.math.VMath;
import com.virtuel.math.vec.Vec2;
import com.virtuel.rendering.Font;
import com.virtuel.rendering.FontRenderer;
import com.virtuel.rendering.Tessellator;
import com.virtuel.util.commands.Commands;

/** Minecraft-like terminal. */
public class Terminal extends Control {

	public static Terminal Instance;
	
	public class TerminalLine {
		
		private String Text;
		private int time = 4000;
		
		/** @param text- the text for the terminal line. */
		public TerminalLine(String text) {
			Text = text;
		}

		/** @param text- the text for the terminal line
		 *  @param time- the time (int milliseconds) left till the terminalLine doesn't get shown. */
		public TerminalLine(String text, int time) {
			Text = text;
			this.time = time;
		}
		
		public void update() {
			this.time-=Time.getDelta();
		}
		

		public void update(int delta) {
			time-=delta;
		}
		
		
		public boolean shouldShow() {
			return time>0;
		}
		
		public String getText() {
			return Text;
		}
		
	}
	
	private static String AllowedChars;
	
	private static Font TermFont = new Font(18, 18, 16, new Color(192, 192, 192, 256), false, Font.FontImage);
	
	private List<TerminalLine> Lines;
	private String currentLine = "";
	private FontRenderer fontRender;
	private boolean isTyping = false;
	
	public Terminal(Vec2.i pos, Vec2.i size, String name) {
		super(pos, size, name);
		Lines = new ArrayList<TerminalLine>();
		Instance = this;
	}
	
	public void init() {
		fontRender = new FontRenderer(TermFont);
		fontRender.init();
		
		String chars = "";

        try {
            BufferedReader var1 = new BufferedReader(new InputStreamReader(IOUtil.getPathAsStream("res/font.txt"), "UTF-8"));
            String var2 = "";

            while ((var2 = var1.readLine()) != null)
            {
                if (!var2.startsWith("#"))
                {
                	chars += var2;
                }
            }

            var1.close();
        } catch (Exception var3) { }
        AllowedChars = chars;
	}
	
	
	public Terminal setFont(Font font) {
		fontRender.setFont(font);
		return this;
	}

	
	public void update() {
		for (TerminalLine termLine : Lines) {
			if (termLine.shouldShow()) {
				termLine.update();
			}
		}
		if (isTyping) {
			char[] keys = InputHandler.getKeysPressed();
			for (char key : keys) {
				if (AllowedChars.indexOf(key) > -1 || Character.isSpaceChar(key)) {
					currentLine += key;
				}
			}
			
			if (InputHandler.isKeyPressed(Keys.KEY_BACKSPACE)) {
				if (currentLine.length() > 0)
					currentLine = currentLine.substring(0, currentLine.length() - 1);
			} if (InputHandler.isKeyPressed(Keys.KEY_ESCAPE) && isTyping) {
				isTyping = false;
				currentLine = "";
			} else if (InputHandler.isKeyPressed(Keys.KEY_ENTER) && isTyping) {
				process(currentLine);
				currentLine = "";
				isTyping = false;
			}
			
			return;
		}
		if (InputHandler.isKeyPressed(Keys.KEY_T)) {
			isTyping = true;
		}
	}
	
	public void process(String command) {
		process(command, true);
	}

	public void process(String command, boolean isCommand) {
		if (command.startsWith("/") && isCommand) {
			command = command.substring(1); 				// Removes the first character being the forward slash /.
			String[] comAndArgs = command.split(" "); 		// Splits the command into a list of words. The first element being the name or alias of the command.

			if (comAndArgs.length > 1) {
				Object[] args = new Object[comAndArgs.length-1];
				for (int i = 1; i < args.length; i++) {
					args[i-1] = comAndArgs[i];
				}
				Commands.runCommand(comAndArgs[0], args);
			} else {
				Commands.runCommand(comAndArgs[0], new Object[0]);
			}
		} else {
			Lines.add(new TerminalLine(command));
		}
	}
	
	
	public void draw() {
		Tessellator tess = Tessellator.Instance;
		double t = TermFont.Height*1.5;
		
		int availableLines = 0; 
		for (int i = 0; i < Lines.size(); i++){
			if (Lines.get(i).shouldShow()) availableLines++;
		}
		
		double height = VMath.min(Size.Y, availableLines*t);
		
		TerminalLine[] lineBuffer = new TerminalLine[(int)VMath.min(height/t, availableLines*t)];
		int i = 0, j = 0; 
		while (i < lineBuffer.length || j < Lines.size()) {
			if (Lines.get(j).shouldShow()) {
				lineBuffer[i] = Lines.get(j); 
				i++;
			}
			j++;
		}
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		for (int k = 0; k < lineBuffer.length; k++) {
			if (lineBuffer[k] == null) continue;
			int A  = lineBuffer[k].time < 128*16 ? lineBuffer[k].time/16 : 128;
			double width = VMath.max(fontRender.calculateLength(lineBuffer[k].getText()), Size.X);
			
			tess.beginDrawingQuads();
		
			tess.setColor(new Color(128, 128, 128, A));
			tess.addVertex(Pos.X	  , Pos.Y-(lineBuffer.length-k+1)*t  , -1);
			tess.addVertex(Pos.X+width, Pos.Y-(lineBuffer.length-k+1)*t  , -1);
			tess.addVertex(Pos.X+width, Pos.Y-(lineBuffer.length-k+1)*t+t, -1);
			tess.addVertex(Pos.X	  , Pos.Y-(lineBuffer.length-k+1)*t+t, -1);

			tess.draw();
		}
		
		if (isTyping) {
			double width = VMath.max(fontRender.calculateLength(currentLine), Size.X)+TermFont.Width/4;
			tess.beginDrawingQuads();
				tess.setColor(128, 128, 128, 128);
				tess.addVertex(Pos.X	   , Pos.Y	, -1);
				tess.addVertex(Pos.X+width , Pos.Y	, -1);
				tess.addVertex(Pos.X+width , Pos.Y-t, -1);
				tess.addVertex(Pos.X	   , Pos.Y-t, -1);
			tess.draw();
		}
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		tess.beginDrawingQuads();
		
		for (int k = 0; k < lineBuffer.length; k++) {
			if (lineBuffer[k] == null) continue;
			int A = lineBuffer[k].time < 255*8 ? lineBuffer[k].time/8 : 255;
			Color col = TermFont.color.clone();
			col.A = A;
			fontRender.drawText(Pos.X+TermFont.Width/4, Pos.Y-(lineBuffer.length-k+1)*t + (TermFont.Height/2 - 1.5), lineBuffer[k].Text, col);

		}
		tess.draw();
		
		if (isTyping) {
			
			fontRender.drawText(Pos.X+TermFont.Width/4, Pos.Y-t + (TermFont.Height/2 - 1.5), currentLine);
		}

		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	@Override
	public void hover() {
		
	}
	
	
	public boolean isTyping() {
		return isTyping;
	}

}
