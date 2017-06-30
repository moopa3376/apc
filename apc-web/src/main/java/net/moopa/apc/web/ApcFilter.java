package net.moopa.apc.web;

import net.moopa.apc.core.Apc;
import net.moopa.apc.core.ApcCheckResult;
import net.moopa.apc.core.common.config.ApcConfig;
import net.moopa.apc.core.http.HttpRequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by Moopa on 10/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class ApcFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(ApcFilter.class);

    private List<String> lists = new ArrayList<String>();

    //此处加上一个Map用来快速判断是否需要进行过滤,相当于一个缓存
    private HashSet<String> ex_urls_set = new HashSet<String>();

    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("api-paramter-checker filter start.");

        String ex_urls = ApcConfig.getValue("exclude_url");
        if(ex_urls == null){
            ex_urls = "";
        }

        //获得所需要的进行校验的url
        for(String url : ex_urls.split(",")){
            ex_urls_set.add(url);
        }

        //初始化apc-core
        Apc.init();
    }

    /**
     * 此处向apc-core来提供core需要的信息,然后根据获得的信息从而进行处理
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //如果Apc没有被配置正确,则无法被正确使用
        if(!Apc.valid){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        /** 开始进行过滤 **/
        //获得了request的请求路径
        String path = ((HttpServletRequest)servletRequest).getPathInfo();
        String method = ((HttpServletRequest) servletRequest).getMethod();

        //是否需要过滤该请求路径
        if(!ex_urls_set.contains(Apc.apcService.apiNameDefinetion(path,HttpRequestMethod.get(method)))){
            /** 接下来把该请求交给apc-core去处理,并且根据处理结果来进行 **/

            ApcCheckResult checkResult = Apc.check(servletRequest);

            if(checkResult.isPassed()){
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }else{
                Apc.apcService.errorHandle(servletResponse,Apc.apcService.apiNameDefinetion(path,HttpRequestMethod.get(method)),checkResult,logger);

                return;
                //logger.error("Invalid request : {}, error param : {}, error message : {}",path,checkResult.getWrongParamter(),checkResult.getWrongMessage());
            }





        }else {
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
    }

    public boolean needFilter(String path){
        boolean result = false;

        for(String url : lists){
            if(path.matches(url)){
                result = true;
                break;
            }
        }

        return result;
    }


    public void destroy() {
        logger.info("api-paramter-checker filter closed.");
    }

    public void handlerError(){

    }

}
