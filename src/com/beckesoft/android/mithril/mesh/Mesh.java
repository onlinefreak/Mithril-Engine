package com.beckesoft.android.mithril.mesh;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Mesh {
	
	// Vertices
	private float[] vertices;
	private FloatBuffer vertexBuffer;
	
	// Color
	private float[] colors;
	private FloatBuffer colorBuffer;
	
	// Texture Coordinates
	private float[] texCoord;
	private FloatBuffer texCoordBuffer;
	
	// Normals
	private float[] normals;
	private FloatBuffer normalBuffer;
	
	
	private int index = 0;
	private int numVertices = 0;
	
	private int primitive;
	
	
	private FloatBuffer allocateBuffer(int size)
	{
		return ByteBuffer.allocateDirect(size*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
	}
	
	public Mesh(int numVertices, boolean hasColor, boolean hasTextureCoord, boolean hasNormals, PrimitiveType primitive)
	{
		vertices = new float[numVertices * 3];
		vertexBuffer = allocateBuffer(numVertices*3);
		
		if (hasColor)
		{
			colors = new float[numVertices * 4];
			colorBuffer = allocateBuffer(numVertices * 4);
		}
		
		if (hasTextureCoord)
		{
			texCoord = new float[numVertices * 2];
			texCoordBuffer = allocateBuffer(numVertices * 2);
		}
		
		if (hasNormals)
		{
			normals = new float[numVertices * 3];
			normalBuffer = allocateBuffer(numVertices * 3);
		}
		
		this.primitive = primitive.getValue();
	}

	private void update()
	{
		vertexBuffer.put(vertices).position(0);
		if (colors != null)
			colorBuffer.put(colors).position(0);
		if (texCoord != null)
			texCoordBuffer.put(texCoord).position(0);
		if (normals != null)
			normalBuffer.put(normals).position(0);
		
		numVertices = index;
		index = 0;
	}
	
	
	
	
}
