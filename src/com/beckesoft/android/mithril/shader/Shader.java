package com.beckesoft.android.mithril.shader;

import java.util.ArrayList;
import java.util.List;

import com.beckesoft.android.mithril.output.Logger;

import android.opengl.GLES20;


public class Shader {
	private ShaderType 			shaderType;
	private int 				shaderHandle  = 0;
	private String				shaderCode    = "";
	private List<ShaderProgram>	programParent = new ArrayList<ShaderProgram>(0);
	
	
	private Shader() {	}
	
	private Shader(ShaderType shaderType, int shaderHandle, String shaderCode)
	{
		this.shaderType = shaderType;
		this.shaderHandle = shaderHandle;
		this.shaderCode = shaderCode;
	}
	
	public static Shader loadShader(ShaderType type, String shaderCode)
	{
		int shaderHandle = GLES20.glCreateShader(type.getValue());

		if (shaderHandle == 0)
		{
			Logger.e("Could not create shader: " + type.getName());
			return null;
		}
		
		
		GLES20.glShaderSource(shaderHandle, shaderCode);
		
		GLES20.glCompileShader(shaderHandle);
		
		int[] compiled = new int[1];
		
		GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compiled, 0);
		
		if (compiled[0] == 0)
		{
			Logger.e("Failed to compile shader: " + type.getName());
			GLES20.glDeleteShader(shaderHandle);
			return null;
		}
		
		Shader s = new Shader(type, shaderHandle, shaderCode);
		
		ShaderManager.getInstance().addShader(s);
		
		return s;
	}
	
	public int getReferencesCount()
	{
		return programParent.size();
	}
	
	public int getShaderHandle()
	{
		return shaderHandle;
	}
	
	public ShaderType getType()
	{
		return shaderType;
	}
	
	public void addReference(ShaderProgram shaderProg)
	{
		programParent.add(shaderProg);
	}
	
	public void removeReference(ShaderProgram shaderProg)
	{
		programParent.remove(shaderProg);
	}
	
	public void dispose()
	{
		// If there are still references, do not dispose
		if (programParent.size() == 0)
		{
			programParent = null;
			GLES20.glDeleteShader(shaderHandle);
		
			ShaderManager.getInstance().removeShader(this);
		}
	}
	
	public String getShaderCode()
	{
		return this.shaderCode;
	}
}
