package net.moopa.apc.core.http;

/**
 * Created by Moopa on 10/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public enum HttpRequestMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    CONNECT("CONNECT"),
    HEAD("HEAD");

    private String methodName;

    HttpRequestMethod(String name){
        this.methodName = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public static HttpRequestMethod get(String methodName){
        return valueOf(methodName.toUpperCase());
    }
}
