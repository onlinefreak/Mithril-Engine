package com.beckesoft.android.mithril.shader;

public class ShaderFactory {
    private static final String vertexShaderCode = 
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
    		"uniform mat4 MVPMatrix; \n" +
            "attribute vec4 vPosition;  \n" +
            "attribute vec2 vTextureCoord; \n" +
            
            "varying vec2 aTextureCoord; \n" +
            "void main(){               \n" +
            
            // the matrix must be included as a modifier of gl_Position
            " gl_Position = MVPMatrix * vPosition; \n" +
            " aTextureCoord = vTextureCoord; \n" +
            "}  \n";
        
    private static final String fragmentShaderCode = 
            "precision mediump float;  \n" +
            "uniform sampler2D textureSampler; \n" +
            "varying vec2 aTextureCoord; \n" +
           "void main(){              \n" +
            " gl_FragColor = texture2D(textureSampler, aTextureCoord); \n" +
            //" gl_FragColor = vec4 (0.63671875, 0.76953125, 0.22265625, 1.0); \n" +
            "}                         \n";

    
    public static ShaderProgram standartModel()
    {
    	return ShaderProgram.loadProgram(vertexShaderCode, fragmentShaderCode);
    }
}
