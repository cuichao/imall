package com.eleven7.imall.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class MD5 {
	public static String getMD5(String str) {
		String result = "";
		byte cResult[] = null;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] content = str.getBytes();
		md.update(content);
		cResult = md.digest();
		result = byteto10(cResult);
		return result;
	}

	public static String byteto10(byte tyds[]) // 字节转10进制
	{
		String result = "";
		for (int i = 0; i < tyds.length; i++) {
			int low = (tyds[i] & 0xf0) >> 4;// 取低4位
			int high = tyds[i] & 0xf;// 取高4位
			result = tiany10tohex(low, result); // 调用10进制转16进制
			result = tiany10tohex(high, result);// 调用10进制转16进制
		}
		return result;
	}

	public static String tiany10tohex(int a, String result)// 10进制转16进制
	{
		String str = new String("0123456789ABCDEF");
		result += str.toLowerCase().charAt((a));
		return result;
	}
	public static void main(String[] argv)
	{
		String s = "admin";
		System.out.println(MD5.getMD5(s));
	}
}
