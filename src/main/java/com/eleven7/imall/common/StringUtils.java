package com.eleven7.imall.common;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.io.Reader;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Clob;
import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Utility class to peform common String manipulation algorithms.
 */
public class StringUtils {

  public static String killNull(String str) {
    return (str == null) ? "" : str;
  }

  public static double TrimPrefixZero(String str) {
    double value = 0;
    if ( (str == null) || (str.equalsIgnoreCase(""))) {
      return 0;
    }
    char ch = ' ';
    for (int i = 0; i < str.trim().length(); i++) {
      ch = str.charAt(i);
      if (ch == 0)
        continue;
      else
        value = Double.parseDouble(str.substring(i, str.length()));
    }
    return value;
  }

  /** 说明: ISO_8559_1字符转换为GBK字符
   *  @param  需要转换的字符
   *  @return 转换后的GBK字符
   */
  public static String IsoToGBK(String strIn) {
    String strOut = null;
    if (strIn == null)
      return "";
    try {
      byte[] b = strIn.getBytes("ISO8859-1");
      strOut = new String(b, "GBK");
    }
    catch (UnsupportedEncodingException e) {}

    return strOut;
  }

  /** 说明: ISO_8559_1字符转换为GBK字符
   *  @param  需要转换的字符
   *  @return 转换后的GBK字符
   */
  public static String IsoToUTF(String strIn) {
    String strOut = null;
    if (strIn == null)
      return "";
    try {
      byte[] b = strIn.getBytes("ISO8859-1");
      strOut = new String(b, "UTF-8");
    }
    catch (UnsupportedEncodingException e) {}

    return strOut;
  }
  

  /** 说明: GBK字符转换为ISO_8559_1字符
   *  @param  需要转换的GBK字符
   *  @return 转换后的ISO_8559_1字符
   */
  public static String GBKToIso(String strIn) {
    byte[] b;
    String strOut = null;
    if (strIn == null)
      return "";
    try {
      b = strIn.getBytes("GBK");

      strOut = new String(b, "ISO8859-1");

    }
    catch (UnsupportedEncodingException e) {}

    return strOut;
  }
  
  public static String GBKToLatin1(String strIn) {
	    byte[] b;
	    String strOut = null;
	    if (strIn == null)
	      return "";
	    try {
	      b = strIn.getBytes("GBK");

	      strOut = new String(b, "latin1");

	    }
	    catch (UnsupportedEncodingException e) {}

	    return strOut;
	  }

  /**
   * 说明：GBK字符转换为UTF8编码
   * @param strIn 需要转换的GBK字符
   * @return 转换后的UTF8字符
   */
  public static String GBKToUtf8(String strIn) {
    byte[] b;
    String strOut = null;
    if (strIn == null)
      return "";
    try {
      b = strIn.getBytes("GBK");

      strOut = new String(b, "utf8");

    }
    catch (UnsupportedEncodingException e) {}

    return strOut;
  }

  // Constants used by escapeHTMLTags
  private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
  private static final char[] AMP_ENCODE = "&amp;".toCharArray();
  private static final char[] LT_ENCODE = "&lt;".toCharArray();
  private static final char[] GT_ENCODE = "&gt;".toCharArray();
  private static final char[] DD_ENCODE = "&apos;".toCharArray();

  /**
   * Replaces all instances of oldString with newString in line.
   *
   * @param line the String to search to perform replacements on
   * @param oldString the String that should be replaced by newString
   * @param newString the String that will replace all instances of oldString
   *
   * @return a String will all instances of oldString replaced by newString
   */
  public static final String replace(String line, String oldString,
                                     String newString) {
    if (line == null) {
      return null;
    }
    int i = 0;
    if ( (i = line.indexOf(oldString, i)) >= 0) {
      char[] line2 = line.toCharArray();
      char[] newString2 = newString.toCharArray();
      int oLength = oldString.length();
      StringBuffer buf = new StringBuffer(line2.length);
      buf.append(line2, 0, i).append(newString2);
      i += oLength;
      int j = i;
      while ( (i = line.indexOf(oldString, i)) > 0) {
        buf.append(line2, j, i - j).append(newString2);
        i += oLength;
        j = i;
      }
      buf.append(line2, j, line2.length - j);
      return buf.toString();
    }
    return line;
  }

