package com.eleven7.imall.common;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 文件操作类
 *
 * @author houxianyou
 * @author houhao
 */
public class FileUtils {
    
	private static final Log logger = LogFactory.getLog(FileUtils.class);
    
    /**
     * 读取文件
     *
     * @param fileName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static String readFile(String fileName,String charset) {
        String readline = null;
        StringBuffer logSb = new StringBuffer(1024);
        BufferedReader buffer = null;

        try {
        	if(charset != null)
               buffer = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),charset));
        	else 
        	   buffer = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

            while ((readline = buffer.readLine()) != null) {
                logSb.append(readline + "\n");
            }
            
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
        	if(buffer != null){
        		try {
        			buffer.close();
				} catch (IOException e) {
					//ignore
				}
        	}
        }

        return logSb.toString();
    }
    
    public static String readFile(InputStream fin) {
        
    	String readline = null;
        StringBuffer logSb = new StringBuffer(1024);

        try {
           
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fin));

            while ((readline = buffer.readLine()) != null) {
                logSb.append(readline + "\n");
            }
            
            fin.close();
        } catch (FileNotFoundException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
        }

        return logSb.toString();
    }
    
    /**
     * 读取文件
     *
     * @param fileName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static String readFile(String fileName) {
        return readFile(fileName,null);
    }
    
    /**
	 * 写文件操作
	 * 
	 * @param msg
	 * @param filePath
	 */
	public static void writeFile(String msg, String filePath) {
		writeFile(msg, filePath, null);
	}

	/**
	 * 写文件操作，如果目标文件存在则删除，支持字符集
	 * 
	 * @param msg
	 * @param filePath
	 * @param charset
	 */
	public static void writeFile(final String msg, final String filePath,
			final String charset) {
		BufferedOutputStream wf = null;
		File file = new File(filePath);
		if (!file.exists() || file.delete()) {
			try {
				wf = new BufferedOutputStream(new FileOutputStream(filePath));
				if (charset == null) {
					wf.write(msg.getBytes());
				} else {
					wf.write(msg.getBytes(charset));
				}
				wf.flush();
			} catch (IOException e) {
				logger.warn("IOException thrown : " + e.getMessage(), e);
			} finally {
				try {
					if (wf != null) {
						wf.close();
					}
				} catch (IOException e) {
					logger.debug("IOException in finally : " + e.getMessage(),
							e);
				}
			}
		}
	}
	//write ,if fail ,then throw a exception
	public static void write(final String msg, final String filePath,
			final String charset) throws Exception{
		BufferedOutputStream wf = null;
		File file = new File(filePath);
		if (!file.exists() || file.delete()) {
			try {
				wf = new BufferedOutputStream(new FileOutputStream(filePath));
				if (charset == null) {
					wf.write(msg.getBytes());
				} else {
					wf.write(msg.getBytes(charset));
				}
				wf.flush();
			} catch (IOException e) {
				logger.warn("IOException thrown : " + e.getMessage(), e);
				throw e;
			} finally {
				try {
					if (wf != null) {
						wf.close();
					}
				} catch (IOException e) {
					logger.debug("IOException in finally : " + e.getMessage(),
							e);
				}
			}
		}
	}
}
