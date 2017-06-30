package net.moopa.apc.web.demo;

import net.moopa.apc.core.annotation.ApcHttpMethod;
import net.moopa.apc.core.annotation.ApcHttpParam;
import net.moopa.apc.core.annotation.ApcHttpPath;
import net.moopa.apc.core.http.HttpRequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.nio.channels.Channel;
import java.nio.channels.SelectableChannel;

/**
 * Created by Moopa on 29/06/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
@Controller
public class HelloWorldController {
    @Autowired
    ServletRequest servletRequest;

    @ApcHttpPath(requestPath = "/helloworld")
    @ApcHttpMethod(httpMethod = HttpRequestMethod.GET)
    @RequestMapping(value = "helloworld")
    @ResponseBody
    public String helloworld(){
        return "helloworld";
    }

    @ApcHttpPath(requestPath = "/helloworld1")
    @ApcHttpMethod(httpMethod = HttpRequestMethod.GET)
    @ApcHttpParam(params = "[" +
            "{" +
            "\"key\":\"hello1\"," +
            "\"pattern\":\"\\\\d+\"," +
            "\"required\":\"true\"" +
            "}," +
            "{" +
            "\"key\":\"hello2\"," +
            "\"pattern\":\"\\\\d\"" +
            "}" +
            "]")
    @RequestMapping(value = "helloworld1")
    @ResponseBody
    public String helloworld1(){
        return "helloworld"+servletRequest.getParameter("hello1")+servletRequest.getParameter("hello2");
    }


    public ServletRequest getServletRequest() {
        return servletRequest;
    }

    public void setServletRequest(ServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
}
