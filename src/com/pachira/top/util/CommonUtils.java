package com.pachira.top.util;

public class CommonUtils {
	public static void waitObjWithoutSync(Object obj, long timeout) {
		try {
			obj.wait(timeout);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void notifyObjWithoutSync(Object obj) {
		try {
			obj.notifyAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
