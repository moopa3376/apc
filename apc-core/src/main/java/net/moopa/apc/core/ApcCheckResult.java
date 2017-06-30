package net.moopa.apc.core;

/**
 * Created by Moopa on 10/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class ApcCheckResult {

    private boolean passed = false;
    private String wrongParamter = null;
    private ApcError code = null;
    private String wrongMessage = null;

    public ApcCheckResult(boolean passed, String wrongParamter, ApcError apcError, String ms){
        this.passed = passed;
        this.wrongMessage = ms;
        this.wrongParamter = wrongParamter;
        this.code = apcError;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getWrongParamter() {
        return wrongParamter;
    }

    public void setWrongParamter(String wrongParamter) {
        this.wrongParamter = wrongParamter;
    }

    public ApcError getCode() {
        return code;
    }

    public void setCode(ApcError code) {
        this.code = code;
    }

    public String getWrongMessage() {
        return wrongMessage;
    }

    public void setWrongMessage(String wrongMessage) {
        this.wrongMessage = wrongMessage;
    }
}
