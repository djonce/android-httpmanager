package tech.wangjie.httpmanager;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import tech.wangjie.httpmanager.utils.LoggerInterceptor;

/**
 * Created by wangjie on 2016/10/28
 */

public class OkHttpManagerImpl extends HttpManager {

    private OkHttpClient httpClient;

    public OkHttpManagerImpl() {
        if (httpClient == null) {
            this.httpClient = new OkHttpClient();
        }
    }

    public OkHttpClient newOkHttpClient(HttpConfig httpConfig) {
        if (httpConfig == null) {
            throw new IllegalArgumentException("httpconfig is not null");
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (httpConfig.isDebugged()) {
            builder.addInterceptor(new LoggerInterceptor(TAG));
        }

        builder.connectTimeout(httpConfig.getConnectTimeOut(), TimeUnit.MILLISECONDS);
        builder.readTimeout(httpConfig.getReadTimeOut(), TimeUnit.MILLISECONDS);

        return httpClient = builder.build();
    }

    @Override
    public void enqueue(HttpRequest request, final HttpListener callback) {

        request.buildRequest(callback);
        deliveryStartResult(callback);
        request.getRealCall().enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                deliveryFailResult(e, callback);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                try {
                    if (call.isCanceled()) {
                        deliveryFailResult(new IOException("This request is canceled~"), callback);
                        return;
                    }

                    if (response == null || !response.isSuccessful()) {
                        deliveryFailResult(new IOException("Request failed ~, Response code " + response.code()), callback);
                        return;
                    }

                    Object obj = callback.parseNetworkResponse(response);
                    deliverySuccessResult(obj, callback);
                } catch (Exception e) {
                    deliveryFailResult(new IOException(e.getMessage()), callback);
                } finally {
                    if (response != null && response.body() != null) {
                        response.body().close();
                    }
                }
            }
        });
    }

    @Override
    public void cancel(HttpRequest request) {

        Object tag = request.getTag();
        for (Call call : httpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                Log.e(TAG, " queuedCalls Cancel" );
                call.cancel();
            }
        }

        for (Call call : httpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                Log.e(TAG, " runningCalls Cancel" );
                call.cancel();
            }
        }
    }

    @Override
    public Call newRealCall(HttpRequest request) {
        return httpClient.newCall(request.getRealRequest());
    }

    @Override
    public void setHttpConfig(HttpConfig httpConfig) {
        // 处理网络配置
        newOkHttpClient(httpConfig);
    }

    private void deliveryFailResult(final Exception e, final HttpListener callback) {
        if (callback == null)
            return;
        CallbackDelivery.get().execute(new Runnable() {
            @Override
            public void run() {
                callback.onFail(e.getMessage());
                callback.onFinish();
            }
        });
    }

    private void deliverySuccessResult(final Object obj, final HttpListener callback) {
        if (callback == null)
            return;

        CallbackDelivery.get().execute(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(obj);
                callback.onFinish();
            }
        });
    }

    private void deliveryStartResult(final HttpListener callback) {
        if (callback == null) return ;

        CallbackDelivery.get().execute(new Runnable() {
            @Override
            public void run() {
                callback.onStart();
            }
        });
    }
}
