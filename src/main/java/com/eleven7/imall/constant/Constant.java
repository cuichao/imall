package com.eleven7.imall.constant;

public class Constant {
	
	public static final String UPLOAD_PIC_PATH = "upload/picture";
	public static final String UPLOAD_VIDEO_PATH = "upload/video";
	public static final String CKEDITOR_UPLOAD_PATH = "upload/ckeditor";
	public static final String UPLOAD_ADVERTISE_PATH = "upload/advertise";
	
	public static final int LOGIN_COUNT_BEFORE_CAPTCHA = 3;
	
	public static final String MD5_PREFIX = "md5_prefix";
	
	public interface PayMethod{
		public static final String directPay = "directPay";
		public static final String bankPay = "bankPay";
		public static final String CREDITCARD = "CREDITCARD";
		public static final String creditPay = "creditPay";
		public static final String cash = "cash";
		
	}
	public static final String PAYTYPE_BUYING_PRODUCT = "1";
	
	public interface ADVERTISE_TYPE{
		public static final int INDEX_BIG = 1;
		public static final int INDEX_SMALL = 2;
		public static final int PLAIN = 3;
	}
	
	public static final long MILLISECONDS_ONE_DAY = 3600 * 24 * 1000;
}
