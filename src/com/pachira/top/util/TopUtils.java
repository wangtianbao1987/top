package com.pachira.top.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.pachira.top.callback.InputLineFilter;
import com.pachira.top.callback.TopAddCallback;
import com.pachira.top.domain.TjTop;
import com.pachira.top.domain.Top;

public class TopUtils {
	
	private static String cmdStr = "top -bn1";
	
	public static List<Top> getAllTopMsg(List<Top> tops, TopAddCallback callback) {
		Cmd.exec(cmdStr, new InputLineFilter() {
			@Override
			public boolean filter(String line) {
				Top top = strToTop(line);
				if (top != null) {
					if (callback.checkAdd(top)) {
						tops.add(top);
					}
				}
				return false;
			}
		});
		return tops;
	}
	
	public static List<Top> getTopMsg(List<Top> tops, List<String> strs, List<String> regexes, TopAddCallback callback) {
		if (strs == null || strs.isEmpty()) {
			if (regexes == null || regexes.isEmpty()) {
				return getAllTopMsg(tops, callback);
			} else {
				return getTopMsgByRegex(tops, regexes, callback);
			}
		} else if (regexes == null || regexes.isEmpty()) {
			return getTopMsgByStr(tops, strs, callback);
		}

		Cmd.exec(cmdStr, new InputLineFilter() {
			@Override
			public boolean filter(String line) {
				for (String str : strs) {
					if (line.contains(str)) {
						Top top = strToTop(line);
						if (top != null) {
							if (callback.checkAdd(top)) {
								tops.add(top);
								return false;
							}
						}
					}
				}
				for (String regex : regexes) {
					if (Pattern.matches(regex, line)) {
						Top top = strToTop(line);
						if (top != null) {
							if (callback.checkAdd(top)) {
								tops.add(top);
								return false;
							}
						}
					}
				}
				return false;
			}
		});
		return tops;
	}
	
	public static List<Top> getTopMsgByRegex(List<Top> tops, List<String> regexes, TopAddCallback callback) {
		Cmd.exec(cmdStr, new InputLineFilter() {
			@Override
			public boolean filter(String line) {
				for (String regex : regexes) {
					if (Pattern.matches(regex, line)) {
						Top top = strToTop(line);
						if (top != null) {
							if (callback.checkAdd(top)) {
								tops.add(top);
								return false;
							}
						}
					}
				}
				return false;
			}
		});
		return tops;
	}
	
	
	public static List<Top> getTopMsgByStr(List<Top> tops, List<String> strs, TopAddCallback callback) {
		Cmd.exec(cmdStr, new InputLineFilter() {
			@Override
			public boolean filter(String line) {
				for (String str : strs) {
					if (line.contains(str)) {
						Top top = strToTop(line);
						if (top != null) {
							if (callback.checkAdd(top)) {
								tops.add(top);
								return false;
							}
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
			// ?????????????????????
			if (!line.matches("^[0-9].*$")) {
				return null;
			}
			String[] items = line.split("\\s+");
			if (items.length == 7) {
				Top top = new Top();
				top.setPid(Integer.parseInt(items[0]));
				top.setVirt(items[4]);
				top.setRes(items[5]);
				top.setShr(items[6]);
				top.setCpu(items[8]);
				top.setMem(items[9]);
				top.setCommand(items[11]);
				return top;
			} else if (items.length == 11) {
				Top top = new Top();
				top.setPid(Integer.parseInt(items[0]));
				top.setUser(items[1]);
				top.setPr(items[2]);
				top.setNi(items[3]);
				top.setVirt(items[4]);
				top.setRes(items[5]);
				top.setShr(items[6]);
				top.setCpu(items[7]);
				top.setMem(items[8]);
				top.setTime(items[9]);
				top.setCommand(items[10]);
				return top;
			}  else if (items.length == 12) {
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
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static Map<Integer, TjTop> getTjRes(List<Top> tops) {
		Map<Integer, TjTop> res = new HashMap<Integer, TjTop>();
		for (Top top : tops) {
			Integer pid = top.getPid();
			TjTop tjtop = res.get(pid);
			if (tjtop == null) {
				tjtop = new TjTop();
				res.put(pid, tjtop);
			}
			tjtop.addTop(top);
		}
		return res;
	}

	public static void printTjRes(Map<Integer, TjTop> tjTopMap) {
		Iterator<Integer> it = tjTopMap.keySet().iterator();
		System.out.println("\tVIRT(????????????)\tRES(????????????)\tSHR(????????????)\t%CPU\t%MEM\tPID[command])");
		while (it.hasNext()) {
			Integer pid = it.next();
			TjTop tjTop = tjTopMap.get(pid);
			System.out.println(tjTop.toRes());
		}
	}
	
	
	public static void readFile(File file, InputLineFilter filter) {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				filter.filter(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SourceUtils.close(br, fr);
		}
	}


	
	
}
