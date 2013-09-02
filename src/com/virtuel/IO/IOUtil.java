package com.virtuel.IO;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class IOUtil {
	
	/** 
	 * checks if the directory exists. If it doesn't, returns false and makes it. If it does, returns true.
	 * */
	public static boolean check(String directory) {
		if (!new File(directory).exists()){
			new File(directory).mkdirs();
			System.out.println("Creating directory: " + directory);
			return false;
		}
		return true;
	}
	
	public static File[] getFiles(String directory){
		return new File(directory).listFiles();
	}

	public static File[] getFiles(String directory, final String prefix) {
		return new File(directory).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				name.endsWith(prefix);
				return false;
			}
		});
	}
	
	public static String getFileAsString(String filename) throws Exception {
		StringBuilder source = new StringBuilder();
		FileInputStream in = new FileInputStream(filename);
		Exception exception = null;
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			
			Exception innerExc = null;
			try {
				String line;
				while((line = reader.readLine()) != null)
					source.append(line).append('\n');
				
			} catch(Exception exc) {
				exception = exc;
				
			} finally {
				try {
					reader.close();
					
				} catch(Exception exc) {
					if(innerExc == null)
						innerExc = exc;
					else
						exc.printStackTrace();
				}
			}
			if(innerExc != null)
				throw innerExc;
			
		} catch(Exception exc) {
			exception = exc;
			
		} finally {
			try {
				in.close();
				
			} catch(Exception exc) {
				if(exception == null)
					exception = exc;
				else
					exc.printStackTrace();
			}
			if(exception != null)
				throw exception;
		}
		return source.toString();
	}

	public static InputStream getPathAsStream(String path) throws FileNotFoundException {
		return new FileInputStream(path);
	}

	public static BufferedImage getImageFromStream(InputStream stream) throws IOException {
		BufferedImage img = ImageIO.read(stream);
		stream.close();
		return img;
	}

	public static BufferedImage getImageFromUrl(String url) {
		return null;
	}
	
}
