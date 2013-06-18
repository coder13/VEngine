package com.virtuel.util.commands;

import com.virtuel.Engine;

public class QuitCommand extends BaseCommand {

	public QuitCommand() {
		super("quit", 0);
	}
	
	@Override
	public void perform() {
		Engine.Instance.stop();
	}

	
	
}
