package tech.wangjie.httpmanager.sample.test;

/**
 *
 * data : {"code":"0","errorMsg":"正常","data":null,"total":null,"success":true}
 *
 * Created by djonce on 2016/10/8.
 */
public class ApiModel<T> implements Model {

    public int code;   // 状态码
    public String msg; // 返回信息

    public T data;

    public boolean isSuccess() {
        return code == 0;
    }

    public boolean hasResult() {
        return isSuccess() && data != null;
    }

    @Override
    public String toString() {
        return "ApiModel{" +
                "code=" + code +
                ", errorMsg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
