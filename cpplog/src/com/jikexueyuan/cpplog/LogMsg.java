package com.jikexueyuan.cpplog;

public class LogMsg {
	
	public static native void logMsg();

	static {
		System.loadLibrary("cpplog");
	}
}