  /**
   * Replaces all instances of oldString with newString in line with the
   * added feature that matches of newString in oldString ignore case.
   *
   * @param line the String to search to perform replacements on
   * @param oldString the String that should be replaced by newString
   * @param newString the String that will replace all instances of oldString
   *
   * @return a String will all instances of oldString replaced by newString
   */
  public static final String replaceIgnoreCase(String line, String oldString,
                                               String newString) {
    if (line == null) {
      return null;
    }
    String lcLine = line.toLowerCase();
    String lcOldString = oldString.toLowerCase();
    int i = 0;
    if ( (i = lcLine.indexOf(lcOldString, i)) >= 0) {
      char[] line2 = line.toCharArray();
      char[] newString2 = newString.toCharArray();
      int oLength = oldString.length();
      StringBuffer buf = new StringBuffer(line2.length);
      buf.append(line2, 0, i).append(newString2);
      i += oLength;
      int j = i;
      while ( (i = lcLine.indexOf(lcOldString, i)) > 0) {
        buf.append(line2, j, i - j).append(newString2);
        i += oLength;
        j = i;
      }
      buf.append(line2, j, line2.length - j);
      return buf.toString();
    }
    return line;
  }

  /**
   * Replaces all instances of oldString with newString in line with the
   * added feature that matches of newString in oldString ignore case.
   * The count paramater is set to the number of replaces performed.
   *
   * @param line the String to search to perform replacements on
   * @param oldString the String that should be replaced by newString
   * @param newString the String that will replace all instances of oldString
   * @param count a value that will be updated with the number of replaces
   *      performed.
   *
   * @return a String will all instances of oldString replaced by newString
   */
  public static final String replaceIgnoreCase(String line, String oldString,
                                               String newString, int[] count) {
    if (line == null) {
      return null;
    }
    String lcLine = line.toLowerCase();
    String lcOldString = oldString.toLowerCase();
    int i = 0;
    if ( (i = lcLine.indexOf(lcOldString, i)) >= 0) {
      int counter = 0;
      char[] line2 = line.toCharArray();
      char[] newString2 = newString.toCharArray();
      int oLength = oldString.length();
      StringBuffer buf = new StringBuffer(line2.length);
      buf.append(line2, 0, i).append(newString2);
      i += oLength;
      int j = i;
      while ( (i = lcLine.indexOf(lcOldString, i)) > 0) {
        counter++;
        buf.append(line2, j, i - j).append(newString2);
        i += oLength;
        j = i;
      }
      buf.append(line2, j, line2.length - j);
      count[0] = counter;
      return buf.toString();
    }
    return line;
  }

  /**
   * Replaces all instances of oldString with newString in line.
   * The count Integer is updated with number of replaces.
   *
   * @param line the String to search to perform replacements on
   * @param oldString the String that should be replaced by newString
   * @param newString the String that will replace all instances of oldString
   *
   * @return a String will all instances of oldString replaced by newString
   */
  public static final String replace(String line, String oldString,
                                     String newString, int[] count) {
    if (line == null) {
      return null;
    }
    int i = 0;
    if ( (i = line.indexOf(oldString, i)) >= 0) {
      int counter = 0;
      counter++;
      char[] line2 = line.toCharArray();
      char[] newString2 = newString.toCharArray();
      int oLength = oldString.length();
      StringBuffer buf = new StringBuffer(line2.length);
      buf.append(line2, 0, i).append(newString2);
      i += oLength;
      int j = i;
      while ( (i = line.indexOf(oldString, i)) > 0) {
        counter++;
        buf.append(line2, j, i - j).append(newString2);
        i += oLength;
        j = i;
      }
      buf.append(line2, j, line2.length - j);
      count[0] = counter;
      return buf.toString();
    }
    return line;
  }

