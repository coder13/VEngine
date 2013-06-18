package com.virtuel.gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.opengl.GL11;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.virtuel.Engine;
import com.virtuel.ImageHandler;
import com.virtuel.math.Rect;
import com.virtuel.rendering.FontRenderer;
import com.virtuel.rendering.Renderer;
import com.virtuel.rendering.texturing.Texture;
import com.virtuel.rendering.texturing.TextureMap;
import com.virtuel.util.Color;
import com.virtuel.util.Font;
import com.virtuel.util.IOUtil;

public class Theme {
	
	private String Path, Name;
	private File backgroundPath, guiPath;
	private Texture Background;
	private TextureMap Gui;
	private File themeData;
	private FontRenderer fontRender;
	
	/**
	 * @param directory
	 *            - the directory the Images and Files required for the Theme.
	 * @param name
	 *            - the name of the theme to be used when searching for the
	 *            required images and files.
	 */
	public Theme(String directory) {
		Path = directory;
		themeData = new File(directory + "ThemeData.xml");
		fontRender = new FontRenderer();
	}

	public Theme setFont(Font font){
		this.fontRender.setFont(font);
		return this;
	}
	
	/** Initializes the theme. Loads the required Images and Files. */
	public boolean init() {
		System.out.println("initializing!");
		try {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = docBuilder.parse(themeData);
			Element theme = document.getDocumentElement();
			theme.normalize();
			if (theme.getNodeName() == "Theme") {
				NodeList nodes = theme.getChildNodes();

				for (int i = 0; i < nodes.getLength(); i++) {
					if (nodes.item(i).getNodeName() == "properties") {
						NodeList properties = nodes.item(i).getChildNodes();
						for (int j = 0; j < properties.getLength(); j++) {
							
							Node property = properties.item(j);
							Node value = property.getFirstChild();
							
							if (property.getNodeType() == Node.ELEMENT_NODE){
								System.out.println(property.getNodeName() +  ": " + value.getNodeValue());
								if (property.getNodeName() == "Name") {
									Name = value.getNodeValue();
								} else if (property.getNodeName() == "guiPath") {
									guiPath = new File(Path+value.getNodeValue());
								} else if (property.getNodeName() == "backgroundPath") {
									backgroundPath = new File(Path + value.getNodeValue());
								}
							}
						}
					} else if (nodes.item(i).getNodeName() == "controls") {

					}
				}
			} else {
				return false;

			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		
		fontRender.init();
		try {
			createTextures();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void createTextures() throws IOException{
		Background = Texture.load(ImageIO.read(backgroundPath));
		Gui = TextureMap.load(Texture.load(ImageIO.read(guiPath)));
		Gui.map("normal", 0, 0 , 128, 32);
		Gui.map("hover",  0, 32, 128, 32);
		Gui.map("down",   0, 64, 128, 32);
	}
	
	public void use() {
		
	}

	public void destory() {

	}

	public void drawControl(Control control) {
		if (control instanceof Button) {
			drawButton((Button) control);
		} else {
		}
	}
	
	public void drawBackground() {
		float w = Engine.Instance.getWindow().getWidth(), h = Engine.Instance.getWindow().getHeight();
		Renderer render = Renderer.getPerferedRenderer();
		Background.bind();
		render.beginQuads();
		render.setColor(Color.White);
			render.addVertexWithUV(0, 0, -1, 0, 0);
			render.addVertexWithUV(w, 0, -1, 1, 0);
			render.addVertexWithUV(w, h, -1, 1, 1);
			render.addVertexWithUV(0, h, -1, 0, 1);
		render.draw();
	}

	public void drawButton(Button button) {
		float s, t, u, v;
		Rect.f guiRect = Gui.getForGL("normal");
		if (button.getState() == Control.STATE_HOVER) {
			// Hover
			guiRect = Gui.getForGL("hover");
		} else if (button.getState() == IClickable.STATE_DOWN) {
			// Down 
			guiRect = Gui.getForGL("down");
		}

		s = guiRect.X;
		t = guiRect.Y;
		u = guiRect.Width;
		v = guiRect.Height;
		
		Rect.i rect = button.getBounds();
		Renderer render = Renderer.getPerferedRenderer();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		Gui.bind();
		
		render.beginQuads();
		render.setColor(Color.White);
			render.addVertexWithUV(rect.X			, rect.Y			, 0, s, t);
			render.addVertexWithUV(rect.X+rect.Width, rect.Y			, 0, u, t);
			render.addVertexWithUV(rect.X+rect.Width, rect.Y+rect.Height, 0, u, v);
			render.addVertexWithUV(rect.X			, rect.Y+rect.Height, 0, s, v);
		render.draw();
		
		fontRender.drawText(rect.X + rect.Width / 2 - fontRender.calculateLength(button.getDisplayName()) / 2, 
							rect.Y + rect.Height/ 2 - fontRender.getFont().Size / 2, 
								button.getDisplayName(), Color.Red);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

}
