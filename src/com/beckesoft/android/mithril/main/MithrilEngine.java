package com.beckesoft.android.mithril.main;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.beckesoft.android.mithril.shader.ShaderManager;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;

public class MithrilEngine extends Activity implements Renderer{
	
	/** GLSurfaceView **/
	private GLSurfaceView glSurface;
	
	private static MithrilEngine INSTANCE = null;
	
	private int width, height;
	
	private MithrilListener listener;
	
	private long lastFrameStart;
	
	private float deltaTime;
	
	private static String projectName = "Mithril Engine";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Create OpenGL Interface
		glSurface = new GLSurfaceView(this);
		
		glSurface.setEGLContextClientVersion(2);
		glSurface.setRenderer(this);
		
		INSTANCE = this;
		
		this.setContentView(glSurface);
	}
	
	public void suraceDestroyed(SurfaceHolder surface)
	{
		
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
	}	
	
	@Override
	public void onDrawFrame(GL10 gl) {
		long currentTime = System.nanoTime();
		deltaTime = (currentTime - lastFrameStart) / 1000000000.0f;
		lastFrameStart = currentTime;
		
		if (listener != null)
			listener.mainLoopIteration(this);
		

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		this.width = width;
		this.height = height;
		
		ShaderManager.getInstance();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		lastFrameStart = System.nanoTime();

		
		if (listener != null)
			listener.setup(this);
	}
	
	public void setMithrilListener(MithrilListener listener)
	{
		this.listener = listener;
	}
	
	public float getDeltaTime()
	{
		return deltaTime;
	}
	
	public int getViewportWidth()
	{
		return this.width;
	}
	
	public int getViewportHeight()
	{
		return this.height;
	}
	
	public void setProjectName(String name)
	{
		projectName = name;
	}
	
	public static String getName()
	{
		return projectName;
	}
	
	public static MithrilEngine getInstance()
	{
		return INSTANCE;
	}
}
