package com.beckesoft.android.mithril.shader;

import android.opengl.GLES20;

public enum ShaderType {
	ST_VERTEX(GLES20.GL_VERTEX_SHADER),
	ST_FRAGMENT(GLES20.GL_FRAGMENT_SHADER);
	
	private int value;    

	private ShaderType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public String getName()
	{
		if (value == GLES20.GL_VERTEX_SHADER)
			return "ST_VERTEX";
		else
			return "ST_FRAGMENT";
	}
}
