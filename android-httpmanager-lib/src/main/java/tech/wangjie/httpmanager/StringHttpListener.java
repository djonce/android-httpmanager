package tech.wangjie.httpmanager;

import okhttp3.Response;

/**
 * Created by wangjie on 2016/10/28
 */

public abstract class StringHttpListener extends HttpListener<String> {

    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        return response.body().string();
    }
}
