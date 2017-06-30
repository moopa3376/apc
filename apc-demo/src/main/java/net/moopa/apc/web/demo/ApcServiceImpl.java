package net.moopa.apc.web.demo;

import net.moopa.apc.core.ApcCheckResult;
import net.moopa.apc.core.http.HttpRequestMethod;
import net.moopa.apc.core.service.IApcService;
import org.slf4j.Logger;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Moopa on 29/06/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class ApcServiceImpl implements IApcService {
    public String apiNameDefinetion(String path, HttpRequestMethod httpRequestMethod) {
        return path+"|"+httpRequestMethod.getMethodName();
    }

    public void errorHandle(ServletResponse servletResponse, String path, ApcCheckResult apcCheckResult, Logger logger) {
        ((HttpServletResponse)servletResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        logger.error("Invalid request : {}, error param : {}, error message : {}",path,apcCheckResult.getWrongParamter(),apcCheckResult.getWrongMessage());
    }
}
