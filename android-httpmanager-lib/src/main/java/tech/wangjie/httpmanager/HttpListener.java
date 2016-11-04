package tech.wangjie.httpmanager;

import okhttp3.Response;

/**
 * Created by wangjie on 2016/10/28
 */

public abstract class HttpListener<T> {

    public void onStart() {}

    public void inProgress(float progress, long total) {}

    public void onFail(String error) {}

    public void onFinish() {}

    public abstract void onSuccess(T response);

    public abstract T parseNetworkResponse(Response response) throws Exception;

}