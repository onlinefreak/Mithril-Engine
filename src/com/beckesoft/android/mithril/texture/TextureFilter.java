package com.beckesoft.android.mithril.texture;

import android.opengl.GLES20;

public enum TextureFilter
{
	Nearest(GLES20.GL_NEAREST),
	Linear(GLES20.GL_LINEAR),
	MipMap(GLES20.GL_LINEAR_MIPMAP_NEAREST);
	
	private int value;
	
	private TextureFilter(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
}