  /**
   * This method takes a string which may contain HTML tags (ie, &lt;b&gt;,
   * &lt;table&gt;, etc) and converts the '&lt'' and '&gt;' characters to
   * their HTML escape sequences.
   *
   * @param in the text to be converted.
   * @return the input string with the characters '&lt;' and '&gt;' replaced
   *  with their HTML escape sequences.
   */
  public static final String escapeHTMLTags(String in) {
    if (in == null) {
      return null;
    }
    char ch;
    int i = 0;
    int last = 0;
    char[] input = in.toCharArray();
    int len = input.length;
    StringBuffer out = new StringBuffer( (int) (len * 1.3));
    for (; i < len; i++) {
      ch = input[i];
      if (ch > '>') {
        continue;
      }
      else if (ch == '<') {
        if (i > last) {
          out.append(input, last, i - last);
        }
        last = i + 1;
        out.append(LT_ENCODE);
      }
      else if (ch == '>') {
        if (i > last) {
          out.append(input, last, i - last);
        }
        last = i + 1;
        out.append(GT_ENCODE);
      }
    }
    if (last == 0) {
      return in;
    }
    if (i > last) {
      out.append(input, last, i - last);
    }
    return out.toString();
  }

  /**
   * Used by the hash method.
   */
  private static MessageDigest digest = null;

  /**
   * Hashes a String using the Md5 algorithm and returns the result as a
   * String of hexadecimal numbers. This method is synchronized to avoid
   * excessive MessageDigest object creation. If calling this method becomes
   * a bottleneck in your code, you may wish to maintain a pool of
   * MessageDigest objects instead of using this method.
   * <p>
   * A hash is a one-way function -- that is, given an
   * input, an output is easily computed. However, given the output, the
   * input is almost impossible to compute. This is useful for passwords
   * since we can store the hash and a hacker will then have a very hard time
   * determining the original password.
   * <p>
   * In Jive, every time a user logs in, we simply
   * take their plain text password, compute the hash, and compare the
   * generated hash to the stored hash. Since it is almost impossible that
   * two passwords will generate the same hash, we know if the user gave us
   * the correct password or not. The only negative to this system is that
   * password recovery is basically impossible. Therefore, a reset password
   * method is used instead.
   *
   * @param data the String to compute the hash of.
   * @return a hashed version of the passed-in String
   */
  public synchronized static final String hash(String data) {
    if (digest == null) {
      try {
        digest = MessageDigest.getInstance("MD5");
      }
      catch (NoSuchAlgorithmException nsae) {
        System.err.println("Failed to load the MD5 MessageDigest. " +
                           "Jive will be unable to function normally.");
        nsae.printStackTrace();
      }
    }
    // Now, compute hash.
    if(data != null)
    	digest.update(data.getBytes());
    return encodeHex(digest.digest());
  }

  /**
   * Turns an array of bytes into a String representing each byte as an
   * unsigned hex number.
   * <p>
   * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
   * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
   * Distributed under LGPL.
   *
   * @param bytes an array of bytes to convert to a hex-string
   * @return generated hex string
   */
  public static final String encodeHex(byte[] bytes) {
    StringBuffer buf = new StringBuffer(bytes.length * 2);
    int i;

    for (i = 0; i < bytes.length; i++) {
      if ( ( (int) bytes[i] & 0xff) < 0x10) {
        buf.append("0");
      }
      buf.append(Long.toString( (int) bytes[i] & 0xff, 16));
    }
    return buf.toString();
  }

  /**
   * Turns a hex encoded string into a byte array. It is specifically meant
   * to "reverse" the toHex(byte[]) method.
   *
   * @param hex a hex encoded String to transform into a byte array.
   * @return a byte array representing the hex String[
   */
  public static final byte[] decodeHex(String hex) {
    char[] chars = hex.toCharArray();
    byte[] bytes = new byte[chars.length / 2];
    int byteCount = 0;
    for (int i = 0; i < chars.length; i += 2) {
      byte newByte = 0x00;
      newByte |= hexCharToByte(chars[i]);
      newByte <<= 4;
      newByte |= hexCharToByte(chars[i + 1]);
      bytes[byteCount] = newByte;
      byteCount++;
    }
    return bytes;
  }

  public static byte[] int2byte(int in[], byte out[]) {
    int inpos = 0;
    int outpos = 0;
    for (; inpos < 4; inpos++) {
      out[outpos++] = (byte) (in[inpos] & 0xff);
      out[outpos++] = (byte) (in[inpos] >>> 8 & 0xff);
      out[outpos++] = (byte) (in[inpos] >>> 16 & 0xff);
      out[outpos++] = (byte) (in[inpos] >>> 24 & 0xff);
    }
    return out;
  }

