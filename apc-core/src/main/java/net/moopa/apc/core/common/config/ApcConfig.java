package net.moopa.apc.core.common.config;

import net.moopa.apc.core.common.util.PropertiesFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Moopa on 28/06/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class ApcConfig {
    private static Logger logger = LoggerFactory.getLogger(ApcConfig.class);
    private static Map<String,String> configs = new HashMap<String, String>();

    //往ApcConfig中传入配置
    static{
        //是否在收集api时输出信息
        configs.put("output_api","true");
        //是否每次请求时都输出request
        configs.put("output_request","true");
        //是否输出error
        configs.put("output_error","true");
        //不需要过滤的path
        configs.put("exclude_url","");
    }

    public static boolean init(){
        //读取配置文件
        Properties properties = null;
        try {
            properties = PropertiesFileUtil.getProperties("apc.config");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(properties == null){
            logger.error("--------!!! init apcConfig error, api-parameter-checker won't work, no request will be checked. !!!--------");
            return false;
        }else {
            Set<Object> keys = properties.keySet();
            for(Object key : keys){
                configs.put(key.toString(),properties.get(key).toString());
            }
            logger.info("--------init apcConfig successfully--------");
            return true;
        }
    }

    public static String getValue(String key){
        return configs.get(key);
    }
}
