package net.moopa.apc.core;

/**
 * Created by Moopa on 10/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public enum ApcError {
    NOT_MATCH(1,"Not Match Regex."),
    PARAM_REQUIRED(2,"Need Parameter.");

    int code = 0;
    String errorMessage = null;

    ApcError(int c, String m){
        this.code = c;
        this.errorMessage = m;
    }
}
