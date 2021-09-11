package com.pachira.top.util;

import com.pachira.top.callback.InputLineFilter;
import com.pachira.top.runnable.InputStreamReadRunnable;

public class Cmd {
	public static String[] exec(String cmd, InputLineFilter filter) {
		Process proc = null;
		String[] res = new String[] {"", "", "-1"};
		try {
			proc = Runtime.getRuntime().exec(cmd);
			InputStreamReadRunnable right = new InputStreamReadRunnable(proc.getInputStream(), filter);
			InputStreamReadRunnable error = new InputStreamReadRunnable(proc.getInputStream(), filter);
			new Thread(right).start();
			new Thread(error).start();
			proc.waitFor();
			res[0] = right.getRes();
			res[1] = error.getRes();
			res[2] = String.valueOf(proc.exitValue());
		} catch (Exception e) {
			
		} finally {
			SourceUtils.destoryProc(proc);
		}
		return res;
	}
	
	
	
	
	
}
