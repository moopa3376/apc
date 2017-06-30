package net.moopa.apc.core.service;

import net.moopa.apc.core.ApcCheckResult;
import net.moopa.apc.core.http.HttpRequestMethod;
import org.slf4j.Logger;

import javax.servlet.ServletResponse;

/**
 * Created by Moopa on 29/06/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public interface IApcService {
    public String apiNameDefinetion(String path, HttpRequestMethod httpRequestMethod);

    public void errorHandle(ServletResponse servletResponse, String path, ApcCheckResult apcCheckResult, Logger logger);
}
