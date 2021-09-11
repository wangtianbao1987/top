package com.pachira.top.util;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public final class SourceUtils {
	
	private static void runMethod(String methodName,Object... objs){
		if(objs == null){
			return;
		}
		for(Object obj:objs){
			if(obj == null){
				return;
			}
			try {
				Method m = obj.getClass().getMethod(methodName);
				m.invoke(obj);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void write(InputStream in,OutputStream out){
		try {
			byte[] buff = new byte[1024*10];
			int len = 0;
			while((len=in.read(buff)) != -1){
				try {
					out.write(buff,0,len);
					out.flush();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void write(InputStream in,OutputStream out,long[] times){
		try {
			byte[] buff = new byte[1024*10];
			int len = 0;
			while((len=in.read(buff)) != -1){
				try {
					out.write(buff,0,len);
					out.flush();
					if(times[7] == 0) {
						times[7] = System.currentTimeMillis();
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Object... objs){
		if(objs == null){
			return;
		}
		for(Object obj:objs){
			if(obj == null){
				return;
			}
			try {
				if(obj instanceof Closeable){
					((Closeable) obj).close();
				}else if(obj instanceof AutoCloseable){
					((AutoCloseable) obj).close();
				}else{
					runMethod("close", obj);
				}
				obj = null;
			} catch (Throwable e) {
				// logging.warn("资源关闭失败:"+obj, e);
			}
		}
	}
	
	public static void flush(Object... objs){
		if(objs == null){
			return;
		}
		for(Object obj:objs){
			if(obj == null){
				return;
			}
			try {
				if(obj instanceof OutputStream){
					((OutputStream) obj).flush();
				}else{
					runMethod("flush", obj);
				}
			} catch (Throwable e) {
				// logging.warn("数据刷新失败:"+obj, e);
			}
		}
	}
	
	public static void destoryProc(Process proc) {
		if (proc == null) {
			return;
		}
		try {
			proc.destroy();
		} catch (Exception e) {
			
		}
		proc = null;
	}
	
}
