package base;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class localTest {
	
	public static void main(String[] argv)
	{
		String s="11:12";
		System.out.println(s.split(":").length);
		String b ="8%3D1";
		try {
			System.out.println(URLDecoder.decode(b,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
