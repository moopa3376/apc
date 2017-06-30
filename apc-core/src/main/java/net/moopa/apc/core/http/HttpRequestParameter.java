package net.moopa.apc.core.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Moopa on 11/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class HttpRequestParameter {
    /**
     * 每个request参数主要有:
     * 参数名,
     * 参数形式(regex),
     * 参数是否required,
     */
    String paramName = null;
    Pattern pattern = null;
    boolean required = false;


    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
