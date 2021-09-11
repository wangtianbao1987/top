package com.pachira.top;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pachira.top.callback.TopAddCallback;
import com.pachira.top.domain.Top;
import com.pachira.top.util.TopUtils;

public class Jiankong {

	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			System.out.println("java -jar top.jar <指令> <参数> ...\n"
					+ "\t\t-t\t每隔多久监控一次(单位：s)\n"
					+ "\t\t-tt\t监控总时长(单位：s)\n"
					+ "\t\t-s <str...>\t筛选结果：结果包含那些字符串，可以写多个\n"
					+ "\t\t-ss <regex...>\t筛选结果：结果能够被那些正则表达式匹配，可以写多个\n");
			return;
		}
		Map<String, List<String>> params = getParams(args);
		List<String> strs = params.get("-s");
		List<String> regexes = params.get("-ss");
		TopUtils.getTopMsg(strs, regexes, new TopAddCallback() {
			@Override
			public void add(List<Top> tops, Top top) {
				System.out.println(top);
				tops.add(top);
			}
		});
		
		System.out.println("end...");
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
