package com.pachira.top.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.pachira.top.callback.InputLineFilter;
import com.pachira.top.callback.TopAddCallback;
import com.pachira.top.domain.Top;

public class TopUtils {
	
	private static String cmdStr = "top -bn1";
	
	public static List<Top> getAllTopMsg(TopAddCallback callback) {
		List<Top> tops = new ArrayList<Top>();
		Cmd.exec(cmdStr, new InputLineFilter() {
			@Override
			public boolean filter(String line) {
				Top top = strToTop(line);
				if (top != null) {
					callback.add(tops, top);
				}
				return false;
			}
		});
		return tops;
	}
	
	public static List<Top> getTopMsg(List<String> strs, List<String> regexes, TopAddCallback callback) {
		if (strs == null || strs.isEmpty()) {
			if (regexes == null || regexes.isEmpty()) {
				return getAllTopMsg(callback);
			} else {
				return getTopMsgByRegex(regexes, callback);
			}
		} else if (regexes == null || regexes.isEmpty()) {
			return getTopMsgByStr(strs, callback);
		}
		
		List<Top> tops = new ArrayList<Top>();
		Cmd.exec(cmdStr, new InputLineFilter() {
			@Override
			public boolean filter(String line) {
				for (String str : strs) {
					if (line.contains(str)) {
						Top top = strToTop(line);
						if (top != null) {
							callback.add(tops, top);
							return false;
						}
					}
				}
				for (String regex : regexes) {
					if (Pattern.matches(regex, line)) {
						Top top = strToTop(line);
						if (top != null) {
							callback.add(tops, top);
							return false;
						}
					}
				}
				return false;
			}
		});
		return tops;
	}
	
	public static List<Top> getTopMsgByRegex(List<String> regexes, TopAddCallback callback) {
		List<Top> tops = new ArrayList<Top>();
		Cmd.exec(cmdStr, new InputLineFilter() {
			@Override
			public boolean filter(String line) {
				for (String regex : regexes) {
					if (Pattern.matches(regex, line)) {
						Top top = strToTop(line);
						if (top != null) {
							callback.add(tops, top);
							return false;
						}
					}
				}
				return false;
			}
		});
		return tops;
	}
	
	
	public static List<Top> getTopMsgByStr(List<String> strs, TopAddCallback callback) {
		List<Top> tops = new ArrayList<Top>();
		Cmd.exec(cmdStr, new InputLineFilter() {
			@Override
			public boolean filter(String line) {
				for (String str : strs) {
					if (line.contains(str)) {
						Top top = strToTop(line);
						if (top != null) {
							callback.add(tops, top);
							return false;
						}
					}
				}
				return false;
			}
		});
		return tops;
	}
	
	public static Top strToTop(String line) {
		try {
			line = line.trim();
			String[] items = line.split("\\s+");
			if (items.length < 12) {
				return null;
			}
			Top top = new Top();
			top.setPid(Integer.parseInt(items[0]));
			top.setUser(items[1]);
			top.setPr(items[2]);
			top.setNi(items[3]);
			top.setVirt(items[4]);
			top.setRes(items[5]);
			top.setShr(items[6]);
			top.setCpu(items[8]);
			top.setMem(items[9]);
			top.setTime(items[10]);
			top.setCommand(items[11]);
			return top;
		} catch (Exception e) {
			return null;
		}
	}
	
	
}