  public static byte[] int2byte(int in) {
    int inpos = 0;
    int outpos = 0;
    byte[] out = new byte[2];
    for (int i = 0; i < 1; i++) {
      out[outpos++] = (byte) (in & 0xff);
      out[outpos++] = (byte) (in >>> 8 & 0xff);
    }
    return out;
  }

  /**
   * Returns the the byte value of a hexadecmical char (0-f). It's assumed
   * that the hexidecimal chars are lower case as appropriate.
   *
   * @param ch a hexedicmal character (0-f)
   * @return the byte value of the character (0x00-0x0F)
   */
  private static final byte hexCharToByte(char ch) {
    switch (ch) {
      case '0':
        return 0x00;
      case '1':
        return 0x01;
      case '2':
        return 0x02;
      case '3':
        return 0x03;
      case '4':
        return 0x04;
      case '5':
        return 0x05;
      case '6':
        return 0x06;
      case '7':
        return 0x07;
      case '8':
        return 0x08;
      case '9':
        return 0x09;
      case 'a':
        return 0x0A;
      case 'b':
        return 0x0B;
      case 'c':
        return 0x0C;
      case 'd':
        return 0x0D;
      case 'e':
        return 0x0E;
      case 'f':
        return 0x0F;
    }
    return 0x00;
  }

  /**
   * 将整型数值转换为8进制字符编码
   * @param Value
   * @return
   */
  private static final byte IntToHex(int Value) {
    switch (Value) {
      case 0:
        return 0x7f;
      case 1:
        return 0x3f;
      case 2:
        return 0x1f;
      case 3:
        return 0x0f;
      case 4:
        return 0x07;
      case 5:
        return 0x03;
      case 6:
        return 0x01;
      case 7:
        return 0x00;
    }
    return 0x7f;
  }

  //*********************************************************************
   //* Base64 - a simple base64 encoder and decoder.
    //*
     //*     Copyright (c) 1999, Bob Withers - bwit@pobox.com
      //*
       //* This code may be freely used for any purpose, either personal
        //* or commercial, provided the authors copyright notice remains
         //* intact.
          //*********************************************************************

           /**
            * Encodes a String as a base64 String.
            *
            * @param data a String to encode.
            * @return a base64 encoded String.
            */
           public static String encodeBase64(String data) {
             return encodeBase64(data.getBytes());
           }

  /**
   * Encodes a byte array into a base64 String.
   *
   * @param data a byte array to encode.
   * @return a base64 encode String.
   */
  public static String encodeBase64(byte[] data) {
    int c;
    int len = data.length;
    StringBuffer ret = new StringBuffer( ( (len / 3) + 1) * 4);
    for (int i = 0; i < len; ++i) {
      c = (data[i] >> 2) & 0x3f;
      ret.append(cvt.charAt(c));
      c = (data[i] << 4) & 0x3f;
      if (++i < len)
        c |= (data[i] >> 4) & 0x0f;

      ret.append(cvt.charAt(c));
      if (i < len) {
        c = (data[i] << 2) & 0x3f;
        if (++i < len)
          c |= (data[i] >> 6) & 0x03;

        ret.append(cvt.charAt(c));
      }
      else {
        ++i;
        ret.append( (char) fillchar);
      }

      if (i < len) {
        c = data[i] & 0x3f;
        ret.append(cvt.charAt(c));
      }
      else {
        ret.append( (char) fillchar);
      }
    }
    return ret.toString();
  }

  /**
   * Decodes a base64 String.
   *
   * @param data a base64 encoded String to decode.
   * @return the decoded String.
   */
  public static String decodeBase64(String data) {
    return decodeBase64(data.getBytes());
  }

