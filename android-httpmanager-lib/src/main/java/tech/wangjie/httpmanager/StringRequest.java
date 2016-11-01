package tech.wangjie.httpmanager;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by wangjie on 2016/11/1 0001
 */

public class StringRequest extends HttpRequest {

    public StringRequest(String url) {
        super(url);
    }

    public StringRequest(Request request) {
        super(request);
    }

    public StringRequest(Object apiParam) {
        super(apiParam);
    }

    public StringRequest(String url, Map<String, String> params, Map<String, String> headers) {
        super(url, params, headers);
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }
}
