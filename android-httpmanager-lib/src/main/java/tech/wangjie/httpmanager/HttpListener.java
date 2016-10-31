package tech.wangjie.httpmanager;

import okhttp3.Response;

/**
 * Created by wangjie on 2016/10/28
 */

public abstract class HttpListener<T> {

    public void onStart(HttpRequest request) {}

    public void inProgress(float progress, long total) {}

    public void onFail(String error) {}

    public void onFinish() {}

    public abstract void onSuccess(T response);

    public abstract T parseNetworkResponse(Response response) throws Exception;

    public static HttpListener defaultListener = new HttpListener() {
        @Override
        public void onSuccess(Object response) {

        }

        @Override
        public Response parseNetworkResponse(Response response) throws Exception {
            return response;
        }
    };
}