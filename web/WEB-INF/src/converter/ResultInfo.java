package converter;

import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by qudaohan on 2017/7/14.
 */
public class ResultInfo {

    String result;
    String reason;
    Object data;

    public String getResult() {
        return result;
    }

    public String getReason() {
        return reason;
    }

    public Object getData() {
        return data;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
