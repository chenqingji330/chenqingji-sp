package com.jt;

import org.junit.Test;
import org.springframework.util.DigestUtils;

import com.sun.javafx.geom.transform.BaseTransform.Degree;

public class Test1MD5 {
	
	@Test
	public void testMD5() {
		String password="001123456987mkij";
		String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
		System.out.println(md5);
	}

}
