package com.beckesoft.android.mithril.shader;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.beckesoft.android.mithril.output.Logger;

public class ShaderManager {
	
	private static ShaderManager INSTANCE = null;
	
	private List<ShaderProgram> programs = new ArrayList<ShaderProgram>(0);
	private List<Shader> 		shaders  = new ArrayList<Shader>(0);
	private ShaderProgram activeProgram = null;
	
	private ShaderManager() {};
	
	public static ShaderManager getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new ShaderManager();
			INSTANCE.programs = new ArrayList<ShaderProgram>(0);
			INSTANCE.shaders  = new ArrayList<Shader>(0);
		}
		
		return INSTANCE;
	}
	
	public ShaderProgram getActiveProgram()
	{
		return activeProgram;
	}
	
	public void setActiveProgram(ShaderProgram prog)
	{
		if (activeProgram != null)
			activeProgram.deactivate();
		activeProgram = prog;
	}
	
	public void addShaderProgram(ShaderProgram prog)
	{
		if (prog != null)
			programs.add(prog);
		else
			Logger.w("Unreferenced ShaderProgram 'prog' in com.beckesoft.android.mithril.shader.ShaderManager");
	}
	
	public void addShader(Shader shader)
	{
		if (shader != null)
			shaders.add(shader);
		else
			Logger.w("Unreferenced ShaderProgram 'shader' in com.beckesoft.android.mithril.shader.ShaderManager");
	}
	
	public void removeShaderProgram(ShaderProgram prog)
	{
		if (prog != null)
			programs.remove(prog);
		else
			Logger.w("Unreferenced ShaderProgram 'prog' in com.beckesoft.android.mithril.shader.ShaderManager");
	}
	
	public void removeShader(Shader shader)
	{
		if (shader != null)
			shaders.remove(shader);
		else
			Logger.w("Unreferenced ShaderProgram 'shader' in com.beckesoft.android.mithril.shader.ShaderManager");
	}
	
	public Shader loadShader(ShaderType type, String shaderCode)
	{
		Shader shad = Shader.loadShader(type, shaderCode);
		if (shad == null)
			return null;
		return shad;
	}
	
	public ShaderProgram loadProgram(String vertexCode, String fragmentCode)
	{
		ShaderProgram prog = ShaderProgram.loadProgram(vertexCode, fragmentCode);
		if (prog == null)
			return null;
		return prog;
	}
	
	public void clear()
	{
		for (int i = shaders.size()-1; i >= 0; i--)
		{
			if (shaders.get(i).getReferencesCount() == 0)
				shaders.get(i).dispose();
		}
	}
	
	public void dispose()
	{
		if (programs.size() > 0)
			Log.w("Mithril Engine", "Not all Programs are destroyed. Automatic destruction launched!");
		
		if (shaders.size() > 0)
			Log.w("Mithril Engine", "Not all Shaders are destroyed. Automatic destruction launched!");
		
		for (int i = programs.size()-1; i >= 0; i--)
		{
			programs.get(i).dispose();
		}

		for (int i = shaders.size()-1; i >= 0; i--)
		{
			shaders.get(i).dispose();
		}
		
		
		shaders = null;
		programs = null;
		
		INSTANCE = null;
	}
}
