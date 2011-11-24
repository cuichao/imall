package com.eleven7.imall.common;


public class Base64 {
	static public String encode(String str)
	 {
	  return new String(encode(str.getBytes()));
	 }
	 static public String decode(String str)
	 {
	  return decode(str,"UTF-8");
	 }
	 static public String decode(String str,String code)
	 {
	  String mystr = null;
	  try{
	   return new String(decode(str.toCharArray()),code);
	  }catch(Exception e){}
	  return new String(decode(str.toCharArray()));
	 }
	 static public char[] encode(byte[] data)
	 {
	  char[] out = new char[((data.length + 2) / 3) * 4];
	 
	  for (int i=0, index=0; i<data.length; i+=3, index+=4)
	  {
	   boolean quad = false;
	   boolean trip = false;
	 
	   int val = (0xFF & (int) data[i]);
	   val <<= 8;
	   if ((i+1) < data.length)
	   {
	    val |= (0xFF & (int) data[i+1]);
	    trip = true;
	   }
	   val <<= 8;
	   if ((i+2) < data.length)
	   {
	    val |= (0xFF & (int) data[i+2]);
	    quad = true;
	   }
	   out[index+3] = alphabet[(quad? (val & 0x3F): 64)];
	   val >>= 6;
	   out[index+2] = alphabet[(trip? (val & 0x3F): 64)];
	   val >>= 6;
	   out[index+1] = alphabet[val & 0x3F];
	   val >>= 6;
	   out[index+0] = alphabet[val & 0x3F];
	  }
	  return out;
	 }
	 static private byte[] decode(char[] data)
	 {
	  int tempLen = data.length;
	  for( int ix=0; ix<data.length; ix++ )
	  {
	   if( (data[ix] > 255) || codes[ data[ix] ] < 0 )
	   --tempLen;
	  }
	 
	  int len = (tempLen / 4) * 3;
	  if ((tempLen % 4) == 3) len += 2;
	  if ((tempLen % 4) == 2) len += 1;
	 
	  byte[] out = new byte[len];
	 
	  int shift = 0;
	  int accum = 0;
	  int index = 0;
	 
	  for (int ix=0; ix<data.length; ix++)
	  {
	   int value = (data[ix]>255)? -1: codes[ data[ix] ];
	   if ( value >= 0 )
	   {
	    accum <<= 6;
	    shift += 6;
	    accum |= value;
	    if ( shift >= 8 ) 
	    {
	     shift -= 8;
	     out[index++] = (byte) ((accum >> shift) & 0xff);
	    }
	   }
	  }
	 
	  if( index != out.length)
	  {
	   throw new Error("Miscalculated data length (wrote " + index + " instead of " + out.length + ")");
	  }
	  return out;
	 }
	 
	 static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray(); 
	 static private byte[] codes = new byte[256];
	 static{
	  for(int i=0; i<256; i++)
	   codes[i] = -1;
	  for(int i = 'A'; i <= 'Z'; i++)
	   codes[i] = (byte)( i - 'A');
	  for(int i = 'a'; i <= 'z'; i++)
	   codes[i] = (byte)(26 + i - 'a');
	  for(int i = '0'; i <= '9'; i++)
	   codes[i] = (byte)(52 + i - '0');
	  codes['+'] = 62;
	  codes['/'] = 63; 
	 }
}

