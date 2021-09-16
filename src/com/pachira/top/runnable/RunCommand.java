package com.pachira.top.runnable;

import java.util.List;

import com.pachira.top.callback.TopAddCallback;
import com.pachira.top.domain.Top;
import com.pachira.top.util.CommonUtils;
import com.pachira.top.util.TopUtils;

public class RunCommand implements Runnable {
	private List<Top> tops;
	private List<String> strs;
	private List<String> regexes;
	private boolean end = false;
	private boolean run = true;
	private Object obj = new Object();
	
	public RunCommand(List<Top> tops, List<String> strs, List<String> regexes) {
		this.tops = tops;
		this.strs = strs;
		this.regexes = regexes;
	}
	
	public void end() {
		synchronized (obj) {
			end = true;
			CommonUtils.notifyObjWithoutSync(obj);
		}
	}
	
	public void runComand() {
		synchronized (obj) {
			run = true;
			CommonUtils.notifyObjWithoutSync(obj);
		}
	}
	
	private void waitComand() {
		while (!run) {
			synchronized (obj) {
				CommonUtils.waitObjWithoutSync(obj, 5000);
			}
		}
	}
	
	@Override
	public void run() {
		while (!end) {
			run = false;
			TopUtils.getTopMsg(tops, strs, regexes, new TopAddCallback() {
				@Override
				public boolean checkAdd(Top top) {
					System.out.println(top);
					return true;
				}
			});
			synchronized (obj) {
				if (end) {
					break;
				}
				waitComand();
			}
		}
	}

}
