package com.virtuel.util.commands;

import java.util.HashMap;
import java.util.Map;

import com.virtuel.util.Terminal;

public class Commands {
	private static Map<String, BaseCommand> Commands = new HashMap<String, BaseCommand>();
	
	
	public static void registerCommand(String name, BaseCommand command) {
		Commands.put(name, command);
	}
	
	public static void removeCommand(String name) {
		if (Commands.containsKey(name)) {
			Commands.remove(name);
		}
	}
	
	public static BaseCommand getCommand(String name) {
		return Commands.get(name);
	}
	
	public static void runCommand(String name, Object[] args) {
		if (isRegistered(name)) {
			BaseCommand command = getCommand(name);
			if (command.run(args) && Terminal.Instance!=null) {
				Terminal.Instance.process(String.format("Could not run command: %s!", name));
			}
		} else {
			if (Terminal.Instance!=null) {
				Terminal.Instance.process(String.format("Could not run command: %s!", name));
			}
		}
	}
	
	public static boolean isRegistered(String name) {
		return Commands.containsKey(name);
	}
	
}
