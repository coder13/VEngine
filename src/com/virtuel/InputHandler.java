package com.virtuel;


import java.util.ArrayList;
import java.util.List;

import com.virtuel.math.vec.Vec2;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputHandler {

	public static boolean DEBUG = false;
	
	public static boolean FlipX = false, FlipY = true;
	
	private static byte[] keys;
	private static byte[] buttons;
	private static Vec2.i mouseClickPos;

	private static List<Character> lastKeys;
	
	public static void init() throws LWJGLException{
		Keyboard.create();
		Mouse.create();
		
		keys = new byte[Keyboard.KEYBOARD_SIZE];
		buttons = new byte[Mouse.getButtonCount()];
		lastKeys = new ArrayList<Character>();
	}
	
	public static void pollInput(){
		if (lastKeys.size()!=0) {
			lastKeys.clear();
		}
				
		int eventKey, eventButton; 
		char eventChar;
		boolean keyState, buttonState;
		
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] == 2)
				keys[i] = 0;
		}
		
		while (Keyboard.next()){
			eventKey = Keyboard.getEventKey();
			keyState = Keyboard.getEventKeyState();
			eventChar = Keyboard.getEventCharacter();
			if (eventKey >= 0 && eventKey < keys.length){
				
				
				if (keyState) {
					keys[eventKey]++;
					lastKeys.add(eventChar);
					if (DEBUG)
						System.out.println(eventChar + " : " + (int)eventChar);
				} else if (!keyState && keys[eventKey]==1){
					keys[eventKey] = 2;
				}
				
				if (DEBUG) {
//					System.out.println(Keyboard.getKeyName(eventKey) + " " + eventKey + ": " + (keyState ? "down" : "up") + " " + keys[eventKey]);
//				
//					if (keys[eventKey] == 2)
//						System.out.println(Keyboard.getKeyName(eventKey) + ": Pressed");
				}
				
			}
		}
		
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i] == 2)
				buttons[i] = 0;
		}
		
		while (Mouse.next()){
			eventButton = Mouse.getEventButton();
			buttonState = Mouse.getEventButtonState();
			if (eventButton >= 0 && eventButton < buttons.length){
				if (DEBUG) {
					System.out.println(Mouse.getButtonName(eventButton) + ": " + (buttonState?"down":"up"));
					System.out.println(Mouse.getButtonName(eventButton) + ": " + buttons[eventButton]);
				}
					
				if (buttonState) {
					buttons[eventButton]++;
				} else if (!buttonState && buttons[eventButton]==1){
					buttons[eventButton] = 2;
				}
				

				if (buttons[eventButton]== 2 && DEBUG) {
					System.out.println(Mouse.getButtonName(eventButton) + ": Pressed");
				}
				
			}
		}
		
	}
	
	
	public static boolean isKeyDown(int id){
		return Keyboard.isKeyDown(id);
	}
	
	public static boolean isKeyPressed(int id){
		if (id < 0 || id >= keys.length) return false;
		return keys[id]==2;
	}

	public static boolean isMouseButtonDown(int id){
		return Mouse.isButtonDown(id);
	}
	
	public static boolean isMouseButtonPressed(int id){
		if (id < 0 || id >= buttons.length) return false;
		return buttons[id]==2;
	}

	
	public static char[] getKeysPressed() {
		if (lastKeys.size()==0) {
			return new char[] {};
		}
		char[] keys = new char[lastKeys.size()];
		for (int i = 0; i < lastKeys.size(); i++) {
			keys[i] = lastKeys.get(i);
		}
		
		return keys;
	}
	
	public static Vec2.i getMousePosition(){
		return new Vec2.i(Mouse.getX(), Mouse.getY());
	}


	public static Vec2.i getMouseClickedPosition(){
		return mouseClickPos;
	}
	

	public static int getMouseX(){
		if (FlipX)
			return Engine.Instance.window.getWidth()-Mouse.getX();
		return Mouse.getX();
	}
	
	
	public static int getMouseY(){
		if (FlipY)
			return Engine.Instance.window.getHeight()-Mouse.getY();
		return Mouse.getY();
	}
	
}
