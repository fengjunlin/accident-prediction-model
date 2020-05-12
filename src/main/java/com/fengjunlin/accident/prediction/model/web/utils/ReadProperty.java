package com.fengjunlin.accident.prediction.model.web.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
public class ReadProperty {
    private static final Logger logger = LoggerFactory.getLogger(ReadProperty.class);

    /**
     * 系统配置变量
     */
    private static volatile Map<String, String> confDataMap = null;
    /**
     * 获取系统变量
     *
     * @param key
     * @return
     */
    public static String getConfigData(String key) {

        if (confDataMap != null) {
            return confDataMap.get ( key );
        }
        InputStream sysInputStream = null;
        InputStream devInputStream = null;
        confDataMap = new ConcurrentHashMap<    > ( 16 );
        try {
            sysInputStream = ReadProperty.class.getResourceAsStream ("/application.properties");
            Properties prop = new Properties ( );
            prop.load ( sysInputStream );

            String valueString = prop.getProperty ( "sys.config.file" );
            if ("application.properties".equals ( valueString )) {
                Iterator<String> it = prop.stringPropertyNames ( ).iterator ( );
                String proKeyString;
                while (it.hasNext ( )) {
                    proKeyString = it.next ( );
                    confDataMap.put ( proKeyString, prop.getProperty ( proKeyString ) );
                }
                return confDataMap.get ( key );
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (sysInputStream != null) {
                    sysInputStream.close ( );
                }
                if (devInputStream != null) {
                    devInputStream.close ( );
                }
            } catch (IOException e) {
            }
        }

        return null;
    }

    /**
     * 测试代码
     * @param args
     */
    public static void main(String[] args) {
         String configData = getConfigData ( "esIp" );
        System.out.println (configData );
    }

}
