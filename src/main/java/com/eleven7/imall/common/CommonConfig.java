package com.eleven7.imall.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class CommonConfig {
    private static Logger log = LoggerFactory.getLogger(CommonConfig.class);
    private static CompositeConfiguration configuration;
    private static InetAddress addr;

    static {
        try {
            reload();
        } catch (ConfigurationException e) {
            log.warn(e.toString());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static String getCurrentClassPath() {
        String currentClassPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        try {
            return URLDecoder.decode(currentClassPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return currentClassPath;
        }
    }
    
    /**
     * 载入配置 在配置文件夹下的所有配置文件都要重新加载
     *
     * @throws ConfigurationException
     */
    public static void reload() throws ConfigurationException {
        configuration = new CompositeConfiguration();

        try {
            String path = URLDecoder.decode(Thread.currentThread().getContextClassLoader().getResource("").getPath() +
                    "/properties", "UTF-8");
            log.info("The root path is {}", path);

            File file = new File(path);
            File[] properites = file.listFiles();

            Assert.notNull(properites);

            for (File propertyFile : properites) {
                if (propertyFile.getName().endsWith(".properties")) {
                    log.info("Add the file {}", propertyFile.getAbsolutePath());

                    PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration(propertyFile);
                    propertiesConfiguration.setEncoding("UTF-8");
                    configuration.addConfiguration(propertiesConfiguration);
                }
            }
        } catch (UnsupportedEncodingException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * 得到所有的配置文件
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String,String> getAllProperties() {
        Map<String,String> ret = new LinkedHashMap<String,String>();

        Iterator iter = configuration.getKeys();

        while (iter.hasNext()) {
            String key = (String) iter.next();

            if (key.startsWith("datasource.")) {
                continue;
            }

            if (key.startsWith("svn.")) {
                continue;
            }

            if (key.startsWith("hibernate.")) {
                continue;
            }

            if (key.startsWith("SQL.")) {
                continue;
            }

            if (key.contains("username")) {
                continue;
            }

            if (key.contains("password")) {
                continue;
            }

            ret.put(key, configuration.getString(key));
        }

        return ret;
    }

    /**
     * 取得配置项的值
     *
     * @param key 配置项的键
     *
     * @return 配置项的值
     */
    public static String get(String key) {
        return configuration.getString(key);
    }

    /**
     * 取得配置项的值
     *
     * @param key 配置项的键
     * @param defaultValue DOCUMENT ME!
     *
     * @return 配置项的值
     */
    public static int getInt(String key, int defaultValue) {
        String value = configuration.getString(key);

        if (value == null) {
            return defaultValue;
        } else {
            return Integer.parseInt(value.trim());
        }
    }

    /**
     * 取得配置项的值
     *
     * @param key 配置项的键
     * @param defaultValue DOCUMENT ME!
     *
     * @return 配置项的值
     */
    public static long getLong(String key, long defaultValue) {
        String value = configuration.getString(key);

        if (value == null) {
            return defaultValue;
        } else {
            return Long.parseLong(value.trim());
        }
    }
    
    public static boolean isAdmin(String username)
    {
    	String admins = configuration.getString("imall.admin");   	
        String array[] = admins.split(";");
        return Arrays.asList(array).contains(username);
    }
    public static boolean isDebugMode()
    {
    	String debug = configuration.getString("debug");   
    	if(debug.equals("true") || debug.equals("on"))
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    

}
