package com.virtuel.IO;

import java.io.IOException;

public abstract class Asset {

	protected String Path, Name;
	protected AssetType Type;

	public Asset(String path) {
		Path = path;
	}

	public Asset(String path, AssetType type) {
		Path = path;
		Type = type;
	}
	
	
	public void determineAssetType(){
		String prefix = Path.split("/")[Path.split("/").length-1].split(".")[1];
		Type = AssetType.getTypeFromPrefix(prefix);
	}
	
	public void determineName(){
		Name = Path.split("/")[Path.split("/").length-1].split(".")[0];
	}

	public abstract boolean load() throws IOException;

	public abstract void save() throws IOException;

	
	public String getPath() {
		return Path;
	}

	public String getName() {
		return Name;
	}

	public AssetType getType() {
		return Type;
	}

}
