package com.pachira.top.runnable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.pachira.top.callback.InputLineFilter;
import com.pachira.top.util.SourceUtils;

public class InputStreamReadRunnable implements Runnable {
	private InputStream in;
	
	private StringBuilder sb;
	
	private InputLineFilter filter;
	
	public InputStreamReadRunnable(InputStream in, InputLineFilter filter) {
		this.in = in;
		this.filter = filter;
	}
	
	public InputStreamReadRunnable(InputStream in) {
		this.in = in;
		this.filter = new InputLineFilter() {
			@Override
			public boolean filter(String line) {
				return true;
			}
		};
	}
	
	public String getRes() {
		return sb.length() < 2 ? "" : sb.substring(1);
	}
	
	@Override
	public void run() {
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(in);
			br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (filter.filter(line)) {
					sb.append("\n").append(line);
				}
			}
		} catch (Exception e) {
			
		} finally {
			SourceUtils.close(in);
		}
	}
	
}
