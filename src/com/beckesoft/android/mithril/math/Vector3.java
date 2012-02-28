package com.beckesoft.android.mithril.math;

public class Vector3 {
	
	/** Values of the vector **/
	public float x, y, z;
	
	public static final Vector3 ZERO 		= new Vector3(0, 0, 0);
	public static final Vector3 UNIT_X 		= new Vector3(1, 0, 0);
	public static final Vector3 UNIT_Y 		= new Vector3(0, 1, 0);
	public static final Vector3 UNIT_Z 		= new Vector3(0, 0, 1);
	public static final Vector3 UNIT_XYZ 	= new Vector3(1, 1, 1);
	
	public static int EIGEN_PRECISION = 2;
	/** Constructor **/
	public Vector3()
	{
		x = y = z = 0;
	}
	
	public Vector3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vector3 copy)
	{
		this.x = copy.x;
		this.y = copy.y;
		this.z = copy.z;
	}
	
	/** Set values of Vector3 **/
	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3 set(Vector3 copy)
	{
		if (copy == null)
			return null;
		
		
		this.x = copy.x;
		this.y = copy.y;
		this.z = copy.z;
		
		return this;
	}
	
	
	public Vector3 addLocal(Vector3 vec)
	{
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
		return this;
	}
	
	public Vector3 addLocal(float x, float y, float z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	/** Add Vector vec to this vector and return a new
	 * resultant vector
	 * @param vec 
	 * 			Vector to add to this
	 * @return 
	 * 			resultant vector
	 */
	public Vector3 add(Vector3 vec)
	{		
		return new Vector3(this.x + vec.x, this.y + vec.y, this.z + vec.z);
	}

	
	public Vector3 add(Vector3 vec, Vector3 result)
	{
		if (result == null)
			result = new Vector3();
		
		result.x = this.x + vec.x;
		result.y = this.y + vec.y;
		result.z = this.z + vec.z;
		
		return result;
	}
	
	/** 
	 * Add Vector vec to this vector and return a new
	 * resultant vector
	 * 
	 * @param x	
	 * 			x value to add
	 * @param y
	 * 			y value to add
	 * @param z
	 * 			z value to add
	 * 
	 * @return 
	 * 			the result vector
	 */
	public Vector3 add(float x, float y, float z)
	{
		return new Vector3(this.x + x, this.y + y, this.z + z);
	}
	
	/**
	 * Add Vector (x, y, z) to this vector and return a new resultant
	 * vector. A copy is returned by result
	 * 
	 * @param x	
	 * 			x value to add
	 * @param y
	 * 			y value to add
	 * @param z
	 * 			z value to add
	 * @param result
	 * 			vector to store result in
	 * @return
	 * 			return the supplied result vector
	 */
	public Vector3 add(float x, float y, float z, Vector3 result)
	{
		if (result == null)
			result = new Vector3();
		
		result.x = this.x + x;
		result.y = this.y + y;
		result.z = this.z + z;
		
		return result;
	}
	
	public float dot(Vector3 vec)
	{
		if (vec == null)
			return 0;
		
		return x * vec.x + y * vec.y + z * vec.z;
	}
	
	public Vector3 cross(Vector3 vec)
	{
		if (vec == null)
			return null;
		return new Vector3((y * vec.z) - (z * vec.y), (z * vec.x) - (x * vec.z), (x * vec.y) - (y * vec.x));
	}
	
	public Vector3 cross(Vector3 vec, Vector3 result)
	{
		if (vec == null)
			return null;
		if (result == null)
			result = new Vector3();
		
		result.x = (y * vec.z) - (z * vec.y);
		result.y = (z * vec.x) - (x * vec.z);
		result.z = (x * vec.y) - (y * vec.x);
		
		return result;
	}
	
	public Vector3 cross(float otherX, float otherY, float otherZ)
	{
		return cross(otherX, otherY, otherZ, null);
	}
	
	public Vector3 cross(float otherX, float otherY, float otherZ, Vector3 result)
	{
		if (result == null)
			result = new Vector3();
		
		result.x = (y * otherZ) - (z * otherY);
		result.y = (z * otherX) - (x * otherZ);
		result.z = (x * otherY) - (y * otherX);
		
		return result;
	}
	
	public Vector3 crossLocal(Vector3 vec)
	{
		if (vec == null)
			return null;
		
		x = (y * vec.z) - (z * vec.y);
		y = (z * vec.x) - (x * vec.z);
		z = (x * vec.y) - (y * vec.x);
		
		return this;
	}
	
	public Vector3 crossLocal(float otherX, float otherY, float otherZ)
	{
		x = (y * otherZ) - (z * otherY);
		y = (z * otherX) - (x * otherZ);
		z = (x * otherY) - (y * otherX);
		
		return this;
	}
	
	public float LengthSquared()
	{
		return x * x + y * y + z * z;
	}
	
	public float length()
	{
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public float distanceSquared(Vector3 vec)
	{
		if (vec == null)
			return 0;
		float dx = vec.x - x;
		float dy = vec.y - y;
		float dz = vec.z - z;
		
		return (float) dx * dx + dy * dy + dz * dz;
	}
	
	public float distance(Vector3 vec)
	{
		return (float) Math.sqrt(distanceSquared(vec));
	}
	
	public Vector3 negate(Vector3 result)
	{
		if (result == null)
			result = new Vector3();
		
		result.x = -x;
		result.y = -y;
		result.z = -z;
		
		return result;
	}
	
	public Vector3 negate()
	{
		return negate(null);
	}
	
	public Vector3 negateLocal()
	{
		set(negate());
		return this;
	}
	
	public Vector3 mult(float scalar, Vector3 result)
	{
		if (result == null)
			result = new Vector3();
		
		result.x = x * scalar;
		result.y = y * scalar;
		result.z = z * scalar;
		
		return result;
	}
	
	public Vector3 mult(float scalar)
	{
		return mult(scalar, null);
	}
	
	public Vector3 multLocal(float scalar)
	{
		x = x * scalar;
		y = y * scalar;
		z = z * scalar;
		
		return this;
	}
	
	public Vector3 divide(float scalar, Vector3 vec)
	{
		return mult(1.0f/scalar, vec);
	}
	
	public Vector3 divide(float scalar)
	{
		return mult(1.0f/scalar, null);
	}
	
	public Vector3 divideLocal(float scalar)
	{
		return (multLocal(1.0f/scalar));
	}
	
	public Vector3 subtract(Vector3 vec, Vector3 result)
	{
		return add(vec.negate(), result);
	}
	
	public Vector3 subtract(Vector3 vec)
	{
		return add(vec.negate(), null);
	}
	
	public Vector3 subtract(float otherX, float otherY, float otherZ, Vector3 result)
	{
		return add( - otherX, - otherY, - otherZ, result);
	}
	
	public Vector3 subtract(float otherX, float otherY, float otherZ)
	{
		return add( - otherX, - otherY, - otherZ, null);
	}
	
	public Vector3 subtractLocal(Vector3 vec)
	{
		return addLocal(vec.negate());
	}
	
	public boolean isUnit()
	{
		float len = length();
		float prec = (float) Math.pow(10, - EIGEN_PRECISION);
		return len > (1 - prec) && len < (1 + prec);
	}
	
	public Vector3 normalize()
	{
		float len = x * x + y * y + z * z;
		if (len != 0 && len != 1)
		{
			len = 1.0f / (float) Math.sqrt(len);
			return new Vector3(x*len, y*len, z*len);
		}
		
		return clone();
	}
	
	public Vector3 normalizeLocal()
	{
		float len = x * x + y * y + z * z;
		if (len != 0 && len != 1)
		{
			len = 1.0f / (float) Math.sqrt(len);
			x *= len;
			y *= len;
			z *= len;
		}
		
		return this;
	}
	
	public Vector3 clone()
	{
		return new Vector3(x, y, z);
	}
	
	public float[] toArray(float[] result)
	{
		if (result == null)
			result = new float[3];
		
		result[0] = x;
		result[1] = y;
		result[2] = z;
		
		return result;
	}
	
	public float[] toArray()
	{
		float[] arr = { x, y, z} ;
		return arr;
	}
	
	public boolean equals(Vector3 vec)
	{
		if (vec == null)
			return false;
		return vec.x == x && vec.y == y && vec.z == z;
	}
	
}