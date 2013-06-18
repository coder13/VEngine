package com.virtuel.IO;

import java.awt.image.BufferedImage;

public class ImageFile extends Asset {

	private BufferedImage contents;

	public ImageFile(String path) {
		super(path);
	}

	@Override
	public boolean load() {
		return false;

	}

	@Override
	public void save() {

	}

}
