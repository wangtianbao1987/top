package com.pachira.top;

import com.pachira.top.callback.InputLineFilter;
import com.pachira.top.callback.TopAddCallback;
import com.pachira.top.domain.TjTop;
import com.pachira.top.domain.Top;
import com.pachira.top.util.TopUtils;

import java.io.File;
import java.util.*;

public class Jiankong {

	public static void main(String[] args) {
		try {
			if (args == null || args.length == 0) {
				printTip();
				return;
			}
			Map<String, List<String>> params = getParams(args);
			List<String> fs = params.get("-f");
			if (fs == null || fs.isEmpty()) {
				realtimeJk(params);
				return;
			}
			File file = new File(fs.get(0));
			analyOfflineFile(file);
			
		} catch (Exception e) {
			e.printStackTrace();
			printTip();
		}
	}
	
	private static boolean analyOfflineFile(File file) {
		if (!file.isFile()) {
			System.out.println("文件不存在：" + file.getAbsolutePath());
			return false;
		}
		Map<Integer, TjTop> tjMap = new HashMap<Integer, TjTop>();
		TopUtils.readFile(file, new InputLineFilter() {
			@Override
			public boolean filter(String line) {
				Top top = TopUtils.strToTop(line);
				if (top == null) {
					return true;
				}
				TjTop tjTop = tjMap.get(top.getPid());
				if (tjTop == null) {
					tjTop = new TjTop();
					tjMap.put(top.getPid(), tjTop);
				}
				tjTop.addTop(top);
				return true;
			}
		});
		TopUtils.printTjRes(tjMap);
		return true;
	}
	
	private static void realtimeJk(Map<String, List<String>> params) {
		List<String> tes = params.get("-t");
		if (tes == null || tes.isEmpty()) {
			printTip();
			return;
		}
		long t = Long.parseLong(tes.get(0));
		if (t < 1) {
			printTip();
			return;
		}
		List<String> ttes = params.get("-tt");
		long tt = Long.parseLong(ttes.get(0));
		if (tt < t) {
			printTip();
			return;
		}
		List<String> strs = params.get("-s");
		List<String> regexes = params.get("-ss");
		System.out.println("PID\tUSER\tPR\tNI\tVIRT\tRES\tSHR\tCPU(%)\tMEM(%)\tTIME\tCOMMAND");
		long endTime = System.currentTimeMillis() + tt * 1000;
		List<Top> tops = Collections.synchronizedList(new ArrayList<Top>());
		while (System.currentTimeMillis() <= endTime) {
			TopUtils.getTopMsg(tops, strs, regexes, new TopAddCallback() {
				@Override
				public boolean checkAdd(Top top) {
					System.out.println(top);
					return true;
				}
			});
		}
		TopUtils.printTjRes(TopUtils.getTjRes(tops));
	}

	private static void printTip() {
		System.out.println("java -jar top.jar <指令> <参数> ...\n"
				+ "\t-f\t解析日志，若传入该参数其他参数无效\n"
				+ "\t-t\t每隔多久监控一次(单位：s)\n"
				+ "\t-tt\t监控总时长(单位：s)\n"
				+ "\t-s <str...>\t筛选结果：结果包含那些字符串，可以写多个\n"
				+ "\t-ss <regex...>\t筛选结果：结果能够被那些正则表达式匹配，可以写多个\n");
	}
	
	public static Map<String, List<String>> getParams(String[] args) {
		String prevKey = null;
		Map<String, List<String>> res = new HashMap<String, List<String>>();
		for (String arg : args) {
			if (arg.startsWith("-")) {
				res.put(arg, new ArrayList<String>());
				prevKey = arg;
			} else if (prevKey == null) {
				continue;
			} else {
				List<String> list = res.get(prevKey);
				list.add(arg);
			}
		}
		return res;
	}
	

}
