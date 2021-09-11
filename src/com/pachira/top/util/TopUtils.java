package com.pachira.top.util;

import com.pachira.top.domain.Top;

public class TopUtils {
	public static Top getTopMsg(String searchStr) {
		String cmd = "top -bn1";
		Top top = new Top();
		return top;
	}
	
	
}
