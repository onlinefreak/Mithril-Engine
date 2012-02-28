package com.beckesoft.android.mithril.shader;

import java.util.ArrayList;
import java.util.List;


import com.beckesoft.android.mithril.output.Logger;

import android.opengl.GLES20;

public class ShaderProgram {
	private int programHandle;
	private List<Shader> vertexShader = new ArrayList<Shader>(0);
	private List<Shader> fragmentShader = new ArrayList<Shader>(0);
	
	private boolean isActive = false;
	
	private ShaderProgram() {}
	
	public void attachShader(ShaderType type, String shaderCode)
	{
		attachShader(Shader.loadShader(type, shaderCode));
	}
	
	private void attachShader(Shader shader)
	{
		if (shader == null || shader.getShaderHandle() == 0)
			return;
		
		int Program = GLES20.glCreateProgram();
		
		if (Program == 0)
		{
			Logger.e("Could not create program [glCreateProgram]");
			shader.dispose();
			return;
		}
		
		
		for (int i = 0; i < vertexShader.size(); i++)
			GLES20.glAttachShader(Program, vertexShader.get(i).getShaderHandle());
		
		for (int i = 0; i < fragmentShader.size(); i++)
			GLES20.glAttachShader(Program, fragmentShader.get(i).getShaderHandle());
		
		GLES20.glAttachShader(Program, shader.getShaderHandle());
		
		GLES20.glLinkProgram(Program);
		
		int[] linked = new int[1];
		
		GLES20.glGetProgramiv(Program, GLES20.GL_LINK_STATUS, linked, 0);
		
		if (linked[0] == 0)
		{
			Logger.e("Failed to link program");
			shader.dispose();
			GLES20.glDeleteProgram(Program);
			return;
		}
		
		if (shader.getType() == ShaderType.ST_FRAGMENT)
			fragmentShader.add(shader);
		else
			vertexShader.add(shader);
		
		GLES20.glDeleteProgram(programHandle);
		shader.addReference(this);
		programHandle = Program;
	}
	
	public static ShaderProgram loadProgram(String vertexCode, String fragmentCode)
	{
		int Program;
		Shader vertexShader;
		Shader fragmentShader;
		int[] linked = new int[1];
		
		
		vertexShader = Shader.loadShader(ShaderType.ST_VERTEX, vertexCode);
		
		
		if (vertexShader == null)
		{
			Logger.e("Could not create VertexShader");
			return null;
		}
		
		fragmentShader = Shader.loadShader(ShaderType.ST_FRAGMENT, fragmentCode);
		
		if (fragmentShader == null)
		{
			Logger.e("Could not create FragmentShader");
			vertexShader.dispose();
			return null;
		}
		
		Program = GLES20.glCreateProgram();
		
		if (Program == 0)
		{
			Logger.e("Could not create program [glCreateProgram]");
			vertexShader.dispose();
			fragmentShader.dispose();
			return null;
		}
		
		GLES20.glAttachShader(Program, vertexShader.getShaderHandle());
		GLES20.glAttachShader(Program, fragmentShader.getShaderHandle());
		
		GLES20.glLinkProgram(Program);
		
		GLES20.glGetProgramiv(Program, GLES20.GL_LINK_STATUS, linked, 0);
		
		if (linked[0] == 0)
		{
			Logger.e("Failed to link program [glLinkProgram]");
			vertexShader.dispose();
			vertexShader.dispose();
			GLES20.glDeleteProgram(Program);
			return null;
		}
		
		ShaderProgram sp = new ShaderProgram();
		vertexShader.addReference(sp);
		fragmentShader.addReference(sp);
		
		sp.programHandle = Program;
		sp.vertexShader.add(vertexShader);
		sp.fragmentShader.add(fragmentShader);
		
		
		ShaderManager.getInstance().addShaderProgram(sp);
		
		return sp;
	}
	
	public void deactivate()
	{
		isActive = false;
	}
	
	public int getShaderProgramHandle()
	{
		return programHandle;
	}
	
	public void dispose()
	{
		GLES20.glDeleteProgram(programHandle);
		ShaderManager.getInstance().removeShaderProgram(this);
		
		for (int i = 0; i < vertexShader.size(); i++)
			vertexShader.get(i).removeReference(this);
		
		for (int i = 0; i < fragmentShader.size(); i++)
			fragmentShader.get(i).removeReference(this);
		
		vertexShader.clear();
		vertexShader = null;
		fragmentShader.clear();
		fragmentShader = null;
	}
	
	public void use()
	{
		if (!isActive)
		{
			GLES20.glUseProgram(programHandle);
			ShaderManager.getInstance().setActiveProgram(this);
			isActive = true;
		}
	}
}
