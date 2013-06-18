package com.virtuel.IO;

public enum AssetType {
	
	Image("image", new String[] {"png", "bmp", "jpg"}), 
	Text("text", new String[] {"txt", "xml", "yml"}), 
	Audio("audio", new String[] {"wav", "mp3", "mp4", "ogg"}), 
	Zip("zip", new String[] {"zip", "7zip"});
	
	private String Name;
	private String[] Prefixes;
	
	AssetType(String name, String[] prefixes){
		Name = name;
		Prefixes = prefixes;
	}
	
	public String getName(){
		return Name;
	}
	
	public String[] getPrefixes(){
		return Prefixes;
	}
	
	public boolean isPrefixAssociated(String prefix){
		for (String pre : Prefixes)
			if (pre == prefix)
				return true;
		return false;
	}
	
	
	public static AssetType getTypeFromPrefix(String prefix){
		for (AssetType type: AssetType.values()){
			if (type.isPrefixAssociated(prefix))
				return type;
		}
		return null;
	}
	
}
