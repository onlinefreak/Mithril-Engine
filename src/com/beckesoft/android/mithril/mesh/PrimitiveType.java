package com.beckesoft.android.mithril.mesh;

import android.opengl.GLES20;

public enum PrimitiveType {
	
	Points(GLES20.GL_POINTS),
	Line(GLES20.GL_LINES),
	LineStrip(GLES20.GL_LINE_STRIP),
	Triangle(GLES20.GL_TRIANGLES),
	TriangleFan(GLES20.GL_TRIANGLE_FAN),
	TriangleStrip(GLES20.GL_TRIANGLE_STRIP);
	
	private int value;
	
	private PrimitiveType(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
}
