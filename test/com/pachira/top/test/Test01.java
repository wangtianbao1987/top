package com.pachira.top.test;

import org.junit.Test;

import com.pachira.top.Jiankong;

public class Test01 {
	
	@Test
	public void test01() {
		Jiankong.main(new String[] {"-t" , "1", "-tt" , "10", "-s", "java", "mysql"});
	}
	
}