  /**
   * Decodes a base64 aray of bytes.
   *
   * @param data a base64 encode byte array to decode.
   * @return the decoded String.
   */
  public static String decodeBase64(byte[] data) {
    int c, c1;
    int len = data.length;
    StringBuffer ret = new StringBuffer( (len * 3) / 4);
    for (int i = 0; i < len; ++i) {
      c = cvt.indexOf(data[i]);
      ++i;
      c1 = cvt.indexOf(data[i]);
      c = ( (c << 2) | ( (c1 >> 4) & 0x3));
      ret.append( (char) c);
      if (++i < len) {
        c = data[i];
        if (fillchar == c)
          break;

        c = cvt.indexOf( (char) c);
        c1 = ( (c1 << 4) & 0xf0) | ( (c >> 2) & 0xf);
        ret.append( (char) c1);
      }

      if (++i < len) {
        c1 = data[i];
        if (fillchar == c1)
          break;

        c1 = cvt.indexOf( (char) c1);
        c = ( (c << 6) & 0xc0) | c1;
        ret.append( (char) c);
      }
    }
    return ret.toString();
  }

  private static final int fillchar = '=';
  private static final String cvt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
      + "abcdefghijklmnopqrstuvwxyz"
      + "0123456789+/";

  /**
   * Converts a line of text into an array of lower case words using a
   * BreakIterator.wordInstance(). <p>
   *
   * This method is under the Jive Open Source Software License and was
   * written by Mark Imbriaco.
   *
   * @param text a String of text to convert into an array of words
   * @return text broken up into an array of words.
   */
  public static final String[] toLowerCaseWordArray(String text) {
    if (text == null || text.length() == 0) {
      return new String[0];
    }

    ArrayList wordList = new ArrayList();
    BreakIterator boundary = BreakIterator.getWordInstance();
    boundary.setText(text);
    int start = 0;

    for (int end = boundary.next(); end != BreakIterator.DONE;
         start = end, end = boundary.next()) {
      String tmp = text.substring(start, end).trim();
      // Remove characters that are not needed.
      tmp = replace(tmp, "+", "");
      tmp = replace(tmp, "/", "");
      tmp = replace(tmp, "\\", "");
      tmp = replace(tmp, "#", "");
      tmp = replace(tmp, "*", "");
      tmp = replace(tmp, ")", "");
      tmp = replace(tmp, "(", "");
      tmp = replace(tmp, "&", "");
      if (tmp.length() > 0) {
        wordList.add(tmp);
      }
    }
    return (String[]) wordList.toArray(new String[wordList.size()]);
  }

  /**
   * Pseudo-random number generator object for use with randomString().
   * The Random class is not considered to be cryptographically secure, so
   * only use these random Strings for low to medium security applications.
   */
  private static Random randGen = new Random();

