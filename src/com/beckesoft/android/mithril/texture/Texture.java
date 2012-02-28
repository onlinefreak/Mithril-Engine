package com.beckesoft.android.mithril.texture;

import java.io.IOException;

import com.beckesoft.android.mithril.MithrilEngine;
import com.beckesoft.android.mithril.math.MathHelper;
import com.beckesoft.android.mithril.output.Logger;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class Texture {
	
	private int textureID;
	
	private int width, height;

	
	
	private void buildMipmap(Bitmap bitmap) 
	{

		int level = 0;
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();	      	       		

		while(height >= 1 || width >= 1 && level < 4 ) {
			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, level, bitmap, 0);
			if(height == 1 || width == 1) 
			{
				break;
			}

			level++;
			if( height > 1 )
				height /= 2;
			if( width > 1 )
				width /= 2;

			Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, width, height, true);
			bitmap.recycle();
			bitmap = bitmap2;
		}		
	}
	
	// TODO: Find best resolution for resize if not POT
	public Texture(Bitmap bmp, TextureFilter minFilter, TextureFilter magFilter, TextureWrap sWrap, TextureWrap tWrap)
	{
		if (bmp == null)
			return;
		if (!MathHelper.isPOT(bmp.getWidth()) || !MathHelper.isPOT(bmp.getHeight()))
		{
			Logger.e("Texture could not be loaded. Not a potenz of 2");
			return;
		}
        
		
        int[] texture = new int[1];
        
        // Generate new texture ID
        GLES20.glGenTextures(1, texture, 0);
        
        // Bind texture to set parameters
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture( GLES20.GL_TEXTURE_2D, texture[0] );
		
		//Set parameters
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, minFilter.getValue() );
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, magFilter.getValue() );
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, sWrap.getValue() );
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, tWrap.getValue() );
		
		//Set properties
		textureID = texture[0];
		width = bmp.getWidth();
		height = bmp.getHeight();
		
		
		// Create texture(s)
		if (minFilter == TextureFilter.MipMap || magFilter == TextureFilter.MipMap)
			buildMipmap(bmp);
		else
			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);
		
		TextureManager.getInstance().addTexture(this);
		
		bmp.recycle();
	}
	
	// TODO: Find best resolution for resize if not POT
	public Texture(String fileName, TextureFilter minFilter, TextureFilter magFilter, TextureWrap sWrap, TextureWrap tWrap)
	{
		try {
			Bitmap bmp = BitmapFactory.decodeStream(MithrilEngine.getInstance().getAssets().open(fileName));
			if (bmp == null)
				return;
			if (!MathHelper.isPOT(bmp.getWidth()) || !MathHelper.isPOT(bmp.getHeight()))
			{
				Logger.e("Texture could not be loaded. Not a potenz of 2");
				return;
			}
	        
			
	        int[] texture = new int[1];
	        
	        // Generate new texture ID
	        GLES20.glGenTextures(1, texture, 0);
	        
	        // Bind texture to set parameters
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture( GLES20.GL_TEXTURE_2D, texture[0] );
			
			//Set parameters
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, minFilter.getValue() );
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, magFilter.getValue() );
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, sWrap.getValue() );
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, tWrap.getValue() );
			
			//Set properties
			textureID = texture[0];
			width = bmp.getWidth();
			height = bmp.getHeight();
			
			
			// Create texture(s)
			if (minFilter == TextureFilter.MipMap || magFilter == TextureFilter.MipMap)
				buildMipmap(bmp);
			else
				GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);
			
			TextureManager.getInstance().addTexture(this);
			
			bmp.recycle();
		} catch (IOException e) {
			Logger.e(e.getMessage());
		}
	}
	
	
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getTextureHandle()
	{
		return textureID;
	}
	
	public void dispose()
	{
		int[] textures = {textureID};
		GLES20.glDeleteTextures(1, textures, 0);
		textureID = 0;
		width = 0;
		height = 0;
		
		TextureManager.getInstance().removeTexture(this);
	}
}
