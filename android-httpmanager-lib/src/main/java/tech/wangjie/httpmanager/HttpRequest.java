package tech.wangjie.httpmanager;

import android.net.Uri;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;
import tech.wangjie.httpmanager.utils.ClassUtils;
import tech.wangjie.httpmanager.utils.HttpMethods;
import tech.wangjie.httpmanager.utils.ModelParamBuilder;

import static tech.wangjie.httpmanager.HttpManager.TAG;

/**
 * Created by wangjie on 2016/10/27
 */
public abstract class HttpRequest {

    // 构建一个RealRequest (okhttp3.HttpRequest)

    protected String baseUrl;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected Object apiParam;

    protected Request realRequest;
    protected Request.Builder builder = new Request.Builder();


    public HttpRequest(String url) {
        this.baseUrl = url;
        initBaseParam();
    }

    public HttpRequest(Request request) {
        this.realRequest = request;
    }

    // 对象参数
    public HttpRequest(Object apiParam) {
        this.apiParam = apiParam;
        // a. 获取HttpUri、 method、
        String apiUri = ClassUtils.getHttpUri(apiParam);
        HttpMethods method = ClassUtils.getHttpMethod(apiParam);
        String baseURL = ClassUtils.getHttpBaseUrl(apiParam);
        if (baseURL != null) {
            baseUrl = baseURL;
        }
        // b. 获取参数
        baseUrl += apiUri;
        // c. 组装参数
        try {
            params = ModelParamBuilder.buildPrimaryMap(apiParam);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        switch (method) {
            case GET:
                // 处理参数
                baseUrl = appendParams(baseUrl, params);

                builder.get();
                break;

            case POST:

                builder.post(buildRequestBody());
                break;
        }

        Log.e(TAG, baseUrl);

        initBaseParam();
    }

    public HttpRequest(String url, Map<String, String> params, Map<String, String> headers) {
        this.baseUrl = url;
        this.params = params;
        this.headers = headers;
        initBaseParam();
    }

    /**
     * 初始化一些基本参数 baseUrl , tag , headers
     */
    private void initBaseParam() {
        builder.url(baseUrl).tag(tag);
        appendHeaders();

        // 解析参数并组装成ok request
        realRequest = builder.build();

        Log.e(TAG, realRequest.toString());
    }

    public Call getRealCall() {
        return HttpManager.getInstance().newRealCall(this);
    }

    public okhttp3.Request getRealRequest() {
        return realRequest;
    }

    protected abstract RequestBody buildRequestBody();

    protected void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    protected String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }

    public HttpRequest createDefaultRequest() {
        RequestBody requestBody = buildRequestBody();

        return this;
    }
}