  /**
   * Array of numbers and letters of mixed case. Numbers appear in the list
   * twice so that there is a more equal chance that a number will be picked.
   * We can use the array to get a random number or letter by picking a random
   * array index.
   */
  private static char[] numbersAndLetters = (
      "0123456789abcdefghijklmnopqrstuvwxyz" +
      "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

  /**
   * Returns a random String of numbers and letters (lower and upper case)
   * of the specified length. The method uses the Random class that is
   * built-in to Java which is suitable for low to medium grade security uses.
   * This means that the output is only pseudo random, i.e., each number is
   * mathematically generated so is not truly random.<p>
   *
   * The specified length must be at least one. If not, the method will return
   * null.
   *
   * @param length the desired length of the random String to return.
   * @return a random String of numbers and letters of the specified length.
   */
  public static final String randomString(int length) {
    if (length < 1) {
      return null;
    }
    // Create a char buffer to put random letters and numbers in.
    char[] randBuffer = new char[length];
    for (int i = 0; i < randBuffer.length; i++) {
      randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
    }
    return new String(randBuffer);
  }

  /**
   * Intelligently chops a String at a word boundary (whitespace) that occurs
   * at the specified index in the argument or before. However, if there is a
   * newline character before <code>length</code>, the String will be chopped
   * there. If no newline or whitespace is found in <code>string</code> up to
       * the index <code>length</code>, the String will chopped at <code>length</code>.
   * <p>
   * For example, chopAtWord("This is a nice String", 10) will return
   * "This is a" which is the first word boundary less than or equal to 10
   * characters into the original String.
   *
   * @param string the String to chop.
   * @param length the index in <code>string</code> to start looking for a
   *       whitespace boundary at.
   * @return a substring of <code>string</code> whose length is less than or
   *       equal to <code>length</code>, and that is chopped at whitespace.
   */
  public static final String chopAtWord(String string, int length) {
    if (string == null) {
      return string;
    }

    char[] charArray = string.toCharArray();
    int sLength = string.length();
    if (length < sLength) {
      sLength = length;
    }

    // First check if there is a newline character before length; if so,
    // chop word there.
    for (int i = 0; i < sLength - 1; i++) {
      // Windows
      if (charArray[i] == '\r' && charArray[i + 1] == '\n') {
        return string.substring(0, i + 1);
      }
      // Unix
      else if (charArray[i] == '\n') {
        return string.substring(0, i);
      }
    }
    // Also check boundary case of Unix newline
    if (charArray[sLength - 1] == '\n') {
      return string.substring(0, sLength - 1);
    }

    // Done checking for newline, now see if the total string is less than
    // the specified chop point.
    if (string.length() < length) {
      return string;
    }

    // No newline, so chop at the first whitespace.
    for (int i = length - 1; i > 0; i--) {
      if (charArray[i] == ' ') {
        return string.substring(0, i).trim();
      }
    }

    // Did not find word boundary so return original String chopped at
    // specified length.
    return string.substring(0, length);
  }

  /**
   * Escapes all necessary characters in the String so that it can be used
   * in an XML doc.
   *
   * @param string the string to escape.
   * @return the string with appropriate characters escaped.
   */
  public static final String escapeForXML(String string) {
    if (string == null) {
      return null;
    }
    char ch;
    int i = 0;
    int last = 0;
    char[] input = string.toCharArray();
    int len = input.length;
    StringBuffer out = new StringBuffer( (int) (len * 1.3));
    for (; i < len; i++) {
      ch = input[i];
      if (ch > '>') {
        continue;
      }
      else if (ch == '<') {
        if (i > last) {
          out.append(input, last, i - last);
        }
        last = i + 1;
        out.append(LT_ENCODE);
      }
      else if (ch == '&') {
        if (i > last) {
          out.append(input, last, i - last);
        }
        last = i + 1;
        out.append(AMP_ENCODE);
      }
      else if (ch == '"') {
        if (i > last) {
          out.append(input, last, i - last);
        }
        last = i + 1;
        out.append(QUOTE_ENCODE);
      }
    }
    if (last == 0) {
      return string;
    }
    if (i > last) {
      out.append(input, last, i - last);
    }
    return out.toString();
  }

  /**
   * Unescapes the String by converting XML escape sequences back into normal
   * characters.
   *
   * @param string the string to unescape.
   * @return the string with appropriate characters unescaped.
   */
  public static final String unescapeFromXML(String string) {
    string = replace(string, "&lt;", "<");
    string = replace(string, "&gt;", ">");
    string = replace(string, "&quot;", "\"");
    return replace(string, "&amp;", "&");
  }

  private static final char[] zeroArray = "0000000000000000".toCharArray();

  /**
   * Pads the supplied String with 0's to the specified length and returns
   * the result as a new String. For example, if the initial String is
   * "9999" and the desired length is 8, the result would be "00009999".
   * This type of padding is useful for creating numerical values that need
   * to be stored and sorted as character data. Note: the current
   * implementation of this method allows for a maximum <tt>length</tt> of
   * 16.
   *
   * @param string the original String to pad.
   * @param length the desired length of the new padded String.
   * @return a new String padded with the required number of 0's.
   */
  public static final String zeroPadString(String string, int length) {
    if (string == null || string.length() > length) {
      return string;
    }
    StringBuffer buf = new StringBuffer(length);
    buf.append(zeroArray, 0, length - string.length()).append(string);
    return buf.toString();
  }

  /**
   * Formats a Date as a fifteen character long String made up of the Date's
   * padded millisecond value.
   *
   * @return a Date encoded as a String.
   */
  public static final String dateToMillis(Date date) {
    return zeroPadString(Long.toString(date.getTime()), 15);
  }

  /**
   * 隐藏手机号码的中间四位数字
   * @param mobile
   * @return
   */
  public static final String hiddenMobile(String mobile) {
    if (mobile == null) {
      return mobile;
    }
    String start = mobile.substring(0, 3);
    String hidden = "****";
    String end = mobile.substring(7, mobile.length());
    return start + hidden + end;
  }

  /**
   * 说明:统一将字符串变小写
   * @param str
   * @return
   */
  public static final String toLower(String str) {
    return str.toLowerCase();
  }

  /**
   * 说明：生成随机数
   * @param iLength
   * @return
   */
  public static int getRandom(int iLength) {
    java.util.Random rd = new java.util.Random();
    int ran = 0;

    int iMax = 1;
    while (iLength > 1) {
      iMax = iMax * 10;
      iLength--;
    }
    int iRan = iMax * 10 - 1;

    while (ran < iMax) {
      ran = rd.nextInt(iRan);
    }
    return ran;
  }

  /**
   * 说明：生成随机数字符串
   * @param iLength int 生成长度
   * @return String
   */
  public static String getRandomStr(int iLength) {
    return zeroPadString(Integer.toString(getRandom(iLength)),iLength);
  }

  public static String formatNumber(int ai_Number, String as_Pattern) {
    DecimalFormat ldecimalformat_Format;
    String ls_Formatted;

    try {
      ldecimalformat_Format = new DecimalFormat(as_Pattern);
      ls_Formatted = ldecimalformat_Format.format(ai_Number);
    }
    catch (Exception e) {
      System.out.println("CommonUtil.formatNumber(): " + e.getMessage());
      ls_Formatted = Double.toString(ai_Number);
    }

    return ls_Formatted;
  }

  public void tewt() {
    String aa = formatNumber(4, "##.#");
    String bb = formatNumber(10, "##.#");
    double cc = (Double.parseDouble(aa)) / (Double.parseDouble(bb));

    System.out.println("c==" + cc);

  }

  /**
   * 说明：给定长度正整数最小值
   * @param length
   * @return
   */
  public static int getMinPositiveNumberbyGivenLength(int length) {
    if (length <= 0) {
      return 0;
    }
    int min = 1;
    for (int i = 1; i < length; i++) {
      min = min * 10;
    }
    return min;
  }

  /**
   * 说明：得到等长正整数递增字符串
   * @param length
   * @return
   */
  public static String[] getIncrementalStringwithEqualLength(int start,
      int total, int length) {
    int max = StringUtils.getMaxPositiveNumberbyGivenLength(length);
    if (start + total - 1 > max) {
      return null;
    }
    String[] retStrArray = new String[total];
    int min = StringUtils.getMinPositiveNumberbyGivenLength(length);
    for (int i = 0; i < total; i++) {
      retStrArray[i] = Integer.toString(start + i);
    }
    String fillStr = Integer.toString(max + 1).substring(1); //fillStr = 0000...0(length个0)
    int counter = min - start < total ? min - start : total;
    for (int i = 0; i < counter; i++) {
      retStrArray[i] = fillStr.concat(retStrArray[i]);
      int size = retStrArray[i].length();
      retStrArray[i] = retStrArray[i].substring(size - length, size);
    }
    return retStrArray;
  }

  /**
   * 说明：给定长度正整数最大值
   * @param length
   * @return
   */
  public static int getMaxPositiveNumberbyGivenLength(int length) {
    return getMinPositiveNumberbyGivenLength(length) * 10 - 1;
  }

  /**
   * 说明：批量生成随机数
   * @param length
   * @return
   */
  public static String[] getBatchRandomStringwithEqualLength(int length,
      int total) {
    java.util.Random rd = new java.util.Random();
    String ran[] = new String[total];
    int max, seed;
    int min = getMinPositiveNumberbyGivenLength(length);
    if (0 == min) {
      return null;
    }
    max = min * 10 - 1;
    seed = max;
    for (int i = 0; i < total; i++) {
      int randomInt = rd.nextInt(seed + i);
      String randomStr = Integer.toString(randomInt);
      if (randomInt < min) {
        randomStr.concat(randomStr);
        while (randomStr.length() < length) {
          randomStr = randomStr.concat(randomStr);
        }
        randomStr = randomStr.substring(0, length);
      }
      else if (randomInt > max) {
        randomStr = randomStr.substring(0, length);
      }
      ran[i] = randomStr;
    }
    return ran;
  }

  public static final String encodeUpperHex(byte[] bytes) {
    StringBuffer buf = new StringBuffer(bytes.length * 2);
    int i;

    for (i = 0; i < bytes.length; i++) {
      if ( ( (int) bytes[i] & 0xff) < 0x10) {
        buf.append("0");
      }
      buf.append(Long.toString( (int) bytes[i] & 0xff, 16).toUpperCase());
    }
    return buf.toString();
  }

  public static String boundRandom(int bound) {
    String boundrandom = "";
    boundrandom = "" + (690 + (int) (Math.random() * bound));
    return boundrandom;
  }

  public static byte[] Utf8ToGBK(byte[] srcByte,String encoding) throws UTFDataFormatException
   {
       StringBuffer str = new StringBuffer();
       int len = srcByte.length;
       int char1,char2,char3;
       int count = 0;
       while(count<len){
           char1 = (int)srcByte[count]&0xff;
           switch(char1>>4){
               case 0:
               case 1:
               case 2:
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
                   count++;
                   str.append((char)char1);
                   break;
               case 12:
               case 13:
                   count += 2;
                   if(count>len){
                       throw new UTFDataFormatException();
                   }
                   char2 = (int)srcByte[count-1];
                   if((char2&0xC0)!=0x80){
                       throw new UTFDataFormatException();
                   }
                   str.append((char)(((char1&0x1F)<<6)|(char2&0x3F)));
                   break;
               case 14:

                   /* 1110 xxxx  10xx xxxx  10xx xxxx */
                   count += 3;
                   if(count>len){
                       throw new UTFDataFormatException();
                   }
                   char2 = (int)srcByte[count-2];
                   char3 = (int)srcByte[count-1];
                   if(((char2&0xC0)!=0x80)||((char3&0xC0)!=0x80)){
                       throw new UTFDataFormatException();
                   }
                   str.append((char)(((char1&0x0F)<<12)|
                                     ((char2&0x3F)<<6)|
                                     ((char3&0x3F)<<0)));
                   break;
               default:
                   throw new UTFDataFormatException();
           }
       }
       String temp = new String(str);
       try{
           if(encoding==null){
               encoding = "GBK";
           }
           return temp.getBytes(encoding);
       } catch(Exception e){
           return null;
       }
   }
  public static int compareString(String s1, String s2) {
	    try {
	      byte[] ba1 = s1.getBytes();
	      byte[] ba2 = s2.getBytes();
	      int len1 = ba1.length;
	      int len2 = ba2.length;
	      int n = (len1 == len2) ? len1 : Math.min(len1, len2);
	          for (int i = 0; i < n; i++) {
	        if (! (ba1[i] == ba2[i])) {
	          return ba1[i] - ba2[i];
	        }
	      }
	      if (len1 == len2) { //equal
	        return 0;
	      }
	      else
	      if (n == len1) { //less
	        return ba2[n];
	      }
	      else { //greater
	        return ba1[n];
	      }
	    }
	    catch (Exception ex) {
	      //ex.printStackTrace();
	      //equal
	      return 0;
	    }
	  }

  public static String utf8ToStr(String s) {
      String ret = null;
      try {
          ret = java.net.URLDecoder.decode(s, "utf-8");
      } catch (UnsupportedEncodingException ex) {
      }
      return ret;
  }
  
  /**  
   *   CLOB转换为STRING处理  
   *   @author:   testjava  
   */  
 public   static   String   clob2string(Clob   c)    
 {    
	 StringBuffer   sb   =   new   StringBuffer(1024);  
	 Reader   instream   =   null;  
	 try{  
		 instream   =   c.getCharacterStream();  
		 char[]   buffer   =   new   char[(int)c.length()];  
		 int   length   =   0;  
		 while   ((length   =   instream.read(buffer))   !=   -1){  
			 sb.append(buffer);  
		 }  
	 }  
	 catch(Exception   ex){  
		 ex.printStackTrace();  
	 }  
	 finally{  
		 try{  
			 if(instream   !=   null)   instream.close();  
		 }  
		 catch(Exception   dx){  
			 instream   =   null;  
		 }  
		 return   sb.toString();  
	 }  
 }   
 
 
}

