package com.virtuel.IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextFile extends Asset {

	public String contents;
	
	public TextFile(String path) {
		super(path);
	}

	@Override
	public boolean load() throws IOException {
		File file = new File(Path);
		FileReader reader = new FileReader(file);
		try {
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			contents = new String(chars);
		} finally {
			reader.close();
		}
		return false;
	}

	@Override
	public void save() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(Path));
		try {
			writer.write(contents);
		} finally {
			writer.close();
		}
	}
	

}
