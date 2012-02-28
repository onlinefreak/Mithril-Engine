package com.beckesoft.android.mithril.math;

public class MathHelper {
	private MathHelper() {}

	
	public static boolean isPOT(int value)
	{
		return ((value & (value - 1)) == 0);
	}
}
