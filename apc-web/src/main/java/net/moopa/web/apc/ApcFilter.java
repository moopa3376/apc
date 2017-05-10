package net.moopa.web.apc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String,Boolean> url_checks = new HashMap<String, Boolean>();

    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("api-paramter-checker filter start.");

        String urls = filterConfig.getInitParameter("urls");

        //获得所需要的进行校验的url
        for(String url : urls.split(",")){
            lists.add(url);
        }

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
        //获得了request的请求路径
        String path = ((HttpServletRequest)servletRequest).getServletPath();

        //是否需要过滤该请求路径
        Boolean need_check = false;
        if( ((need_check = url_checks.get(path)) != null && need_check )
                ||needFilter(path)){
            //接下来把该请求交给apc-core去处理,并且根据处理结果来进行









        }else {
            filterChain.doFilter(servletRequest,servletResponse);
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

        url_checks.put(path,Boolean.valueOf(result));
        return result;
    }


    public void destroy() {
        logger.info("api-paramter-checker filter closed.");
    }

    public void handlerError(){

    }
}
