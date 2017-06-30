package net.moopa.apc.core.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.moopa.apc.core.http.HttpRequestMethod;
import net.moopa.apc.core.http.HttpRequestParameter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Moopa on 10/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class Api {
    /**
     * api抽象:
     * 请求路径,
     * 请求的http方法,
     * 请求参数 -- 参数名,参数pattern,是否必须
     * api描述
     */
    public String requestPath = null;
    public HttpRequestMethod requestMethod = null;
    public Map<String,HttpRequestParameter> params = new HashMap<String, HttpRequestParameter>();
    public String api_desc = null;

    //根据jsonElement来得到相映射的api
    public Api buildByJsonElement(JsonElement jsonElement){
        Api result = null;
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        //获取请求路径
        String resquestPath = jsonObject.get("requestPath").toString();
        //获取http方法
        HttpRequestMethod httpRequestMethod = HttpRequestMethod.get(jsonObject.get("method").toString());
        //获取请求参数
        JsonArray jsonArray = jsonObject.getAsJsonArray("parameters");
        //获取api描述
        String desc = jsonObject.get("desc").toString();
        return result;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public HttpRequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(HttpRequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Map<String, HttpRequestParameter> getParams() {
        return params;
    }

    public void setParams(Map<String, HttpRequestParameter> params) {
        this.params = params;
    }

    public String getApi_desc() {
        return api_desc;
    }

    public void setApi_desc(String api_desc) {
        this.api_desc = api_desc;
    }
}
