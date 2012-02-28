package com.beckesoft.android.mithril.texture;

import android.opengl.GLES20;

public enum TextureWrap
{
	ClampToEdge(GLES20.GL_CLAMP_TO_EDGE),
	Wrap(GLES20.GL_REPEAT);
	
	private int value;
	
	private TextureWrap(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
}
