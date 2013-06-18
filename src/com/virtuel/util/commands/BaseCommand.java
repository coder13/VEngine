package com.virtuel.util.commands;

public abstract class BaseCommand {

	private String Name;
	private Object[] Args;
	private int ArgCount = 0;
	
	public BaseCommand(String name) {
		Name = name;
	}
	
	public BaseCommand(String name, int argCount) {
		Name = name;
		ArgCount = argCount;
	}
	
	public boolean run(Object[] args) {
		if (args.length < ArgCount)
			return false;
		
		Args = args;
		perform();
		return true;
	}
	
	protected abstract void perform();
	
	public int getArgCount() {
		return ArgCount;
	}
	
}
