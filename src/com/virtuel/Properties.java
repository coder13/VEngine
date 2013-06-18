package com.virtuel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.virtuel.IO.Asset;

public class Properties extends Asset {

	private Map<String, String> properties;
	
	public Properties(String path) {
		super(path);
		properties = new HashMap<String, String>();
	}
	
	@Override
	public boolean load() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(Path));
		try {
			String line = reader.readLine();
			while (line != null){
				if (line.startsWith("#")) 
					continue;
				String[] property = line.trim().split("=");
				properties.put(property[0], property[1]);
				line = reader.readLine();
			}
		} finally {
			reader.close();
		}
		return true;
	}
	
	@Override 
	public void save() {
		
	}

	
	public String getString(String name){
		return properties.get(name);
	}
	
	public int getInt(String name){
		return Integer.parseInt(properties.get(name));
	}

	public long getLong(String name){
		return Long.parseLong(properties.get(name));
	}
	
	public boolean getBoolean(String name){
		return Boolean.parseBoolean(properties.get(name));
	}

	public float getFloat(String name){
		return Float.parseFloat(properties.get(name));
	}

	public double getDouble(String name){
		return Double.parseDouble(properties.get(name));
	}
	
}
