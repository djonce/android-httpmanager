package tech.wangjie.httpmanager;


import okhttp3.Call;

/**
 * 网络管理器
 *
 * Created by wangjie on 2016/10/27
 */
public abstract class HttpManager {

    public static final String TAG = HttpManager.class.getSimpleName();

    private static HttpManager httpManager;
    private HttpConfig httpConfig;

    public static void initialize(HttpConfig httpConfig) {
        getInstance().setHttpConfig(httpConfig);
    }

    public static HttpManager getInstance() {
        if (httpManager == null) {
            synchronized (HttpManager.class) {
                if (httpManager == null) {
                    httpManager = new OkHttpManagerImpl();
                }
            }
        }
        return httpManager;
    }

    public abstract void enqueue(HttpRequest request, HttpListener callback);

    public abstract void cancel(HttpRequest request);

    public abstract Call newRealCall(HttpRequest request);

    public HttpConfig getHttpConfig() {
        if (httpConfig == null)
            httpConfig = new HttpConfig(null);
        return httpConfig;
    }

    public void setHttpConfig(HttpConfig httpConfig) {
        this.httpConfig = httpConfig;
    }
}
