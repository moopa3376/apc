package net.moopa.apc.core;

import net.moopa.apc.core.api.Api;
import net.moopa.apc.core.api.ApiInfoMngt;
import net.moopa.apc.core.common.config.ApcConfig;
import net.moopa.apc.core.http.HttpRequestMethod;
import net.moopa.apc.core.http.HttpRequestParameter;
import net.moopa.apc.core.service.IApcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Apc
{
    private static Logger logger = LoggerFactory.getLogger(Apc.class);
    //Apc是否可以成功启动
    public static volatile boolean valid = false;

    public static IApcService apcService = null;

    /**
     * 该方法主要用于
     */
    public static void init(){
        //apcConfig
        if(!ApcConfig.init()){
            return;
        }

        String serviceImlpName = ApcConfig.getValue("apcservice.name");
        try {
            apcService = (IApcService)Class.forName(serviceImlpName).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            logger.error("---------- Apc won't filter any request. -----------");
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            logger.error("---------- Apc won't filter any request. -----------");
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            logger.error("---------- Apc won't filter any request. -----------");
            return;
        }

        ApiInfoMngt.init();

        valid = true;
    }

    public static ApcCheckResult check(ServletRequest servletRequest){
        //获取path和method
        String path = ((HttpServletRequest)servletRequest).getPathInfo();
        String method = ((HttpServletRequest) servletRequest).getMethod();

        //从api缓存中获取相应api
        Api api = ApiInfoMngt.getApi(path,HttpRequestMethod.get(method));
        if(api == null){
            return new ApcCheckResult(true,null,null,null);
        }

        /** 开始对参数进行比较验证 **/
        for(Map.Entry<String,HttpRequestParameter> p : api.getParams().entrySet()){
            String p_value = servletRequest.getParameter(p.getKey());
            //是否存在
            if(p_value == null && p.getValue().isRequired()){
                return new ApcCheckResult(false,p.getKey(),ApcError.PARAM_REQUIRED,ApcError.PARAM_REQUIRED.errorMessage);
            }

            //进行pattern验证
            if(!p.getValue().getPattern().matcher(p_value).matches()){
                return new ApcCheckResult(false,p.getKey(),ApcError.NOT_MATCH,ApcError.NOT_MATCH.errorMessage);
            }
        }

        return new ApcCheckResult(true,null,null,null);
    }
}
