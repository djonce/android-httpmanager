package tech.wangjie.httpmanager;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by wangjie on 2016/10/28
 */

public class JsonRequest extends HttpRequest {

    protected MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    public JsonRequest(String url) {
        super(url);
    }

    public JsonRequest(Object apiParam) {
        super(apiParam);
    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mediaType, appendParams(baseUrl, params));
    }

    @Override
    protected void buildRequest(HttpListener callback) {}
}
