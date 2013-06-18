package com.virtuel.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.virtuel.InputHandler;
import com.virtuel.math.vec.Vec2;

public class MenuHandler {

	private List<String> lastMenus = new ArrayList<String>();
	private Map<String, Menu> Menus;
	private String currentMenu;
	
	private Theme currentTheme;
	
	public MenuHandler() {
		Menus = new HashMap<String, Menu>();
	}
	
	public void setTheme(Theme theme){
		currentTheme = theme;
	}
	
	
	public void addMenu(Menu menu) {
		addMenu(menu, menu.getName());
	}
	
	public void addMenu(Menu menu, String name) {
		Menus.put(name, menu);
		System.out.println(String.format("Menu: %s added.", name));
	}
	
	public void setCurrentMenu(String name) {
		if (name==null) {
			currentMenu = null;
			lastMenus.add(null);
			return;
		} if (Menus.containsKey(name)) {
			currentMenu = name;
			lastMenus.add(currentMenu);
		} else {
			System.out.println("Failed at setting menu " + name);
		}
	}

	public void lastMenu() {
		if (lastMenus.size()>1) {
			String menu = lastMenus.get(lastMenus.size()-2);
			currentMenu = menu;
			lastMenus.remove(lastMenus.size()-1);
		}
	}
	
	public void updateControls() {
		if (currentMenu == null) return;
		if (Menus.get(currentMenu).isHidden()) return;
		Vec2.i mousePos = new Vec2.i(InputHandler.getMouseX(), InputHandler.getMouseY());
		
		List<Control> controls = Menus.get(currentMenu).Children;
		
		for (Control control : controls) {
			if (control.getBounds().isPointInside(mousePos)){
				
				control.hover();
				control.setState(Control.STATE_HOVER);
				
				if (control instanceof IClickable) {
					if (InputHandler.isMouseButtonDown(0)) {
						((IClickable)control).down();
						control.setState(IClickable.STATE_DOWN);
					} else if (!InputHandler.isMouseButtonDown(0)){
						((IClickable)control).up();
						control.setState(Control.STATE_HOVER);
					} if (InputHandler.isMouseButtonPressed(0)) {
						((IClickable)control).clicked();
					}	
				} 
			} else {
				control.setState(Control.STATE_NONE);
			}
		} 
	}	

	
	public void drawBackground(){
		if (currentTheme != null){
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			currentTheme.drawBackground();

			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
	}
	
	public void draw() {
		if (currentMenu == null) return;
		if (Menus.get(currentMenu).isHidden()) return;
		if (currentTheme != null) {
			GL11.glEnable(GL11.GL_BLEND);
			Menu menu = Menus.get(currentMenu);
			menu.draw();
			menu.drawControls(currentTheme);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
	
		
	public int getMenuCount() {
		return Menus.size();
	}
	
	public Menu getCurrentMenu() {
		return Menus.get(currentMenu);
	}
	
	public String getCurrentMenuName() {
		return currentMenu;
	}

	public List<String> getLastMenus(){
		return lastMenus;
	}
 
}
