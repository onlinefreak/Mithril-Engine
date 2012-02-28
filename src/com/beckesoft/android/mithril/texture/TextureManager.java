package com.beckesoft.android.mithril.texture;

import java.util.ArrayList;
import java.util.List;

import com.beckesoft.android.mithril.output.Logger;

public class TextureManager {
	
	private static TextureManager INSTANCE = null;
	private List<Texture> textures = null;
	
	private TextureManager()  {}
	
	public static TextureManager getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new TextureManager();
			
			INSTANCE.textures = new ArrayList<Texture>(0);
		}
		
		return INSTANCE;
	}
	
	public void addTexture(Texture tex)
	{
		textures.add(tex);
	}
	
	public void removeTexture(Texture tex)
	{
		textures.remove(tex);
	}
	
	public void dispose()
	{
		if (textures.size() > 0)
			Logger.w("Not all Textures were removed. Launching automatic destruction.");
		
		for (int i = textures.size() -1; i >= 0; i--)
		{
			textures.get(i).dispose();
		}
		
		//Won't be necassary, just to make sure!
		textures.clear();
		
		textures = null;
		INSTANCE = null;
	}
	
}
